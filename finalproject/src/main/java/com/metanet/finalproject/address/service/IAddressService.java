package com.metanet.finalproject.address.service;

import com.metanet.finalproject.address.model.Address;

public interface IAddressService {
	Address getAddress(int memberId);
	Address getOneAddress(int addressId);
	void insertAddress(Address address);
	void updateAddress(Address address);
	void deleteAddress(int address);
}
