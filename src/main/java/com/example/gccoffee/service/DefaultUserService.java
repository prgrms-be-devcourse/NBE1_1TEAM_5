package com.example.gccoffee.service;


import com.example.gccoffee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultUserService implements UserService  {

    private final UserRepository userRepository;

}
