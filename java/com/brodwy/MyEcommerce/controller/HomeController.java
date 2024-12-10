package com.brodwy.MyEcommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.brodwy.MyEcommerce.service.CategoryService;
import com.brodwy.MyEcommerce.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private CategoryService categoryservice;
	
	@Autowired
	private ProductService prodservice;
	
	@GetMapping("/index")
	public String index(Model model,HttpSession session) {
		model.addAttribute("category", categoryservice.getAllCategory());
		model.addAttribute("products", prodservice.getAllProduct());
		return "index";
	}
	
	@GetMapping("/product")
	public String product(Model model) {
		model.addAttribute("categories", categoryservice.getAllCategory());

		model.addAttribute("products", prodservice.getAllProduct());

		return"product";
	}
	
	@GetMapping("/product/category")
	public String shopByCategory(Model model, @RequestParam int id) {
	  
	    model.addAttribute("categories", categoryservice.getAllCategory());
	    model.addAttribute("products", prodservice.getProductsByCategoryId(id));
	    return "product";
	}

	@GetMapping("/viewDetails")
	public String view(@RequestParam int id,Model model, HttpSession session) {
		 if(session.getAttribute("activeUser")==null) {
				
				return "login";
			}
		model.addAttribute("product", prodservice.getProductById(id));
		
		return "user/view_product";
	}
	
	@GetMapping("/search")
	
	public String search(@RequestParam("ch") String query,Model model) {
		
		model.addAttribute("products", prodservice.searchByTitleOrCategory(query));
		model.addAttribute("categories", categoryservice.getAllCategory());
		return "product";
	}
	
}
