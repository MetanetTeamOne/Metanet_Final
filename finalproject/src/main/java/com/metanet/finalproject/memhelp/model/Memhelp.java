package com.metanet.finalproject.memhelp.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString 
@Builder @NoArgsConstructor @AllArgsConstructor
public class Memhelp {
	private int memHelpNum; // 문의번호
	private String memHelpTitle; // 문의제목
	private String memHelpContent; // 문의내용
	private byte[] memHelpFile; // 첨부파일
	private String memHelpState; // 문의상태
	private Date memHelpDate; // 문의날짜
	private int memberId; // 회원 ID
	private int memCategoryNum; // 문의유형번호
}
