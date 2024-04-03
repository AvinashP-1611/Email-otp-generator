package com.example.Sunbase_project.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateRequest {
    private int userId;
    private String firstName;
    private String lastName;
    private String street;
    private String address;
    private String phone;
}
