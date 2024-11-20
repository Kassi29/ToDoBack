package com.kass.backendtodo.services;


import com.kass.backendtodo.models.UserModel;
import com.kass.backendtodo.repositories.IUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
public class UserJPADetailsService implements UserDetailsService {

    private final IUser iUser;

    public UserJPADetailsService(IUser Iuser) {
        this.iUser = Iuser;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserModel> userModelOptional =  iUser.findByUsername(username);

        if (userModelOptional.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));

        }
        UserModel userModel = userModelOptional.get();

       GrantedAuthority grantedAuthorities = new SimpleGrantedAuthority(userModel.getRole());

       return new User(userModel.getUsername(),
               userModel.getPassword(),
               userModel.isEnabled(),
               true,
               true,
               true,
               List.of(grantedAuthorities)
       );


    }




}
