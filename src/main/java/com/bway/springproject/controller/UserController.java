package com.bway.springproject.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.bway.springproject.model.User;
import com.bway.springproject.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
@Slf4j //for logger
@Controller
public class UserController {
	
	//private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userSevice;
	
   @GetMapping({"/","/login"})
	public String getLogin() {
		
		return"LoginForm";
	}
   @PostMapping("/login")
   public String postLogin(@ModelAttribute User user, Model model, HttpSession session) {
	   
	    User usr = userSevice.userLogin(user.getEmail(), user.getPassword());
	   
	    if(usr != null) {
	    	
	    	log.info("------- login success --------");
	    	session.setAttribute("activeuser", usr);
	    	session.setMaxInactiveInterval(300);
	    	
	    	//model.addAttribute("fname",usr.getFname());
	    	return"Home";
	    }
	   log.info("------- login failed -------");
	    model.addAttribute("message","User not found !!");
	   return"LoginForm";
   }
   @GetMapping("/signup")
   public String getSignup() {
	   
	   return"SignUpForm";
   }
   @PostMapping("/signup")
   public String postSignup(@ModelAttribute User user) {
	   
	 userSevice.userSignup(user);
	   
	   return"LoginForm";
   }
   @GetMapping("/logout")
   public String logout(HttpSession session) {
	   
	   session.invalidate(); // session kill
	   log.info("------ logout success ------");
	   
	   return"LoginForm";
   }
   @GetMapping("/profile")
   public String getProfile() {
	   
	   return"Profile";
   }
}
