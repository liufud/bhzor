package ics.ui.controller;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ics.model.Order;
import ics.model.Product;
import ics.model.User;
import ics.services.OrderService;
import ics.services.ProductService;
import ics.services.UserService;

@Controller
public class OrderController {
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="products/placeorder",method=RequestMethod.POST)
	public String placeOrder(@Valid Product product, BindingResult bindingResult,
							Model model, RedirectAttributes attr) {		
		
		if(bindingResult.hasErrors()) {
			System.out.println("data binding unsuccessful");
			attr.addFlashAttribute("org.springframework.validation.BindingResult.order",bindingResult);
			attr.addFlashAttribute("error", "Indicate the quantity you want to order");
			Long productID = product.getProductID();
			return "redirect:" + productID + "/order";
		}
		
		Order order = new Order();
		Product productToBuy = productService.get(product.getProductID());
		
		Integer totalPrice = product.getInventoryLevel()*productToBuy.getCostForSale();
		System.out.println("totalPrice is " + totalPrice + "...................");
		Integer unitPrice = product.getCostForSale();
		Integer quantity = product.getInventoryLevel();
		
		//System.out.println("getting user details............................");
		UserDetails userDetails =
				 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//System.out.println("user details: " + userDetails.toString());
		System.out.println("order placed by " + userDetails.getUsername() + "..........................");	
		User user = userService.findUserByName(userDetails.getUsername());
		//System.out.println(user + "......................");
		
		productToBuy.getOrders().add(order);
		order.setProduct(productToBuy);
		order.setProductName(productToBuy.getProductName());
		order.setCreateByUser(user.getUsername());
		order.setQuantity(quantity);
		order.setUnitPrice(unitPrice);
		order.setTotalPrice(totalPrice);
		
		System.out.println("order and product beans are completed ====================");
		
		Long productID = productToBuy.getProductID();
		productService.buyProduct(quantity, productID);
		System.out.println("finish buying.............................");
		//System.out.println("productID " + productID + "........................................");
/////////////////////////////////////////////////////////////////////////////////////////////	
		//System.out.println(productService.get(productID));
		//order.setProduct(productService.get(productID));		
/////////////////////////////////////////////////////////////////////////////////////////////	
		//System.out.println(order);
		productService.addOrUpdateProduct(productToBuy);
		//orderService.createOrder(order);
		System.out.println("Order is placed...");	
		model.addAttribute("product",productService.get(productID));
		model.addAttribute("productList",productService.listProducts());
		return "redirect:/products";
	}
	
	@RequestMapping(value="products/{productID}/order",method=RequestMethod.GET)
	public String orderBindLink(@PathVariable Long productID, HttpSession session,
								@ModelAttribute("error") String error, RedirectAttributes attr) {
		System.out.println("orderbind is called...");
		Product product = productService.get(productID);
		session.setAttribute("product", product);
		attr.addFlashAttribute("error", error);
		return "redirect:/products/selectedproduct";
	}
	@RequestMapping(value="/products/selectedproduct",method=RequestMethod.GET)
	public String placeOrder(Model model, HttpSession session, 
							@ModelAttribute("error") String error, RedirectAttributes attr) {
		System.out.println("placeOrder is called...");
		Product product = new Product();
		product = (Product) session.getAttribute("product");
		//Collection<Product> productList = productService.listProducts();
		model.addAttribute("product",product);
		//model.addAttribute("productList",productList);
		attr.addFlashAttribute("error", error);
		//session.setAttribute("product", product);
		return "order";
	}
//	
//	public String orderPage() {
//		return 
//	}
	
	@RequestMapping(value="invoice",method=RequestMethod.GET)
	public ModelAndView listOrder() {
		System.out.println("listOrder is called");
		ModelAndView model = new ModelAndView();
		Collection<Order> orderList = orderService.listOrders();
		model.addObject("order",new Order());
		model.addObject("orderList",orderList);
		model.setViewName("invoice");
		
		UserDetails userDetails =
				 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		//System.out.println(username + "=================================");
		try {
			List<Order> orderListBasedOnUser = orderService.getOrderByUsername(username);			
			//System.out.println(orderListBasedOnUser);
			model.addObject("orderListBasedOnUser",orderListBasedOnUser);			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return model;
	}
	
	@RequestMapping(value="order/{orderID}/deleteOrder", method=RequestMethod.GET)
	public String deleteOrder(@PathVariable Long orderID) {
		System.out.println("deleteOrder is called");	
		
		Order order = orderService.getOrder(orderID);
		Product product = order.getProduct();
		Integer inventoryLevel = product.getInventoryLevel();
		Integer orderQty = order.getQuantity();
		product.setInventoryLevel(inventoryLevel + orderQty);

		System.out.println("try to update product after cancelling order ==========");
		productService.addOrUpdateProduct(product);
		System.out.println("successfully updated product after cancelling order ========");
		
		return "redirect:/delete/" + orderID;
	}
	
	@RequestMapping(value="delete/{orderID}", method=RequestMethod.GET)
	public String delete(@PathVariable Long orderID) {
		try {
			orderService.delete(orderID);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/invoice";
	}
	
	@RequestMapping(value="order/{orderID}/editOrder",method=RequestMethod.GET)
	public String editOrder(@PathVariable Long orderID, RedirectAttributes attr) {
		System.out.println("updateOrder is called");
		Order order = orderService.getOrder(orderID);
		System.out.println(order.getQuantity());
		attr.addFlashAttribute("orderUpdate",order);
		attr.addFlashAttribute("update", "Update the Quantity");
		return "redirect:/invoice";
	}
	
	
	@RequestMapping(value="order",method=RequestMethod.POST)
	public String editOrder(@Valid Order order, BindingResult bindingResult, RedirectAttributes attr, HttpSession session) {
		System.out.println("editOrder is called");
		if(bindingResult.hasErrors()) {
			System.out.println("data binding unsuccessful");
			attr.addFlashAttribute("org.springframework.validation.BindingResult.order",bindingResult);
			attr.addFlashAttribute("update", "Update the Quantity");
			attr.addFlashAttribute("error", "Indicate the quantity you want to change to");
			attr.addFlashAttribute("orderUpdate",order);
			return "redirect:/invoice";
		}
		System.out.println(order);
		Integer oldQty = orderService.getOrder(order.getOrderID()).getQuantity();
		Integer newQty = order.getQuantity();
		System.out.println("getting product..........................");
//		if (session != null) {
//		    session.invalidate();
//		}
		Product product = productService.getProductByName(orderService.getOrder(order.getOrderID()).getProductName());
		System.out.println("product gotten............................");
		Integer newInventoryLvl = product.getInventoryLevel() - (newQty - oldQty);
		product.setInventoryLevel(newInventoryLvl);
		Integer totalPrice = order.getUnitPrice() * order.getQuantity();
		order.setTotalPrice(totalPrice);
		System.out.println("updating order................................");
		
		Order originalOrder = orderService.getOrder(order.getOrderID());
		originalOrder.setQuantity(newQty);
		originalOrder.setTotalPrice(totalPrice);
		product.getOrders().add(originalOrder);
		originalOrder.setProduct(product);
		productService.addOrUpdateProduct(product);
		//orderService.createOrder(originalOrder);
		System.out.println("order updated...............................");
		return "redirect:/invoice";
	}
}
