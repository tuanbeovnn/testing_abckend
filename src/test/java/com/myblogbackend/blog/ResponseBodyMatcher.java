package com.myblogbackend.blog;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseBodyMatcher {
    public static ResponseBodyMatcher responseBody() {
        return new ResponseBodyMatcher();
    }

    public <T>ResultMatcher containsObjectBody(Object objectExpected, Class<T> targetClass, ObjectMapper objectMapper) {
        return mvc -> {
            String json = mvc.getResponse().getContentAsString();
            T actualObject = objectMapper.readValue(json, targetClass);
            assertThat(actualObject).usingRecursiveComparison().isEqualTo(objectExpected);
        };
    }
}
