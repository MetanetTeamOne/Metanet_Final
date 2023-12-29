package com.metanet.finalproject.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metanet.finalproject.product.model.Product;
import com.metanet.finalproject.product.repository.IProductRepository;

@Service
public class ProductService implements IProductService{
	@Autowired
	IProductRepository productRepository;
	@Override
	public String getProductAll() {
		// TODO Auto-generated method stub
		return productRepository.getProductAll();
	}

	@Override
	public String getProductDetail(String productId) {
		// TODO Auto-generated method stub
		return productRepository.getProductDetail(productId);
	}

	@Override
	public List<Product> getProductByName(String productName) {
		// TODO Auto-generated method stub
		return productRepository.getProductByName(productName);
	}

	@Override
	public String insertProduct(Product product) {
		// TODO Auto-generated method stub
		return productRepository.insertProduct(product);
	}

	@Override
	public String updateProduct(Product product) {
		// TODO Auto-generated method stub
		return productRepository.updateProduct(product);
	}

	@Override
	public String deleteProduct(String productId) {
		// TODO Auto-generated method stub
		return productRepository.deleteProduct(productId);
	}
	
}
