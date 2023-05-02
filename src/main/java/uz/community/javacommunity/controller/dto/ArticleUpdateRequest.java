package uz.community.javacommunity.controller.dto;

import lombok.Getter;
import lombok.Setter;
import uz.community.javacommunity.controller.domain.Article;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class ArticleUpdateRequest{
    @NotNull
    private UUID categoryId;
    @NotBlank
    private String name;
}
