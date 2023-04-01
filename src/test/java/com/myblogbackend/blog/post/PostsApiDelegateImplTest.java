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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;
import java.util.UUID;


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
        UUID uuid = UUID.randomUUID();
        prepareRepositoryForFindingCategory(uuid, PostsTestApi.prepareCategoryForCreating(uuid));
        var expectedPostResponse = PostsTestApi.makePostResponse(uuid, PostsTestApi.createPostRequestData().getTitle(),
                PostsTestApi.createPostRequestData().getContent());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/posts")
                        .content(IntegrationTestUtil.asJsonString(PostsTestApi.createPostRequestData()))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private void prepareRepositoryForFindingCategory(final UUID uuid, final CategoryEntity category) {
        Mockito.when(categoryRepository.findById(uuid)).thenReturn(Optional.of(category));
    }

}
