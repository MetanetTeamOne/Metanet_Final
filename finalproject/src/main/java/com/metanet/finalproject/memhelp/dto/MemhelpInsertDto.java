package com.metanet.finalproject.memhelp.dto;

import lombok.Data;

@Data
public class MemhelpInsertDto {
	private int memCategoryNum; // 문의유형번호
	private String memHelpTitle; // 문의제목
	private String memHelpContent; // 문의내용
	private byte[] memHelpFile; // 첨부파일
}
