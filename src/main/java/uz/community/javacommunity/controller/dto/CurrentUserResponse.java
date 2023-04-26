package uz.community.javacommunity.controller.dto;

import lombok.Builder;
import lombok.Getter;
import uz.community.javacommunity.controller.domain.User;

import java.util.List;
import java.util.Set;

@Getter
@Builder
public class CurrentUserResponse {
    private String userName;
    private String info;
    private int age;
    private Set<String> roles;

    public static CurrentUserResponse from(User user) {
        return CurrentUserResponse.builder()
                .userName(user.getUsername())
                .info(user.getInfo())
                .age(user.getAge())
                .roles(user.getRoles())
                .build();
    }
}
