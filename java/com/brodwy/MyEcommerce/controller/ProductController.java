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

import com.brodwy.MyEcommerce.model.Product;
import com.brodwy.MyEcommerce.service.CategoryService;
import com.brodwy.MyEcommerce.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {
 
	@Autowired
	private ProductService prodservice;
	@Autowired
	private CategoryService categoryservice;
	
	@GetMapping("/products")
	public String getProd(Model model) {
		model.addAttribute("products", prodservice.getAllProduct());
		return "admin/products";
	}
	
	
	@GetMapping("/addProduct")
	public String addProd(Model model) {
		model.addAttribute("categories", categoryservice.getAllCategory());
		model.addAttribute("products", new Product());
		return "admin/add_product";
	}
	
	@PostMapping("/saveProduct")
	public String saveprod(@RequestParam("productImage") MultipartFile image,
	                      @RequestParam("imgName") String imgName,
	                      @ModelAttribute Product product) {
	    String imageUID;

	    if (!image.isEmpty()) {
	        try {
	            String originalFilename = image.getOriginalFilename();
	            
				Files.copy(image.getInputStream(),Path.of("src/main/resources/static/img/productImages/"+image.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
	            imageUID = originalFilename; 
	        } catch (IOException e) {
	            e.printStackTrace();
	           
	            return "redirect:/addProduct?error=Image upload failed";
	        }
	    } else {
	        imageUID = imgName; // Use existing image name if no new file is uploaded
	    }

	    product.setImage(imageUID);
	    product.setDiscount(0);
	    product.setDiscountPrice(product.getPrice());
	    prodservice.addProduct(product); // Save the product

	    return "redirect:/products"; // Redirect to the products list or another appropriate page
	}
	
	
	
	@GetMapping("/deleteProduct")
 	public String deleteProd(@RequestParam int id) {
 		prodservice.deleteProduct(id);
 		return "redirect:/products";
 	}
 	
	@GetMapping("/editProduct")
	public String editProd(@RequestParam int id,Model model) {
		model.addAttribute("product",prodservice.getProductById(id));
		model.addAttribute("categories", categoryservice.getAllCategory());
		return"admin/edit_product";
	}
	 
	
	@PostMapping("/updateProduct")
	public String updateProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile image,
			HttpSession session, Model m) {

		if (product.getDiscount() < 0 || product.getDiscount() > 100) {
			session.setAttribute("errorMsg", "invalid Discount");
		} else {
			prodservice.updateProduct(product, image);
			
		}
		return "redirect:/products";
	}
}
