package com.metanet.finalproject.laundry_category.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metanet.finalproject.laundry_category.model.LaundryCategory;
import com.metanet.finalproject.laundry_category.repository.ILaundryCategoryRepository;

@Transactional
@Service
public class LaundryCategoryService implements ILaundryCategoryService{

	@Autowired
	ILaundryCategoryRepository laundryCategorysRepository;
	
	@Override
	public List<LaundryCategory> getLaundryCategory() {
		return laundryCategorysRepository.getLaundryCategory();
	}
	
	@Override
	public LaundryCategory getLaundryCategory(int laundryCategoryId) {
		return laundryCategorysRepository.getLaundryCategory(laundryCategoryId);
	}
}
