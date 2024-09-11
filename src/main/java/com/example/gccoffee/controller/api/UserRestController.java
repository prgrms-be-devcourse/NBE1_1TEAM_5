package com.example.gccoffee.controller.api;

import com.example.gccoffee.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserRestController {

    private final UserService userService;


    @GetMapping
    public ApiResponse<> read(@AuthenticationPrincipal ) {

        userService.findAll();
    }

    @GetMapping("/{userId}")
    public ApiResponse<> readDetail(@PathVariable Long userId) {
        userService.findUser()
    }

    @PostMapping
    public ApiResponse<> signUp(UserRequest request) {

        userService.addUser();
    }

    @PutMapping("/{userId}")
    public ApiReponse<> edit(UpdateRequest request, @PathVariable Long userId) {

        userService.update();
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<> delete(@PathVariable Long userId) {
        userService.remove();
    }



}
