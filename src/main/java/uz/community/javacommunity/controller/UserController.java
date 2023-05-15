package uz.community.javacommunity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.community.javacommunity.controller.converter.UserConverter;
import uz.community.javacommunity.controller.domain.User;
import uz.community.javacommunity.controller.dto.*;
import uz.community.javacommunity.service.UserService;

import java.security.Principal;

import static org.springframework.http.HttpStatus.CREATED;

@Tag(name = "user")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserConverter userConverter;

    @PostMapping(value = "/login")
    @Operation(summary = "Authenticate a person")
    public JwtTokenResponse userLogin(@RequestBody @Validated JwtTokenRequest loginRequest) {
        String token = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return JwtTokenResponse.builder()
                .token(token)
                .build();
    }

    @GetMapping("/current")
    @Operation(summary = "Get current user info")
    UserResponse currentUser(Principal principal) {
        User user = userService.findByUserName(principal.getName());
        return userConverter.convertEntityToResponse(user);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Create a user. Should be used only by BE team")
    public UserResponse createUser(@RequestBody @Validated UserRequest userRequest) {

        User user = userConverter.convertRequestToEntity(userRequest);
        return userConverter.convertEntityToResponse(userService.create(user));
    }
}
