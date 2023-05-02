package uz.community.javacommunity.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.community.javacommunity.controller.domain.Article;
import uz.community.javacommunity.controller.dto.ArticleCreateRequest;
import uz.community.javacommunity.controller.dto.ArticleResponse;
import uz.community.javacommunity.service.ArticleService;

import java.security.Principal;

import static org.springframework.http.HttpStatus.CREATED;

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
}
