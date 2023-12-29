package com.metanet.finalproject.product.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.metanet.finalproject.product.model.Product;

public interface IProductService {
	String getProductAll();
	String getProductDetail(@Param("productId") String productId);
	List<Product> getProductByName(@Param("productName") String productName);
	String insertProduct(@Param("product") Product product);
	String updateProduct(@Param("product") Product product);
	String deleteProduct(@Param("productId") String productId);
}
