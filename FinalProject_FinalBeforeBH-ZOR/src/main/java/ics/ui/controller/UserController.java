package ics.ui.controller;

import java.util.List;

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

import ics.model.Order;
import ics.model.User;
import ics.services.OrderService;
import ics.services.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value= "customer",method=RequestMethod.GET)
	public String listCustomer(Model model, @ModelAttribute("userHasOrder") String customerWithOrder,
								RedirectAttributes attr) {
		System.out.println("listCustomer is called");
		if(!model.containsAttribute("customer")) {
			System.out.println("load customer model....");
			model.addAttribute("customer",new User());
		}		
		model.addAttribute("customerList",userService.getAllUsers());
		attr.addFlashAttribute("userHasOrder", customerWithOrder);
		return "customer";
	}
	
	@RequestMapping(value="customer/{username}/updateCustomer",method=RequestMethod.GET)
	public String updateCustomer(@PathVariable String username, RedirectAttributes attr) {
		System.out.println("updateCustomer is called");
		User user = userService.findUserByName(username);
		attr.addFlashAttribute("customer",user);
		return "redirect:/customer";
	}
	
	@RequestMapping(value="customer",method=RequestMethod.POST)
	public String updateCustomer(@Valid User user, BindingResult bindingResult, RedirectAttributes attr) {
		System.out.println("updateCustomer is called");
		if(bindingResult.hasErrors()) {
			System.out.println("data binding unsuccessful");
			attr.addFlashAttribute("org.springframework.validation.BindingResult.user",bindingResult);
			attr.addFlashAttribute("customer",user);
			return "redirect:/customer";
		}
		System.out.println(user);
		userService.editUser(user);
		
		return "redirect:/customer";
	}
	
	@RequestMapping(value="customer/{userId}/deleteCustomer", method=RequestMethod.GET)
	public String deleteCustomer(@PathVariable Long userId, RedirectAttributes attr) {
		System.out.println("deleteCustomer is called");
		List<Order> orderList = orderService.getOrderByUsername(userService.findUser(userId).getUsername());
		if(orderList != null) {
			attr.addFlashAttribute("userHasOrder", "cannot delete cutomer who has placed orders");
			return "redirect:/customer";
		}
		userService.deleteUser(userId);
		
		return "redirect:/customer";
	}
	
	@RequestMapping(value="customer/{orderID}/{username}/deleteOrder", method=RequestMethod.GET)
	public String deleOrder(@PathVariable(name="orderID") Long orderID, @PathVariable(name="username") String username, RedirectAttributes attr) {
		System.out.println("deleteOrder is called");
		orderService.delete(orderID);
		
		try {
			List<Order> orderList = orderService.getOrderByUsername(username);
			System.out.println(orderList);
			if(orderList!= null) {		
				attr.addFlashAttribute("orderplacedby", orderList.get(0).getCreateByUser());
				attr.addFlashAttribute("orderList",orderList);
			}else {
				System.out.println("order list is null.................");
				attr.addFlashAttribute("error","This customer hasn't place any order yet!");
				return "redirect:/customer";
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/customer";
	}
	
	@RequestMapping(value="customer/{username}/viewOrder", method=RequestMethod.GET)
	public String viewOrder(@PathVariable String username, RedirectAttributes attr) {
		System.out.println("viewOrder is called...........");
		try {
			List<Order> orderList = orderService.getOrderByUsername(username);
			System.out.println(orderList);
			if(orderList!= null) {		
				attr.addFlashAttribute("orderplacedby", orderList.get(0).getCreateByUser());
				attr.addFlashAttribute("orderList",orderList);
			}else {
				System.out.println("order list is null.................");
				attr.addFlashAttribute("error","This customer hasn't place any order yet!");
				return "redirect:/customer";
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}		
		return "redirect:/customer";
	}
}
