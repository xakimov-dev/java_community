package uz.community.javacommunity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.community.javacommunity.controller.converter.ArticleConverter;
import uz.community.javacommunity.controller.dto.ArticleCreateRequest;
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

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Create an article.")
    @PreAuthorize("hasRole('ADMIN')")
    public ArticleResponse createArticle(
            @RequestBody @Validated ArticleCreateRequest request,
            Principal principal
    ) {
        return ArticleConverter.from(articleService.create(ArticleConverter.convertToEntity(request), principal.getName()));
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Update article")
    @PreAuthorize("hasRole('ADMIN')")
    public ArticleResponse updateArticle(
            @RequestBody @Validated ArticleCreateRequest articleCreateRequest,
            @PathVariable UUID id,
            Principal principal
    ) {
        return ArticleConverter.from(articleService.update(ArticleConverter.convertToEntity(id, articleCreateRequest), principal.getName()));
    }

    @GetMapping("/{categoryId}")
    @Operation(summary = "get articles by categoryId")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<ArticleResponse> getAllByCategoryId(
            @PathVariable UUID categoryId
    ){
        return ArticleConverter.fromList(articleService.getAllByCategoryId(categoryId));
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
