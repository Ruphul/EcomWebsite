package com.brodwy.MyEcommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brodwy.MyEcommerce.model.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer>{

}
