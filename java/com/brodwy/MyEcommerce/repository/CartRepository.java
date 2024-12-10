package com.brodwy.MyEcommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brodwy.MyEcommerce.model.Cart;


public interface CartRepository extends JpaRepository<Cart, Integer>{

	public Cart findByProductIdAndUserId(int pid,int uid);
	public Integer countByUserId(Integer userId);

	public List<Cart> findByUserId(Integer userId);
}
