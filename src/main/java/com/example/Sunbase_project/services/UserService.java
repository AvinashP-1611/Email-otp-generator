package com.example.Sunbase_project.services;

import com.example.Sunbase_project.dto.request.UserUpdateRequest;
import com.example.Sunbase_project.dto.request.GetCustomerRequest;
import com.example.Sunbase_project.dto.request.UserRequest;
import com.example.Sunbase_project.dto.response.BaseResponseDto;
import com.example.Sunbase_project.entity.User;
import com.example.Sunbase_project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public BaseResponseDto create(UserRequest request) {
        BaseResponseDto responseDto = new BaseResponseDto();
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .street(request.getStreet())
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();
        userRepository.save(user);
        responseDto.setMessage("User added successfully");
        return responseDto;
    }

    public BaseResponseDto update(UserUpdateRequest request) {
        BaseResponseDto responseDto = new BaseResponseDto();
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .street(request.getStreet())
                .address(request.getAddress())
                .phone(request.getPhone())
                .build();
        userRepository.save(user);
        responseDto.setMessage("User update successfully");
        return responseDto;
    }

    public BaseResponseDto getAllCustomer(GetCustomerRequest request) {
        BaseResponseDto responseDto = new BaseResponseDto();
        List<User> userList = userRepository.findAll();
        if(userList.isEmpty()){
            responseDto.setMessage("No customer found");
            responseDto.setStatusCode("404");
        }
        if(request.getFirstname() != null){
           userList = userRepository.findByName(request.getFirstname());
        }
        if(request.getCity() != null){
            userList = userRepository.findByCity(request.getCity());
        }
        if(request.getEmail() != null){
            userList = userRepository.findByEmail(request.getEmail());
        }
        if(request.getPhone() != null){
            userList = userRepository.findByPhone(request.getPhone());
        }
        else{
            Map<String, Object> responseMap = new HashMap<>();
            List<UserRequest> responseList = new ArrayList<>();
            userList.forEach(item->{
                UserRequest response = UserRequest.builder()
                        .firstName(item.getFirstName())
                        .lastName(item.getLastName())
                        .street(item.getStreet())
                        .address(item.getAddress())
                        .city(item.getCity())
                        .state(item.getState())
                        .email(item.getEmail())
                        .phone(item.getEmail())
                        .build();
                responseList.add(response);
            });
            Page<UserRequest> responsePage = paginate(responseList, request.getPageNo(), request.getPageSize());
            responseMap.put("CustomerList", responsePage.getContent());
            responseMap.put("TotalCount", responseList.size());
            responseDto.setData(responseMap);
            responseDto.setMessage("Customer list fetched");
        }
        return responseDto;
    }
    public BaseResponseDto getCustomerById(int id) {
        BaseResponseDto responseDto = new BaseResponseDto();
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            responseDto.setStatusCode("404");
            responseDto.setMessage("No data found with given id");
        }
        else{
            UserRequest response = UserRequest.builder()
                    .firstName(user.get().getFirstName())
                    .lastName(user.get().getLastName())
                    .street(user.get().getStreet())
                    .address(user.get().getAddress())
                    .city(user.get().getCity())
                    .state(user.get().getState())
                    .email(user.get().getEmail())
                    .phone(user.get().getEmail())
                    .build();
            responseDto.setData(response);
            responseDto.setMessage("User found");
        }
        return responseDto;
    }
    public BaseResponseDto deleteCustomer(int id) {
        BaseResponseDto responseDto = new BaseResponseDto();
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            responseDto.setStatusCode("404");
            responseDto.setMessage("No data found with given id");
        }
        else{
            userRepository.deleteById(user.get().getUserId());
            responseDto.setMessage("Customer delete successfully");
        }
        return responseDto;
    }
    public static <T> Page<T> paginate(List<T> response, int pageNo, int pageSize) {
        int startIndex = pageNo * pageSize;
        int endIndex = Math.min(startIndex + pageSize, response.size());
        if(startIndex > endIndex){
            startIndex = endIndex;
        }
        List<T> pageContent = response.subList(startIndex, endIndex);
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        return new PageImpl<>(pageContent, pageRequest, response.size());
    }

}
