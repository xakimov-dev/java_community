package uz.community.javacommunity.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ArticleCreateRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String categoryId;
}
