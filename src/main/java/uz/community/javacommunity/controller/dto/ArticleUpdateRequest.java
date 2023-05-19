package uz.community.javacommunity.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class ArticleUpdateRequest extends BaseArticleRequest{
    @NotNull(message = "article id cannot be null value")

    private UUID id;
}
