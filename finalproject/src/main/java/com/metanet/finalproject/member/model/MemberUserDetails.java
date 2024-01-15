package com.metanet.finalproject.member.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class MemberUserDetails extends User {
    private static final long serialVersionUID = 1L;

    private int id;

    public MemberUserDetails(String username, String password,
                             Collection<? extends GrantedAuthority> authorities, int id) {
        super(username, password, authorities);
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
