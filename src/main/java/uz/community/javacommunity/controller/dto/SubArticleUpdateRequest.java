package uz.community.javacommunity.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class SubArticleUpdateRequest extends BaseSubArticleRequest{
    @NotNull(message = "sub article id cannot be null value")
    private UUID id;
}
