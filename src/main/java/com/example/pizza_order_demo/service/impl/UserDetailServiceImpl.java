package com.example.pizza_order_demo.service.impl;

import com.example.pizza_order_demo.model.*;
import com.example.pizza_order_demo.service.RoleService;
import com.example.pizza_order_demo.service.UserRoleService;
import com.example.pizza_order_demo.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)){throw new UsernameNotFoundException("user name doesn't exist");}
        UserExample userExample = new UserExample();
        userExample.or().andUsernameEqualTo(username);
        User user = userService.selectFirstByExample(userExample);
        if (ObjectUtils.isEmpty(user)){
            throw new UsernameNotFoundException("user name doesn't exist");
        }
        int id = user.getId();
        UserRoleExample userRoleExample = new UserRoleExample();
        userRoleExample.or().andUserIdEqualTo(id);
        List<UserRole> userRoleList = userRoleService.selectByExample(userRoleExample);
        List<GrantedAuthority> authorities = new ArrayList<>(userRoleList.size());
        Role role = null;
        for(UserRole userRole:userRoleList){
            role = roleService.selectByPrimaryKey(userRole.getRoleId());
            if (!ObjectUtils.isEmpty(role)){
                authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            }
        }
        return new UserDetailsImpl(user,authorities);

    }
}
