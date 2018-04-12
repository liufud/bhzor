package ics.ui.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import ics.model.BillingInfo;
import ics.model.Cart;
import ics.model.Order;
import ics.model.OrderedProd;
import ics.model.Product;
import ics.model.ReplenishmentOrder;
import ics.model.ShippedOrder;
import ics.model.User;
import ics.services.CartService;
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
	@Autowired
	private CartService	cartService;
	
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
		return "placeOrder";
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
		Product product = productService.getProductByName(orderService.getOrder(order.getOrderID()).getShippedProductName());
		System.out.println("product gotten............................");
		Integer newInventoryLvl = product.getInventoryLevel() - (newQty - oldQty);
		product.setInventoryLevel(newInventoryLvl);
		Integer totalPrice = order.getUnitPrice() * order.getQuantity();
		order.setTotalPrice(totalPrice);
		System.out.println("updating order................................");
		
		Order originalOrder = orderService.getOrder(order.getOrderID());
		originalOrder.setQuantity(newQty);
		originalOrder.setTotalPrice(totalPrice);
		product.getOrderCreator().add(originalOrder);
		originalOrder.setProduct(product);
		productService.addOrUpdateProduct(product);
		//orderService.createOrder(originalOrder);
		System.out.println("order updated...............................");
		return "redirect:/invoice";
	}
	@RequestMapping(value="orders",method=RequestMethod.GET)
	public String showOrder(Model model, HttpSession session, HttpServletRequest request,
							@ModelAttribute("showList")String showList,
							@ModelAttribute("addToCartSucceeded")String addToCartSucceeded,
							@ModelAttribute("showMyCart")String showMyCart,
							@ModelAttribute("showCheckout")String showCheckout,
							@ModelAttribute("user")String user,
							@ModelAttribute("showAddressForm")String showAddressForm,
							@ModelAttribute("thisAddress")String thisAddress,
							@ModelAttribute("orderConfirmation")String orderConfirmation,
							@ModelAttribute("shoppingCartQtyError")String shoppingCartQtyError,
							@ModelAttribute("orderType")String orderType,
							@ModelAttribute("orderID")String orderID,
							@ModelAttribute("shippedOrderForm")String shippedOrderForm,
							@ModelAttribute("shippedQtyError")String shippedQtyError,
							@ModelAttribute("shipFromAnotherLot")String shipFromAnotherLot,
							@ModelAttribute("orderShipped")String orderShipped,
							@ModelAttribute("orderPaid")String orderPaid,
							@ModelAttribute("unselectedBoxError")String unselectedBoxError,
							@ModelAttribute("markAsPaid")String markAsPaid,
							@ModelAttribute("qtyOnShelfExceeded")String qtyOnShelfExceeded,
							@ModelAttribute("lotIDs")ArrayList<String> lotIDs) {
		model.addAttribute("showList", showList);
		model.addAttribute("addToCartSucceeded", addToCartSucceeded);
		if(!addToCartSucceeded.isEmpty()) {
			model.addAttribute("showList", "showList");
			model.addAttribute("listProducts", (ArrayList<Product>) productService.listProducts());
		}
		model.addAttribute("showMyCart", showMyCart);
		model.addAttribute("showCheckout", showCheckout);
		model.addAttribute("showAddressForm", showAddressForm);	
		model.addAttribute("thisAddress", thisAddress);
		model.addAttribute("shoppingCartQtyError", shoppingCartQtyError);
		model.addAttribute("orderID", orderID);
		model.addAttribute("shippedOrderForm", shippedOrderForm);
		model.addAttribute("shippedQtyError", shippedQtyError);
		model.addAttribute("shipFromAnotherLot", shipFromAnotherLot);
		model.addAttribute("orderShipped", orderShipped);
		System.out.println("Shipped order ID: " + orderShipped);
		model.addAttribute("orderPaid", orderPaid);
		model.addAttribute("unselectedBoxError", unselectedBoxError);
		model.addAttribute("qtyOnShelfExceeded", qtyOnShelfExceeded);
		model.addAttribute("lotIDs", lotIDs);
		if(!markAsPaid.isEmpty()) {
			model.addAttribute("markAsPaid", markAsPaid);
			List<Order> unpaid = (List<Order>) orderService.listOrders("paymentStatus","Pending Payment");
			model.addAttribute("viewUnpaidOrders", unpaid);
		}
		if(!orderID.isEmpty()) {
			model.addAttribute("shippedOrder", new ShippedOrder());
			List<OrderedProd> orderedProds = orderService.getOrder(Long.valueOf(orderID)).getProducts();
			List<String> orderedProdNames = new ArrayList<String>();
			for(OrderedProd o:orderedProds) orderedProdNames.add(o.getProductName());
			model.addAttribute("orderedProdNames", orderedProdNames);
			model.addAttribute("orderID", orderID);
			List<Order> unshipped = (List<Order>) orderService.listOrders("shipmentStatus","Pending Shipment");
			model.addAttribute("viewUnshippedOrders", unshipped);
		}
		if(!orderConfirmation.isEmpty()) {
			model.addAttribute("orderConfirmation",orderConfirmation);
			Order confirmedOrder = orderService.getLatestOrder();			
			System.out.println("Confirmed order ID is " + confirmedOrder.getOrderID());
			model.addAttribute("confirmedOrder", confirmedOrder);
		}		
		if(!showCheckout.isEmpty()) {			
			User userWhoPlaceOrder = userService.findUserByName(user);
			model.addAttribute("user", userWhoPlaceOrder);				
			if(!thisAddress.isEmpty()) {
				System.out.println("not using new address");
				BillingInfo billingInfo = new BillingInfo();
				billingInfo.setEmail(userWhoPlaceOrder.getEmail());
				billingInfo.setFirstName(userWhoPlaceOrder.getFirstName());
				billingInfo.setLastName(userWhoPlaceOrder.getLastName());
				billingInfo.setAddress(userWhoPlaceOrder.getAddress());
				billingInfo.setCity(userWhoPlaceOrder.getCity());
				billingInfo.setState(userWhoPlaceOrder.getState());
				billingInfo.setPostalCode(userWhoPlaceOrder.getZip());
				billingInfo.setPhone(userWhoPlaceOrder.getPhoneNumber());
				model.addAttribute("billingInfo", billingInfo);
			}else if(!showAddressForm.isEmpty()){
				System.out.println("using new address");
				model.addAttribute("billingInfo", new BillingInfo());
			}
		}		
		Cart cart = (Cart) session.getAttribute("cart");
		if(null != cart) {
			model.addAttribute("cartTotal", cart.getCartTotal());
		}		
		if(!showList.isEmpty()) {
			model.addAttribute("listProducts", (ArrayList<Product>) productService.listProducts());
		}
		
		//List Unshipped Order and Unpaid Order
		List<Product> listProductNames = (List<Product>) productService.listProducts();
		model.addAttribute("productNames", listProductNames);
		System.out.println("order type is: " + orderType);
		if(orderType.equals("Unshipped Orders")) {
			List<Order> unshipped = (List<Order>) orderService.listOrders("shipmentStatus","Pending Shipment");
			model.addAttribute("viewUnshippedOrders", unshipped);
		}else if(orderType.equals("Unpaid Orders")) {
			List<Order> unpaid = (List<Order>) orderService.listOrders("paymentStatus","Pending Payment");
			model.addAttribute("viewUnpaidOrders", unpaid);
		}else if(orderType.equals("Todos los Pedidos")) {
			List<Order> all = (List<Order>) orderService.listOrders();
			model.addAttribute("viewAllOrders", all);
		}else if(orderType.equals("Shipped Orders")) {
			List<Order> shipped = (List<Order>) orderService.getShippedOrdersByStatus("Cerrado");
//			System.out.println("shipped order" + shipped.get(0).getOrderID() + "===========================");
			model.addAttribute("viewShippedOrders", shipped);
		}
		
		if(null!= request.getQueryString() && request.getQueryString().contains("selectOrderType")) {
			model.addAttribute("selectOrderType", "selectOrderType");
			UserDetails userDetails =
					 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User currentUser = userService.findUserByName(userDetails.getUsername());
			if(null != currentUser) {
				List<Order> myOrders = orderService.getOrderByUserId(currentUser.getUserId());
				model.addAttribute("myOrders", myOrders);
			}			
		}
		
		return "orders";
	}
	
	@RequestMapping(value="{orderID}/orderPaid", method=RequestMethod.GET)
	public String orderPaid(@PathVariable Long orderID, Model model, HttpServletRequest request) {
		model.addAttribute("markAsPaid", orderID.toString());
		return "redirect:/orders?selectOrderType=true"; 
	}
	
	@RequestMapping(value="{orderID}/orderPaid", method=RequestMethod.POST)
	public String orderPaidPost(@PathVariable Long orderID, Model model, HttpServletRequest request) {
		Order order = orderService.getOrder(orderID);
		order.setPaymentStatus("Payment Received"); //Pago Recibido
		order.setShipmentStatus("Pending Shipment"); // Envio Pendiente
		String paymentMethod = request.getParameter("paymentMethod");
		if(null == paymentMethod) order.setPaymentMethod("AwaitPayment");
		else order.setPaymentMethod(paymentMethod);
		DateFormat dtf = new SimpleDateFormat(("yyyy-MM-dd HH:mm:ss"));
		Date date = new Date();
		order.setPaid_At(dtf.format(date));
		orderService.createOrder(order);
		model.addAttribute("orderPaid", "# de Pedido" + order.getOrderID() + " ha sido pagado!");
		model.addAttribute("orderType", "Unpaid Orders");
		return "redirect:/orders?selectOrderType=true"; 
	}
	
	@RequestMapping(value="{orderID}/shippedOrder", method=RequestMethod.GET)
	public String shippedOrderForm(@PathVariable String orderID, Model model,
									@ModelAttribute("shippedQtyError")String shippedQtyError,
									@ModelAttribute("shipFromAnotherLot")String shipFromAnotherLot,
									@ModelAttribute("qtyOnShelfExceeded")String qtyOnShelfExceeded) {
		model.addAttribute("shippedOrderForm", orderID);
		model.addAttribute("orderID", orderID);
		model.addAttribute("shippedQtyError", shippedQtyError);
		model.addAttribute("shipFromAnotherLot", shipFromAnotherLot);
		model.addAttribute("qtyOnShelfExceeded", qtyOnShelfExceeded);
		model.addAttribute("lotIDs", getLotIdArray(orderID));
		return "redirect:/orders?selectOrderType=true";
	}
	
	@RequestMapping(value="shippedOrder", method=RequestMethod.POST)
	public String ShippedOrder(@Valid@ModelAttribute("shippedOrder")ShippedOrder shippedOrder,
								BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			System.out.println("data binding unsuccessful");			
			for(FieldError e:bindingResult.getFieldErrors()) System.out.println(e);
			model.addAttribute("org.springframework.validation.BindingResult.receivedorder",bindingResult);
			model.addAttribute("shippedOrder", shippedOrder);
			model.addAttribute("shippedOrderForm", "shippedOrderForm");
			List<OrderedProd> orderedProds = orderService.getOrder(shippedOrder.getOrderID()).getProducts();
			List<String> orderedProdNames = new ArrayList<String>();
			for(OrderedProd o:orderedProds) orderedProdNames.add(o.getProductName());
			model.addAttribute("orderedProdNames", orderedProdNames);
			model.addAttribute("orderID", shippedOrder.getOrderID());
			List<Order> unshipped = (List<Order>) orderService.listOrders("shipmentStatus","Pending Shipment");
			model.addAttribute("viewUnshippedOrders", unshipped);
			model.addAttribute("selectOrderType", "selectOrderType");
			model.addAttribute("shippedOrderForm", shippedOrder.getOrderID());
			return "orders";
		}
		ShippedOrder shippedOrderToBeSaved = new ShippedOrder();
		UserDetails userDetails =
				 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUserByName(userDetails.getUsername());
		System.out.println("User is " + user.getUsername());
		shippedOrderToBeSaved.setShippedByUser(user);
		shippedOrderToBeSaved.setOrderID(shippedOrder.getOrderID());
		shippedOrderToBeSaved.setShippedProductName(shippedOrder.getShippedProductName());
		shippedOrderToBeSaved.setShelfID(shippedOrder.getShelfID());
		shippedOrderToBeSaved.setTrackingNumber(shippedOrder.getTrackingNumber());
		shippedOrderToBeSaved.setLotID(shippedOrder.getLotID());
		
		Order customerOrder = orderService.getOrder(shippedOrder.getOrderID());
		//Checking the quantity received
		List<OrderedProd> orderedProducts = customerOrder.getProducts();
		List<ShippedOrder> shippedOrdersByShelf = orderService.getShippedOrderByShelf(shippedOrder.getOrderID() ,shippedOrder.getShelfID(), shippedOrder.getShippedProductName());
		for(OrderedProd o:orderedProducts) {
			if(o.getProductName().equals(shippedOrder.getShippedProductName())) {
				//check if quantity on shelf exceeded
				boolean qtytyOnShelfNotExceeded = qtyOnShelfExceeded(shippedOrder.getShippedProductName(), shippedOrder.getShelfID(), shippedOrder.getQtyShipped());
				if(qtytyOnShelfNotExceeded) {
					model.addAttribute("qtyOnShelfExceeded", "La cantidad excede la cantidad disponible en este estante!");//La cantidad excede la cantidad disponible en este estante
					return "redirect:/" + customerOrder.getOrderID() + "/shippedOrder";
				}
				//if qty received is greater than quantity ordered, throw error
				if(shippedOrder.getQtyShipped() > o.getOrderedProductQty()) {
					System.out.println("received order qty greater than ordered qty, error!");
					model.addAttribute("shippedQtyError", "Cantidad Enviada no puede ser mayor a la cantidad Pedida!");
					return "redirect:/" + customerOrder.getOrderID() + "/shippedOrder";
				}
				//if qty received is less than quantity ordered, ask user if he/she wants to save it to another lot
				else if(shippedOrder.getQtyShipped() < o.getOrderedProductQty()) {
					Collection<ShippedOrder> shippedOrders = orderService.getShippedOrderByID(shippedOrder.getOrderID(), shippedOrder.getShippedProductName());
					Integer quantityShipped = new Integer(0);
					if(null != shippedOrders) {
						for(ShippedOrder sOrder:shippedOrders) quantityShipped+=sOrder.getQtyShipped();
					}
					System.out.println("Total qty shipped of product " + shippedOrder.getShippedProductName() + " so far is " + quantityShipped);
					//check if the added quantity exceeds ordered quantity
					if(quantityShipped + shippedOrder.getQtyShipped() > o.getOrderedProductQty()) {
						model.addAttribute("shippedQtyError", "Cantidad Enviada no puede ser mayor a la cantidad Pedida!");
						return "redirect:/" + customerOrder.getOrderID() + "/shippedOrder";
					}else if(quantityShipped + shippedOrder.getQtyShipped() < o.getOrderedProductQty()){
						model.addAttribute("shipFromAnotherLot", customerOrder.getOrderID());
						if(null != shippedOrdersByShelf) {
							System.out.println("shipped qty less than ordered qty -----------  shipped product exist in the lot");
							shippedOrdersByShelf.get(0).setQtyShipped((shippedOrdersByShelf.get(0).getQtyShipped() + shippedOrder.getQtyShipped()));							
							shipProducts(shippedOrder.getShippedProductName(), shippedOrder.getQtyShipped());
							orderService.createShippedOrder(shippedOrdersByShelf.get(0));
						}else {
							System.out.println("received qty less than ordered qty -----------  received product does NOT exist in the lot");
							shippedOrderToBeSaved.setQtyShipped(shippedOrder.getQtyShipped());							
							shipProducts(shippedOrder.getShippedProductName(), shippedOrder.getQtyShipped());
							orderService.createShippedOrder(shippedOrderToBeSaved);
						}
						o.setUnshippedProductqty(o.getUnshippedProductqty()-shippedOrder.getQtyShipped());
						customerOrder.setProducts(orderedProducts);
						System.out.println("saving customer ordered products -----");
						orderService.createOrder(customerOrder);
						System.out.println("order saved ----");
						
						System.out.println("Shipped " + shippedOrder.getQtyShipped() + " of " + shippedOrder.getShippedProductName());
						return "redirect:/" + customerOrder.getOrderID() + "/shippedOrder";
					}else if(quantityShipped + shippedOrder.getQtyShipped() == o.getOrderedProductQty()){					
						System.out.println("Shipped qty EQUAL to ordered qty -----------  received product exist in the lot");
						if(null != shippedOrdersByShelf) {
							shippedOrdersByShelf.get(0).setQtyShipped((shippedOrdersByShelf.get(0).getQtyShipped() + shippedOrder.getQtyShipped()));
							shipProducts(shippedOrder.getShippedProductName(), shippedOrder.getQtyShipped());
							orderService.createShippedOrder(shippedOrdersByShelf.get(0));
						}else {
							shippedOrderToBeSaved.setQtyShipped(shippedOrder.getQtyShipped());							
							shipProducts(shippedOrder.getShippedProductName(), shippedOrder.getQtyShipped());
							orderService.createShippedOrder(shippedOrderToBeSaved);
						};
						o.setUnshippedProductqty(o.getUnshippedProductqty()-shippedOrder.getQtyShipped());
						customerOrder.setProducts(orderedProducts);
						System.out.println("saving customer ordered products -----");
						orderService.createOrder(customerOrder);
						System.out.println("order saved ----");
						
						System.out.println("Shipped all the quantity for product [" + shippedOrder.getShippedProductName() + "]" );
						
					}
					
					
				}
				//if qty received is equal to the ordered qty, close the replenishment order
				else if(shippedOrder.getQtyShipped() == o.getOrderedProductQty()){
					Collection<ShippedOrder> shippedOrders = orderService.getShippedOrderByID(shippedOrder.getOrderID(), shippedOrder.getShippedProductName());
					Integer quantityShipped = new Integer(0);
					if(null != shippedOrders) {
						for(ShippedOrder sOrder:shippedOrders) quantityShipped+=sOrder.getQtyShipped();
					}
					if(quantityShipped + shippedOrder.getQtyShipped() > o.getOrderedProductQty()) {
						model.addAttribute("shippedQtyError", "Cantidad Enviada no puede ser mayor a la cantidad Pedida!");
						return "redirect:/" + customerOrder.getOrderID() + "/shippedOrder";
					}
					if(null != shippedOrders) {
						for(ShippedOrder sOrder:shippedOrders) quantityShipped+=sOrder.getQtyShipped();
					}
					if(null != shippedOrdersByShelf) {
						shippedOrdersByShelf.get(0).setQtyShipped((shippedOrdersByShelf.get(0).getQtyShipped() + shippedOrder.getQtyShipped()));
						shipProducts(shippedOrder.getShippedProductName(), shippedOrder.getQtyShipped());
						orderService.createShippedOrder(shippedOrdersByShelf.get(0));
					}else {
						shippedOrderToBeSaved.setQtyShipped(shippedOrder.getQtyShipped());							
						shipProducts(shippedOrder.getShippedProductName(), shippedOrder.getQtyShipped());
						orderService.createShippedOrder(shippedOrderToBeSaved);
					}
					o.setUnshippedProductqty(o.getUnshippedProductqty()-shippedOrder.getQtyShipped());
					customerOrder.setProducts(orderedProducts);
					System.out.println("saving customer ordered products -----");
					orderService.createOrder(customerOrder);
					System.out.println("order saved ----");
					
					System.out.println("Received all the quantity for product [" + shippedOrder.getQtyShipped() + "]" );
				}
			}
		}
		List<OrderedProd> orderedProds = orderService.getOrder(customerOrder.getOrderID()).getProducts();
		
		System.out.println("checking if order is closed");
		boolean closeOrder = true;
		for(OrderedProd o:orderedProds) {
			if(o.getUnshippedProductqty() > 0) {
				System.out.println("unshipped qty is greater than zero, cannot close order");
				closeOrder = false;
			}
		}
		if(closeOrder  == true) {	
			customerOrder.setOrderStatus("Cerrado");
			customerOrder.setShipmentStatus("Enviado");
			DateFormat dtf = new SimpleDateFormat(("yyyy-MM-dd HH:mm:ss"));
			Date date = new Date();
			customerOrder.setShipped_At(dtf.format(date));
			orderService.createOrder(customerOrder);
			model.addAttribute("orderShipped", String.valueOf(customerOrder.getOrderID()));
			System.out.println("order closed");
			model.addAttribute("orderType", "Unshipped Orders");
		}
		System.out.println("check complete");
		
		return "redirect:/orders?selectOrderType=true";
	}
	
	private ArrayList<String> getLotIdArray(String orderID){
		Order order = orderService.getOrder(Long.valueOf(orderID));
		List<OrderedProd> orderedProds = order.getProducts();
		ArrayList<String> lotIDArray = new ArrayList<String>();
		for(OrderedProd o:orderedProds) {
			Product product = productService.getProductByName(o.getProductName());
			if(o.getOrderedProductQty() > 0) {
				for(Long lotID:product.getLotID()) {
					System.out.println("Lot ID is " + lotID.toString());
					lotIDArray.add(lotID.toString());
				}
			}			
		}
		return lotIDArray;
	}
	
	private boolean qtyOnShelfExceeded(String productName, Long formShelfID, Integer qtyShipped) {
		boolean productQtyExceeded = true;
		int qtyOnShelf = orderService.qtyInInventoryByShelf(productName, formShelfID);
		if(qtyOnShelf > qtyShipped.intValue()) {
			productQtyExceeded = false;
		}
		return productQtyExceeded;
	}
	
	private void shipProducts(String productName, Integer quantity) {
		Product product = productService.getProductByName(productName);
		product.setQuantity(product.getQuantity() - quantity);
		productService.addOrUpdateProduct(product);
	}
	
	@ModelAttribute("allVendors")
	public List<User> getAllVendors(Model model) {
		List<User> allVendors = userService.getUsersByRole("Vendedor");
		model.addAttribute("allVendors", allVendors);
		return allVendors;
	}
	
	@ModelAttribute("allDistributors")
	public List<User> getAllDistributors(Model model){
		List<User> allDistributors = userService.getUsersByRole("Distribuidor");
		model.addAttribute("allDistributors", allDistributors);
		return allDistributors;
	}
	
	@ModelAttribute("allCustomers")
	public List<User> getAllCustomers(Model model){
		List<User> allCustomers = userService.getUsersByRole("Cliente");
		model.addAttribute("allCustomers", allCustomers);
		return allCustomers;
	}
	
	@RequestMapping(value="orderType",method=RequestMethod.POST)
	public String orderType(@RequestParam("orderTypeName")String orderTypeName,Model model) {
		model.addAttribute("orderType", orderTypeName);
		return "redirect:/orders?selectOrderType=true";
	}
	
	@ModelAttribute("productList")
    public List<String> getProductList() {
		ArrayList<Product> products = (ArrayList<Product>) productService.listProducts();
	    List<String> productList = new ArrayList<String>();
		for(Product p : products) productList.add(p.getProductName());
	   return productList;
    }
	
	//////////////////////////////////////////////////////////
	@RequestMapping(value="listProducts",method=RequestMethod.GET)
	public String placeOrder(Model model,
							@ModelAttribute("shoppingCartQtyError")String shoppingCartQtyError) {		
		model.addAttribute("showList", "showList");
		model.addAttribute("shoppingCartQtyError", shoppingCartQtyError);
		return "redirect:/orders";
	}
	
//	@RequestMapping(value="{productID}/addToCart",method=RequestMethod.GET)
//	public String addToCart(@PathVariable("productID")Long productID, HttpSession session,
//							Model model) {
//		
//		if(session.getAttribute("cart") == null) {
//			System.out.println("Cart doesn't exist, create a new one");
//			Cart cart = new Cart();
//			List<Product> products = new ArrayList<Product>();
//			Product product = productService.get(productID);
//			products.add(product);
//			cart.setProducts(products);
//			model.addAttribute("addToCartSucceeded", "You have added " + product.getProductName() + " to My Cart");
//			UserDetails userDetails =
//					 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//			cart.setUser(userService.findUserByName(userDetails.getUsername()));			
//			Double cartTotal = cartTotal(products, cart);
//			cart.setCartTotal(cartTotal);
//			session.setAttribute("cart", cart);
//			cartService.addOrUpdateCart(cart);
//		}else {
//			System.out.println("Cart exists");
//			Cart cart = (Cart) session.getAttribute("cart");
//			List<Product> products = cart.getProducts();
//			
//			//use method isExisting
//			System.out.println("checking if product added to cart exists in cart");
//			int index = isExisting(productID, session);
//			System.out.println("isExisting method gives index " + index);
//			if(index == -1) {
//				System.out.println("product doesn't exist in cart");
//				Product product = productService.get(productID);
//				products.add(product);
//				cart.setProducts(products);
//				model.addAttribute("addToCartSucceeded", "You have added " + product.getProductName() + " to My Cart");
//			}else {
//				System.out.println("product exists in cart");
//				for(Product p:products)System.out.println(p.getProductName());
//				if(null == products.get(index).getQuantity()) {
//					System.out.println("product qty is null, setting it to 0");
//					products.get(index).setQuantity(0);
//					System.out.println("adding product with qty 0 back to products");				
//				}
//				System.out.println("updating product qty");
//				Integer quantity = products.get(index).getQuantity()+1;
//				products.get(index).setQuantity(quantity);
//				System.out.println("product qty updated");
//				cart.setProducts(products);
//				Product product = products.get(index);
//				model.addAttribute("addToCartSucceeded", "You have added " + product.getProductName() + " to My Cart");
//			}
//			Double cartTotal = cartTotal(products, cart);
//			cart.setCartTotal(cartTotal);
//			session.setAttribute("cart", cart);
//			Cart newCart = cartService.get(cart.getCartId());			
//			cartService.addOrUpdateCart(newCart);
//		}
//		return "redirect:/orders";
//	}
//	
	@RequestMapping(value="{productID}/addToCart",method=RequestMethod.POST)
	public String addToCart(@PathVariable("productID")Long productID, HttpSession session,
							Model model, HttpServletRequest request) {
		
		if(session.getAttribute("cart") == null) {
			System.out.println("Cart doesn't exist, create a new one");
			Cart cart = new Cart();
			List<OrderedProd> products = new ArrayList<OrderedProd>();			
			Product productInDB = productService.get(productID);
			OrderedProd product = new OrderedProd();
			product.setProductName(productInDB.getProductName());
			product.setProductID(productInDB.getProductID());
//			product.setPrice(productInDB.getPrice());
			product.setCost(productInDB.getCost());
			products.add(product);
			String[] quantity = request.getParameterValues("quantity");
			try {
				Integer.parseInt(quantity[0]);
				if(Integer.parseInt(quantity[0]) <= 0) {
					model.addAttribute("shoppingCartQtyError", "Cantidad de Pedido no puede ser 0!");
					return "redirect:/listProducts";
				}else if(Integer.parseInt(quantity[0]) > 10000000) {
					model.addAttribute("shoppingCartQtyError", "Cantidad Invalida!");
					return "redirect:/listProducts";
				}else {
					for (int i = 0; i < products.size(); i++) {
						products.get(i).setOrderedProductQty(Integer.parseInt(quantity[0]));
					 }
				}	
			} catch (Exception e) {
				System.out.println(e);
				model.addAttribute("shoppingCartQtyError", "Cantidad Invalida!");
				return "redirect:/listProducts";
			}
					
			cart.setProducts(products);
			model.addAttribute("addToCartSucceeded", "Ha Agregado " + product.getOrderedProductQty() + " [" + product.getProductName() + "] a su Pedido");
			UserDetails userDetails =
					 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			cart.setUser(userService.findUserByName(userDetails.getUsername()));			
			Double cartTotal = cartTotal(products, cart);
			cart.setCartTotal(cartTotal);
			session.setAttribute("cart", cart);
			cartService.addOrUpdateCart(cart);
		}else {
			System.out.println("Cart exists");
			Cart cart = (Cart) session.getAttribute("cart");
			List<OrderedProd> products = cart.getProducts();
			Double cartTotal = (double) 0;
			
			//use method isExisting
			System.out.println("checking if product added to cart exists in cart");
			int index = isExisting(productID, session);
			System.out.println("isExisting method gives index " + index);
			if(index == -1) {
				System.out.println("product doesn't exist in cart");
				Product productInDB = productService.get(productID);
				OrderedProd product = new OrderedProd();
				product.setProductName(productInDB.getProductName());
				product.setProductID(productInDB.getProductID());
//				product.setPrice(productInDB.getPrice());
				product.setCost(productInDB.getCost());
				String[] quantity = request.getParameterValues("quantity");
				
				try {
					Integer.parseInt(quantity[0]);
					if(Integer.parseInt(quantity[0]) <= 0) {
						model.addAttribute("shoppingCartQtyError", "Cantidad de Pedido no puede ser 0!");
						return "redirect:/listProducts";
					}else if(Integer.parseInt(quantity[0]) > 10000000) {
						model.addAttribute("shoppingCartQtyError", "Cantidad Invalida!");
						return "redirect:/listProducts";
					}else {
						product.setOrderedProductQty(Integer.parseInt(quantity[0]));
						products.add(product);
					}	
				} catch (Exception e) {
					System.out.println(e);
					model.addAttribute("shoppingCartQtyError", "Cantidad Invalida!");
					return "redirect:/listProducts";
				}			
				cart.setProducts(products);
				cartTotal = cartTotal(products, cart);
				model.addAttribute("addToCartSucceeded", "Ha Agregado " + product.getOrderedProductQty() + " [" + product.getProductName() + "] a su Pedido");
			}else {
				System.out.println("product exists in cart");
				for(OrderedProd p:products)System.out.println(p.getProductName());
				System.out.println("updating product qty");
				String[] quantityParam = request.getParameterValues("quantity");
				
				try {
					Integer.parseInt(quantityParam[0]);
					if(Integer.parseInt(quantityParam[0]) <= 0) {
						model.addAttribute("shoppingCartQtyError", "Cantidad de Pedido no puede ser 0!");
						return "redirect:/listProducts";
					}else if(Integer.parseInt(quantityParam[0]) > 10000000) {
						model.addAttribute("shoppingCartQtyError", "Cantidad Invalida!");
						return "redirect:/listProducts";
					}else {
						Integer quantity = products.get(index).getOrderedProductQty()+Integer.parseInt(quantityParam[0]);
						products.get(index).setOrderedProductQty(quantity);
					}	
				} catch (Exception e) {
					System.out.println(e);
					model.addAttribute("shoppingCartQtyError", "ICantidad Invalida!");
					return "redirect:/listProducts";
				}	
				System.out.println("product qty updated");
				cart.setProducts(products);
				cartTotal = cartTotal(products, cart);
				OrderedProd product = products.get(index);
				model.addAttribute("addToCartSucceeded", "Ha Agregado " + product.getOrderedProductQty() + " [" + product.getProductName() + "] a su Pedido");
			}
			
			cart.setCartTotal(cartTotal);
			session.setAttribute("cart", cart);
			Cart newCart = cartService.get(cart.getCartId());			
			cartService.addOrUpdateCart(newCart);
		}
		return "redirect:/orders";
	}
	
	 @SuppressWarnings("unchecked")
	 private int isExisting(Long productID, HttpSession session) {
		 Cart cart = (Cart) session.getAttribute("cart");
		 List<OrderedProd> products = cart.getProducts();
	
		  for (int i = 0; i < products.size(); i++)
	
		   if (products.get(i).getProductID() == productID)
		    return i;
	
		  return -1;
	 }
	 
	 private Double cartTotal(List<OrderedProd> products, Cart cart) {
		Double cartTotal = (double) 0;
		List<OrderedProd> p = cart.getProducts();
		for(OrderedProd a:p) {
//			cartTotal+=a.getPrice()*a.getOrderedProductQty();
		}
		return cartTotal;
	 }
	 
	@ModelAttribute("productsInCart")
    public List<OrderedProd> productInCart(Model model, HttpSession session) {
		Cart cart = (Cart) session.getAttribute("cart");
		if(null != cart) {
			List<OrderedProd> products = cart.getProducts();
			return products;
		}
		return null;
    }
	 
	 @RequestMapping(value="myCart",method=RequestMethod.GET)
	 public String myCart(Model model, HttpSession session,
			 			@ModelAttribute("shoppingCartQtyError")String shoppingCartQtyError) {
		 model.addAttribute("showMyCart", "showMyCart");
		 model.addAttribute("shoppingCartQtyError", shoppingCartQtyError);
		 return "redirect:/orders";
	 }
	 
	 @RequestMapping(value="updateCart",method=RequestMethod.POST)
	 public String updateCart(HttpServletRequest request, HttpSession session, Model model) {
		 Cart cart = (Cart) session.getAttribute("cart");
		 List<OrderedProd> products = cart.getProducts();
		 String[] quantity = request.getParameterValues("quantity");
		 for (int i = 0; i < products.size(); i++) {
			 try {
					if(Integer.parseInt(quantity[i]) <= 0) {
						model.addAttribute("shoppingCartQtyError", "Quantity has to be greater than zero!");
						return "redirect:/myCart";
					}else if(Integer.parseInt(quantity[i]) > 10000000) {
						model.addAttribute("shoppingCartQtyError", "Invalid quantity!");
						return "redirect:/myCart";
					}else {
						products.get(i).setOrderedProductQty(Integer.parseInt(quantity[i]));
					}
				} catch (Exception e) {
					System.out.println(e);
					model.addAttribute("shoppingCartQtyError", "Invalid quantity!");
					return "redirect:/myCart";
				}				
		 }
		 cart.setProducts(products);
		 Double cartTotal = cartTotal(products, cart);
		 cart.setCartTotal(cartTotal);
		 session.setAttribute("cart", cart);
		 Cart newCart = cartService.get(cart.getCartId());			
		 cartService.addOrUpdateCart(newCart);
		 return "redirect:/myCart";
	 }
	 
	 @RequestMapping(value= {"{productID}/deleteProductInCart","{productID}/deleteProductInCheckout","{productID}/deleteProductInRpCart"},method=RequestMethod.GET)
	 public String deleteProductInCart(@PathVariable(value = "productID")Long productID, Model model,
			 						HttpServletRequest request,HttpSession session) {
		 System.out.println("deleting product from cart");
		 Cart cart = (Cart) session.getAttribute("cart");
		 List<OrderedProd> products = cart.getProducts();
		 int index = 0;
		 for(int i=0; i<products.size(); i++) {
			 if(products.get(i).getProductID() == productID) {
				 index = i;
			 }			 
		 }
		 products.remove(index);
		 cart.setProducts(products);
		 Double cartTotal = cartTotal(products, cart);
		 cart.setCartTotal(cartTotal);
		 session.setAttribute("cart", cart);
		 Cart newCart = cartService.get(cart.getCartId());			
		 cartService.addOrUpdateCart(newCart);
		 if(request.getRequestURI().contains("deleteProductInCheckout")) {
			 model.addAttribute("showCheckout", "showCheckout");
			 return "redirect:/checkout";
		 }
		 if(request.getRequestURI().contains("deleteProductInRpCart")) {
			 return "redirect:/orderSummary";
		 }
		 return "redirect:/myCart";
	 }
	 
	 @ModelAttribute("paymentMethod")
	    public List<String> getPaymentMethod() {
			List<String> paymentMethods = new ArrayList<String>();
			paymentMethods.add("Cash");
			paymentMethods.add("Direct Deposit");
			paymentMethods.add("Credit");
		   return paymentMethods;
	    }
	 
	 @RequestMapping(value="checkout",method=RequestMethod.GET)
	 public String checkout(Model model,
			 				@ModelAttribute("showAddressForm")String showAddressForm,
			 				@ModelAttribute("thisAddress")String thisAddress) {
		 model.addAttribute("showCheckout", "showCheckout");
		 UserDetails userDetails =
				 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 model.addAttribute("user", userDetails.getUsername());
		 model.addAttribute("showAddressForm", showAddressForm);
		 model.addAttribute("thisAddress", thisAddress);
		 return "redirect:/orders";
	 }
	 
	 @RequestMapping(value="placeOrder",method=RequestMethod.POST)
	 public String placeOrder(/*@Valid@ModelAttribute("billingInfo") BillingInfo billingInfo,*/
								/*BindingResult bindingResult,*/
								Model model,
								HttpSession session, @RequestParam("paymentMethod")String paymentMethod,
			 					@RequestParam(value = "paymentStatus", required = false)String paymentStatus,
			 					HttpServletRequest request) {
		 System.out.println("Placing order");
//		 System.out.println(bindingResult);
		 /*if(bindingResult.hasErrors()) {
				System.out.println("placeOrder data binding unsuccessful");
				UserDetails userDetails =
						 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				User user = userService.findUserByName(userDetails.getUsername());
				model.addAttribute("org.springframework.validation.BindingResult.billingInfo",bindingResult);
				model.addAttribute("showCheckout", "showCheckout");				
				model.addAttribute("showAddressForm", "showAddressForm");
				model.addAttribute("billingInfo", billingInfo);			
				model.addAttribute("user", user);
				return "orders";
		 }*/
		 
		 
		 Cart cart = (Cart) session.getAttribute("cart");		 
		 List<OrderedProd> productInCart = cart.getProducts();
		 List<OrderedProd> productToBeSavedInOrder = new ArrayList<OrderedProd>();
		 List<Product> productList = (ArrayList<Product>) productService.listProducts();
		 Order order = new Order();
		 for(int i=0; i<productList.size(); i++) {
			 Product productInDB = productService.get(productList.get(i).getProductID());
			 OrderedProd product = new OrderedProd();
			 product.setProductName(productList.get(i).getProductName());
			 product.setCost(productList.get(i).getCost());
//			 product.setPrice(productList.get(i).getPrice());
			 product.setQuantity(productInDB.getQuantity());
			 product.setOrderedProductQty(0);
			 product.setUnshippedProductqty(0);
			 productToBeSavedInOrder.add(product);			 
		 }
		 for(int i=0; i < productInCart.size(); i++) {
			 for(int j=0; j < productToBeSavedInOrder.size(); j++) {
				 if(productInCart.get(i).getProductName().equals(productToBeSavedInOrder.get(j).getProductName())) {
					 Product productInDB = productService.get(productInCart.get(i).getProductID());
//					 productInDB.setQuantity(productInDB.getQuantity() - productInCart.get(i).getOrderedProductQty());
					 productToBeSavedInOrder.get(j).setProductName(productInCart.get(i).getProductName());
					 productToBeSavedInOrder.get(j).setCost(productInCart.get(i).getCost());
//					 productToBeSavedInOrder.get(j).setPrice(productInCart.get(i).getPrice());
					 productToBeSavedInOrder.get(j).setQuantity(productInDB.getQuantity());
					 productToBeSavedInOrder.get(j).setOrderedProductQty(productInCart.get(i).getOrderedProductQty());
					 productToBeSavedInOrder.get(j).setUnshippedProductqty(productInCart.get(i).getOrderedProductQty());
					 productToBeSavedInOrder.get(j).getOrders().add(order);
				 }
			 }
		 }
		 order.setProducts(productToBeSavedInOrder);
		 order.setTotalPrice(cart.getCartTotal());
		 order.setPaymentMethod(paymentMethod);
		 if(paymentStatus != null) {
			 order.setPaymentStatus("Payment Received");
			 order.setShipmentStatus("Pending Shipment");
		 }else {
			 order.setPaymentStatus("Pending Payment");
			 order.setShipmentStatus("Pending Payment");
		 }
		 
		
		 String orderType = request.getParameter("saleType");
		 
		 if(!validateSelectBox(orderType)) {
			 System.out.println("Check select type of sale");
			 model.addAttribute("unselectedBoxError", "Falto en escojer una opcion. Porfavor selecciona una opcion!");
			 model.addAttribute("showCheckout", "showCheckout");
			 return "redirect:/orders";
		 }
		 
		 UserDetails userDetails =
				 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 User user = userService.findUserByName(userDetails.getUsername());
		 
		 //Vendor Sale
		 String vendorName = request.getParameter("_vandorSale");
		 String customerName = request.getParameter("_customers");		 
		 if(orderType.equals("vendorSale") && validateSelectBox(vendorName) && validateSelectBox(customerName)) {
			 User customer = userService.getUserByName(customerName);
			 User vendor = userService.getUserByName(vendorName);
			 order.setCreateByUser(vendor);
			 order.setCreateForUser(customer);
			 order.setOrderType("Vendor mediated sale");
			 order.setBillingInfo(setBillingInfo(customer,order));
			 model.addAttribute("orderConfirmation", customer.getUsername());
			 vendor.getOrderCreator().add(order);
			 customer.getOrderReceiver().add(order);
		 }else if(orderType.equals("vendorSale") && (!validateSelectBox(vendorName) || !validateSelectBox(customerName))) {
			 System.out.println("check vendor sale");
			 model.addAttribute("unselectedBoxError", "There is an unselected box. Please select!");
			 model.addAttribute("showCheckout", "showCheckout");
			 return "redirect:/orders";
		 }
		 
		 //Distributor Sale
		 String distributorName = request.getParameter("_distributorSale");		 
		 if(orderType.equals("distributorSale") && validateSelectBox(distributorName)) {
			 User distributor = userService.getUserByName(distributorName);
			 order.setCreateByUser(user);
			 order.setCreateForUser(distributor);
			 order.setOrderType("Distributor sale");
			 order.setBillingInfo(setBillingInfo(distributor,order));
			 model.addAttribute("orderConfirmation", distributor.getUsername());
			 user.getOrderCreator().add(order);
			 distributor.getOrderReceiver().add(order);
		 }else if(orderType.equals("distributorSale") && !validateSelectBox(distributorName)) {
			 System.out.println("check distributor sale");
			 model.addAttribute("unselectedBoxError", "There is an unselected box. Please select!");
			 model.addAttribute("showCheckout", "showCheckout");
			 return "redirect:/orders";
		 }
		 
		 //Direct Sale
		 String directCustomerName = request.getParameter("_directSale");		
		 if(orderType.equals("directSale") && validateSelectBox(directCustomerName)) {
			 User directCustomer = userService.getUserByName(directCustomerName);
			 order.setCreateByUser(user);
			 order.setCreateForUser(directCustomer);
			 order.setOrderType("Direct sale");
			 order.setBillingInfo(setBillingInfo(directCustomer,order));
			 model.addAttribute("orderConfirmation", directCustomer.getUsername());
			 user.getOrderCreator().add(order);
			 directCustomer.getOrderCreator().add(order);
		 }else if(orderType.equals("directSale") && !validateSelectBox(directCustomerName)) {
			 System.out.println("check direck sale");
			 model.addAttribute("unselectedBoxError", "There is an unselected box. Please select!");
			 model.addAttribute("showCheckout", "showCheckout");
			 return "redirect:/orders";
		 }

		 
		 
//		 if(user.getUsername() == "Manager") {
//			 String email = billingInfo.getEmail();
//			 User managerAssistedUser = userService.findUserByEmail(email);
//			 order.setCreateByUser(managerAssistedUser);
////			 billingInfo.setEmail(managerAssistedUser.getEmail());
////			 billingInfo.setFirstName(managerAssistedUser.getFirstName());
////			 billingInfo.setLastName(managerAssistedUser.getLastName());
////			 billingInfo.setAddress(managerAssistedUser.getAddress());
////			 billingInfo.setCity(managerAssistedUser.getCity());
////			 billingInfo.setState(managerAssistedUser.getState());
////			 billingInfo.setPostalCode(managerAssistedUser.getZip());
////			 billingInfo.setPhone(managerAssistedUser.getPhoneNumber());
//		 }else {
//			 order.setCreateByUser(user);			 
//		 }
		 
		 order.setOrderStatus("Abierto");
		 System.out.println("Saving order into database");
		 orderService.createOrder(order);
		 userService.editUser(user);		 
		 session.removeAttribute("cart");
//		 User userWhoPlacedOrder = userService.findUserByName(user.getUsername());
//		 List<Order> orderCreatedByThisUser = userWhoPlacedOrder.getOrders();
//		 Order confirmedOrder = orderCreatedByThisUser.get(orderCreatedByThisUser.size()-1);
		 System.out.println("Order has been placed");
		 return "redirect:/orders";
	 }
	 
	 @RequestMapping(value="thisAddress",method=RequestMethod.GET)
	 public String thisAddress(Model model) {
		 model.addAttribute("thisAddress", "thisAddress");
		 return "redirect:/checkout";
	 }
	 
	 @RequestMapping(value="newAddress",method=RequestMethod.GET)
	 public String newAddress(Model model) {
		 model.addAttribute("showAddressForm", "showAddressForm");
		 return "redirect:/checkout";
	 }
	 
	 @ModelAttribute("stateName")
	    public Map<String,String> getStateName() {
			Map<String,String> stateNames = new LinkedHashMap<String,String>();
			stateNames.put("AK","Alaska");
			stateNames.put("AL","Alabama");
			stateNames.put("AZ","Arizona");
			stateNames.put("AR","Arkansas");
			stateNames.put("CA","California");
			stateNames.put("CO","Colorado");
			stateNames.put("CT","Connecticut");
			stateNames.put("DE","Delaware");
			stateNames.put("FL","Florida");
			stateNames.put("GA","Georgia");
			stateNames.put("HI","Hawaii");
			stateNames.put("ID","Idaho");
			stateNames.put("IL","Illinois");
			stateNames.put("IN","Indiana");
			stateNames.put("IA","Iowa");
			stateNames.put("KS","Kansas");
			stateNames.put("KY","Kentucky");
			stateNames.put("LA","Louisiana");
			stateNames.put("ME","Maine");
			stateNames.put("MD","Maryland");
			stateNames.put("MA","Massachusetts");
			stateNames.put("MI","Michigan");
			stateNames.put("MN","Minnesota");
			stateNames.put("MS","Mississippi");
			stateNames.put("MO","Missouri");
			stateNames.put("MT","Montana");
			stateNames.put("NE","Nebraska");
			stateNames.put("NV","Nevada");
			stateNames.put("NH","New");
			stateNames.put("NJ","New");
			stateNames.put("NM","New");
			stateNames.put("NY","New");
			stateNames.put("NC","North");
			stateNames.put("ND","North");
			stateNames.put("OH","Ohio");
			stateNames.put("OK","Oklahoma");
			stateNames.put("OR","Oregon");
			stateNames.put("PA","Pennsylvania");
			stateNames.put("RI","Rhode");
			stateNames.put("SC","South");
			stateNames.put("SD","South");
			stateNames.put("TN","Tennessee");
			stateNames.put("TX","Texas");
			stateNames.put("UT","Utah");
			stateNames.put("VT","Vermont");
			stateNames.put("VA","Virginia");
			stateNames.put("WA","Washington");
			stateNames.put("DC","Washington");
			stateNames.put("WV","West");
			stateNames.put("WI","Wisconsin");
			stateNames.put("WY","Wyoming");
		   return stateNames;
	    }
	 
	 private BillingInfo setBillingInfo(User user, Order order) {
		 BillingInfo billingInfo = new BillingInfo();
		 billingInfo.setAddress(user.getAddress());
		 billingInfo.setCity(user.getCity());
		 billingInfo.setEmail(user.getEmail());
		 billingInfo.setFirstName(user.getFirstName());
		 billingInfo.setLastName(user.getLastName());
		 billingInfo.setPhone(user.getPhoneNumber());
		 billingInfo.setPostalCode(user.getZip());
		 billingInfo.setState(user.getState());
		 billingInfo.setOrder(order);
		 return billingInfo;
	 }
	 
	 private boolean validateSelectBox(String nameInput) {
		 boolean check = true;
		 if(nameInput.equals("Choose...")) {
			 check = false;
		 }
		 return check;
	 }
	 
}
