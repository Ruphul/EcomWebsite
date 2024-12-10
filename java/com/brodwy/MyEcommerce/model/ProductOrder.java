package com.brodwy.MyEcommerce.model;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class ProductOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String orderId;

	
	private LocalDate orderDate;

	@ManyToOne
	private Product product;

	private Double price;

	private Integer quantity;

	@ManyToOne
	private User user;

	private String status;

	private String paymentType;

	@OneToOne(cascade=CascadeType.ALL)
	private OrderAddress address;

	
}
