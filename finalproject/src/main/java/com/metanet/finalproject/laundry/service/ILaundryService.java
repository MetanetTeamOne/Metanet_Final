package com.metanet.finalproject.laundry.service;

import java.util.List;

import com.metanet.finalproject.laundry.model.Laundry;

public interface ILaundryService {
	List<Laundry> getLaundry();
	Laundry getLaundry(int laundryId);
	Laundry getLaundryId(String laundryName);
	List<Laundry> getLaundryCategory(int laundryCategoryId);

    List<Laundry> getPagingLaundry(int start, int end);

	int getLaundryCount();
	int getLaundryCount(int id);

	List<Laundry> getPagingLaundryCategory(int start, int end, int id);
}
