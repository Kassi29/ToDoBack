package com.kass.backendtodo.repositories;

import com.kass.backendtodo.models.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICategory extends JpaRepository<CategoryModel, Long> {

    Optional<CategoryModel> findByName(String name);

    boolean existsByName(String name);
}
