package com.metanet.finalproject.files.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.metanet.finalproject.files.model.Files;

@Repository
@Mapper
public interface IFilesRepository {
	void insertImageFile(Files files);
}
