package com.myblogbackend.blog.category;

import com.myblogbackend.blog.IntegrationTestUtil;
import com.myblogbackend.blog.models.CategoryEntity;
import com.myblogbackend.blog.repositories.CategoryRepository;
import com.myblogbackend.blog.request.CategoryRequest;
import com.myblogbackend.blog.response.CategoryResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CategoryApiDelegateImplTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void createCategorySuccess() throws Exception {
        UUID categoryId = UUID.randomUUID();
        CategoryRequest request = prepareCategoryForRequesting();
        CategoryEntity categoryEntity = makeCategoryForSaving1(categoryId);

        Mockito.when(categoryRepository.save(Mockito.eq(categoryEntity)))
                .thenReturn(categoryEntity);

        CategoryResponse expectedResponse = prepareCategoryForResponse(categoryId, request.getName());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/category")
                        .content(IntegrationTestUtil.asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(categoryId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(request.getName()));
    }

    public static CategoryRequest prepareCategoryForRequesting() {
        return CategoryRequest.builder()
                .name("Category A")
                .build();
    }

    public static CategoryEntity makeCategoryForSaving1(UUID categoryId) {
        return CategoryEntity.builder()
                .id(categoryId)
                .name("Category A")
                .build();
    }

    public static CategoryResponse prepareCategoryForResponse(UUID categoryId, String name) {
        return CategoryResponse.builder()
                .id(categoryId)
                .name(name)
                .build();
    }

}
