package ics.ui.controller;

import java.util.ArrayList;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ics.model.Cart;
import ics.model.Order;
import ics.model.Product;
import ics.model.ReplenishmentOrder;
import ics.model.Vendor;
import ics.services.CartService;
import ics.services.ProductService;
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
	
	@RequestMapping(value="inventory",method=RequestMethod.GET)
	public String showInventory(Model model, HttpSession session,
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
								@ModelAttribute("replenishmentOrderConfirmed")String replenishmentOrderConfirmed) {
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
//		System.out.println("Replenishmen order info: " + replenishmentOrderConfirmed);
		if(!orderSummary.isEmpty()) {
			Cart cart = (Cart) session.getAttribute("cart");
			model.addAttribute("cart", cart);
			List<Product> products = cart.getProducts();
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
		return "inventory";
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
				productService.addOrUpdateProduct(product);
				System.out.println("Product added succesfully!");
				attr.addFlashAttribute("addProductSucceeded", "You have successfully added product " + product.getProductName() + "!");
			} catch (Exception e) {
				System.out.println(e);
			}
		}else {
			productInInventory.setCost(product.getCost());
//			product.setOrders(productToBeUpdated.getOrders());
			productInInventory.setPrice(product.getPrice());
			productInInventory.setProductName(product.getProductName());
			productService.addOrUpdateProduct(productInInventory);
			attr.addFlashAttribute("updateProductSucceeded", "You have successfully updated product " + product.getProductName() + "!");
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
			List<Product> products = new ArrayList<Product>();			
			Product productInDB = productService.get(productID);
			Product product = new Product();
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
						products.get(i).setQuantity(Integer.parseInt(quantity[0]));
					 }
				}	
			} catch (Exception e) {
				System.out.println(e);
				model.addAttribute("shoppingCartQtyError", "Quantity has to be integer");
				return "redirect:/orderReplenishment";
			}
					
			cart.setProducts(products);
			model.addAttribute("addToCartSucceeded", "You have added " + product.getQuantity() + " [" + product.getProductName() + "] to your shopping cart");
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
			List<Product> products = cart.getProducts();
			Double cartTotal = (double) 0;
			
			//use method isExisting
			System.out.println("checking if product added to cart exists in cart");
			int index = isExisting(productID, session);
			System.out.println("isExisting method gives index " + index);
			if(index == -1) {
				System.out.println("product doesn't exist in cart");
				Product productInDB = productService.get(productID);
				Product product = new Product();
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
						product.setQuantity(Integer.parseInt(quantity[0]));
						products.add(product);
					}	
				} catch (Exception e) {
					System.out.println(e);
					model.addAttribute("shoppingCartQtyError", "Quantity has to be integer");
					return "redirect:/orderReplenishment";
				}			
				cart.setProducts(products);
				cartTotal = cartTotal(products, cart);
				model.addAttribute("addToCartSucceeded", "You have added " + product.getQuantity() + " [" + product.getProductName() + "] to your shopping cart");
			}else {
				System.out.println("product exists in cart");
				for(Product p:products)System.out.println(p.getProductName());
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
						Integer quantity = products.get(index).getQuantity()+Integer.parseInt(quantityParam[0]);
						products.get(index).setQuantity(quantity);
					}	
				} catch (Exception e) {
					System.out.println(e);
					model.addAttribute("shoppingCartQtyError", "Quantity has to be integer");
					return "redirect:/orderReplenishment";
				}	
				System.out.println("product qty updated");
				cart.setProducts(products);
				cartTotal = cartTotal(products, cart);
				Product product = products.get(index);
				model.addAttribute("addToCartSucceeded", "You have added " + product.getQuantity() + " [" + product.getProductName() + "] to your shopping cart");
			}
			
			cart.setCartTotal(cartTotal);
			session.setAttribute("cart", cart);
			Cart newCart = cartService.get(cart.getCartId());			
			cartService.addOrUpdateCart(newCart);
		}
		return "redirect:/orderReplenishment";
	}
	
	@RequestMapping(value="orderSummary",method=RequestMethod.GET)
	public String orderSummary(Model model, HttpSession session) {
		Cart cart = (Cart) session.getAttribute("cart");
		if(cart == null) {
			model.addAttribute("shoppingCartQtyError", "Order Quantity cannot be 0");
			return "redirect:/orderReplenishment";
		}
		model.addAttribute("orderSummary", "orderSummary");
		return "redirect:/inventory";
	}
	
	@RequestMapping(value="updateRpCart",method=RequestMethod.POST)
	 public String updateCart(HttpServletRequest request, HttpSession session) {
		 Cart cart = (Cart) session.getAttribute("cart");
		 List<Product> products = cart.getProducts();
		 String[] quantity = request.getParameterValues("quantity");
		 for (int i = 0; i < products.size(); i++) {
			products.get(i).setQuantity(Integer.parseInt(quantity[i]));
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
		List<Product> products = cart.getProducts();
		List<Product> rpProducts = new ArrayList<Product>();
		for(Product p:products) {
			Product rpProduct = new Product();
			Product productInDB = productService.getProductByName(p.getProductName());
			rpProduct.setProductID(productInDB.getProductID());
			rpProduct.setCost(productInDB.getCost());
			rpProduct.setPrice(productInDB.getPrice());
			rpProduct.setProductName(productInDB.getProductName());
			rpProduct.setQuantity(productInDB.getQuantity());
			rpProducts.add(rpProduct);
		}
		System.out.println("adding products-------");
		rpOrder.setProducts(rpProducts);
		replenishmentOrderService.createOrder(rpOrder);
		session.removeAttribute("cart");
		System.out.println("Replenishment order has been place");
		model.addAttribute("replenishmentOrderConfirmed", "You have placed an replenishment order!");
		return "redirect:/inventory";
	}
	
	private Double cartTotal(List<Product> products, Cart cart) {
		Double cartTotal = (double) 0;
		List<Product> p = cart.getProducts();
		for(Product a:p) {
			cartTotal+=a.getCost()*a.getQuantity();
		}
		return cartTotal;
	 }
	
	private int isExisting(Long productID, HttpSession session) {
		 Cart cart = (Cart) session.getAttribute("cart");
		 List<Product> products = cart.getProducts();
	
		  for (int i = 0; i < products.size(); i++)
	
		   if (products.get(i).getProductID() == productID)
		    return i;
	
		  return -1;
	 }
}
