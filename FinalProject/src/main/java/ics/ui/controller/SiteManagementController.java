package ics.ui.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
									@ModelAttribute("passwordError")String passwordError,
									@ModelAttribute("userForm")User user
									/*@ModelAttribute("count")String count,
									@ModelAttribute("offset")String offset*/) {		
		model.addAttribute("user", new User());
		model.addAttribute("userForm", user);
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
	
	@ModelAttribute("allVendorsName")
	public List<String> getAllVendors(Model model) {
		List<User> allVendors = userService.getUsersByRole("Vendor");
		List<String> allVendorNames = new ArrayList<String>();
		for(User u:allVendors) {
			allVendorNames.add(u.getFirstName() + " " + u.getLastName());
		}
		model.addAttribute("allVendorNames", allVendorNames);
		return allVendorNames;
	}
	
	@ModelAttribute("roleList")
    public List<String> getroleList() {
       List<String> roleList = new ArrayList<String>();
       roleList.add("Cliente");
       roleList.add("Vendedor");
       roleList.add("Distribuidor");
       roleList.add("Administrador");
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
		attr.addFlashAttribute("updateRoleSucceeded", "El papel del " + user.getUsername() + " se ha actualizado con éxito a " + role.getRoleName());
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
		attr.addFlashAttribute("userUpdateSucceeded", "Usted ha actualizado exitosamente usuario " + oldUser.getUsername() + "!");
		return "redirect:/siteManagement";
	}
	
	@RequestMapping(value="addUser",method=RequestMethod.GET)
	public String addUser(RedirectAttributes attr,
							@ModelAttribute("passwordError")String passwordError,
							Model model) {	
		attr.addFlashAttribute("addUser", "addUser");
		model.addAttribute("userForm", new User());
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
	
	
}
