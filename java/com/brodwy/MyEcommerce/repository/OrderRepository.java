package com.brodwy.MyEcommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brodwy.MyEcommerce.model.ProductOrder;


public interface OrderRepository extends JpaRepository<ProductOrder,Integer>{

	List<ProductOrder> findByUserId(Integer userId);

	ProductOrder findByOrderId(String orderId);
}
