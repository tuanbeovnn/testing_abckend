package com.myblogbackend.blog.mapper;
import com.myblogbackend.blog.response.UserResponse;
import com.myblogbackend.blog.models.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toUserEntity(UserResponse userResponse);
    UserResponse toUserDTO(UserEntity userEntity);
}
