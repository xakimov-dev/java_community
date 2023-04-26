package uz.community.javacommunity.controller.dto;

import lombok.Builder;
import lombok.Data;
import uz.community.javacommunity.controller.domain.User;

import java.util.Set;

@Builder
@Data
public class UserResponse {
    private String username;
    private String password;
    private Set<String> roles;
    private String info;
    private int age;

    public static UserResponse from(User user, String password) {
        return UserResponse.builder()
                .username(user.getUsername())
                .password(password)
                .roles(user.getRoles())
                .info(user.getInfo())
                .age(user.getAge())
                .build();
    }
}
