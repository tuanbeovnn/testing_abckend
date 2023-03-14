package com.myblogbackend.blog.mapper;
import com.myblogbackend.blog.dtos.UserResponse;
import com.myblogbackend.blog.models.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toUserEntity(UserResponse userResponse);
    UserResponse toUserDTO(UserEntity userEntity);
}
