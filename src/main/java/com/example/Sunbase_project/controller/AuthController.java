
package com.example.Sunbase_project.controller;

import com.example.Sunbase_project.dto.request.LoginRequest;
import com.example.Sunbase_project.dto.request.OtpRequest;
import com.example.Sunbase_project.dto.response.BaseResponseDto;
import com.example.Sunbase_project.services.AuthService;
import com.example.Sunbase_project.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public BaseResponseDto login(@RequestBody LoginRequest request ) {
        BaseResponseDto responseDto;
        try {
            responseDto = authService.login(request);
            log.debug("Response Sent For /login :{}", responseDto);
        }catch (Exception e) {
            responseDto = new BaseResponseDto(e);
            log.error("Exception: ", e);
        }
        return responseDto;
    }
    @PostMapping("/verify-otp")
    public BaseResponseDto verifyOtp(@RequestBody OtpRequest otp) {
        BaseResponseDto responseDto;
        try {
            responseDto = authService.verifyOtp(otp);
            log.debug("Response Sent For /login :{}", responseDto);
        }catch (Exception e) {
            responseDto = new BaseResponseDto(e);
            log.error("Exception: ", e);
        }
        return responseDto;
    }
}
