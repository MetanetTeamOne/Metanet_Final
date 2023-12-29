package com.metanet.finalproject.product.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.metanet.finalproject.product.model.Product;

@Repository
@Mapper
public interface IProductRepository {
	String getProductAll();
	String getProductDetail(@Param("productId") String productId);
	List<Product> getProductByName(@Param("productName") String productName);
	String insertProduct(@Param("product") Product product);
	String updateProduct(@Param("product") Product product);
	String deleteProduct(@Param("productId") String productId);
}
