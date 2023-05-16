package uz.community.javacommunity.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest extends BaseUserRequest{
    @NotBlank
    String username;
    @NotBlank
    String password;
    @NotEmpty
    Set<String> roles;
    @NotBlank
    String info;

}
