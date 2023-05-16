package uz.community.javacommunity.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
public class BaseSubArticleRequest {
    UUID articleId;
    UUID parentSubArticleId;
    @NotBlank(message = "error.invalid.name.not_blank")
    String name;
}
