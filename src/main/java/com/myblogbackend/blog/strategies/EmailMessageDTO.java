package com.myblogbackend.blog.strategies;

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
