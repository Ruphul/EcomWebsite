package com.brodwy.MyEcommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.brodwy.MyEcommerce.model.Cart;
import com.brodwy.MyEcommerce.model.Product;
import com.brodwy.MyEcommerce.model.User;
import com.brodwy.MyEcommerce.repository.CartRepository;
import com.brodwy.MyEcommerce.repository.ProductRepository;
import com.brodwy.MyEcommerce.repository.UserRepository;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private ProductRepository prodRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	public Cart saveCart(int pid, int uid) {
	    User usr = userRepo.findById(uid).orElse(null);
	    Product product = prodRepo.findById(pid).orElse(null);
	    
	    // Validate that the user and product exist
	    if (usr == null || product == null) {
	        throw new IllegalArgumentException("User or Product not found");
	    }

	    // Check if the cart item already exists
	    Cart cartStatus = cartRepo.findByProductIdAndUserId(pid, uid);
	    Cart cart;

	    if (ObjectUtils.isEmpty(cartStatus)) {
	        // Create a new cart entry if it does not exist
	        cart = new Cart();
	        cart.setProduct(product);
	        cart.setUser(usr);
	        cart.setQuantity(1);
	    } else {
	        // Update the existing cart entry
	        cart = cartStatus;
	        cart.setQuantity(cart.getQuantity() + 1);
	    }

	    // Calculate and set the total price
	    cart.setTotalPrice(cart.getQuantity() * product.getDiscountPrice());

	    // Save and return the cart
	    return cartRepo.save(cart);
	}

	
	
	public List<Cart> getCartByUser(int uid){
		List<Cart> carts=cartRepo.findByUserId(uid);
		List<Cart> updateCarts = new ArrayList<>();
		Double totalPrice=0.0;
		Double totalOrderPrice=0.0;
		for (Cart c : carts) {
			totalPrice = (c.getProduct().getDiscountPrice() * c.getQuantity());
			c.setTotalPrice(totalPrice);
			totalOrderPrice = totalOrderPrice + totalPrice;
			c.setTotalOrderPrice(totalOrderPrice);
			updateCarts.add(c);
		}

		return updateCarts;
		
	}
	
	
	public void updateQuantity(String sy, Integer cid) {

		Cart cart = cartRepo.findById(cid).get();
		int updateQuantity;

		if (sy.equalsIgnoreCase("de")) {
			updateQuantity = cart.getQuantity() - 1;

			if (updateQuantity <= 0) {
				cartRepo.delete(cart);
			} else {
				cart.setQuantity(updateQuantity);
				cartRepo.save(cart);
			}

		} 
		else {
			updateQuantity = cart.getQuantity() + 1;
			cart.setQuantity(updateQuantity);
			cartRepo.save(cart);
		}

	}
}
