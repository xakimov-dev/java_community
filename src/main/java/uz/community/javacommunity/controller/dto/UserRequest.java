package uz.community.javacommunity.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
public record UserRequest(
        @NotBlank String username,
        @NotBlank String password,
        @NotEmpty Set<String> roles,
        Integer age,
        @NotBlank String info,
        String imgUrl
) {
}
