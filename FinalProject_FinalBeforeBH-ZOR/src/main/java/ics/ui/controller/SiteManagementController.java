package ics.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ics.model.Role;
import ics.model.User;
import ics.services.RoleService;
import ics.services.UserService;

@Controller
public class SiteManagementController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	
	@RequestMapping(value="siteManagement",method=RequestMethod.GET)
	public String showSiteManagement(Model model, @ModelAttribute("userSelected")User userSelected,
									@ModelAttribute("userList")ArrayList<String> userList,
									@ModelAttribute("allUsers")ArrayList<User> allUsers,
									@ModelAttribute("check")String check,
									@ModelAttribute("checkUpdateRole")String checkUpdateRole,
									@ModelAttribute("allUsersByRole")ArrayList<User> allUsersByRole,
									@ModelAttribute("roleList")ArrayList<String> roleList,
									@ModelAttribute("viewUserByRole")String viewUserByRole,
									@ModelAttribute("userTobeUpdated")User userTobeUpdated,
									@ModelAttribute("roleTobeUpdated")Role roleTobeUpdated,
									@ModelAttribute("updateRoleSucceeded")String updateRoleSucceeded,
									@ModelAttribute("userInfoToBeUpdated")User userInfoToBeUpdated,
									@ModelAttribute("passwordError")String passwordError
									/*@ModelAttribute("count")String count,
									@ModelAttribute("offset")String offset*/) {		
		model.addAttribute("user", new User());
		model.addAttribute("userForm", new User());
		model.addAttribute("role", new Role());
		model.addAttribute("allUsers", allUsers);
		model.addAttribute("userSelected", userSelected);
		model.addAttribute("userList", userList);
		model.addAttribute("check", check);
		model.addAttribute("viewUserByRole", viewUserByRole);
		model.addAttribute("checkUpdateRole", checkUpdateRole);
		model.addAttribute("allUsersByRole", allUsersByRole);
		model.addAttribute("roleList", roleList);
		model.addAttribute("userTobeUpdated", userTobeUpdated);
		model.addAttribute("roleTobeUpdated", roleTobeUpdated);
		model.addAttribute("updateRoleSucceeded", updateRoleSucceeded);
		model.addAttribute("userInfoToBeUpdated", userInfoToBeUpdated);
		model.addAttribute("passwordError", passwordError);
//		model.addAttribute("count", count);
//		model.addAttribute("offset", offset);
		
		return "siteManagement";
	}
	
	@ModelAttribute("roleList")
    public List<String> getroleList() {
       List<String> roleList = new ArrayList<String>();
       roleList.add("Customer");
       roleList.add("Vendor");
       roleList.add("Distributor");
       roleList.add("Manager");
       return roleList;
    }
	
	@RequestMapping(value="allUsers",method=RequestMethod.GET)
	public String listAllUsers(RedirectAttributes attr, Integer offset, Integer maxResults) {
		List<User> allUsers = userService.getAllUsers();
		attr.addFlashAttribute("allUsers", allUsers);
//		attr.addFlashAttribute("allUsers", userService.list(offset, maxResults));
//		attr.addFlashAttribute("count", userService.totalUserNum());
//		attr.addFlashAttribute("offset", offset);
		attr.addFlashAttribute("viewUserByRole", "viewUserByRole");
		return "redirect:/siteManagement";
	}	
	
	@RequestMapping(value="user/{userId}/updateRole",method=RequestMethod.GET)
	public String updateRole(@PathVariable Long userId, RedirectAttributes attr) {
		System.out.println("updateRole is called");
		User user = userService.findUser(userId);
		Role role = roleService.findRoleByUserId(userId);
		List<User> allUsers = userService.getAllUsers();
		attr.addFlashAttribute("allUsers", allUsers);
		attr.addFlashAttribute("userTobeUpdated",user);
		attr.addFlashAttribute("roleTobeUpdated", role);
		attr.addFlashAttribute("checkUpdateRole", "check");
		return "redirect:/siteManagement";
	}
	
	@RequestMapping(value="user/{userId}/updateRole",method=RequestMethod.POST)
	public String updateRole(@Valid@ModelAttribute("roleTobeUpdated")Role role,
							@PathVariable("userId")Long userId,
							ModelMap model, BindingResult bindingResult, 
							RedirectAttributes attr) {
		System.out.println("Role updating..........");
		if(bindingResult.hasErrors()) {
			System.out.println("data binding unsuccessful");
			attr.addFlashAttribute("org.springframework.validation.BindingResult.user",bindingResult);
			attr.addFlashAttribute("roleTobeUpdated",role);
			return "redirect:/siteManagement";
		}
		Role oldRole = roleService.findRoleByUserId(userId);
		oldRole.setRoleName(role.getRoleName());
		User user = userService.findUser(userId);
		user.setRoleName(role.getRoleName());
		roleService.updateRole(oldRole);
		userService.editUser(user);
		attr.addFlashAttribute("updateRoleSucceeded", "Role of " + user.getUsername() + " has successfully been updated to " + role.getRoleName());
		System.out.println("Role updated..........");
		return "redirect:/allUsers";
	}
	
	@RequestMapping(value="showUsersByRole",method=RequestMethod.POST)
	public String showUsersByRole(@ModelAttribute("role")Role role, RedirectAttributes attr) {
		String roleName = role.getRoleName();
		ArrayList<User> allUsersByRole = (ArrayList<User>) userService.getUsersByRole(roleName);
		attr.addFlashAttribute("allUsersByRole", allUsersByRole);
		attr.addFlashAttribute("viewUserByRole", "viewUserByRole");
		return "redirect:/siteManagement";
	}
	
	@RequestMapping(value="user/{userId}/updateInfo",method=RequestMethod.GET)
	public String updateInfo(@PathVariable("userId")Long userId,
							RedirectAttributes attr) {
		User userFound = userService.findUser(userId);
		attr.addFlashAttribute("userInfoToBeUpdated", userFound);
		return "redirect:/siteManagement";
	}
	
	@RequestMapping(value="updateUserInfo",method=RequestMethod.POST)
	public String updateInfo(@Valid@ModelAttribute("userInfoToBeUpdated")User user, BindingResult bindingResult, Model model,
							RedirectAttributes attr) {		
		if(bindingResult.hasErrors()) {
			System.out.println("data binding unsuccessful");
			model.addAttribute("org.springframework.validation.BindingResult.user",bindingResult);
			model.addAttribute("userInfoToBeUpdated",user);
			return "siteManagement";
		}else if(!user.getPassword().equals(user.getPasswordConfirm())) {
			System.out.println("passwords don't match");
			model.addAttribute("userInfoToBeUpdated",user);
			model.addAttribute("passwordError", "Please enter the same password!");
			return "siteManagement";
			}
		User oldUser = userService.findUser(user.getUserId());
		updateUser(oldUser, user);
		userService.editUser(oldUser);
		attr.addFlashAttribute("userUpdateSucceeded", "You have successfully updated user" + oldUser.getUsername() + "!");
		return "redirect:/siteManagement";
	}
	
	@RequestMapping(value="addUser",method=RequestMethod.GET)
	public String addUser(RedirectAttributes attr,
							@ModelAttribute("passwordError")String passwordError) {	
		attr.addFlashAttribute("addUser", "addUser");
		attr.addFlashAttribute("passwordError", passwordError);
		return "redirect:/siteManagement";
	}
	
	@RequestMapping(value="user/{userId}/deleteUser", method=RequestMethod.GET)
	public String deleteUser(@PathVariable Long userId, RedirectAttributes attr) {
		System.out.println("deleteUser is called");
		userService.deleteUser(userId);
		
		return "redirect:/allUsers";
	}
	
	@RequestMapping(value="addUser/backTOsiteManagement",method=RequestMethod.GET)
	public String goBackTositeManagement() {
		return "redirect:/siteManagement";
	}
	
	@RequestMapping(value="addUser/backTOallUsers",method=RequestMethod.GET)
	public String goBackToallUsers() {
		return "redirect:/allUsers";
	}
	
	@RequestMapping(value="viewUserInfo",method=RequestMethod.GET)
	public String viewUserInfo(RedirectAttributes attr) {
		List<User> users = userService.getAllUsers();
		List<String> userList = new ArrayList<String>();
		for(User user:users) {
			userList.add(user.getUsername());
		}
		attr.addFlashAttribute("userList", userList);
		return "redirect:/siteManagement";
	}
	
	@RequestMapping(value="viewUserInfo",method=RequestMethod.POST)
	public String viewUserInfo(@ModelAttribute("user")User user, RedirectAttributes attr) {
		String username = user.getUsername();
		User userSelected = userService.findUserByName(username);
		attr.addFlashAttribute("userSelected", userSelected);
		attr.addFlashAttribute("check", "check");
		return "redirect:/siteManagement";
	}
	
	private static void updateUser(User oldUser, User newUser) {
		oldUser.setAddress(newUser.getAddress());
		oldUser.setCity(newUser.getCity());
		oldUser.setEmail(newUser.getEmail());
		oldUser.setFirstName(newUser.getFirstName());
		oldUser.setLastName(newUser.getLastName());
		oldUser.setPassword(newUser.getPassword());
		oldUser.setPasswordConfirm(newUser.getPasswordConfirm());
		oldUser.setPhoneNumber(newUser.getPhoneNumber());
		oldUser.setState(newUser.getState());
		oldUser.setUsername(newUser.getUsername());
		oldUser.setZip(newUser.getZip());
	}
	
	
}
