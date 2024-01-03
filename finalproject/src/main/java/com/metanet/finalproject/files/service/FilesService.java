package com.metanet.finalproject.files.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metanet.finalproject.files.model.Files;
import com.metanet.finalproject.files.repository.IFilesRepository;

@Service
public class FilesService implements IFilesService{
	@Autowired
	IFilesRepository filesRepository;

	@Override
	public void insertImageFile(Files files) {
		filesRepository.insertImageFile(files);
	}
	
}
