package uz.community.javacommunity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.community.javacommunity.controller.converter.ArticleConverter;
import uz.community.javacommunity.controller.domain.Article;
import uz.community.javacommunity.controller.dto.ArticleCreateRequest;
import uz.community.javacommunity.controller.dto.ArticleResponse;
import uz.community.javacommunity.controller.dto.ArticleUpdateRequest;
import uz.community.javacommunity.service.ArticleService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@Tag(name = "article")
@RestController
@RequestMapping("/article/")
@RequiredArgsConstructor
@EnableMethodSecurity
@PreAuthorize("hasRole('ADMIN')")
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Create an article.")
    public ArticleResponse createArticle(
            @RequestBody @Validated ArticleCreateRequest articleCreateRequest,
            Principal principal)
    {
        Article article = ArticleConverter.convertToEntity(articleCreateRequest);
        Article savedArticle = articleService.create(article, principal.getName());
        return ArticleConverter.from(savedArticle);
    }

    @PutMapping
    @ResponseStatus(OK)
    @Operation(summary = "Update article")
    public ArticleResponse updateArticle(
            @RequestBody @Validated ArticleUpdateRequest articleUpdateRequest,
            Principal principal)
    {
        Article article = ArticleConverter.convertToEntity(articleUpdateRequest);
        Article updatedArticle = articleService.update(article, principal.getName());
        return ArticleConverter.from(updatedArticle);
    }

    @GetMapping("{categoryId}")
    @Operation(summary = "get articles by categoryId")
    @PreAuthorize("permitAll()")
    public List<ArticleResponse> getAllByCategoryId(
            @PathVariable UUID categoryId)
    {
        List<Article> articleList = articleService.getAllByCategoryId(categoryId);
        return ArticleConverter.from(articleList);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Delete article")
    public void deleteArticle(
            @PathVariable("id") UUID id)
    {
        articleService.delete(id);
    }

}
