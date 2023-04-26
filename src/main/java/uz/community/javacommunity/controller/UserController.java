package uz.community.javacommunity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
    CurrentUserResponse currentUser(Principal principal) {
        String username = principal.getName();
        User user = userService.findByUserName(username);
        return CurrentUserResponse.from(user);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Create a user. Should be used only by BE team")
    public UserResponse createUser(@RequestBody @Validated UserCreateRequest userCreateRequest) {
        User user = userService.create(userCreateRequest);
        return UserResponse.from(user, userCreateRequest.getPassword());
    }
}
