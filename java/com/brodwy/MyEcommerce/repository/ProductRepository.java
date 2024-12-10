package com.brodwy.MyEcommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.brodwy.MyEcommerce.model.Category;
import com.brodwy.MyEcommerce.model.Product;

public interface ProductRepository extends JpaRepository<Product,Integer>{

	List<Product> findAllByCategory_Id(int id);

	List<Product> findByTitleContainingIgnoreCase(String title);

    // Search by category name (case-insensitive)
    @Query("SELECT p FROM Product p WHERE p.category.name LIKE %:category%")
    List<Product> findByCategoryNameContaining(@Param("category") String category);

    // Search by title or category
    @Query("SELECT p FROM Product p WHERE LOWER(p.title) LIKE %:query% OR LOWER(p.category.name) LIKE %:query%")
    List<Product> findByTitleOrCategory(@Param("query") String query);
}
