package com.pitchbeef.webservice.domain.user.service;

import com.pitchbeef.webservice.domain.user.model.User;
import com.pitchbeef.webservice.domain.user.repository.UserRepository;
import com.pitchbeef.webservice.web.user.dto.UserJoinDto;
import com.pitchbeef.webservice.web.user.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //CREATE

    /**
     * 유저 저장
     * @param userJoinDto
     * @return User
     */
    public User saveUser(UserJoinDto userJoinDto) {
        User user = createUserFromUserJoinDto(userJoinDto);
        return userRepository.save(user);
    }

    //READ

    /**
     * userName으로 유저 객체 검색
     * @param userName
     * @return User
     */
    public User findByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }

    /**
     * 기본키로 유저 검색
     * @param memberId
     * @return User
     */
    public User findByMemberId(Long memberId) {
        return userRepository.findById(memberId).orElse(null);
    }

    //UPDATE

    public User updateUser(Long memberId, UserUpdateDto userUpdateDto) {
        User user = createUserFromUserUpdateDto(memberId, userUpdateDto);
        return userRepository.save(user);
    }

    //DELETE

    public boolean deleteUser(Long memberId) {
        User user = userRepository.findById(memberId).orElse(null);
        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    private User createUserFromUserJoinDto(UserJoinDto userJoinDto) {
        User user = new User(userJoinDto.getUsername(), userJoinDto.getEmail(), passwordEncoder.encode(userJoinDto.getPassword()));
        return user;
    }

    private User createUserFromUserUpdateDto(Long memberId, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(memberId).orElse(null);
        if (user != null) {
            user.updateUser(userUpdateDto);
        } else {
            return null;
        }
        return user;
    }
}
