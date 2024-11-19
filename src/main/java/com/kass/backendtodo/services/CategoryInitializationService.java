package com.kass.backendtodo.services;

import com.kass.backendtodo.models.CategoryModel;
import com.kass.backendtodo.repositories.ICategory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class CategoryInitializationService implements CommandLineRunner {

    private final ICategory iCategory;

    public CategoryInitializationService(ICategory iCategory) {
        this.iCategory = iCategory;
    }


    @Override
    public void run(String... args) {
        System.out.println("Initializing Category");
        String category = "Ninguna";
        if(!iCategory.existsByName(category)){
            CategoryModel rootCategory = new CategoryModel();
            rootCategory.setName(category);
            rootCategory.setParent(null);
            iCategory.save(rootCategory);
        }
    }
}
