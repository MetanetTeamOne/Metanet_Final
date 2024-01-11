package com.metanet.finalproject.memhelp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metanet.finalproject.memhelp.model.Memhelp;
import com.metanet.finalproject.memhelp.model.MemhelpSearchByMemberId;
import com.metanet.finalproject.memhelp.repository.IMemhelpRepository;

@Service
public class MemhelpService implements IMemhelpService{
	@Autowired
	IMemhelpRepository memhelpRepository;
	
	@Override
	public List<Memhelp> searchAllMemhelp() {
		return memhelpRepository.searchAllMemhelp();
	}

	@Override
	public List<MemhelpSearchByMemberId> searchMemhelp(int memberId) {
		return memhelpRepository.searchMemhelp(memberId);
	}

	@Override
	public void insertMemhelp(Memhelp memhelp) {
		memhelpRepository.insertMemhelp(memhelp);
	}
}
