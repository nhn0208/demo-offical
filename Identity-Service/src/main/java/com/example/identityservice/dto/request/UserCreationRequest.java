package com.example.identityservice.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreationRequest {
    private String username;
    private String firstName;
    private String lastName;

    @Size(min = 8, message = "Password must be at least 8 characters!")
    private String password;
    private String email;
    private List<String> roles;         //gây lỗi (sửa -> comment)
}
