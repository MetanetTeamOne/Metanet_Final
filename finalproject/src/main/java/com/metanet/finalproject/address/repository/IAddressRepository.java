package com.metanet.finalproject.address.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.metanet.finalproject.address.model.Address;

import java.util.List;

@Repository
@Mapper
public interface IAddressRepository {
	Address getAddress(int memberId);
	Address getOneAddress(int addressId);
	void insertAddress(Address address);
	void updateAddress(Address address);
	void deleteAddress(int address);

	Address getAddressByMemberId(@Param("memberId") int memberId);

}
