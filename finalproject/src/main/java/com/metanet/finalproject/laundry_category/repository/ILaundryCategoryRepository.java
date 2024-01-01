package com.metanet.finalproject.laundry_category.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.metanet.finalproject.laundry_category.model.LaundryCategory;

@Repository
@Mapper
public interface ILaundryCategoryRepository {
	// 전체 조회	
	List<LaundryCategory> getLaundryCategory();
	// 개별 조회
	LaundryCategory getLaundryCategory(int laundryCategoryId);
}
