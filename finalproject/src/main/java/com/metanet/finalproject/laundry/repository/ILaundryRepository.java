package com.metanet.finalproject.laundry.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.metanet.finalproject.laundry.model.Laundry;

@Repository
@Mapper
public interface ILaundryRepository {
	List<Laundry> getLaundry();
	Laundry getLaundry(int laundryId);
	List<Laundry> getLaundryCategory(int laundryCategoryId);
}
