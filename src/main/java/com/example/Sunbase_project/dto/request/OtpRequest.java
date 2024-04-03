package com.example.Sunbase_project.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OtpRequest {
    private String email;
    private String otp;
}
