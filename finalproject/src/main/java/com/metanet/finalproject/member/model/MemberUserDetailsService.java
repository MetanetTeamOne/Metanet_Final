package com.metanet.finalproject.member.model;

import java.util.List;

import com.metanet.finalproject.member.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;



/*
@Component
public class MemberUserDetailsService implements UserDetailsService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MemberService memberService;

	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
		Member memberInfo = memberService.selectMember(memberId);
		if (memberInfo==null) {
			throw new UsernameNotFoundException("["+ memberId +"] 사용자를 찾을 수 없습니다.");
		}
//		String[] roles = {"ROLE_USER", "ROLE_ADMIN"};
		String[] roles = memberInfo.getRole().split(",");
		logger.info(memberInfo.getUserid());

		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roles);

		return new User(memberInfo.getUserid(), "{noop}"+memberInfo.getPassword(), authorities);
}
*/
