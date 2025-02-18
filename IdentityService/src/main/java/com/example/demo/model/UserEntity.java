package com.example.demo.model;

import com.example.demo.dto.request.UserRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserEntity {
    @Id
    private String username;
    @Column(name = "password")
    private String password;

    public UserRequestDTO toUserRequestDTO() {
        return UserRequestDTO.builder()
                .username(this.username)
                .build();
    }
}
