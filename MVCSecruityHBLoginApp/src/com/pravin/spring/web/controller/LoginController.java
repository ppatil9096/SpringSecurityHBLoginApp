package com.pravin.spring.web.controller;

import java.security.Principal;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@ComponentScan("com.pravin.spring.*")
public class LoginController {
    // @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    // public ModelAndView welcomePage() {
    // ModelAndView model = new ModelAndView();
    // model.setViewName("welcomePage");
    // return model;
    // }
    //
    // @RequestMapping(value = { "/homePage" }, method = RequestMethod.GET)
    // public ModelAndView homePage() {
    // ModelAndView model = new ModelAndView();
    // model.setViewName("homePage");
    // return model;
    // }
    //
    // @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
    // public ModelAndView loginPage(@RequestParam(value = "error", required =
    // false) String error,
    // @RequestParam(value = "logout", required = false) String logout) {
    //
    // ModelAndView model = new ModelAndView();
    // if (error != null) {
    // model.addObject("error", "Invalid Credentials");
    // }
    //
    // if (logout != null) {
    // model.addObject("message", "Logged out successfully.");
    // }
    //
    // model.setViewName("loginPage");
    // return model;
    // }

    @RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
    public String welcomePage(Model model) {
	model.addAttribute("title", "Welcome");
	model.addAttribute("message", "This is welcome page!");
	return "welcomePage";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model) {
	return "adminPage";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {

	return "loginPage";
    }

    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model) {
	model.addAttribute("title", "Logout");
	return "logoutSuccessfulPage";
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {

	// After user login successfully.
	String userName = principal.getName();
	model.addAttribute("message", userName);

	System.out.println("User Name: " + userName);

	return "userInfoPage";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {

	if (principal != null) {
	    model.addAttribute("message",
		    "Hi " + principal.getName() + "<br> You do not have permission to access this page!");
	} else {
	    model.addAttribute("msg", "You do not have permission to access this page!");
	}
	return "403Page";
    }
}
