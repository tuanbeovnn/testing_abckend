package com.myblogbackend.blog.repositories;

import com.myblogbackend.blog.models.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
