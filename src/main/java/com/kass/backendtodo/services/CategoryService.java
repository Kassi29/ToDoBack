package com.kass.backendtodo.services;

import com.kass.backendtodo.dto.CategoryDTO;
import com.kass.backendtodo.exceptions.PersonalizedException;
import com.kass.backendtodo.models.CategoryModel;
import com.kass.backendtodo.repositories.ICategory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final ICategory iCategory;

    public CategoryService(ICategory ICategory) {
        this.iCategory = ICategory;
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> getAll() {
        List<CategoryModel> categories = iCategory.findAll();
        return categories.stream()
                .map(CategoryDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<CategoryDTO> getById(Long id) {
        Optional<CategoryModel> categoryModelOptional = iCategory.findById(id);
        return categoryModelOptional.map(CategoryDTO::new);
    }

    @Transactional
    public CategoryDTO save(CategoryDTO categoryDTO) {

        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setName(categoryDTO.getName());
        if (iCategory.existsByName(categoryModel.getName())) {
            throw new PersonalizedException("El nombre de la categoría debe ser único: " + categoryModel.getName());
        }

        if (categoryDTO.getParentId() != null) {
            if (iCategory.existsById(categoryDTO.getParentId())) {
                Optional<CategoryModel> parentCategoryOptional = iCategory.findById(categoryDTO.getParentId());
                parentCategoryOptional.ifPresent(categoryModel::setParent);
            } else {
                throw new PersonalizedException("El padre con ID " + categoryDTO.getParentId() + " no existe.");
            }

        } else {
            Optional<CategoryModel> parentNone = parentNone();
            parentNone.ifPresent(categoryModel::setParent);
        }
        CategoryModel savedCategory = iCategory.save(categoryModel);
        return new CategoryDTO(savedCategory);
    }


    @Transactional
    public Optional<CategoryDTO> update(Long id, CategoryDTO categoryModel) {
        Optional<CategoryModel> optionalCategoryModel = iCategory.findById(id);
        if (optionalCategoryModel.isPresent()) {
            CategoryModel category = optionalCategoryModel.get();
            if (category.getId() == 1) {
                throw new PersonalizedException("No puedes editar esta categoria");
            }

            category.setName(categoryModel.getName());

            if (categoryModel.getParentId() != null) {
                Optional<CategoryModel> parentCategory = iCategory.findById(categoryModel.getParentId());
                parentCategory.ifPresent(category::setParent);
            } else {
                Optional<CategoryModel> parentNone = parentNone();
                parentNone.ifPresent(category::setParent);
            }
            CategoryModel updatedCategory = iCategory.save(category);
            return Optional.of(new CategoryDTO(updatedCategory));
        }
        return Optional.empty();


    }

    @Transactional
    public Optional<CategoryDTO> delete(Long id) {
        Optional<CategoryModel> optionalCategoryModel = iCategory.findById(id);
        if (optionalCategoryModel.isPresent()) {
            CategoryModel category = optionalCategoryModel.get();

            if (category.getId() == 1) {
                throw new PersonalizedException("No puedes eliminar esta categoria");
            }
            category.setParent(null);
            iCategory.save(category);

            iCategory.delete(category);
            return Optional.of(new CategoryDTO(category));
        }
        return Optional.empty();
    }


    public Optional<CategoryModel> parentNone() {
        return iCategory.findByName("Ninguna");
    }

}
