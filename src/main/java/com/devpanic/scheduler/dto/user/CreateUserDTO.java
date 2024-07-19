package com.devpanic.scheduler.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CreateUserDTO {
    private String username;
    private String password;
    private String email;
}
