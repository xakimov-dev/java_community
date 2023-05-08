package uz.community.javacommunity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.community.javacommunity.controller.domain.Article;
import uz.community.javacommunity.controller.dto.ArticleCreateRequest;
import uz.community.javacommunity.controller.dto.ArticleResponse;
import uz.community.javacommunity.controller.dto.ArticleUpdateRequest;
import uz.community.javacommunity.service.ArticleService;

import java.security.Principal;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

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
            @RequestBody @Validated ArticleCreateRequest request,
            Principal principal
    ) {
        Article savedArticle = articleService.create(request, principal.getName());
        return ArticleResponse.from(savedArticle);
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Update article")
    @PreAuthorize("hasRole('ADMIN')")
    public ArticleResponse update(
            @RequestBody @Validated ArticleUpdateRequest articleUpdateRequest,
            @PathVariable UUID id,
            Principal principal
    ) {
        String username = principal.getName();
        Article article = articleService.update(id, articleUpdateRequest, username);
        return ArticleResponse.from(article);
    }
}
