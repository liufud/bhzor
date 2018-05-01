package ics.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ics.model.Role;
import ics.model.User;
import ics.services.SecurityService;
import ics.services.UserService;

@Controller
public class RegisterController {
	@Autowired
	private UserService userService;
	
//	@Autowired
//	private SecurityService securityService;
	
	@RequestMapping(value= {"register","addNewUser"},method=RequestMethod.GET)
	public String registration(Model model, @ModelAttribute("addUser")String addUser) {
		System.out.println("registration is called..........");
		model.addAttribute("userForm", new User());
		model.addAttribute("addUser", addUser);
		return "registration";
	}
	
	@RequestMapping(value="register",method=RequestMethod.POST)
	public String registration(@Valid @ModelAttribute("userForm")User user, 
								BindingResult bindingResult, Model model,HttpServletRequest request,
								RedirectAttributes attr) {
		System.out.println("registering user..........");
		if(bindingResult.hasErrors()) {
			System.out.println("error occured while registering");
			return "registration";
		}else if(!user.getPassword().equals(user.getPasswordConfirm())) {
			System.out.println("passwords don't match");
			attr.addFlashAttribute("passwordError", "Please enter the same password!");
			return "redirect:/register";
		}
		try {
			Role role = new Role();
			role.setRoleName("Customer");
			user.getRoles().add(role);
			user.setRoleName("Customer");
			user.setEnabled(true);
			role.setUser(user);
			userService.addUser(user);
			System.out.println("user registration successful");
		} catch (Exception e) {
			System.out.println(e);
		}
		
		//securityService.autologin(user.getUsername(), user.getPassword(),request);
		
		return "login";
	}
	
	@RequestMapping(value= {"siteManagement","addNewClient"},method=RequestMethod.POST)
	public String addUser(@Valid @ModelAttribute("userForm")User user, 
						BindingResult bindingResult, Model model,HttpServletRequest request,
						RedirectAttributes attr) {
		System.out.println("registering user..........");
		if(bindingResult.hasErrors()) {
		System.out.println("error occured while registering");
		model.addAttribute("org.springframework.validation.BindingResult.user",bindingResult);
		model.addAttribute("addUser", "addUser");
		return "siteManagement";
		}else if(!user.getPassword().equals(user.getPasswordConfirm())) {
		System.out.println("passwords don't match");
		attr.addFlashAttribute("passwordError", "Please enter the same password!");
		attr.addFlashAttribute("userForm", user);
		attr.addFlashAttribute("addUser", "addUser");
		return "redirect:/siteManagement";
		}
		String roleName = request.getParameter("roleList");
		if(!roleName.equals("Choose...")) {
			String vendorName = (String) request.getParameter("_vandorName");
			if(!vendorName.isEmpty() && !vendorName.equals("Choose...") && roleName.equals("Cliente")) {
				System.out.println("vendorName is: " + vendorName);
				User vendor = userService.getUserByName(vendorName);
				user.setVendor(vendor);
				vendor.getCustomer().add(user);
			}
			Role role = new Role();
			role.setRoleName(roleName);
			user.getRoles().add(role);
			user.setEnabled(true);
			user.setRoleName(roleName);
			role.setUser(user);		
		}		
				
		try {
			userService.addUser(user);			
			System.out.println("user registration successful");
		} catch (Exception e) {
			System.out.println(e);
		}
		attr.addFlashAttribute("addSucceeded", "Usuario " + user.getUsername() + " ha sido agregado exitosamente!");
		//securityService.autologin(user.getUsername(), user.getPassword(),request);
		if(request.getRequestURI().contains("addNewClient")) {
			return "redirect:/checkout";
		}
		
		return "redirect:/siteManagement";
	}
}
