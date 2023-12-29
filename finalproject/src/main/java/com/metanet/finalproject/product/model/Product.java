package com.metanet.finalproject.product.model;

import lombok.Data;

@Data
public class Product {
	private int productId;
	private String productName;
	private int productPrice;
	private String productDescription;
	private int productStock;
	private String fileName;
	private String filePath;
	private int categoryId;
}
