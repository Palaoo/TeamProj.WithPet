package com.project.withpet.service;

import com.project.withpet.domain.User;
import com.project.withpet.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> join(User user){
        userRepository.save(user);
        return Optional.ofNullable(user);
    };

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

}
