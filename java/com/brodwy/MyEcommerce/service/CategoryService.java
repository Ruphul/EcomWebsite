package com.brodwy.MyEcommerce.service;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brodwy.MyEcommerce.model.Category;
import com.brodwy.MyEcommerce.repository.CategoryRepository;


@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;
	
	public void addCategory(Category category) {
		categoryRepo.save(category);
	}
	
	public void deleteCategory(int id) {
		categoryRepo.deleteById(id);
	}
	
	public List<Category> getAllCategory() {
		
		return categoryRepo.findAll();
	}
	
	public Category getCategoryById(int id) {
		return  categoryRepo.findById(id).get();
	}
}

