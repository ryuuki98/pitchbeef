package com.pitchbeef.webservice.domain.user.model;

import com.pitchbeef.webservice.web.user.dto.UserJoinDto;
import com.pitchbeef.webservice.web.user.dto.UserUpdateDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Entity
@Table(name = "Users")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreatedDate
    private LocalDateTime regDate;

    @LastModifiedDate
    private LocalDateTime modDate;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void updateUser(UserUpdateDto userUpdateDto) {
        this.username = userUpdateDto.getUsername() != null ? userUpdateDto.getUsername() : this.username;
        this.email = userUpdateDto.getEmail() != null ? userUpdateDto.getEmail() : this.email;
        this.password = userUpdateDto.getPassword() != null ? userUpdateDto.getPassword() : this.password;
    }
}
