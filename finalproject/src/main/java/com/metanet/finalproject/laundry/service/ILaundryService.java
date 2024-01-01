package com.metanet.finalproject.laundry.service;

import java.util.List;

import com.metanet.finalproject.laundry.model.Laundry;

public interface ILaundryService {
	List<Laundry> getLaundry();
	Laundry getLaundry(int laundryId);
	List<Laundry> getLaundryCategory(int laundryCategoryId);
}
