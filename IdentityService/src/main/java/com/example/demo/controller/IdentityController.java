package com.example.demo.controller;

import com.example.demo.dto.request.UserRequestDTO;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.service.IdentityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/identity")
public class IdentityController {
    private final IdentityService identityService;

    public IdentityController(IdentityService identityService) {
        this.identityService = identityService;
    }

    @GetMapping(value = "/user")
    public ResponseEntity<ApiResponse> checkExistsUser(@RequestParam("username")String username) {
        return ResponseEntity.ok(ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(identityService.isExistUsername(username))
                .build());
    }
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody UserRequestDTO userRequestDTO) {

        ResponseEntity<ApiResponse> response = ResponseEntity.ok(ApiResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Created user: " + userRequestDTO.getUsername())
                .data(identityService.registerUser(userRequestDTO))
                .build());
        return response;
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@RequestBody UserRequestDTO userRequestDTO) {
        boolean result = identityService.isValidUser(userRequestDTO);
        ResponseEntity<ApiResponse> response = ResponseEntity.ok(ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Login user: " + userRequestDTO.getUsername())
                .data(result)
                .build());
        return response;
    }
    @PutMapping("/user")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(ApiResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Updated user: " + userRequestDTO.getUsername())
                .data(identityService.updateUser(userRequestDTO))
                .build());
    }
    @DeleteMapping("/user/{username}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("username")String username) {
        identityService.deleteUser(username);
        return ResponseEntity.ok(ApiResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Deleted user: " + username)
                .build());
    }
}
