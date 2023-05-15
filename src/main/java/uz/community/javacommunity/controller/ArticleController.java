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
import uz.community.javacommunity.controller.dto.ArticleRequest;
import uz.community.javacommunity.controller.dto.ArticleResponse;
import uz.community.javacommunity.service.ArticleService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "article")
@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
@EnableMethodSecurity
public class ArticleController {
    private final ArticleService articleService;
    private final ArticleConverter articleConverter;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Create an article.")
    @PreAuthorize("hasRole('ADMIN')")
    public ArticleResponse createArticle(
            @RequestBody @Validated ArticleRequest articleRequest,
            Principal principal
    ) {
        Article article = articleConverter.convertRequestToEntity(articleRequest, principal.getName());
        return articleConverter.convertEntityToResponse(articleService.create(article));
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Update article")
    @PreAuthorize("hasRole('ADMIN')")
    public ArticleResponse updateArticle(
            @RequestBody @Validated ArticleRequest articleRequest,
            @PathVariable UUID id,
            Principal principal
    ) {
        Article article = articleConverter.convertRequestToEntity(articleRequest, principal.getName());
        return articleConverter.convertEntityToResponse(articleService.update(article));
    }

    @GetMapping("/{categoryId}")
    @Operation(summary = "get articles by categoryId")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<ArticleResponse> getAllByCategoryId(
            @PathVariable UUID categoryId
    ){
        return articleConverter.convertEntitiesToResponse(articleService.getAllByCategoryId(categoryId));
    }

    @GetMapping("/{articleId}")
    @Operation(summary = "get articles by id")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ArticleResponse getArticleById(
            @PathVariable UUID articleId
    ){
        return articleService.getArticleById(articleId);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Delete article")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteArticle(
            @PathVariable("id") UUID id
    ) {
        articleService.delete(id);
    }


}
