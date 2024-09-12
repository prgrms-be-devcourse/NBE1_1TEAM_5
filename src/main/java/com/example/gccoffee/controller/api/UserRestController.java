package com.example.gccoffee.controller.api;

import com.example.gccoffee.apiResponse.ApiResponse;
import com.example.gccoffee.controller.UserResponse;
import com.example.gccoffee.controller.request.LoginRequest;
import com.example.gccoffee.controller.request.SignUpRequest;
import com.example.gccoffee.controller.request.UserUpdateRequest;
import com.example.gccoffee.model.Email;
import com.example.gccoffee.model.user.Password;
import com.example.gccoffee.model.user.User;
import com.example.gccoffee.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserRestController {

    private final UserService userService;

    @GetMapping("/list")
    public ApiResponse<List<UserResponse>> read() {
        List<UserResponse> responses = userService.readAll().stream()
                .map(UserResponse::from)
                .toList();
        return ApiResponse.onSuccess(responses);
    }

    @GetMapping
    public ApiResponse<UserResponse> readDetail(@AuthenticationPrincipal Email email) {
        User user = userService.readDetail(email);
        UserResponse response = UserResponse.from(user);
        return ApiResponse.onSuccess(response);
    }

    @PostMapping
    public ApiResponse<UserResponse> signUp(@RequestBody SignUpRequest request) {
        User user = User.from(request);
        User entity = userService.signUp(user);
        UserResponse response = UserResponse.from(entity);
        return ApiResponse.onSuccess(response);
    }

    @PutMapping
    public ApiResponse<Void> edit(@AuthenticationPrincipal Email email, UserUpdateRequest request) {
        Password password = request.password();
        userService.edit(email, password);
        return ApiResponse.onSuccess(null);
    }

    @DeleteMapping
    public ApiResponse<Void> delete(@AuthenticationPrincipal Email email) {
        userService.delete(email);
        return ApiResponse.onSuccess(null);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest request) {
        String token = userService.login(request.email(), request.password());
        return ResponseEntity.ok().header("Authorization", "Bearer " + token).build();
    }
}
