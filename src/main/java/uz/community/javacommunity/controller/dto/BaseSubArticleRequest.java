package uz.community.javacommunity.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseSubArticleRequest {
    UUID articleId;
    UUID parentSubArticleId;
    @NotBlank(message = "error.invalid.name.not_blank")
    String name;
}
