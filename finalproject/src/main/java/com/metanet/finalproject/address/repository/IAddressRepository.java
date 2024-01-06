package com.metanet.finalproject.address.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.metanet.finalproject.address.model.Address;

@Repository
@Mapper
public interface IAddressRepository {
	List<Address> getAddress(int memberId);
	void insertAddress(Address address);
	void updateAddress(Address address);
	void deleteAddress(Address address);
}
