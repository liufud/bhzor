package ics.ui.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ics.model.Cart;
import ics.model.Order;
import ics.model.OrderedProd;
import ics.model.Product;
import ics.model.ReceivedRpOrder;
import ics.model.ReplenishmentOrder;
import ics.model.User;
import ics.model.Vendor;
import ics.services.CartService;
import ics.services.ProductService;
import ics.services.ReceivedRpOrderService;
import ics.services.ReplenishmentOrderService;
import ics.services.UserService;

@Controller
public class InventoryController {
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;
	@Autowired
	private CartService cartService;
	@Autowired
	private ReplenishmentOrderService replenishmentOrderService;
	@Autowired
	private ReceivedRpOrderService receivedRpOrderService;
	
	@RequestMapping(value="inventory",method=RequestMethod.GET)
	public String showInventory(Model model, HttpSession session, HttpServletRequest request,
								@ModelAttribute("addProductForm")String addProductForm,
								@ModelAttribute("product")Product productToAdd,
								@ModelAttribute("editProduct")String editProduct,
								@ModelAttribute("editProductInfo")String editProductInfo,
								@ModelAttribute("productToBeEdited")Product productToBeEdited,
								@ModelAttribute("deleteProductSucceeded")String deleteProductAlert,
								@ModelAttribute("productToBeUpdated")Product productToBeUpdated,
								@ModelAttribute("updateProductSucceeded")String productUpdated,
								@ModelAttribute("viewAllProducts")String viewAllProducts,
								@ModelAttribute("orderReplenishment")String orderReplenishment,
								@ModelAttribute("listProducts")String listProductsCheck,
								@ModelAttribute("shoppingCartQtyError")String shoppingCartQtyError,
								@ModelAttribute("orderSummary")String orderSummary,
								@ModelAttribute("addToCartSucceeded")String addToCartSucceeded,
								@ModelAttribute("replenishmentOrderConfirmed")String replenishmentOrderConfirmed,
								@ModelAttribute("rpOrderReceivedForm")String rpOrderReceivedForm,
								@ModelAttribute("rpOrderID")String rpOrderID,
								@ModelAttribute("receivedQtyError")String receivedQtyError,
								@ModelAttribute("saveReceivedQtyToAnotherLot")String saveReceivedQtyToAnotherLot,
								@ModelAttribute("rpOrderClosed")String rpOrderClosed) {
		model.addAttribute("addProductForm", addProductForm);
		model.addAttribute("product", productToAdd);
		model.addAttribute("editProduct", editProduct);
		model.addAttribute("editProductInfo", editProductInfo);
		model.addAttribute("productToBeEdited", productToBeEdited);
		model.addAttribute("deleteProductSucceeded", deleteProductAlert);
		model.addAttribute("productToBeUpdated", productToBeUpdated);
		model.addAttribute("updateProductSucceeded", productUpdated);
		model.addAttribute("viewAllProducts", viewAllProducts);
		model.addAttribute("orderReplenishment", orderReplenishment);
		model.addAttribute("shoppingCartQtyError", shoppingCartQtyError);
		model.addAttribute("orderSummary", orderSummary);
		model.addAttribute("addToCartSucceeded", addToCartSucceeded);
		model.addAttribute("replenishmentOrderConfirmed", replenishmentOrderConfirmed);
		model.addAttribute("rpOrderReceivedForm", rpOrderReceivedForm);
		model.addAttribute("receivedQtyError", receivedQtyError);
		model.addAttribute("saveReceivedQtyToAnotherLot", saveReceivedQtyToAnotherLot);
		model.addAttribute("rpOrderClosed", rpOrderClosed);
		if(!rpOrderReceivedForm.isEmpty()) {
			model.addAttribute("receivedRpOrder", new ReceivedRpOrder());
			List<OrderedProd> rpProds = replenishmentOrderService.getOrder(Long.valueOf(rpOrderID)).getRpProducts();
			List<String> rpProdNames = new ArrayList<String>();
			for(OrderedProd o:rpProds) rpProdNames.add(o.getProductName());
			model.addAttribute("rpProdNames", rpProdNames);
			model.addAttribute("rpOrderID", rpOrderID);
		}
//		System.out.println("Replenishment order info: " + replenishmentOrderConfirmed);
		if(!orderSummary.isEmpty()) {
			Cart cart = (Cart) session.getAttribute("cart");
			model.addAttribute("cart", cart);
			List<OrderedProd> products = cart.getProducts();
			model.addAttribute("products", products);
		}
		if(!listProductsCheck.isEmpty()) {
			ArrayList<Product> listProducts = (ArrayList<Product>) productService.listProducts();
			model.addAttribute("listProducts", listProducts);
		}		
		if(!model.containsAttribute("product")) {
			System.out.println("model did not exist on the page, creating a new one...");
			model.addAttribute("product",new Product());
		}
		String openOrderQueryString = request.getQueryString();
		if(null != openOrderQueryString && openOrderQueryString.contains("openOrder")) {
			List<Product> listProductNames = (List<Product>) productService.listProducts();
			model.addAttribute("productNames", listProductNames);
			Collection<ReplenishmentOrder> openOrders = replenishmentOrderService.listOrders("open");
			model.addAttribute("openOrders", openOrders);
		}	
		
		return "inventory";
	}
	
	@RequestMapping(value="{rpOrderID}/receivedRpOrder",method=RequestMethod.GET)
	public String receivedRpOrder(Model model,@PathVariable("rpOrderID")String rpOrderID,
								@ModelAttribute("receivedQtyError")String receivedQtyError,
								@ModelAttribute("saveReceivedQtyToAnotherLot")String saveReceivedQtyToAnotherLot,
								@ModelAttribute("rpOrderClosed")String rpOrderClosed) {
		model.addAttribute("rpOrderReceivedForm", "rpOrderReceivedForm");
		model.addAttribute("rpOrderID", rpOrderID);
		model.addAttribute("receivedQtyError", receivedQtyError);
		model.addAttribute("saveReceivedQtyToAnotherLot", saveReceivedQtyToAnotherLot);
		model.addAttribute("rpOrderClosed", rpOrderClosed);
		return "redirect:/inventory?orderStatus=openOrder";
	}
	@RequestMapping(value="receivedRpOrder",method=RequestMethod.POST)
	public String receivedRpOrder(@Valid@ModelAttribute("receivedRpOrder") ReceivedRpOrder receivedRpOrder,
								BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			System.out.println("data binding unsuccessful");			
			for(FieldError e:bindingResult.getFieldErrors()) System.out.println(e);
			model.addAttribute("org.springframework.validation.BindingResult.receivedRpOrder",bindingResult);
			model.addAttribute("receivedRpOrder",receivedRpOrder);
			model.addAttribute("rpOrderReceivedForm", "rpOrderReceivedForm");
			List<OrderedProd> rpProds = replenishmentOrderService.getOrder(receivedRpOrder.getRpOrderID()).getRpProducts();
			List<String> rpProdNames = new ArrayList<String>();
			for(OrderedProd o:rpProds) rpProdNames.add(o.getProductName());
			model.addAttribute("rpProdNames", rpProdNames);
			model.addAttribute("rpOrderID", receivedRpOrder.getRpOrderID());
			return "inventory";
		}
		List<ReceivedRpOrder> receivedRpOrdersByLot = receivedRpOrderService.getOrderByLot(receivedRpOrder.getLotID(), receivedRpOrder.getReceivedRpProductName());
		ReceivedRpOrder receivedRpOrderToBeSaved = new ReceivedRpOrder();
		UserDetails userDetails =
				 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUserByName(userDetails.getUsername());
		System.out.println("User is " + user.getUsername());
		receivedRpOrderToBeSaved.setCreateByUser(user);
		receivedRpOrderToBeSaved.setExpDate(receivedRpOrder.getExpDate());
		receivedRpOrderToBeSaved.setLotID(receivedRpOrder.getLotID());
		if(receivedRpOrder.getQuantityRejected() == null) {
			receivedRpOrderToBeSaved.setQuantityRejected(0);
		}else {
			receivedRpOrderToBeSaved.setQuantityRejected(receivedRpOrder.getQuantityRejected());
		}
		receivedRpOrderToBeSaved.setRpOrderID(receivedRpOrder.getRpOrderID());
		receivedRpOrderToBeSaved.setReceivedRpProductName(receivedRpOrder.getReceivedRpProductName());
		double productCost = productService.getProductByName(receivedRpOrder.getReceivedRpProductName()).getCost();
		Double totalCost = receivedRpOrder.getQuantityReceived() * productCost;
		receivedRpOrderToBeSaved.setTotalCost(totalCost);
		
//		receivedRpOrderToBeSaved.setQuantityReceived(receivedRpOrder.getQuantityReceived());
		
		ReplenishmentOrder rpOrder = replenishmentOrderService.getOrder(receivedRpOrder.getRpOrderID());
		//Checking the quantity received
		List<OrderedProd> rpProducts = rpOrder.getRpProducts();
		for(OrderedProd o:rpProducts) {
			if(o.getProductName().equals(receivedRpOrder.getReceivedRpProductName())) {
				//if qty received is greater than quantity ordered, throw error
				if(receivedRpOrder.getQuantityReceived() > o.getOrderedProductQty()) {
					model.addAttribute("receivedQtyError", "Received quantity cannot be greater than ordered quantity!");
					return "redirect:/" + rpOrder.getRpOrderID() + "/receivedRpOrder";
				}
				//if qty received is less than quantity ordered, ask user if he/she wants to save it to another lot
				else if(receivedRpOrder.getQuantityReceived() < o.getOrderedProductQty()) {
					Collection<ReceivedRpOrder> receivedRpOrders = receivedRpOrderService.listOrders(rpOrder.getRpOrderID(),receivedRpOrder.getReceivedRpProductName());
					Integer quantityReceived = new Integer(0);
					for(ReceivedRpOrder recRpOrder:receivedRpOrders) quantityReceived+=recRpOrder.getQuantityReceived();
					//check if the added quantity exceeds ordered quantity
					if(quantityReceived + receivedRpOrder.getQuantityReceived() > o.getOrderedProductQty()) {
						model.addAttribute("receivedQtyError", "Received quantity cannot be greater than ordered quantity!");
						return "redirect:/" + rpOrder.getRpOrderID() + "/receivedRpOrder";
					}else if(quantityReceived + receivedRpOrder.getQuantityReceived() < o.getOrderedProductQty()){
						model.addAttribute("saveReceivedQtyToAnotherLot", rpOrder.getRpOrderID());
//						if(null != receivedRpOrdersByLot) {
//							
//						}
						o.setUnreceivedProductqty(o.getUnreceivedProductqty()-receivedRpOrder.getQuantityReceived());
						rpOrder.setRpProducts(rpProducts);
						System.out.println("saving rpOrder -----");
						replenishmentOrderService.createOrder(rpOrder);
						System.out.println("rpOrder saved ----");
						receivedRpOrderToBeSaved.setQuantityReceived(receivedRpOrder.getQuantityReceived());
						
						
						receivedRpOrderService.createOrder(receivedRpOrderToBeSaved);						
						System.out.println("Received " + receivedRpOrder.getQuantityReceived() + " of " + receivedRpOrder.getReceivedRpProductName());
						return "redirect:/" + rpOrder.getRpOrderID() + "/receivedRpOrder";
					}else if(quantityReceived + receivedRpOrder.getQuantityReceived() == o.getOrderedProductQty()){					
						receivedRpOrderToBeSaved.setQuantityReceived(receivedRpOrder.getQuantityReceived());
						o.setUnreceivedProductqty(o.getUnreceivedProductqty()-receivedRpOrder.getQuantityReceived());
						receivedRpOrderService.createOrder(receivedRpOrderToBeSaved);
						rpOrder.setRpProducts(rpProducts);
						replenishmentOrderService.createOrder(rpOrder);
						System.out.println("Received all the quantity for product [" + receivedRpOrder.getReceivedRpProductName() + "]" );
					}
//					Collection<ReceivedRpOrder> receivedRpOrders = receivedRpOrderService.listOrders(rpOrder.getRpOrderID(),receivedRpOrder.getReceivedRpProductName());
//					Integer quantityReceived = new Integer(0);
//					for(ReceivedRpOrder recRpOrder:receivedRpOrders) quantityReceived+=recRpOrder.getQuantityReceived();
//					//check if the added quantity exceeds ordered quantity
//					if(quantityReceived + receivedRpOrder.getQuantityReceived() > o.getOrderedProductQty()) {
//						model.addAttribute("receivedQtyError", "Received quantity cannot be greater than ordered quantity!");
//						return "redirect:/" + rpOrder.getRpOrderID() + "/receivedRpOrder";
//					}else if(quantityReceived + receivedRpOrder.getQuantityReceived() < o.getOrderedProductQty()){
//						model.addAttribute("saveReceivedQtyToAnotherLot", rpOrder.getRpOrderID());
//						receivedRpOrderToBeSaved.setQuantityReceived(receivedRpOrder.getQuantityReceived());
//						receivedRpOrderService.createOrder(receivedRpOrderToBeSaved);
//						
//						System.out.println("Received " + receivedRpOrder.getQuantityReceived() + " of " + receivedRpOrder.getReceivedRpProductName());
//						return "redirect:/" + rpOrder.getRpOrderID() + "/receivedRpOrder";
//					}else if(quantityReceived + receivedRpOrder.getQuantityReceived() == o.getOrderedProductQty()){					
//						receivedRpOrderToBeSaved.setQuantityReceived(receivedRpOrder.getQuantityReceived());
//						receivedRpOrderService.createOrder(receivedRpOrderToBeSaved);
//						System.out.println("Received all the quantity for product [" + receivedRpOrder.getReceivedRpProductName() + "]" );
//					}
					
				}
				//if qty received is equal to the ordered qty, close the replenishment order
				else {
					receivedRpOrderToBeSaved.setQuantityReceived(receivedRpOrder.getQuantityReceived());
					o.setUnreceivedProductqty(o.getUnreceivedProductqty()-receivedRpOrder.getQuantityReceived());
					receivedRpOrderService.createOrder(receivedRpOrderToBeSaved);
					rpOrder.setRpProducts(rpProducts);
					replenishmentOrderService.createOrder(rpOrder);
					System.out.println("Received all the quantity for product [" + receivedRpOrder.getReceivedRpProductName() + "]" );
				}
			}
		}
		List<OrderedProd> rpOrderedProds = replenishmentOrderService.getOrder(rpOrder.getRpOrderID()).getRpProducts();
		boolean closeRpOrder = true;
		for(OrderedProd o:rpOrderedProds) {
			if(o.getUnreceivedProductqty() != 0) {
				closeRpOrder = false;
			}
		}
		if(closeRpOrder) {
			rpOrder.setOrderStatus("Closed");
			replenishmentOrderService.createOrder(rpOrder);
			List<Product> products = (List<Product>) productService.listProducts();
			for(int i=0; i<products.size(); i++) {
				for(int j=0; j<rpOrderedProds.size();j++) {
					String productName = products.get(i).getProductName();
					String rpProductName = rpOrderedProds.get(j).getProductName();
					if(productName.equals(rpProductName)) {
						products.get(i).setQuantity(products.get(i).getQuantity() + rpOrderedProds.get(j).getOrderedProductQty());
						productService.addOrUpdateProduct(products.get(i));
					}
				}
			}
			model.addAttribute("rpOrderClosed", rpOrder.getRpOrderID());
		}
//		Collection<OrderedProd> rpOrderedProds = replenishmentOrderService.getOrder(rpOrder.getRpOrderID()).getRpProducts();
//		boolean closeRpOrder = true;
//		for(OrderedProd o:rpOrderedProds) {
//			Collection<ReceivedRpOrder> receivedRpOrders = receivedRpOrderService.listOrders(rpOrder.getRpOrderID(), o.getProductName());
//			if(null != receivedRpOrders) {
//				Integer quantityReceived = new Integer(0);
//				for(ReceivedRpOrder recRpOrder:receivedRpOrders) quantityReceived+=recRpOrder.getQuantityReceived();
//				if(quantityReceived == o.getOrderedProductQty()) {
//					continue;
//				}else {
//					closeRpOrder = false;
//				}
//			}
//		}
//		if(closeRpOrder) {
//			rpOrder.setOrderStatus("Closed");
//			replenishmentOrderService.createOrder(rpOrder);
//			model.addAttribute("rpOrderClosed", rpOrder.getRpOrderID());
//		}		
		//In receivedRpOrderDAOImpl, create method such that all the receivedRpOrder with the same rpOrderID will be retrieved into a list
		
		//loop through the quantity in this list against the rpProduct list
		//if product names are the same, and qty are the same, close rpOrder
		//
//		List<OrderedProd> rpProducts = rpOrder.getProducts();
//		for(OrderedProd o:rpProducts) {
//			if(receivedRpOrder.getQuantityReceived() == o.getOrderedProductQty()) {
//				
//			}
//		}
		
		return "redirect:/inventory?orderStatus=openOrder";
	}
	
	@RequestMapping(value="addProduct",method=RequestMethod.GET)
	public String addProduct(RedirectAttributes attr) {
		attr.addFlashAttribute("addProductForm", "addProductForm");
		return "redirect:/inventory";
	}
	
	@RequestMapping(value="addProduct",method=RequestMethod.POST)
	public String addProduct(@Valid@ModelAttribute("product") Product product, BindingResult bindingResult, 
							Model model, RedirectAttributes attr) {
		System.out.println("addProduct is called");
		System.out.println(product);
		if(bindingResult.hasErrors()) {
			System.out.println("data binding unsuccessful");			
			for(FieldError e:bindingResult.getFieldErrors()) System.out.println(e);
			model.addAttribute("org.springframework.validation.BindingResult.product",bindingResult);
			model.addAttribute("product",product);
			model.addAttribute("addProductForm", "addProductForm");
			return "inventory";
		}
		String productName = product.getProductName();
		Product productInInventory = productService.getProductByName(productName);
		if(productInInventory == null) {
			try {
				System.out.println("adding new product ----------");
				productService.addOrUpdateProduct(product);
				System.out.println("Product added succesfully!");
				attr.addFlashAttribute("addProductSucceeded", "You have successfully added product " + product.getProductName() + "!");
			} catch (Exception e) {
				System.out.println(e);
			}
		}else {
			System.out.println("product exists, updating product");
			productInInventory.setCost(product.getCost());
//			product.setOrders(productToBeUpdated.getOrders());
			productInInventory.setPrice(product.getPrice());
			productInInventory.setProductName(product.getProductName());
			productService.addOrUpdateProduct(productInInventory);
			attr.addFlashAttribute("updateProductSucceeded", "You have successfully updated product " + product.getProductName() + "!");
		}
		System.out.println("product added");
		return "redirect:/inventory";
	}
	
	@ModelAttribute("productList")
    public List<String> getProductList() {
		ArrayList<Product> products = (ArrayList<Product>) productService.listProducts();
	    List<String> productList = new ArrayList<String>();
		for(Product p : products) productList.add(p.getProductName());
	   return productList;
    }
	
	@ModelAttribute("listAllProducts")
    public List<Product> listAllProduct() {
		ArrayList<Product> products = (ArrayList<Product>) productService.listProducts();	    
	   return products;
    }
	
	@RequestMapping(value="editProduct",method=RequestMethod.GET)
	public String editProduct(RedirectAttributes attr) {
		attr.addFlashAttribute("editProduct", "editProduct");
		attr.addFlashAttribute("viewAllProducts","viewAllProducts");
		return "redirect:/inventory";
	}
	
	@RequestMapping(value="editProduct",method=RequestMethod.POST)
	public String editProduct(@ModelAttribute("product")Product product, RedirectAttributes attr) {
		String productName = product.getProductName();
		Product productToBeEdited = productService.getProductByName(productName);
		attr.addFlashAttribute("productToBeEdited", productToBeEdited);
		attr.addFlashAttribute("editProduct", "editProduct");
		attr.addFlashAttribute("editProductInfo", "editProduct");
		return "redirect:/inventory";
	}
	
	@RequestMapping(value="{productID}/deleteProduct", method=RequestMethod.GET)
	public String deleteProduct(@PathVariable("productID")Long productID,
								RedirectAttributes attr) {
		System.out.println("deleteProduct is called");
		Product product = productService.get(productID);
		try {
			productService.delete(product.getProductID());;
		} catch (Exception e) {
			System.out.println(e);
		}
		attr.addFlashAttribute("deleteProductSucceeded", "You have delete Product " + product.getProductName());
		return "redirect:/inventory";
	}
	
	@RequestMapping(value="{productID}/updateProduct", method=RequestMethod.GET)
	public String updateProduct(@PathVariable("productID")Long productID,
								RedirectAttributes attr) {
		Product product = productService.get(productID);
		attr.addFlashAttribute("productToBeUpdated", product);
		return "redirect:/inventory";
	}
	
	@RequestMapping(value="updateProduct",method=RequestMethod.POST)
	public String updateProduct(@Valid@ModelAttribute("productToBeUpdated")Product productToBeUpdated,
								BindingResult bindingResult, RedirectAttributes attr, Model model) {
		if(bindingResult.hasErrors()) {
			System.out.println("update product unsuccessful........");
			model.addAttribute("org.springframework.validation.BindingResult.product",bindingResult);
			model.addAttribute("productToBeUpdated", productToBeUpdated);
			return "inventory";
		}
		Product product = productService.get(productToBeUpdated.getProductID());
		product.setCost(productToBeUpdated.getCost());
//		product.setOrders(productToBeUpdated.getOrders());
		product.setPrice(productToBeUpdated.getPrice());
		product.setProductName(productToBeUpdated.getProductName());
		productService.addOrUpdateProduct(product);
		attr.addFlashAttribute("updateProductSucceeded", "You have successfully updated product " + product.getProductName() + "!");
		return "redirect:/inventory";
	}
	
	@RequestMapping(value="orderReplenishment", method=RequestMethod.GET)
	public String orderReplenishment(Model model,
									@ModelAttribute("shoppingCartQtyError")String shoppingCartQtyError,
									@ModelAttribute("addToCartSucceeded")String addToCartSucceeded) {
		model.addAttribute("listProducts", "listProducts");
		model.addAttribute("orderReplenishment", "orderReplenishment");
		model.addAttribute("shoppingCartQtyError", shoppingCartQtyError);
		model.addAttribute("addToCartSucceeded", addToCartSucceeded);
		return "redirect:/inventory";
	}
	
	@RequestMapping(value="{productID}/replenish",method=RequestMethod.POST)
	public String orderReplenishment(@PathVariable("productID")Long productID, HttpSession session,
									HttpServletRequest request, Model model) {
		if(session.getAttribute("cart") == null) {
			System.out.println("Cart doesn't exist, create a new one");
			Cart cart = new Cart();
			List<OrderedProd> products = new ArrayList<OrderedProd>();			
			Product productInDB = productService.get(productID);
			OrderedProd product = new OrderedProd();
			product.setProductName(productInDB.getProductName());
			product.setProductID(productInDB.getProductID());
			product.setPrice(productInDB.getPrice());
			product.setCost(productInDB.getCost());
			products.add(product);
			String[] quantity = request.getParameterValues("num");
			try {
				Integer.parseInt(quantity[0]);
				if(Integer.parseInt(quantity[0]) <= 0) {
					model.addAttribute("shoppingCartQtyError", "Quantity has to be greater than zero!");
					return "redirect:/orderReplenishment";
				}else if(Integer.parseInt(quantity[0]) > 10000000) {
					model.addAttribute("shoppingCartQtyError", "Invalid quantity!");
					return "redirect:/orderReplenishment";
				}else {
					for (int i = 0; i < products.size(); i++) {
						products.get(i).setOrderedProductQty(Integer.parseInt(quantity[0]));
					 }
				}	
			} catch (Exception e) {
				System.out.println(e);
				model.addAttribute("shoppingCartQtyError", "Invalid quantity!");
				return "redirect:/orderReplenishment";
			}
					
			cart.setProducts(products);
			model.addAttribute("addToCartSucceeded", "You have added " + product.getOrderedProductQty() + " [" + product.getProductName() + "] to your shopping cart");
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
				product.setPrice(productInDB.getPrice());
				product.setCost(productInDB.getCost());
				String[] quantity = request.getParameterValues("num");
				
				try {
					Integer.parseInt(quantity[0]);
					if(Integer.parseInt(quantity[0]) <= 0) {
						model.addAttribute("shoppingCartQtyError", "Quantity has to be greater than zero!");
						return "redirect:/orderReplenishment";
					}else if(Integer.parseInt(quantity[0]) > 10000000) {
						model.addAttribute("shoppingCartQtyError", "Invalid quantity!");
						return "redirect:/orderReplenishment";
					}else {
						product.setOrderedProductQty(Integer.parseInt(quantity[0]));
						products.add(product);
					}	
				} catch (Exception e) {
					System.out.println(e);
					model.addAttribute("shoppingCartQtyError", "Invalid Quantity!");
					return "redirect:/orderReplenishment";
				}			
				cart.setProducts(products);
				cartTotal = cartTotal(products, cart);
				model.addAttribute("addToCartSucceeded", "You have added " + product.getOrderedProductQty() + " [" + product.getProductName() + "] to your shopping cart");
			}else {
				System.out.println("product exists in cart");
				for(OrderedProd p:products)System.out.println(p.getProductName());
				System.out.println("updating product qty");
				String[] quantityParam = request.getParameterValues("num");
				
				try {
					Integer.parseInt(quantityParam[0]);
					if(Integer.parseInt(quantityParam[0]) <= 0) {
						model.addAttribute("shoppingCartQtyError", "Quantity has to be greater than zero!");
						return "redirect:/orderReplenishment";
					}else if(Integer.parseInt(quantityParam[0]) > 10000000) {
						model.addAttribute("shoppingCartQtyError", "Invalid quantity!");
						return "redirect:/orderReplenishment";
					}else {
						Integer quantity = products.get(index).getOrderedProductQty()+Integer.parseInt(quantityParam[0]);
						products.get(index).setOrderedProductQty(quantity);
					}	
				} catch (Exception e) {
					System.out.println(e);
					model.addAttribute("shoppingCartQtyError", "Invalid Quantity!");
					return "redirect:/orderReplenishment";
				}	
				System.out.println("product qty updated");
				cart.setProducts(products);
				cartTotal = cartTotal(products, cart);
				OrderedProd product = products.get(index);
				model.addAttribute("addToCartSucceeded", "You have added " + product.getOrderedProductQty() + " [" + product.getProductName() + "] to your shopping cart");
			}
			
			cart.setCartTotal(cartTotal);
			session.setAttribute("cart", cart);
			Cart newCart = cartService.get(cart.getCartId());			
			cartService.addOrUpdateCart(newCart);
		}
		return "redirect:/orderReplenishment";
	}
	
	@RequestMapping(value="orderSummary",method=RequestMethod.GET)
	public String orderSummary(Model model, HttpSession session,
							@ModelAttribute("shoppingCartQtyError")String shoppingCartQtyError) {
		Cart cart = (Cart) session.getAttribute("cart");
		if(cart == null) {
			model.addAttribute("shoppingCartQtyError", "Order Quantity cannot be 0");
			return "redirect:/orderReplenishment";
		}
		model.addAttribute("orderSummary", "orderSummary");
		model.addAttribute("shoppingCartQtyError", shoppingCartQtyError);
		return "redirect:/inventory";
	}
	
	@RequestMapping(value="updateRpCart",method=RequestMethod.POST)
	 public String updateCart(HttpServletRequest request, HttpSession session, Model model) {
		 Cart cart = (Cart) session.getAttribute("cart");
		 List<OrderedProd> products = cart.getProducts();
		 String[] quantity = request.getParameterValues("quantity");
		 for (int i = 0; i < products.size(); i++) {
			 try {
					Integer.parseInt(quantity[i]);
					if(Integer.parseInt(quantity[i]) <= 0) {
						model.addAttribute("shoppingCartQtyError", "Quantity has to be greater than zero!");
						return "redirect:/orderSummary";
					}else if(Integer.parseInt(quantity[i]) > 10000000) {
						model.addAttribute("shoppingCartQtyError", "Invalid quantity!");
						return "redirect:/orderSummary";
					}else {
						products.get(i).setOrderedProductQty(Integer.parseInt(quantity[i]));
					}	
				} catch (Exception e) {
					System.out.println(e);
					model.addAttribute("shoppingCartQtyError", "Invalid Quantity!");
					return "redirect:/orderSummary";
				}
			 
		 }
		 cart.setProducts(products);
		 Double cartTotal = cartTotal(products, cart);
		 cart.setCartTotal(cartTotal);
		 session.setAttribute("cart", cart);
		 Cart newCart = cartService.get(cart.getCartId());			
		 cartService.addOrUpdateCart(newCart);
		 return "redirect:/orderSummary";
	 }
	
	@RequestMapping(value="placeReplenishmentOrder",method=RequestMethod.POST)
	public String placeReplenishmentOrder(Model model, HttpSession session) {
		System.out.println("Placing replenishment order");
		Cart cart = (Cart) session.getAttribute("cart");
		ReplenishmentOrder rpOrder = new ReplenishmentOrder();
		rpOrder.setCreateByUser(cart.getUser());
		rpOrder.setCreated_At(cart.getCreated_At());
		rpOrder.setOrderStatus("Open");
		rpOrder.setTotalPrice(cart.getCartTotal());
		List<OrderedProd> productsInCart = cart.getProducts();
		List<OrderedProd> rpProducts = rpOrder.getRpProducts();
		List<Product> productList = (ArrayList<Product>) productService.listProducts();
		for(int i=0; i<productList.size(); i++) {
			OrderedProd rpProduct = new OrderedProd();
			rpProduct.setCost(productList.get(i).getCost());
			rpProduct.setPrice(productList.get(i).getPrice());
			rpProduct.setProductName(productList.get(i).getProductName());
			rpProduct.setQuantity(productList.get(i).getQuantity());
			rpProduct.setOrderedProductQty(0);
			rpProduct.setUnreceivedProductqty(0);
			rpProducts.add(rpProduct);
		}
		for(int i=0; i<productsInCart.size(); i++) {
			for(int j=0; j<rpProducts.size(); j++) {
				if(productsInCart.get(i).getProductName().equals(rpProducts.get(j).getProductName())) {
					Product productInDB = productService.getProductByName(productsInCart.get(i).getProductName());
					rpProducts.get(j).setCost(productInDB.getCost());
					rpProducts.get(j).setPrice(productInDB.getPrice());
					rpProducts.get(j).setProductName(productInDB.getProductName());
					rpProducts.get(j).setQuantity(productInDB.getQuantity());
					rpProducts.get(j).setOrderedProductQty(productsInCart.get(i).getOrderedProductQty());
					rpProducts.get(j).setUnreceivedProductqty(productsInCart.get(i).getOrderedProductQty());
				}
			}
		}
		System.out.println("adding products-------");
		rpOrder.setRpProducts(rpProducts);
		replenishmentOrderService.createOrder(rpOrder);
		session.removeAttribute("cart");
		System.out.println("Replenishment order has been place");
		model.addAttribute("replenishmentOrderConfirmed", "You have placed an replenishment order!");
		return "redirect:/inventory";
	}
	
	private Double cartTotal(List<OrderedProd> products, Cart cart) {
		Double cartTotal = (double) 0;
		List<OrderedProd> p = cart.getProducts();
		for(OrderedProd a:p) {
			cartTotal+=a.getCost()*a.getOrderedProductQty();
		}
		return cartTotal;
	 }
	
	private int isExisting(Long productID, HttpSession session) {
		 Cart cart = (Cart) session.getAttribute("cart");
		 List<OrderedProd> products = cart.getProducts();
	
		  for (int i = 0; i < products.size(); i++)
	
		   if (products.get(i).getProductID() == productID)
		    return i;
	
		  return -1;
	 }
}
