package com.kass.backendtodo.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="Status")
@Getter
@Setter
@NoArgsConstructor
public class StatusModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 3, max = 15)
    private String name;

    @Override
    public String toString() {
        return "StatusModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
