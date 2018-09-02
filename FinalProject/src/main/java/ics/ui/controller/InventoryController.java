package ics.ui.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.annotations.Check;
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
import ics.services.OrderService;
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
	@Autowired
	private OrderService orderService;
	
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
								@ModelAttribute("rpOrderClosed")String rpOrderClosed,
								@ModelAttribute("receivedRpOrders")String receivedRpOrders) {
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
		model.addAttribute("receivedRpOrders", receivedRpOrders);
		if(!receivedRpOrders.isEmpty()) {
			List<ReceivedRpOrder> receivedRpOrdersList = (List<ReceivedRpOrder>) receivedRpOrderService.listOrders();
			model.addAttribute("receivedRpOrders", receivedRpOrdersList);
//			for(OrderedProd o:receivedRpOrdersList.get(0).getReceivedProds()) System.out.println("received product name " + o.getProductName());
		}		
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
//			System.out.println("Cart total is: " + cart.getCartTotal());
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
//		if(null != openOrderQueryString && openOrderQueryString.contains("closedOrder")) {
//			List<Product> listProductNames = (List<Product>) productService.listProducts();
//			model.addAttribute("productNames", listProductNames);
//			Collection<ReplenishmentOrder> closedOrders = receivedRpOrderService.listOrders("Closed");
//			model.addAttribute("closedOrders", closedOrders);
//		}
		return "inventory";
	}
	
	
	@RequestMapping(value="viewReceivedOrders", method=RequestMethod.GET)
	public String viewReceievedOrders(Model model) {		
		model.addAttribute("receivedRpOrders", "receivedRpOrders");
		return "redirect:/inventory"; 
	}
	
	@RequestMapping(value="{rpOrderID}/receivedRpOrder",method=RequestMethod.GET)
	public String receivedRpOrder(Model model,@PathVariable("rpOrderID")String rpOrderID,
								@ModelAttribute("receivedQtyError")String receivedQtyError,
								@ModelAttribute("saveReceivedQtyToAnotherLot")String saveReceivedQtyToAnotherLot,
								@ModelAttribute("rpOrderClosed")String rpOrderClosed) {
		model.addAttribute("rpOrderReceivedForm", rpOrderID);
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
			Collection<ReplenishmentOrder> openOrders = replenishmentOrderService.listOrders("open");
			model.addAttribute("openOrders", openOrders);
			model.addAttribute("rpOrderReceivedForm", receivedRpOrder.getRpOrderID());
			return "inventory";
		}
			
		List<ReceivedRpOrder> receivedRpOrdersByShelf = receivedRpOrderService.getOrderByShelf(receivedRpOrder.getRpOrderID() ,receivedRpOrder.getShelfID(), receivedRpOrder.getReceivedRpProductName());
		ReceivedRpOrder receivedRpOrderToBeSaved = new ReceivedRpOrder();
		UserDetails userDetails =
				 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUserByName(userDetails.getUsername());
//		if(null != user.getUsername()) System.out.println("User is " + user.getUsername());
		receivedRpOrderToBeSaved.setLotID(receivedRpOrder.getLotID());
		receivedRpOrderToBeSaved.setShelfID(receivedRpOrder.getShelfID());		
		receivedRpOrderToBeSaved.setExpDate(receivedRpOrder.getExpDate());
		receivedRpOrderToBeSaved.setCreateByUser(user);
		receivedRpOrderToBeSaved.setReceivedRpProductName(receivedRpOrder.getReceivedRpProductName());		
		double productCost = productService.getProductByName(receivedRpOrder.getReceivedRpProductName()).getCost();
		Double totalCost = receivedRpOrder.getQuantityReceived() * productCost;
		System.out.println("totoal cost is: " + totalCost);
		receivedRpOrderToBeSaved.setTotalCost(totalCost);
		receivedRpOrderToBeSaved.setRpOrderID(receivedRpOrder.getRpOrderID());
		
//		receivedRpOrderToBeSaved.setQuantityReceived(receivedRpOrder.getQuantityReceived());
		
		ReplenishmentOrder rpOrder = replenishmentOrderService.getOrder(receivedRpOrder.getRpOrderID());
		//Checking the quantity received
		List<OrderedProd> rpProducts = rpOrder.getRpProducts();
		for(OrderedProd o:rpProducts) {
			if(o.getProductName().equals(receivedRpOrder.getReceivedRpProductName())) {
				//if qty received is greater than quantity ordered, throw error
				if(receivedRpOrder.getQuantityReceived() > o.getOrderedProductQty()) {
					System.out.println("Error! Cantidad de Pedido Recibida es mayor a la cantidad de Order Pedida.");
					model.addAttribute("receivedQtyError", "Error! Cantidad Recibida no puede ser mayor a la Cantidad Pedida!");
					return "redirect:/" + rpOrder.getRpOrderID() + "/receivedRpOrder";
				}
				//if qty received is less than quantity ordered, ask user if he/she wants to save it to another lot
				else if(receivedRpOrder.getQuantityReceived() < o.getOrderedProductQty()) {
					Collection<ReceivedRpOrder> receivedRpOrdersFromAllShelfBasedOnOneProduct = receivedRpOrderService.getOrderByRpOrderID(receivedRpOrder.getRpOrderID(), receivedRpOrder.getReceivedRpProductName());
					Integer quantityReceived = new Integer(0);
					Integer quantityRejected = new Integer(0);
					if(null != receivedRpOrdersFromAllShelfBasedOnOneProduct) {
						for(ReceivedRpOrder recRpOrder:receivedRpOrdersFromAllShelfBasedOnOneProduct) {
							quantityReceived+=recRpOrder.getQuantityReceived();
							quantityRejected+=recRpOrder.getQuantityRejected();
						}
					}
//					System.out.println("total quantity unprocessed: " + o.getUnprocessedProductqty());
//					System.out.println("total quantity received: " + quantityReceived);
//					System.out.println("total quantity rejected: " + quantityRejected);
					//check if the added quantity exceeds ordered quantity
					if(quantityReceived + receivedRpOrder.getQuantityReceived() + quantityRejected + receivedRpOrder.getQuantityRejected()> o.getOrderedProductQty()) {
						model.addAttribute("receivedQtyError", "Error! Cantidad Recibida no puede ser mayor a la Cantidad Pedida!");
						return "redirect:/" + rpOrder.getRpOrderID() + "/receivedRpOrder";
					}else if(quantityReceived + receivedRpOrder.getQuantityReceived() + quantityRejected + receivedRpOrder.getQuantityRejected()< o.getOrderedProductQty()){
						model.addAttribute("saveReceivedQtyToAnotherLot", rpOrder.getRpOrderID());
						if(null != receivedRpOrdersByShelf) {
							System.out.println("received qty less than ordered qty -----------  received product exist in the lot");
							receivedRpOrdersByShelf.get(0).setQuantityReceived(receivedRpOrdersByShelf.get(0).getQuantityReceived() + receivedRpOrder.getQuantityReceived());
							receivedRpOrdersByShelf.get(0).setQuantityRejected(receivedRpOrdersByShelf.get(0).getQuantityRejected() + receivedRpOrder.getQuantityRejected());
							receiveProducts(receivedRpOrder.getReceivedRpProductName(), receivedRpOrder.getQuantityReceived());
							receivedRpOrdersByShelf.get(0).setTotalCost(receivedRpOrdersByShelf.get(0).getTotalCost() + totalCost);
							o.setReceivedProductqty(receivedRpOrdersByShelf.get(0).getQuantityReceived());
							o.setRejectedProdutqty(receivedRpOrdersByShelf.get(0).getQuantityRejected());
							receivedRpOrderService.createOrder(receivedRpOrdersByShelf.get(0));
						}else {
							System.out.println("received qty less than ordered qty -----------  received product does NOT exist in the lot");
							receivedRpOrderToBeSaved.setQuantityReceived(receivedRpOrder.getQuantityReceived());
							receivedRpOrderToBeSaved.setTotalCost(totalCost);
							if(receivedRpOrder.getQuantityRejected() == null) {
								receivedRpOrderToBeSaved.setQuantityRejected(quantityRejected);
							}else {
								receivedRpOrderToBeSaved.setQuantityRejected(receivedRpOrder.getQuantityRejected());
							}
							receiveProducts(receivedRpOrder.getReceivedRpProductName(), receivedRpOrder.getQuantityReceived());
							receivedRpOrderService.createOrder(receivedRpOrderToBeSaved);
							o.setReceivedProductqty(receivedRpOrder.getQuantityReceived());
							o.setRejectedProdutqty(receivedRpOrder.getQuantityRejected());
						}
						o.setUnprocessedProductqty(o.getOrderedProductQty() - (receivedRpOrder.getQuantityReceived() + receivedRpOrder.getQuantityRejected() + quantityReceived + quantityRejected));
						o.setUnreceivedProductqty(o.getUnreceivedProductqty() - receivedRpOrder.getQuantityReceived());						
						rpOrder.setRpProducts(rpProducts);
						System.out.println("saving rpOrder ----- ");
						replenishmentOrderService.createOrder(rpOrder);
						System.out.println("rpOrder saved ----");
						
						System.out.println("Received " + receivedRpOrder.getQuantityReceived() + " of " + receivedRpOrder.getReceivedRpProductName());
						
						if(closedRpOrder(rpOrder, quantityReceived, quantityRejected)) {
							model.addAttribute("rpOrderClosed", rpOrder.getRpOrderID());
							addShelfandLot(receivedRpOrderToBeSaved);
							return "redirect:/inventory?orderStatus=openOrder";
						}						
					}else if(quantityReceived + receivedRpOrder.getQuantityReceived() + quantityRejected + receivedRpOrder.getQuantityRejected() == o.getOrderedProductQty()){					
						System.out.println("received qty EQUAL to ordered qty ----------- received product exist in the lot");
						if(null != receivedRpOrdersByShelf) {
							receivedRpOrdersByShelf.get(0).setQuantityReceived(receivedRpOrdersByShelf.get(0).getQuantityReceived() + receivedRpOrder.getQuantityReceived());
							receivedRpOrdersByShelf.get(0).setQuantityRejected(receivedRpOrdersByShelf.get(0).getQuantityRejected() + receivedRpOrder.getQuantityRejected());
							receiveProducts(receivedRpOrder.getReceivedRpProductName(), receivedRpOrder.getQuantityReceived());
							receivedRpOrdersByShelf.get(0).setTotalCost(receivedRpOrdersByShelf.get(0).getTotalCost() + totalCost);
							receivedRpOrderService.createOrder(receivedRpOrdersByShelf.get(0));
							o.setReceivedProductqty(receivedRpOrdersByShelf.get(0).getQuantityReceived());
							o.setRejectedProdutqty(receivedRpOrdersByShelf.get(0).getQuantityRejected());							 
						}else {
							receivedRpOrderToBeSaved.setQuantityReceived(receivedRpOrder.getQuantityReceived());
							if(receivedRpOrder.getQuantityRejected() == null) {
								receivedRpOrderToBeSaved.setQuantityRejected(quantityRejected);
							}else {
								receivedRpOrderToBeSaved.setQuantityRejected(receivedRpOrder.getQuantityRejected());
							}
							receivedRpOrderToBeSaved.setTotalCost(totalCost);
							receiveProducts(receivedRpOrder.getReceivedRpProductName(), receivedRpOrder.getQuantityReceived());
							receivedRpOrderService.createOrder(receivedRpOrderToBeSaved);
							o.setReceivedProductqty(receivedRpOrder.getQuantityReceived());
							o.setRejectedProdutqty(receivedRpOrder.getQuantityRejected());							
						};
						o.setUnprocessedProductqty(o.getOrderedProductQty() - (receivedRpOrder.getQuantityReceived() + receivedRpOrder.getQuantityRejected() + quantityReceived + quantityRejected));
						o.setUnreceivedProductqty(o.getUnreceivedProductqty()-receivedRpOrder.getQuantityReceived());						
						rpOrder.setRpProducts(rpProducts);
						replenishmentOrderService.createOrder(rpOrder);
						System.out.println("Received all the quantity for product [" + receivedRpOrder.getReceivedRpProductName() + "]" );
						if(closedRpOrder(rpOrder, quantityReceived, quantityRejected)) {
							model.addAttribute("rpOrderClosed", rpOrder.getRpOrderID());
							addShelfandLot(receivedRpOrderToBeSaved);
							return "redirect:/inventory?orderStatus=openOrder";
						}
					}
					
					
				}
				//if qty received is equal to the ordered qty, close the replenishment order
				else {
					Collection<ReceivedRpOrder> receivedRpOrders = receivedRpOrderService.getOrderByRpOrderID(receivedRpOrder.getRpOrderID(), receivedRpOrder.getReceivedRpProductName());
					Integer quantityReceived = new Integer(0);
					Integer quantityRejected = new Integer(0);
					if(null != receivedRpOrders) {
						for(ReceivedRpOrder recRpOrder:receivedRpOrders) {
							quantityReceived+=recRpOrder.getQuantityReceived();
							quantityRejected+=recRpOrder.getQuantityRejected();
						}
					}
//					System.out.println("total quantity received: " + quantityReceived);
//					System.out.println("total quantity rejected: " + quantityRejected);
					Integer qtyReceived = new Integer(0);
					if(null != receivedRpOrders) {
						for(ReceivedRpOrder recRpOrder:receivedRpOrders) qtyReceived+=recRpOrder.getQuantityReceived();
					}
					if(qtyReceived + receivedRpOrder.getQuantityReceived() + quantityRejected + receivedRpOrder.getQuantityRejected() > o.getOrderedProductQty()) {
						model.addAttribute("receivedQtyError", "Error! Cantidad Recibida no puede ser mayor a la Cantidad Pedida!");
						return "redirect:/" + rpOrder.getRpOrderID() + "/receivedRpOrder";
					}
					if(null != receivedRpOrdersByShelf) {
						receivedRpOrdersByShelf.get(0).setQuantityReceived(receivedRpOrdersByShelf.get(0).getQuantityReceived() + receivedRpOrder.getQuantityReceived());
						receivedRpOrdersByShelf.get(0).setQuantityRejected(receivedRpOrdersByShelf.get(0).getQuantityRejected() + receivedRpOrder.getQuantityRejected());
						receiveProducts(receivedRpOrder.getReceivedRpProductName(), receivedRpOrder.getQuantityReceived());
						receivedRpOrdersByShelf.get(0).setTotalCost(receivedRpOrdersByShelf.get(0).getTotalCost() + totalCost);
						receivedRpOrderService.createOrder(receivedRpOrdersByShelf.get(0));
					}else {
						receivedRpOrderToBeSaved.setQuantityReceived(receivedRpOrder.getQuantityReceived());
						receivedRpOrderToBeSaved.setQuantityRejected(quantityRejected);
						receivedRpOrderToBeSaved.setTotalCost(totalCost);
						receiveProducts(receivedRpOrder.getReceivedRpProductName(), receivedRpOrder.getQuantityReceived());
						receivedRpOrderService.createOrder(receivedRpOrderToBeSaved);
					}
					o.setUnreceivedProductqty(o.getUnreceivedProductqty() - receivedRpOrder.getQuantityReceived());
					o.setUnprocessedProductqty(o.getOrderedProductQty() - (receivedRpOrder.getQuantityReceived() + receivedRpOrder.getQuantityRejected() + quantityReceived + quantityRejected));
					rpOrder.setRpProducts(rpProducts);
					replenishmentOrderService.createOrder(rpOrder);
					System.out.println("Received all the quantity for product [" + receivedRpOrder.getReceivedRpProductName() + "]" );
					if(closedRpOrder(rpOrder, quantityReceived, quantityRejected)) {
						model.addAttribute("rpOrderClosed", rpOrder.getRpOrderID());
						addShelfandLot(receivedRpOrderToBeSaved);
						return "redirect:/inventory?orderStatus=openOrder";
					}
				}
			}
		}	
		addShelfandLot(receivedRpOrderToBeSaved);
		return "redirect:/inventory?orderStatus=openOrder";
	}
	
	private void addShelfandLot(ReceivedRpOrder receivedRpOrder) {
		//adding shelf location for the product
		Product product = productService.getProductByName(receivedRpOrder.getReceivedRpProductName());
		System.out.println("product name for shelf location is: " + product.getProductName());
		if(!product.getShelfLocations().contains(receivedRpOrder.getShelfID())) {
			product.getShelfLocations().add(receivedRpOrder.getShelfID());
			productService.addOrUpdateProduct(product);
		}	
		//addling lot ID for the product
		if(!product.getLotID().contains(receivedRpOrder.getLotID())) {
			product.getLotID().add(receivedRpOrder.getLotID());
//			System.out.println("adding lotID" + receivedRpOrder.getLotID() + " to product " + product.getProductName());
			productService.addOrUpdateProduct(product);
		}
	}
	
	private void receiveProducts(String productName, Integer quantity) {
		Product product = productService.getProductByName(productName);
		if(null != product.getQuantity()) product.setQuantity(product.getQuantity() + quantity);
		else product.setQuantity(quantity);
		productService.addOrUpdateProduct(product);
	}
	
	private boolean closedRpOrder(ReplenishmentOrder rpOrder, Integer quantityReceived, Integer quantityRejected) {
		List<OrderedProd> rpOrderedProds = replenishmentOrderService.getOrder(rpOrder.getRpOrderID()).getRpProducts();
		System.out.println("checking if rpOrder is closed");
		boolean closeRpOrder = true;
		for(OrderedProd o:rpOrderedProds) {
			if(o.getUnprocessedProductqty() > 0) {
				System.out.println("close rpOrder is false");
				closeRpOrder = false;
			}			
		}
		if(closeRpOrder == true) {			
			rpOrder.setOrderStatus("Closed");
			replenishmentOrderService.createOrder(rpOrder);			
			System.out.println("rpOrder closed");
		}
		System.out.println("check complete");
		return closeRpOrder;
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
				//when adding a new product, need to update the unshipped rporder list
				List<ReplenishmentOrder> rpOrders = (List<ReplenishmentOrder>) replenishmentOrderService.listOrders();
				List<Order> orders = (List<Order>) orderService.listOrders();
				for(ReplenishmentOrder rp:rpOrders) {
					List<OrderedProd> products = rp.getRpProducts();
					OrderedProd newProd = new OrderedProd();
					newProd.setClientPrice(product.getClientPrice());
					newProd.setCost(product.getCost());
					newProd.setDistributorPrice(product.getDistributorPrice());
					newProd.setProductName(product.getProductName());
					newProd.setOrderedProductQty(0);
					newProd.setUnshippedProductqty(0);
					newProd.setUnreceivedProductqty(0);
					newProd.setVendorPrice(product.getVendorPrice());
					products.add(newProd);
					rp.setRpProducts(products);
					replenishmentOrderService.createOrder(rp);
				}
				for(Order o:orders) {
					List<OrderedProd> products = o.getProducts(); 
					OrderedProd newProd = new OrderedProd();
					newProd.setCost(product.getCost());
					newProd.setOrderedProductQty(0);
					newProd.setProductName(product.getProductName());
					newProd.setQuantity(product.getQuantityOnHand());
					newProd.setUnshippedProductqty(0);
					newProd.setUnreceivedProductqty(0);
					products.add(newProd);
					o.setProducts(products);
					orderService.createOrder(o);
				}
				productService.addOrUpdateProduct(product);
				System.out.println("Product added succesfully!");
				attr.addFlashAttribute("addProductSucceeded", "Usted ha agregado producto exitosamente " + product.getProductName() + "!");
			} catch (Exception e) {
				System.out.println(e);
			}
		}else {
			System.out.println("product exists, updating product");
			productInInventory.setCost(product.getCost());
//			product.setOrders(productToBeUpdated.getOrders());
			productInInventory.setDistributorPrice(product.getDistributorPrice());
			productInInventory.setVendorPrice(product.getVendorPrice());
			productInInventory.setClientPrice(product.getClientPrice());
			productInInventory.setProductName(product.getProductName());
			productService.addOrUpdateProduct(productInInventory);
			attr.addFlashAttribute("updateProductSucceeded", "Has actualizado el producto exitosamente " + product.getProductName() + "!");
		}
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
		attr.addFlashAttribute("deleteProductSucceeded", "El Producto de Hierro ha sido cancelado " + product.getProductName());
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
		product.setDistributorPrice(productToBeUpdated.getDistributorPrice());
		product.setVendorPrice(productToBeUpdated.getVendorPrice());
		product.setClientPrice(productToBeUpdated.getClientPrice());
		product.setProductName(productToBeUpdated.getProductName());
		productService.addOrUpdateProduct(product);
		attr.addFlashAttribute("updateProductSucceeded", "Has actualizado el producto exitosamente " + product.getProductName() + "!");
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
			product.setDistributorPrice(productInDB.getDistributorPrice());
			product.setVendorPrice(productInDB.getVendorPrice());
			product.setClientPrice(productInDB.getClientPrice());
			product.setCost(productInDB.getCost());
			products.add(product);
			String[] quantity = request.getParameterValues("num");
			try {
				Integer.parseInt(quantity[0]);
				if(Integer.parseInt(quantity[0]) <= 0) {
					model.addAttribute("shoppingCartQtyError", "Cantidad de Pedido no puede ser 0!");
					return "redirect:/orderReplenishment";
				}else if(Integer.parseInt(quantity[0]) > 10000000) {
					model.addAttribute("shoppingCartQtyError", "Cantidad Invalida!");
					return "redirect:/orderReplenishment";
				}else {
					for (int i = 0; i < products.size(); i++) {
						products.get(i).setOrderedProductQty(Integer.parseInt(quantity[0]));
					 }
				}	
			} catch (Exception e) {
				System.out.println(e);
				model.addAttribute("shoppingCartQtyError", "Cantidad Invalida!");
				return "redirect:/orderReplenishment";
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
				product.setDistributorPrice(productInDB.getDistributorPrice());
				product.setVendorPrice(productInDB.getVendorPrice());
				product.setClientPrice(productInDB.getClientPrice());
				product.setCost(productInDB.getCost());
				String[] quantity = request.getParameterValues("num");
				
				try {
					Integer.parseInt(quantity[0]);
					if(Integer.parseInt(quantity[0]) <= 0) {
						model.addAttribute("shoppingCartQtyError", "Cantidad de Pedido no puede ser 0!");
						return "redirect:/orderReplenishment";
					}else if(Integer.parseInt(quantity[0]) > 10000000) {
						model.addAttribute("shoppingCartQtyError", "Cantidad Invalida!");
						return "redirect:/orderReplenishment";
					}else {
						product.setOrderedProductQty(Integer.parseInt(quantity[0]));
						products.add(product);
					}	
				} catch (Exception e) {
					System.out.println(e);
					model.addAttribute("shoppingCartQtyError", "Cantidad Invalida!");
					return "redirect:/orderReplenishment";
				}			
				cart.setProducts(products);
				cartTotal = cartTotal(products, cart);
				model.addAttribute("addToCartSucceeded", "Ha Agregado " + product.getOrderedProductQty() + " [" + product.getProductName() + "] a su Pedido");
			}else {
				System.out.println("product exists in cart");
				for(OrderedProd p:products)System.out.println(p.getProductName());
				System.out.println("updating product qty");
				String[] quantityParam = request.getParameterValues("num");
				
				try {
					Integer.parseInt(quantityParam[0]);
					if(Integer.parseInt(quantityParam[0]) <= 0) {
						model.addAttribute("shoppingCartQtyError", "Cantidad de Pedido no puede ser 0!");
						return "redirect:/orderReplenishment";
					}else if(Integer.parseInt(quantityParam[0]) > 10000000) {
						model.addAttribute("shoppingCartQtyError", "Cantidad Invalida!");
						return "redirect:/orderReplenishment";
					}else {
						Integer quantity = products.get(index).getOrderedProductQty()+Integer.parseInt(quantityParam[0]);
						products.get(index).setOrderedProductQty(quantity);
					}	
				} catch (Exception e) {
					System.out.println(e);
					model.addAttribute("shoppingCartQtyError", "Cantidad Invalida!");
					return "redirect:/orderReplenishment";
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
		return "redirect:/orderReplenishment";
	}
	
	@RequestMapping(value="orderSummary",method=RequestMethod.GET)
	public String orderSummary(Model model, HttpSession session,
							@ModelAttribute("shoppingCartQtyError")String shoppingCartQtyError) {
		Cart cart = (Cart) session.getAttribute("cart");
		if(cart == null) {
			model.addAttribute("shoppingCartQtyError", "Cantidad de Pedido no puede ser 0!");
			return "redirect:/orderReplenishment";
		}
		model.addAttribute("orderSummary", "orderSummary");
		model.addAttribute("shoppingCartQtyError", shoppingCartQtyError);
		session.setAttribute("cart", cart);
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
						model.addAttribute("shoppingCartQtyError", "Cantidad de Pedido no puede ser 0");
						return "redirect:/orderSummary";
					}else if(Integer.parseInt(quantity[i]) > 10000000) {
						model.addAttribute("shoppingCartQtyError", "Cantidad Invalida!");
						return "redirect:/orderSummary";
					}else {
						products.get(i).setOrderedProductQty(Integer.parseInt(quantity[i]));
					}	
				} catch (Exception e) {
					System.out.println(e);
					model.addAttribute("shoppingCartQtyError", "Cantidad Invalida!");
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
			rpProduct.setDistributorPrice(productList.get(i).getDistributorPrice());
			rpProduct.setVendorPrice(productList.get(i).getVendorPrice());
			rpProduct.setClientPrice(productList.get(i).getClientPrice());
			rpProduct.setProductName(productList.get(i).getProductName());
			rpProduct.setQuantity(productList.get(i).getQuantity());
			rpProduct.setOrderedProductQty(0);
			rpProduct.setUnreceivedProductqty(0);
			rpProduct.setUnprocessedProductqty(0);
			rpProducts.add(rpProduct);
		}
		for(int i=0; i<productsInCart.size(); i++) {
			for(int j=0; j<rpProducts.size(); j++) {
				if(productsInCart.get(i).getProductName().equals(rpProducts.get(j).getProductName())) {
					Product productInDB = productService.getProductByName(productsInCart.get(i).getProductName());
					rpProducts.get(j).setCost(productInDB.getCost());
					rpProducts.get(j).setDistributorPrice(productInDB.getDistributorPrice());
					rpProducts.get(j).setVendorPrice(productInDB.getVendorPrice());
					rpProducts.get(j).setClientPrice(productInDB.getClientPrice());
					rpProducts.get(j).setProductName(productInDB.getProductName());
					rpProducts.get(j).setQuantity(productInDB.getQuantity());
					rpProducts.get(j).setOrderedProductQty(productsInCart.get(i).getOrderedProductQty());
					rpProducts.get(j).setUnreceivedProductqty(productsInCart.get(i).getOrderedProductQty());
					rpProducts.get(j).setUnprocessedProductqty(productsInCart.get(i).getOrderedProductQty());
					rpProducts.get(j).getRpOrders().add(rpOrder);
				}
			}
		}
		System.out.println("adding products-------");
		rpOrder.setRpProducts(rpProducts);
		replenishmentOrderService.createOrder(rpOrder);
		session.removeAttribute("cart");
		System.out.println("Replenishment order has been place");
		model.addAttribute("replenishmentOrderConfirmed", "A realizado una pedido de reposicion de producto!");
		return "redirect:/inventory";
	}
	
	private Double cartTotal(List<OrderedProd> products, Cart cart) {
		Double cartTotal = (double) 0;
		List<OrderedProd> p = cart.getProducts();
		for(OrderedProd a:p) {
			cartTotal+=a.getCost()*a.getOrderedProductQty()*1.16;//16% tax
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
