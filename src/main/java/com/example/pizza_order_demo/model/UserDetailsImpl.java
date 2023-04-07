package com.example.pizza_order_demo.model;

import com.example.pizza_order_demo.service.impl.UserDetailServiceImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails{

    private String pwd;
    private String username;
    private Collection<? extends GrantedAuthority> auths;

    public UserDetailsImpl(User user,Collection<? extends GrantedAuthority> auths){
        pwd = user.getPwd();
        username = user.getUsername();
        this.auths = auths;
    }

    public UserDetailsImpl(){

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return auths;
    }

    @Override
    public String getPassword() {
        return pwd;
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
