package com.pitchbeef.webservice.domain.user.service;


import com.pitchbeef.webservice.domain.user.model.User;
import com.pitchbeef.webservice.domain.user.repository.UserRepository;
import com.pitchbeef.webservice.web.user.dto.UserJoinDto;
import com.pitchbeef.webservice.web.user.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;




    //CREATE

    /**
     * 유저 저장
     *
     * @param userJoinDto
     * @return user
     */
    public User saveUser(UserJoinDto userJoinDto) {
        User user = createUserFromUserJoinDto(userJoinDto);
        return userRepository.save(user);
    }

    //READ

    /**
     * 아이디로 유저 객체 검색
     * @param userName
     * @return User
     */

    public User findByUsername(String userName){
        return userRepository.findByUsername(userName);
    }

    //UPDATE




    //DELETE


    private static User createUserFromUserJoinDto(UserJoinDto userJoinDto) {
        User user = new User(userJoinDto.getUsername(), userJoinDto.getEmail(), userJoinDto.getPassword(),true);
        return user;
    }
}
