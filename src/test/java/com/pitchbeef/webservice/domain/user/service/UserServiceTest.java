package com.pitchbeef.webservice.domain.user.service;

import com.pitchbeef.webservice.domain.user.model.User;
import com.pitchbeef.webservice.domain.user.repository.UserRepository;
import com.pitchbeef.webservice.web.user.dto.UserJoinDto;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserServiceTest {

    private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        // auto_increment 값 초기화
        jdbcTemplate.execute("ALTER TABLE users AUTO_INCREMENT = 0");
    }


    @Test
    @Transactional
    void saveUser(){
        UserJoinDto userJoinDto = new UserJoinDto();
        userJoinDto.setUsername("하이1");
        userJoinDto.setPassword("123456");
        userJoinDto.setEmail("pitchbeef1@gmail.com");

        User user = userService.saveUser(userJoinDto);
        User findUser = userService.findById(user.getId());

        log.info("user ={}",user);
        log.info("findUser ={}",findUser);
        Assertions.assertThat(user).isEqualTo(findUser);
    }

}