package com.brodwy.MyEcommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy=jakarta.persistence.GenerationType.AUTO)
	private int id;
	private String title;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="category_id",referencedColumnName="category_id")
	private Category category;
	
	private String description;
	private long stock;
	private double price;
	private Double discountPrice;
	private String image;
	private int discount;
	private Boolean isActive;

}
