package com.project.withpet.service;

import com.project.withpet.domain.User;
import com.project.withpet.repository.User.UserRepository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;


import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(String userid){
        return userRepository.findById(userid);
    }


    public boolean checkUser(String userId, String password){
        Optional<User> user = userRepository.findById(userId);
        try{
            if(user.get().getPassword().equals(password)){
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean login(User user) {
        Optional<User> findUser = userRepository.findById(user.getUserId());

        try {
            System.out.printf("From UserService.login(), %s\n", findUser.get().getUserId());
        } catch (NoSuchElementException e) {
            return false;
        }

        if (!findUser.get().getPassword().equals(user.getPassword())) {
            return false;
        }
        System.out.println("From UserService.login(), id, pwd 모두 일치");
        return true;
    }

    public String join_str(User user) {
        validDuplicateUser(user);

        userRepository.save(user);
        return user.getUserId();
    }

    public Optional<User> join(User user){
        userRepository.save(user);
        return Optional.ofNullable(user);
    };

    public void validDuplicateUser(User user) {
        userRepository.findById(user.getUserId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
        System.out.println("ID 중복 검사 통과");
    }

    public void delete(String id){
        userRepository.deleteByUserid(id);
    }

    /*public Map<String, String> validateHandling(BindingResult errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }*/
}
