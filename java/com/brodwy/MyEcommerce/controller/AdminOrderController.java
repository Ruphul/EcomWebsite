package com.brodwy.MyEcommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.brodwy.MyEcommerce.model.ProductOrder;
import com.brodwy.MyEcommerce.service.OrderService;

@Controller
public class AdminOrderController {

	@Autowired
	private OrderService orderservice;

	

	
	@GetMapping("/order")
	public String getAllOrders(Model model) {
		model.addAttribute("orders", orderservice.getAllOrders());

		return "/admin/orders";
	}
	
	
	@PostMapping("/update-order-status")
	public String updateStatus(@RequestParam("id") int oid, @RequestParam("st") String status,Model model) {
		 try {
	            // Call the service to update the status
	            orderservice.updateStatus(oid, status);
	            model.addAttribute("successMessage", "Order status updated successfully.");
	        } catch (Exception e) {
	            model.addAttribute("errorMessage", "Failed to update order status: " + e.getMessage());
	        }
	       return "redirect:/order";
	
	}

	@GetMapping("/user/update-status")
	public String updateUserStatus(@RequestParam("id") int oid,Model model) {
		 try {
	            // Call the service to update the status
	            orderservice.updateUserStatus(oid);
	            model.addAttribute("Message", "Order status updated successfully.");
	        } catch (Exception e) {
	            model.addAttribute("Message", "Failed to update order status: " + e.getMessage());
	        }
	       return "redirect:/user-orders";
	
	}
	
	@GetMapping("/search-order")
	public String searchOrder(@RequestParam("orderId") String orderId,Model model) {
		ProductOrder orders = orderservice.getOrderByOrderId(orderId);
		model.addAttribute("orders", orders);
	    return "admin/orders";
	}
	

}
