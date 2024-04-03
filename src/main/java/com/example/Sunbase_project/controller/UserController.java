package com.example.Sunbase_project.controller;

import com.example.Sunbase_project.dto.request.UserRequest;
import com.example.Sunbase_project.services.UserService;
import com.example.Sunbase_project.dto.request.UserUpdateRequest;
import com.example.Sunbase_project.dto.request.GetCustomerRequest;
import com.example.Sunbase_project.dto.response.BaseResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public BaseResponseDto create(@RequestBody UserRequest request) {
        BaseResponseDto responseDto;
        try {
            responseDto = userService.create(request);
            log.debug("Response Sent For /create-user :{}", responseDto);
        }catch (Exception e) {
            responseDto = new BaseResponseDto(e);
            log.error("Exception: ", e);
        }
        return responseDto;
    }
    @PutMapping("/update")
    public BaseResponseDto update(@RequestBody UserUpdateRequest request) {
        BaseResponseDto responseDto;
        try {
            responseDto = userService.update(request);
            log.debug("Response Sent For /update-user :{}", responseDto);
        }catch (Exception e) {
            responseDto = new BaseResponseDto(e);
            log.error("Exception: ", e);
        }
        return responseDto;
    }
    @GetMapping("/get-all-customer")
    public BaseResponseDto getAllCustomer(@RequestBody GetCustomerRequest request) {
        BaseResponseDto responseDto;
        try {
            responseDto = userService.getAllCustomer(request);
            log.debug("Response Sent For /get-all-customer :{}", responseDto);
        }catch (Exception e) {
            responseDto = new BaseResponseDto(e);
            log.error("Exception: ", e);
        }
        return responseDto;
    }
    @GetMapping("/get-customer-by-id")
    public BaseResponseDto getCustomerById(@RequestParam int id) {
        BaseResponseDto responseDto;
        try {
            responseDto = userService.getCustomerById(id);
            log.debug("Response Sent For /get-customer-by-id :{}", responseDto);
        }catch (Exception e) {
            responseDto = new BaseResponseDto(e);
            log.error("Exception: ", e);
        }
        return responseDto;
    }
    @DeleteMapping("/delete")
    public BaseResponseDto deleteCustomer(@RequestParam int id) {
        BaseResponseDto responseDto;
        try {
            responseDto = userService.deleteCustomer(id);
            log.debug("Response Sent For /delete-customer-by-id :{}", responseDto);
        }catch (Exception e) {
            responseDto = new BaseResponseDto(e);
            log.error("Exception: ", e);
        }
        return responseDto;
    }

}
