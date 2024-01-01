package com.metanet.finalproject.files.model;

import lombok.Data;

@Data
public class Files {
	// 파일 ID
	private int files;
	
	// 사진 원본이름
	private String files_oname;
	
	// 사진 저장이름
	private String files_nname;
	
	// 파일경로
	private String files_path;
	
	// 파일타입
	private String files_type;
}
