package uz.community.javacommunity.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class JwtTokenRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
