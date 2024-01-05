package com.metanet.finalproject.address.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metanet.finalproject.address.model.Address;
import com.metanet.finalproject.address.repository.IAddressRepository;

@Service
public class AddressService implements IAddressService{

	@Autowired 
	IAddressRepository addressRepository;
	
	@Override
	public List<Address> getAddress(int memberId) {
		return addressRepository.getAddress(memberId);
	}
	
	@Override
	public void insertAddress(Address address) {
		addressRepository.insertAddress(address);
	}

	@Override
	public void updateAddress(Address address) {
		addressRepository.updateAddress(address);
	}

	@Override
	public void deleteAddress(int address) {
		addressRepository.deleteAddress(address);
	}


}
