package com.brodwy.MyEcommerce.controller;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.brodwy.MyEcommerce.model.User;
import com.brodwy.MyEcommerce.service.UserService;
import com.brodwy.MyEcommerce.utils.VerifyRecaptcha;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	

	@GetMapping("/signup")
	public String getsignup() {
		
		return "signup";
	}
	
	@PostMapping("/signup")
	public String postsignup(@ModelAttribute User user,Model model) {
		
		User susr=userService.finUser(user.getEmail());
		if(susr==null) {
			
			user.setRole("customer");
			userService.signup(user);
			return "login";
		}
		
		model.addAttribute("mesg", "email already exist!!");
		return "signup";

	}
	
	
	
	
	
//	login
	
	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}
	
	@PostMapping("/login")
	public String postLogin(@ModelAttribute User user,Model model,HttpSession session,@RequestParam("g-recaptcha-response") String grcode ) throws IOException {
		
		if(VerifyRecaptcha.verify(grcode)) {
			
			User usr= userService.login(user.getEmail(), user.getPassword());
			if(usr!=null) {
				session.setAttribute("activeUser", usr);
//				session.setMaxInactiveInterval(500);
				session.setAttribute("activeUser.id", usr.getId());
				
				if(usr.getRole().equalsIgnoreCase("admin")) {
					return "admin/index";
				}
				
				return "redirect:/index";
			}
			else {
				model.addAttribute("msg", "user not found!!");
				return"login";
			}
		}
			
		else {
			model.addAttribute("msg", "you are robot!!");
			return"login";
		}
			
		
		

	}
	
	
//	logout
	
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
		
	}
	
//	userList
	
	@GetMapping("/userlist")
	public String getUserList(Model model) {
		
		
		model.addAttribute("ulist",userService.getAllUser() );

		return "userlist";
	}

}
