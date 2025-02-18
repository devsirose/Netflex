package com.example.demo.service;

import com.example.demo.dto.request.UserRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface IdentityService {
    boolean isExistUsername(String username);
    UserRequestDTO registerUser(UserRequestDTO userRequestDTO);
    UserRequestDTO updateUser(UserRequestDTO userRequestDTO);
    boolean isValidUser(UserRequestDTO userRequestDTO);
    void deleteUser(String username);
}
