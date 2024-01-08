package com.metanet.finalproject.member.model;

import java.util.List;

import com.metanet.finalproject.member.service.MemberService;
import com.metanet.finalproject.role.repository.IRoleRepository;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Component
public class MemberUserDetailsService implements UserDetailsService {


	@Autowired
	private MemberService memberService;

	@Autowired
	private IRoleRepository roleRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		log.info("loadUserByUsername....");
		Member memberInfo = memberService.selectMember(email);
//		log.info("memberinfo: {}", memberInfo);
		if (memberInfo == null) {
			throw new UsernameNotFoundException("[" + email + "] 사용자를 찾을 수 없습니다.");
		}

		String roleName = roleRepository.getRoleName(memberInfo.getMemberId());
//		String[] roles = {"ROLE_USER", "ROLE_ADMIN"};
		String[] roles = {roleName};

		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roles);

//		return new User(memberInfo.getMemberEmail(), "{noop}" + memberInfo.getMemberPassword(), authorities);
		return new MemberUserDetails(memberInfo.getMemberEmail(), memberInfo.getMemberPassword(), authorities, memberInfo.getMemberId());
	}
}

