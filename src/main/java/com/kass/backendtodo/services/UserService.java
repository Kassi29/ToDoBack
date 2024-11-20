package com.kass.backendtodo.services;

import com.kass.backendtodo.models.UserModel;
import com.kass.backendtodo.repositories.IUser;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final IUser iUser;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUser iUser, PasswordEncoder passwordEncoder) {
        this.iUser = iUser;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<UserModel> getAll(){
        return iUser.findAll();
    }

    @Transactional
    public UserModel save (UserModel user) {
        if(user.isAdmin()){
            user.setRole("ROLE_ADMIN");
        }else {
            user.setRole("ROLE_USER");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return iUser.save(user);
    }


    public boolean existsByUsername(String username) {
        return iUser.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return iUser.existsByEmail(email);
    }

}
