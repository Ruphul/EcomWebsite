package com.brodwy.MyEcommerce.controller;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.brodwy.MyEcommerce.model.Category;
import com.brodwy.MyEcommerce.service.CategoryService;

@Controller
public class AdminHomeController {

	@Autowired
	private CategoryService categoryservice;
	
	@GetMapping("/")
	public String index() {
		return"admin/index";
	}
	
	@GetMapping("/category")
	public String category(Model m) {
		 m.addAttribute("categorys", categoryservice.getAllCategory());
		 m.addAttribute("categories", new Category());
		return "admin/category";
	}
	
	@PostMapping("/saveCategory")
	public String savecategory(@RequestParam("productImage") MultipartFile image,
	                      @RequestParam("imgName") String imgName,
	                      @ModelAttribute Category category) {
	    String imageUID;

	    if (!image.isEmpty()) {
	        try {
	            String originalFilename = image.getOriginalFilename();
	            
				Files.copy(image.getInputStream(),Path.of("src/main/resources/static/img/categoryImages/"+image.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
	            imageUID = originalFilename; 
	        } catch (IOException e) {
	            e.printStackTrace();
	           
	            return "redirect:/category?error=Image upload failed";
	        }
	    } else {
	        imageUID = imgName; // Use existing image name if no new file is uploaded
	    }

	    category.setImageName(imageUID); // Set the image name in the product
	    categoryservice.addCategory(category); // Save the product

	    return "redirect:/category"; // Redirect to the products list or another appropriate page
	}
	
	@GetMapping("/deleteCategory")
	public String deleteCategory(@RequestParam int id) {
		categoryservice.deleteCategory(id);
		return "redirect:/category";
	}

	@GetMapping("/EditCategory")
	public String editProduct(@RequestParam int id,Model model) {
		model.addAttribute("category",categoryservice.getCategoryById(id));
		return"admin/edit_category";
	}
}
