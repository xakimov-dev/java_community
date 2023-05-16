package uz.community.javacommunity.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class ArticleRequest {
    @NotBlank
    private String name;
    @NotNull
    private UUID categoryId;
}
