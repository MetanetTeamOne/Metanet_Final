package com.metanet.finalproject.member.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class MemberUserDetails extends User{
	private static final long serialVersionUID = -4679039456330600424L;

	private String studentEmail;
	
	public MemberUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, String studentEmail) {
		super(username, password, authorities);

		this.studentEmail = studentEmail;
	}
	
	public String getStudentEmail() {
		return this.studentEmail;
	}
}
