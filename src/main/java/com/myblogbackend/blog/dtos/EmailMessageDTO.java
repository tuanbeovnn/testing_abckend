package com.myblogbackend.blog.dtos;

import com.myblogbackend.blog.dtos.EmailAdditionalPropertiesDTO;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailMessageDTO {

    @NotEmpty
    private String to;

    private EmailAdditionalPropertiesDTO additionalProperties;
}
