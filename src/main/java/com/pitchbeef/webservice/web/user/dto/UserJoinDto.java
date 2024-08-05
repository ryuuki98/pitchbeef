package com.pitchbeef.webservice.web.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserJoinDto {
    private String username;
    private String email;
    private String password;
}
