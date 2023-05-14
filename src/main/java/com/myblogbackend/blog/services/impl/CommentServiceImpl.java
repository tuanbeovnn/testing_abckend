package com.myblogbackend.blog.services.impl;

import com.myblogbackend.blog.exception.commons.BlogRuntimeException;
import com.myblogbackend.blog.exception.commons.ErrorCode;
import com.myblogbackend.blog.mapper.CommentMapper;
import com.myblogbackend.blog.repositories.CommentRepository;
import com.myblogbackend.blog.repositories.PostRepository;
import com.myblogbackend.blog.repositories.UsersRepository;
import com.myblogbackend.blog.request.CommentRequest;
import com.myblogbackend.blog.response.CommentResponse;
import com.myblogbackend.blog.services.CommentService;
import com.myblogbackend.blog.utils.JWTSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;
    private final UsersRepository usersRepository;
    private final CommentRepository commentRepository;

    @Override
    public CommentResponse createComment(CommentRequest commentRequest) {
        //get the user signed in
        var signedInUser = JWTSecurityUtil.getJWTUserInfo().orElseThrow();
        //find the post by post id
        var post = postRepository.findById(commentRequest.getPostId())
                .orElseThrow(() -> new BlogRuntimeException(ErrorCode.ID_NOT_FOUND));
        //convert comment request to comment entity
        var commentEntity = commentMapper.toCommentEntity(commentRequest);
        //find user based on signedInUser id
        var userFound = usersRepository.findById(signedInUser.getId()).orElseThrow();
        //set the user to the comment
        commentEntity.setUser(userFound);
        //set the post to the comment
        commentEntity.setPost(post);
        //save comment
        var createdComment = commentRepository.save(commentEntity);
        //convert to comment response
        return commentMapper.toCommentResponse(createdComment);
    }

    @Override
    public List<CommentResponse> getListComments() {
        return null;
    }
}
