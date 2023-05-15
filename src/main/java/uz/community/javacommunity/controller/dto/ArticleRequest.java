package uz.community.javacommunity.controller.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public record ArticleRequest(
        @NotBlank String name,
        @NotNull UUID categoryId
) {
}
