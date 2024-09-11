package com.example.gccoffee.service;


public interface UserService {

    ApiResponse<> read(Long userId);
    ApiResponse<> readDetail();
    ApiResponse<> signUp(UserRequest request);
    ApiReponse<> edit(UpdateRequest request);
    ApiResponse<> delete();
}
