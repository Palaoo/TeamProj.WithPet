package com.example.WithPet.Service;

import com.example.WithPet.domain.User;
import com.example.WithPet.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean login(User user) {
        Optional<User> findUser = userRepository.findById(user.getId());

        try {
            System.out.printf("From UserService.login(), %s\n", findUser.get().getId());
        } catch (NoSuchElementException e) {
            return false;
        }

        if (!findUser.get().getPassword().equals(user.getPassword())) {
            return false;
        }
        System.out.println("From UserService.login(), id, pwd 모두 일치");
        return true;
    }

    public String join(User user) {
        validDuplicateUser(user);

        userRepository.save(user);
        return user.getId();
    }

    public void validDuplicateUser(User user) {
        System.out.println("ID 중복 검사중");
        userRepository.findById(user.getId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
}
