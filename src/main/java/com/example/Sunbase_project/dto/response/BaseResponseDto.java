package com.example.Sunbase_project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponseDto {
    private String statusCode = "200";
    private String message;
    private Object data;
    public BaseResponseDto (String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
    public BaseResponseDto (Exception e) {
        this.statusCode = "500";
        this.data = new ArrayList<>();
        this.message = e.getMessage();
    }

}
