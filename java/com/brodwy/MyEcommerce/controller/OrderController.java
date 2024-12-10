package com.brodwy.MyEcommerce.controller;

import java.security.Principal;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.brodwy.MyEcommerce.model.Cart;
import com.brodwy.MyEcommerce.model.OrderRequest;
import com.brodwy.MyEcommerce.model.ProductOrder;
import com.brodwy.MyEcommerce.model.User;
import com.brodwy.MyEcommerce.service.CartService;
import com.brodwy.MyEcommerce.service.OrderService;
import com.brodwy.MyEcommerce.utils.OrderStatus;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {
	
	@Autowired
	private CartService cartservice;
	
	@Autowired
	private OrderService orderService;

	@GetMapping("/orders")
	public String getOrder(Model model,HttpSession session) {
		int uid = (int) session.getAttribute("activeUser.id");
		List<Cart> carts=cartservice.getCartByUser(uid);
		if (carts.size() > 0) {
			Double orderPrice = carts.get(carts.size() - 1).getTotalOrderPrice();
			Double totalOrderPrice = carts.get(carts.size() - 1).getTotalOrderPrice() + 250 + 100;
			model.addAttribute("totalPrice", orderPrice);
			model.addAttribute("totalOrderPrice", totalOrderPrice);
		}
		
		
		return "user/order";
	}
	
	@PostMapping("/save-order")
	public String saveOrder(@ModelAttribute OrderRequest request,HttpSession session) {
		int uid = (int) session.getAttribute("activeUser.id");
		orderService.saveOrder(uid, request);
		return "user/success";
	}
	
	@GetMapping("/user-orders")
	public String getMyOrders(HttpSession session,Model model) {
	    User loginUser = (User) session.getAttribute("activeUser");
	    int userId = (int) loginUser.getId();
		List<ProductOrder> orders = orderService.getOrdersByUser(userId);
		model.addAttribute("orders", orders);
		return"user/my_orders";
	}
	
	
	
	

}
