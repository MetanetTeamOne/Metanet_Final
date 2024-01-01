package com.metanet.finalproject.laundry.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metanet.finalproject.laundry.model.Laundry;
import com.metanet.finalproject.laundry.repository.ILaundryRepository;

@Service
public class LaundryService implements ILaundryService{

	@Autowired
	ILaundryRepository laundryRepository;
	
	@Override
	public List<Laundry> getLaundry() {
		return laundryRepository.getLaundry();
	}

	@Override
	public Laundry getLaundry(int laundryId) {
		return laundryRepository.getLaundry(laundryId);
	}

	@Override
	public List<Laundry> getLaundryCategory(int laundryCategoryId) {
		return laundryRepository.getLaundryCategory(laundryCategoryId);
	}

}
