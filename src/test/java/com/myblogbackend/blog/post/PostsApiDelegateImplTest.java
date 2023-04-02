package com.myblogbackend.blog.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myblogbackend.blog.IntegrationTestUtil;
import com.myblogbackend.blog.models.CategoryEntity;
import com.myblogbackend.blog.repositories.CategoryRepository;
import com.myblogbackend.blog.repositories.PostRepository;
import com.myblogbackend.blog.repositories.UsersRepository;
import com.myblogbackend.blog.request.CategoryRequest;
import com.myblogbackend.blog.response.CategoryResponse;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static com.myblogbackend.blog.ResponseBodyMatcher.responseBody;
import static com.myblogbackend.blog.post.PostsTestApi.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.ArgumentMatchers.any;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostsApiDelegateImplTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PostRepository postRepository;
    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
//        UUID uuid = UUID.randomUUID();
//        CategoryRequest categoryEntity = prepareCategoryForCreating(uuid);
//        Mockito.when(categoryRepository.save(Mockito.any(CategoryEntity.class))).thenReturn(categoryEntity);
//        Mockito.when(categoryRepository.findById(uuid)).thenReturn(Optional.of(categoryEntity));

    }

    @Test
    public void testCreatePost() throws Exception {
        UUID uuid = UUID.randomUUID();
        var expectedPostResponse = makePostResponse(uuid, "Post A", "Post A content");
        mockMvc.perform(post("/api/v1/posts")
                        .content(IntegrationTestUtil.asJsonString(createPostRequestData(uuid)))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    private void prepareRepositoryForFindingCategory(final UUID uuid, final CategoryEntity category) {
        Mockito.when(categoryRepository.findById(uuid)).thenReturn(Optional.of(category));
    }


    @Test
    public void createCategorySuccess() throws Exception {

        UUID categoryId = UUID.randomUUID();
        CategoryRequest request = prepareCategoryForRequesting();
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .id(categoryId)
                .name(request.getName())
                .build();

        Mockito.when(categoryRepository.save(Mockito.any(CategoryEntity.class)))
                .thenReturn(categoryEntity);

        CategoryResponse expectedResponse = makeCategoryResponse(categoryId, request.getName());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/category")
                        .content(IntegrationTestUtil.asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(categoryId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(request.getName()));
    }

    private Optional<CategoryEntity> prepareCategoryForFinding(UUID uuid) {
        return Optional.of(new CategoryEntity(uuid, "Category A", new ArrayList<>()));
//        return Optional.of(CategoryEntity.builder().code("code 1111").name("name 1111").categoryStatus(CategoryStatus.ACTIVE).build());

    }



}
