package com.cts.jobportal.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cts.jobportal.entity.ApplicantUser;
import com.cts.jobportal.entity.Roles;

public class CustomerUserDetails implements UserDetails {

	private String username;
    private String password;
    Collection<? extends GrantedAuthority> authorities;

    public CustomerUserDetails(ApplicantUser byUsername) {
    	this.username = byUsername.getUsername();
        this.password= byUsername.getPassword();
        List<GrantedAuthority> auths = new ArrayList<>();

        for(Roles role : byUsername.getRoles()){

            auths.add(new SimpleGrantedAuthority(role.getName().toString()));
        }
        this.authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
