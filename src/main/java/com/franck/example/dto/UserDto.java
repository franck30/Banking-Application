package com.franck.example.dto;


import com.franck.example.models.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDto {

    private Integer id;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    public static UserDto fromEntity(User user) {

        return UserDto.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }


    public static User toEntity(UserDto userDto) {

        return User.builder()
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }

}
