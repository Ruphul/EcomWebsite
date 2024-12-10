package com.brodwy.MyEcommerce.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.brodwy.MyEcommerce.model.Cart;
import com.brodwy.MyEcommerce.model.User;
import com.brodwy.MyEcommerce.service.CartService;
import com.brodwy.MyEcommerce.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class cartController {

	@Autowired
	private CartService cartservice;
	@Autowired
	private UserService userService;
	
	@GetMapping("/addCart")
	public String addToCart(@RequestParam int pid,@RequestParam int uid,HttpSession session) {
		Cart saveCart=cartservice.saveCart(pid, uid);
		if(ObjectUtils.isEmpty(saveCart)) {
			session.setAttribute("errorMsg", "product add to cart failed");
		}
		else {
			session.setAttribute("succMsg", "product add to cart successfully");

		}

		return "redirect:/product";
		
	}
	
	@GetMapping("/cart")
	public String showCart(@RequestParam int uid,Model model) {
		List<Cart> carts=cartservice.getCartByUser(uid);
		model.addAttribute("carts", carts);
		if (carts.size() > 0) {
			Double totalOrderPrice = carts.get(carts.size() - 1).getTotalOrderPrice();
			model.addAttribute("totalOrderPrice", totalOrderPrice);
		}
		return "user/cart";

	    
	}

	
	@GetMapping("/cartQuantityUpdate")
	public String updateCartQuantity(@RequestParam String sy, @RequestParam Integer cid,HttpSession session) {
		  if(session.getAttribute("activeUser")==null) {
				
				return "login";
			}
		  int uid = (int) session.getAttribute("activeUser.id");
		cartservice.updateQuantity(sy, cid);
		return "redirect:/cart?uid=" + uid;
	}


}
