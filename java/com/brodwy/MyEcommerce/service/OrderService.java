package com.brodwy.MyEcommerce.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brodwy.MyEcommerce.model.Cart;
import com.brodwy.MyEcommerce.model.OrderAddress;
import com.brodwy.MyEcommerce.model.OrderRequest;
import com.brodwy.MyEcommerce.model.ProductOrder;
import com.brodwy.MyEcommerce.repository.CartRepository;
import com.brodwy.MyEcommerce.repository.OrderRepository;
import com.brodwy.MyEcommerce.utils.OrderStatus;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private CartRepository cartRepo;
	
	public void saveOrder(int uid,OrderRequest orderRequest) {
		
		List<Cart> carts =cartRepo.findByUserId(uid);
		if (carts == null || carts.isEmpty()) {
	        throw new IllegalArgumentException("Cart is empty for user ID: " + uid);
	    }
		List<ProductOrder> orders = new ArrayList<>();
		for (Cart cart : carts) {
		    ProductOrder order = new ProductOrder();
		    order.setOrderId(UUID.randomUUID().toString());
		    order.setOrderDate(LocalDate.now());
		    order.setProduct(cart.getProduct());
		    order.setPrice(cart.getProduct().getDiscountPrice());
		    order.setQuantity(cart.getQuantity());
		    order.setUser(cart.getUser());
		    order.setStatus(OrderStatus.IN_PROGRESS.getName());
		    order.setPaymentType(orderRequest.getPaymentType());

		    OrderAddress address = new OrderAddress();
		    address.setFirstName(orderRequest.getFirstName());
		    address.setLastName(orderRequest.getLastName());
		    address.setEmail(orderRequest.getEmail());
		    address.setMobileNo(orderRequest.getMobileNo());
		    address.setAddress(orderRequest.getAddress());
		    address.setCity(orderRequest.getCity());
		    address.setState(orderRequest.getState());
		    address.setPincode(orderRequest.getPincode());
		    order.setAddress(address);

		    orders.add(order);
		}
		orderRepo.saveAll(orders);
		cartRepo.deleteAll(carts);
			
		}
		
	

	public List<ProductOrder> getOrdersByUser(int userId) {
		List<ProductOrder> orders = orderRepo.findByUserId(userId);
		return orders;
	}
	

	public List<ProductOrder> getAllOrders() {
		return orderRepo.findAll();
	}
	
	public ProductOrder getOrderByOrderId(String id) {
		return orderRepo.findByOrderId(id);
	}


	public void updateStatus(int oid,String status) {
	    ProductOrder order = orderRepo.findById(oid).get();
	    order.setStatus(status);
	    orderRepo.save(order);

	}
	
	public void updateUserStatus(int oid) {
	    ProductOrder order = orderRepo.findById(oid)
	            .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + oid));
	    
	    if (!"In Progress".equalsIgnoreCase(order.getStatus())) {
	        throw new IllegalStateException("Order cannot be canceled because it is not in progress.");
	    }

	    order.setStatus("Cancelled");
	    orderRepo.save(order);
	}

	
}
