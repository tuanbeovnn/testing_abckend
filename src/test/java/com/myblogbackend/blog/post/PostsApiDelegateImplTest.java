package com.myblogbackend.blog.post;

import com.myblogbackend.blog.IntegrationTestUtil;
import com.myblogbackend.blog.models.CategoryEntity;
import com.myblogbackend.blog.repositories.CategoryRepository;
import com.myblogbackend.blog.repositories.PostRepository;
import com.myblogbackend.blog.repositories.UsersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static com.myblogbackend.blog.post.PostsTestApi.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostsApiDelegateImplTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void testCreatePost() throws Exception {
        // arrange
        UUID uuid = UUID.randomUUID();
        prepareRepositoryForFindingCategory(uuid, prepareCategoryForCreating(uuid));
        var expectedPostResponse = makePostResponse(uuid, createPostRequestData().getTitle(), createPostRequestData().getContent());
        mockMvc.perform(post("/api/v1/posts")
                        .content(IntegrationTestUtil.asJsonString(createPostRequestData()))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    private void prepareRepositoryForFindingCategory(UUID uuid, CategoryEntity category) {
        Mockito.when(categoryRepository.findById(uuid)).thenReturn(Optional.of(category));
    }

}
