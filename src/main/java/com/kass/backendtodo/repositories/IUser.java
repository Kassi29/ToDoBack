package com.kass.backendtodo.repositories;

import com.kass.backendtodo.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUser extends JpaRepository<UserModel, Long> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);



    Optional<UserModel> findByUsername(String username);

}
