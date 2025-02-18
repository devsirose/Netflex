package com.example.demo.mapper;

import com.example.demo.dto.request.UserRequestDTO;
import com.example.demo.model.UserEntity;

public class UserMapper {
    public static UserEntity toUserEntity(UserRequestDTO userRequestDTO) {
        UserEntity userEntity = UserEntity.builder()
                .username(userRequestDTO.getUsername())
                .password(userRequestDTO.getPassword())
                .build();
        return userEntity;
    }
}
