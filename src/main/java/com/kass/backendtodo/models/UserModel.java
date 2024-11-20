package com.kass.backendtodo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kass.backendtodo.validation.email.ExistsByEmail;
import com.kass.backendtodo.validation.username.ExistsByUsername;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ExistsByUsername(message = "El username ya esta ocupado")
    @Column(unique = true)
    @Size(min = 4, max = 9)
    @NotBlank
    private String username;


    @ExistsByEmail(message = "El email ya está registrado")
    @Column(unique = true)
    @Email
    @NotBlank
    private String email;


    @NotBlank
    @Size(min = 6)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.*\\s).+$", message = "must contain at least one lowercase letter, one uppercase letter, one special character, and must not have spaces.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String role;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean enabled;


    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean admin = false;

    @PrePersist
    public void prePersist() {
        enabled = true;
    }
}
