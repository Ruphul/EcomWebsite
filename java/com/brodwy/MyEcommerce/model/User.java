package com.brodwy.MyEcommerce.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="user_tbl")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String fname;
	private String gender;
	private String email;
	private String password;
	private String phone;
	private String role;
	
	
}

