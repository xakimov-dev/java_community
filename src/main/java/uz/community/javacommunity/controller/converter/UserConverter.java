package uz.community.javacommunity.controller.converter;


import org.springframework.stereotype.Component;
import uz.community.javacommunity.controller.domain.User;
import uz.community.javacommunity.controller.dto.UserRequest;
import uz.community.javacommunity.controller.dto.UserResponse;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
@Component
public class UserConverter {

    public User convertUpdateRequestToEntity(UserRequest userRequest) {
        Instant now = Instant.now();
        return User.builder()
                .modifiedDate(now)
                .age(userRequest.getAge())
                .info(userRequest.getInfo())
                .roles(userRequest.getRoles())
                .imageUrl(userRequest.getImgUrl())
                .username(userRequest.username())
                .password(userRequest.password())
                .build();    }

    public User convertRequestToEntity(UserRequest userRequest) {
        Instant now = Instant.now();
        return User.builder()
                .modifiedDate(now)
                .createdDate(now)
                .age(userRequest.getAge())
                .info(userRequest.getInfo())
                .roles(userRequest.getRoles())
                .imageUrl(userRequest.getImgUrl())
                .username(userRequest.username())
                .password(userRequest.password())
                .build();
    }

    public UserResponse convertEntityToResponse(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .age(user.getAge())
                .imgUrl(user.getImageUrl())
                .createdDate(user.getCreatedDate())
                .modifiedDate(user.getModifiedDate())
                .roles(user.getRoles())
                .info(user.getInfo())
                .build();
    }

    public List<UserResponse> convertEntitiesToResponse(List<User> users) {
        return users.stream().map(this::convertEntityToResponse).toList();
    }
}
