package ics.ui.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class LoginController {

	@RequestMapping("/login")
	public final String doLogin() {
		System.out.println("doLogin called...........");
		return "login";
	}
	
	@RequestMapping("/home")
	public String homePage() {
		System.out.println("landing on homepage");
		return "home";
	}
	
	@RequestMapping(value="back", method=RequestMethod.GET)
	public String goBackToHome() {
		System.out.println("go back is called");
		return "redirect:/home";
	}
	
	
//	@RequestMapping(value= "/login/{error}", method=RequestMethod.GET)
//	public final String doLoginError(Model model, @PathVariable final String error) {
//		System.out.println("doLoginError called...........");
//		model.addAttribute("error",error);
//		return "login";
//	}
}
