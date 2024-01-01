package com.metanet.finalproject.address.service;

import java.util.List;

import com.metanet.finalproject.address.model.Address;

public interface IAddressService {
	List<Address> getAddress(int memberId);
	void insertAddress(Address address);
	void updateAddress(Address address);
	void deleteAddress(Address address);
}
