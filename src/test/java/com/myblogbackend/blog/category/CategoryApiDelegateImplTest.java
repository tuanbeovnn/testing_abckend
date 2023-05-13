package com.myblogbackend.blog.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myblogbackend.blog.IntegrationTestUtil;
import com.myblogbackend.blog.mapper.CategoryMapper;
import com.myblogbackend.blog.models.CategoryEntity;
import com.myblogbackend.blog.repositories.CategoryRepository;
import com.myblogbackend.blog.request.CategoryRequest;
import com.myblogbackend.blog.response.CategoryResponse;
import org.junit.Before;
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

import java.util.Optional;
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
    @Autowired
    private CategoryMapper categoryMapper;
    private UUID categoryId;
    private CategoryRequest categoryRequest;
    private CategoryEntity category;

    @Before
    public void setup() {
        categoryId = UUID.randomUUID();
        categoryRequest = prepareCategoryForRequesting();
        category = makeCategoryForSaving(categoryId);
    }

    @Test
    public void givenMoreComplexCategoryData_whenSendData_thenReturnsCategoryCreated() throws Exception {
        Mockito.when(categoryRepository.save(Mockito.any()))
                .thenReturn(category);
        var expectedCategory = toCategoryResponse(category);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/category")
                        .content(IntegrationTestUtil.asJsonString(categoryRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(responseBody().containsObjectBody(expectedCategory, CategoryResponse.class, objectMapper));
    }

    @Test
    public void givenCategoryId_whenSendData_thenReturnsCategoryFound() throws Exception {
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        var expectedCategory = toCategoryResponse(category);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/category/{id}", categoryId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(responseBody().containsObjectBody(expectedCategory, CategoryResponse.class, objectMapper));
    }

    @Test
    public void givenExistingCategory_whenUpdatingCategory_thenReturnsUpdatedCategory() throws Exception {
        // find category
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        // Prepare category update request
        var categoryRequestUpdate = prepareCategoryForRequestingUpdate("testing updated category");
        // Save updated category
        var categoryEntityUpdate = categoryMapper.toCategoryEntity(categoryRequestUpdate);
        Mockito.when(categoryRepository.save(Mockito.any(CategoryEntity.class))).thenReturn(categoryEntityUpdate);
        // Prepare expected response
        var expectedCategory = toCategoryResponse(categoryEntityUpdate);
        // Perform the API request and validate the response
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/category/{id}", categoryId)
                        .content(IntegrationTestUtil.asJsonString(categoryRequestUpdate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(responseBody().containsObjectBody(expectedCategory, CategoryResponse.class, objectMapper));
    }

}
