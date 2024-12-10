package com.brodwy.MyEcommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brodwy.MyEcommerce.model.User;

public interface UserRepository extends JpaRepository<User,Integer>{

	User findByEmailAndPassword(String email, String password);

	User findByEmail(String email);
	
}
