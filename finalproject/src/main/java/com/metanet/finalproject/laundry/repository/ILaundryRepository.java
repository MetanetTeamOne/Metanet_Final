package com.metanet.finalproject.laundry.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import com.metanet.finalproject.laundry.model.Laundry;

@Repository
@Mapper
public interface ILaundryRepository {
	List<Laundry> getLaundry();
	Laundry getLaundryId(String laundryName);
	Laundry getLaundry(int laundryId);
	List<Laundry> getLaundryCategory(int laundryCategoryId);

    List<Laundry> getPagingLaundry(@Param("start") int start, @Param("end") int end);

	int getLaundryCount();
	int getLaundryCount(int id);

	List<Laundry> getPagingLaundryCategory(@Param("start") int start, @Param("end") int end, @Param("id") int id);
}
