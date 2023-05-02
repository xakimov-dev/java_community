package uz.community.javacommunity.controller.dto;

import uz.community.javacommunity.controller.domain.Article;

import javax.validation.constraints.NotBlank;

public record ArticleUpdateRequest(
        @NotBlank Article.ArticleKey articleKey,
        @NotBlank String name
) {
    public ArticleUpdateRequest(Article.ArticleKey articleKey,String name) {
        this.articleKey = articleKey;
        this.name = name;
    }
}
