package com.example.gccoffee.controller.api;

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

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserRestController {

    private final UserService userService;

    @GetMapping("/list")
    public ApiResponse<> read() {
        userService.readAll();
        return ;
    }

    @GetMapping
    public ApiResponse<> readDetail(@AuthenticationPrincipal Email email) {
        userService.readDetail(email);
        return ;
    }

    @PostMapping
    public ApiResponse<> signUp(SignUpRequest request) {
        User user = User.from(request);
        userService.signUp(user);
        return ;
    }

    @PutMapping
    public ApiReponse<> edit(@AuthenticationPrincipal Email email, UserUpdateRequest request) {
        Password password = request.password();
        userService.edit(email, password);
        return ;
    }

    @DeleteMapping
    public ApiResponse<> delete(@AuthenticationPrincipal Email email) {
        userService.delete(email);
        return ;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = userService.login(request.email(), request.password());
        return ResponseEntity.ok().header("Authorization", "Bearer " + token).build();
    }
}
