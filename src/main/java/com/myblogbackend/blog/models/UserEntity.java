package com.myblogbackend.blog.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column
    @NotBlank(message = "User email cannot be null")
    private String email;

    @Column
    @NotNull(message = "Password cannot be null")
    private String password;

    @Column
    @NotBlank(message = "Name can not be blank")
    private String name;

    @Column(nullable = false)
    private Boolean active;


    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }
}