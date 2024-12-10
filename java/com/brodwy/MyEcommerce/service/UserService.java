package com.brodwy.MyEcommerce.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brodwy.MyEcommerce.model.User;
import com.brodwy.MyEcommerce.repository.UserRepository;



@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	public void signup(User user) {
		// TODO Auto-generated method stub
		userRepo.save(user);
		
	}


	public User login(String email, String psw) {
		// TODO Auto-generated method stub
		return userRepo.findByEmailAndPassword(email, psw);
	}


	public User finUser(String email) {
		// TODO Auto-generated method stub
		return userRepo.findByEmail(email);
	}


	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return userRepo.findAll();
	}

		
	}


