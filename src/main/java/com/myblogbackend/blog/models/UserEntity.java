package com.myblogbackend.blog.models;

import com.myblogbackend.blog.enums.OAuth2Provider;
import com.myblogbackend.blog.models.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String name;

    @Column(nullable = false)
    private Boolean active;
    @Column(name = "provider")
    @Enumerated(EnumType.STRING)
    private OAuth2Provider provider;


    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }
}