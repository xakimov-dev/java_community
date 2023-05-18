package uz.community.javacommunity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
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

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "article")
@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
@EnableMethodSecurity
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Create an article.")
    @PreAuthorize("hasRole('ADMIN')")
    public ArticleResponse createArticle(
            @RequestBody @Validated ArticleCreateRequest articleCreateRequest,
            Principal principal
    ) {
        Article article = ArticleConverter.convertToEntity(articleCreateRequest);
        return ArticleConverter.from(articleService.create(article, principal.getName()));
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Update article")
    @PreAuthorize("hasRole('ADMIN')")
    public ArticleResponse updateArticle(
            @RequestBody @Validated ArticleUpdateRequest articleUpdateRequest,
            @PathVariable UUID id,
            Principal principal
    ) {
        Article article = ArticleConverter.convertToEntity(articleUpdateRequest);
        return ArticleConverter.from(articleService.update(article, principal.getName(), id));
    }

    @GetMapping("/{categoryId}")
    @Operation(summary = "get articles by categoryId")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<ArticleResponse> getAllByCategoryId(
            @PathVariable UUID categoryId
    ){
        return ArticleConverter.from(articleService.getAllByCategoryId(categoryId));
    }
    @GetMapping("/{articleId}")
    @Operation(summary = "get entire article by id")
    @PreAuthorize("permitAll()")
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

    @DeleteMapping(value = "/byCategoryId/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Delete articles by Category Id")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteArticleByCategoryId(
            @PathVariable("id") UUID id
    ) {
        articleService.deleteByCategoryId(id);
    }

}
