package ics.ui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ics.model.Role;
import ics.model.User;
import ics.services.SecurityService;
import ics.services.UserService;

@Controller
@RequestMapping("register")
public class RegisterController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private SecurityService securityService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String registration(Model model) {
		System.out.println("registration is called..........");
		model.addAttribute("userForm", new User());
		return "registration";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String registration(@Valid @ModelAttribute("userForm")User user, 
								BindingResult bindingResult, Model model,HttpServletRequest request) {
		System.out.println("registering user..........");
		
		if(bindingResult.hasErrors()) {
			return "registration";
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
}
