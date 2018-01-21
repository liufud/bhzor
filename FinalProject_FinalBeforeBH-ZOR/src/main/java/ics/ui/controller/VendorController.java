package ics.ui.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ics.model.Vendor;
import ics.services.VendorService;

@Controller
public class VendorController {
	@Autowired
	private VendorService vendorService;
	
	@RequestMapping(value="vendor",method=RequestMethod.POST)
	public String addVendor(@Valid @ModelAttribute("vendor")Vendor vendor, BindingResult bindingResult, RedirectAttributes attr) {
		System.out.println("addVendor is called");
		if(bindingResult.hasErrors()) {
			System.out.println("data binding unsuccessful");
			attr.addFlashAttribute("org.springframework.validation.BindingResult.vendor",bindingResult);
			attr.addFlashAttribute("vendor",vendor);
			return "redirect:/vendor";
		}
		System.out.println(vendor);
		vendorService.addOrUpdateVendor(vendor);
		System.out.println("vendor added...............................");
		return "redirect:/vendor";
	}
	
	@RequestMapping(value="vendor/{vendorID}/updateVendor",method=RequestMethod.GET)
	public String updateVendor(@PathVariable Long vendorID, RedirectAttributes attr) {
		System.out.println("updateVendor is called");
		Vendor vendor = vendorService.get(vendorID);
		attr.addFlashAttribute("vendor",vendor);
		attr.addFlashAttribute("update", "Update the fields you wish to change");
		return "redirect:/vendor";
	}
	
	@RequestMapping(value= "vendor",method=RequestMethod.GET)
	public String listVendor(Model model) {
		System.out.println("listVendor is called");
		if(!model.containsAttribute("vendor")) {
			System.out.println("add vendor model...");
			model.addAttribute("vendor",new Vendor());
		}		
		model.addAttribute("vendorList",vendorService.listVendors());
		
//		String errorcheck = (String)session.getAttribute("check");
//		System.out.println(errorcheck);
//		if(errorcheck != null && errorcheck.equals("noerror")) {
//			return "vendor";
//		}else if(errorcheck != null && errorcheck.equals("yeserror")){
//			session.setAttribute("error", "This vendor has associated products, delete these products first");
//		}
		return "vendor";
	}
	
	@RequestMapping(value="vendor/{vendorID}/deleteVendor", method=RequestMethod.GET)
	public String deleteVendor(@PathVariable Long vendorID, RedirectAttributes attr) {
		System.out.println("deleteVendor is called.......");
		try {
			vendorService.delete(vendorID);
		} catch (Exception e) {
			attr.addFlashAttribute("error", "please delete the associated products before deleting the vendor");
			System.out.println(e);
		}
		
		
		return "redirect:/vendor";
	}
	//logout handler, in case spring security logout is not used
/*	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		System.out.println("user logged out.....");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null){    
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}
		return "redirect:/login?logout";
	}*/
}
