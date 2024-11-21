package com.kass.backendtodo.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name="Category")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CategoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 2, max = 50)
    @Column(unique = true)
    private String name;


    @Nullable
    @JsonIgnoreProperties
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    private CategoryModel parent;


    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoryModel> children;


}
