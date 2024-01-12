package com.metanet.finalproject.reply.model;

import java.sql.Date;

import lombok.Data;

@Data
public class Reply {
	private int repNum;
	private String repContent;
	private Date repDate;
	private int memHelpNum;
}
