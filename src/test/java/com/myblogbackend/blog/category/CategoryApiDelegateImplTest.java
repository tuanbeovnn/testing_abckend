package com.myblogbackend.blog.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myblogbackend.blog.IntegrationTestUtil;
import com.myblogbackend.blog.repositories.CategoryRepository;
import com.myblogbackend.blog.response.CategoryResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static com.myblogbackend.blog.ResponseBodyMatcher.responseBody;
import static com.myblogbackend.blog.category.CategoryTestApi.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CategoryApiDelegateImplTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryRepository categoryRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenMoreComplexCategoryData_whenSendData_thenReturnsCategoryCreated() throws Exception {
        var categoryId = UUID.randomUUID();
        var categoryRequest = prepareCategoryForRequesting();
        var category = makeCategoryForSaving(categoryId);

        Mockito.when(categoryRepository.save(Mockito.any()))
                .thenReturn(category);

        var expectedCategory = toCategoryResponse(category);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/category")
                        .content(IntegrationTestUtil.asJsonString(categoryRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(responseBody().containsObjectBody(expectedCategory, CategoryResponse.class, objectMapper));
    }


}
