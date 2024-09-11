package com.example.gccoffee.service.user;

import com.example.gccoffee.model.Email;
import com.example.gccoffee.model.user.Password;
import com.example.gccoffee.model.user.User;
import com.example.gccoffee.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public List<User> readAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public User readDetail(Email email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NoDataException("해당하는 유저가 없습니다!"));
    }

    @Override
    public User signUp(User user) {
        return userRepository.save(user);
    }

    @Override
    public void edit(Email email, Password password) {
        userRepository.findByEmail(email)
                .orElseThrow(() -> new NoDataException("해당하는 유저가 없습니다!"));
        userRepository.update(password, email);
    }

    @Override
    public void delete(@AuthenticationPrincipal Email email) {
        userRepository.delete(email);
    }
}
