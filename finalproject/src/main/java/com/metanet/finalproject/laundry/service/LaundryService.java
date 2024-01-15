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
	public Laundry getLaundryId(String laundryName) {
		// TODO Auto-generated method stub
		return laundryRepository.getLaundryId(laundryName);
	}

	@Override
	public List<Laundry> getLaundryCategory(int laundryCategoryId) {
		return laundryRepository.getLaundryCategory(laundryCategoryId);
	}

	@Override
	public List<Laundry> getPagingLaundry(int start, int end) {
		return laundryRepository.getPagingLaundry(start, end);
	}

	@Override
	public int getLaundryCount() {
		return laundryRepository.getLaundryCount();
	}

	@Override
	public int getLaundryCount(int id) {
		return laundryRepository.getLaundryCount(id);
	}

	@Override
	public List<Laundry> getPagingLaundryCategory(int start, int end, int id) {
		return laundryRepository.getPagingLaundryCategory(start, end, id);
	}


}
