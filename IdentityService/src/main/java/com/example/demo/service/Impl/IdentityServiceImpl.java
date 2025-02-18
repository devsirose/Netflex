package com.example.demo.service.Impl;

import com.example.demo.dto.request.UserRequestDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.IdentityService;
import org.springframework.stereotype.Service;

@Service
public class IdentityServiceImpl implements IdentityService {
    private final UserRepository userRepository;

    public IdentityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isExistUsername(String username) {
        return userRepository.existsById(username);
    }

    @Override
    public UserRequestDTO registerUser(UserRequestDTO userRequestDTO) {
        return userRepository.save(UserMapper.toUserEntity(userRequestDTO)).toUserRequestDTO();
    }

    @Override
    public UserRequestDTO updateUser(UserRequestDTO userRequestDTO) {
        return userRepository.save(UserMapper.toUserEntity(userRequestDTO)).toUserRequestDTO();
    }

    @Override
    public boolean isValidUser(UserRequestDTO userRequestDTO) {
        return userRepository.findByUsername(userRequestDTO.getUsername()).getPassword()
                .equals(userRequestDTO.getPassword());
    }

    @Override
    public void deleteUser(String username) {
        userRepository.delete(userRepository.findByUsername(username));
    }

}
