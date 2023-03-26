package com.myblogbackend.blog.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myblogbackend.blog.models.CategoryEntity;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class PostResponse {
    private Long id;
    private String title;
    private String content;

}
