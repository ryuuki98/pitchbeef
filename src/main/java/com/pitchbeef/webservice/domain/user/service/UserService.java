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
     * userName로 유저 객체 검색
     * @param userName
     * @return User
     */

    public User findByUsername(String userName){
        return userRepository.findByUsername(userName);
    }


    /**
     * 기본키로 유저 검색
     * @param id
     * @return User
     */
    public User findById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    //UPDATE

    public User UpdateUser(Long id, UserUpdateDto userUpdateDto){
        User user = createUserFromUserUpdateDto(id,userUpdateDto);
        User updateUser = userRepository.save(user);
        return updateUser;
    }




    //DELETE

    public boolean deleteUser(Long id){
        User user = userRepository.findById(id).orElse(null);
        if(user != null){
            userRepository.delete(user);
        }else return false;
        return true;
    }

    private User createUserFromUserJoinDto(UserJoinDto userJoinDto) {
        User user = new User(userJoinDto.getUsername(), userJoinDto.getEmail(), userJoinDto.getPassword());
        return user;
    }

    private User createUserFromUserUpdateDto(Long id, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(id).orElse(null);
        if(user != null) {
            user.updateUser(userUpdateDto);
        }else return null;

        return user;
    }



}
