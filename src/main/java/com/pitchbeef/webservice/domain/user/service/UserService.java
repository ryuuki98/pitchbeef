package com.pitchbeef.webservice.domain.user.service;


import com.pitchbeef.webservice.domain.user.model.User;
import com.pitchbeef.webservice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByUsername(String userName){
        return userRepository.findByUsername(userName);
    }
}
