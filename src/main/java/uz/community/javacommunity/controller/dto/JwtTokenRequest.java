package uz.community.javacommunity.controller.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public record JwtTokenRequest(
        @NotBlank String username,
        @NotBlank String password
) {
}
