//package com.ecommerce.authservice.service;
//
//import java.util.Collections;
//
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.ecommerce.authservice.dao.UserDetailsDao;
//import com.ecommerce.userservice.entity.Users;
//
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    private final UserDetailsDao userDao;
//
//    @Override
//    @Transactional()
//    public UserDetails loadUserByUsername(String username) {
//        
//    	Users user = userDao.getByUsername(username)
//                            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        
//        return new User(user.getUsername(),
//                		user.getPassword(),
//                		Collections.singleton(new SimpleGrantedAuthority(user.getRole())) );
//    }
//}
