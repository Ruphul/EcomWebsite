package com.brodwy.MyEcommerce.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.brodwy.MyEcommerce.model.Product;
import com.brodwy.MyEcommerce.repository.ProductRepository;



@Service
public class ProductService {

	
	@Autowired
	private ProductRepository prodRepo;
	
	public void addProduct(Product p) {
		prodRepo.save(p);
	}
	
	public void deleteProduct(int id) {
		prodRepo.deleteById(id);
	}
	
	public Product getProductById(int id) {
		return prodRepo.findById(id).get();
	}
	
	public List<Product> getAllProduct(){
		return prodRepo.findAll();
	}
	
	public List<Product> getProductsByCategoryId(int id){
		return prodRepo.findAllByCategory_Id(id);
	}
	
	
	public Product updateProduct(Product product, MultipartFile image) {
		// TODO Auto-generated method stub
		Product dbProduct = getProductById(product.getId());

		String imageName = image.isEmpty() ? dbProduct.getImage() : image.getOriginalFilename();

		dbProduct.setTitle(product.getTitle());
		dbProduct.setDescription(product.getDescription());
		dbProduct.setCategory(product.getCategory());
		dbProduct.setPrice(product.getPrice());
		dbProduct.setStock(product.getStock());
		dbProduct.setImage(imageName);
		dbProduct.setIsActive(product.getIsActive());
		dbProduct.setDiscount(product.getDiscount());

		// 5=100*(5/100); 100-5=95
		Double disocunt = product.getPrice() * (product.getDiscount() / 100.0);
		Double discountPrice = product.getPrice() - disocunt;
		dbProduct.setDiscountPrice(discountPrice);

		Product updateProduct = prodRepo.save(dbProduct);

		if (!ObjectUtils.isEmpty(updateProduct)) {

			if (!image.isEmpty()) {

				try {
					File saveFile = new ClassPathResource("static/img").getFile();

					Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "product_img" + File.separator
							+ image.getOriginalFilename());
					Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return product;
		}
		return null;
	}
	
	public List<Product> searchByTitle(String title) {
        return prodRepo.findByTitleContainingIgnoreCase(title);
    }

    // Search by category name
    public List<Product> searchByCategory(String category) {
        return prodRepo.findByCategoryNameContaining(category);
    }

    // Search by title or category
    public List<Product> searchByTitleOrCategory(String query) {
        return prodRepo.findByTitleOrCategory(query.toLowerCase());
    }

}
