package com.example.Sunbase_project.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetCustomerRequest {
    private Integer userId;
    private String firstname;
    private String city;
    private String email;
    private String phone;
    private Integer pageNo;
    private Integer pageSize;
}
