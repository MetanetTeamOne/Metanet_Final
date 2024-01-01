package com.metanet.finalproject.laundry_category.service;

import java.util.List;

import com.metanet.finalproject.laundry_category.model.LaundryCategory;

public interface ILaundryCategoryService {
	// 전체 조회	
	List<LaundryCategory> getLaundryCategory();
	// 개별 조회
	LaundryCategory getLaundryCategory(int laundryCategoryId);

}
