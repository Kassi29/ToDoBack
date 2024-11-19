package com.kass.backendtodo.dto;


import com.kass.backendtodo.models.CategoryModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {

    private Long id;
    private String name;
    private Long parentId;

    public CategoryDTO(CategoryModel categoryModel) {
        this.id = categoryModel.getId();
        this.name = categoryModel.getName();
        if (categoryModel.getParent() != null) {
            this.parentId = categoryModel.getParent().getId();
        } else {
            this.parentId = null;
        }

    }
}
