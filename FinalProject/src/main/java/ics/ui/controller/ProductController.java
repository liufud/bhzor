package ics.ui.controller;

import java.util.Collection;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ics.model.Product;
import ics.model.Vendor;
import ics.services.ProductService;
import ics.services.VendorService;

@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private VendorService vendorService;
	
	
//	@RequestMapping(value="vendor/{vendorID}/createproducts",method=RequestMethod.POST)
//	public String addProduct(@PathVariable Long vendorID,@Valid Product product, 
//			BindingResult bindingResult, Model model, RedirectAttributes attr, HttpSession session) {
//		System.out.println("addProduct is called");
//		if(bindingResult.hasErrors()) {
//			System.out.println("data binding unsuccessful");
//			attr.addFlashAttribute("org.springframework.validation.BindingResult.product",bindingResult);
//			attr.addFlashAttribute("product",product);
//			attr.addFlashAttribute("createProduct", "create product");
//			return "redirect:/vendor/" + vendorID + "/products";
//		}
//		try {		
//			attr.addFlashAttribute("createProduct", "create product");
//			Vendor vendor = vendorService.get(vendorID);
//			vendor.getProducts().add(product);
//			product.setVendor(vendor);
//			System.out.println("saving product...................");
//			vendorService.addOrUpdateVendor(vendor);
//			System.out.println("product saved...................");
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//		return "redirect:/vendor/" + vendorID + "/products";
//	}
	
	@RequestMapping(value="vendor/{vendorID}/products",method=RequestMethod.GET)
	public String listProduct(@PathVariable Long vendorID, Model model) {
		Vendor vendor = vendorService.get(vendorID);
		Product newproduct = new Product();
		newproduct.setVendor(vendor);
		System.out.println("listProduct is called");
		if(!model.containsAttribute("product")) {
			System.out.println("model did not exist on the page, creating a new one...");
			model.addAttribute("product",newproduct);
		}		
		model.addAttribute("vendor",vendor);
		model.addAttribute("productList",vendor.getProducts());

		return "products";
	}
	
	@RequestMapping(value="products",method=RequestMethod.GET)
	public ModelAndView listProduct() {
		System.out.println("listProduct is called");
		ModelAndView model = new ModelAndView();
		Collection<Product> productsList = productService.listProducts();
		model.addObject("product",new Product());
		model.addObject("productList",productsList);
		model.setViewName("products");

		return model;
	}
	
	@RequestMapping(value= {"products/{productID}/updateProduct","vendor/{vendorID}/products/{productID}/updateProduct"},method=RequestMethod.GET)
	public String updateProduct(@PathVariable Long productID, RedirectAttributes attr,
								@PathVariable Long vendorID) {
		System.out.println("updateProduct is called");
		Product product = productService.get(productID);
		attr.addFlashAttribute("product", product);
		attr.addFlashAttribute("update", "Update the fields you wish to change");
		return "redirect:/vendor/" + vendorID + "/products";
	}
	
//	@RequestMapping(value="vendor/{vendorID}/products",method=RequestMethod.POST)
//	public String updateProduct(@PathVariable Long vendorID,@Valid Product product, 
//			BindingResult bindingResult, RedirectAttributes attr) {
//		System.out.println("product updated!............");
//		if(bindingResult.hasErrors()) {
//			System.out.println("data binding unsuccessful");
//			attr.addFlashAttribute("org.springframework.validation.BindingResult.product",bindingResult);
//			attr.addFlashAttribute("product",product);
//			return "redirect:/vendor/" + vendorID + "/products";
//		}
//		try {
//			productService.addOrUpdateProduct(product);
//		} catch (Exception e) {
//			System.out.println(e);
//		}		
//		return "redirect:/vendor/" + vendorID + "/products";
//	}
	
	@RequestMapping(value="vendor/{vendorID}/customer")
	public String redirectToCustomer() {
		System.out.println("redirecting to Custosmer....");
		return "redirect:/customer";
	}
	@RequestMapping(value="vendor/{vendorID}/vendor")
	public String redirectToVendor() {
		System.out.println("redirecting to Vendor....");
		return "redirect:/vendor";
	}
	@RequestMapping(value="vendor/{vendorID}/products")
	public String redirectToProduct() {
		System.out.println("redirecting to Product....");
		return "redirect:/product";
	}
	@RequestMapping(value="vendor/{vendorID}/invoice")
	public String redirectToOrder() {
		System.out.println("redirecting to Invoice....");
		return "redirect:/invoice";
	}
	
//	@RequestMapping(value="products",method=RequestMethod.GET)
//	public String listProduct(Model model) {
//		System.out.println("listProduct is called");
//		model.addAttribute("product",new Product());
//		model.addAttribute("productList",productService.listProducts());
//
//		return "products";
//	}
	
//	@RequestMapping(value= {"vendor/{vendorID}/products/{vendorID}/{productID}"}, method=RequestMethod.GET)
//	public String deleteProduct(@PathVariable(name="vendorID") Long vendorID, 
//								@PathVariable(name="productID") Long productID, RedirectAttributes attr) {
//		System.out.println("deleteProduct is called");
//		try {
//			productService.delete(productID);
//		} catch (Exception e) {
//			attr.addFlashAttribute("error", "cannot delete due to the associated orders");
//			System.out.println(e);
//		}		
//		return "redirect:/vendor/" + vendorID + "/products";
//	}
	
	@RequestMapping(value= "products/{vendorID}/{productID}", method=RequestMethod.GET)
	public String deleteProductOnProductPage(@PathVariable(name="vendorID") Long vendorID, 
								@PathVariable(name="productID") Long productID, RedirectAttributes attr) {
		System.out.println("deleteProduct is called");
		try {
			productService.delete(productID);
		} catch (Exception e) {
			attr.addFlashAttribute("error", "cannot delete due to the associated orders");
			System.out.println(e);
		}
		return "redirect:/products";
	}
	
	@RequestMapping(value="vendor/{vendorID}/vendor/{vendorID}/back", method=RequestMethod.GET)
	public String goBackToVendor() {
		System.out.println("go back is called");
		return "redirect:/vendor";
	}
	
	@RequestMapping(value="products/back",method=RequestMethod.GET)
	public String goBackToProduct() {
		return "redirect:/products";
	}
	
//	@RequestMapping(value="products/{productID}",method=RequestMethod.GET)
//	public String placeOrder(@PathVariable Long productID, RedirectAttributes attr) {
//		Product product = productService.get(productID);
//		attr.addFlashAttribute("product",product);
//		return "redirect:/products";
//	}
	
	///////////////////////////////////////////////////////
	

}
