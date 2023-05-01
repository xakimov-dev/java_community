package uz.community.javacommunity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.community.javacommunity.controller.domain.Article;
import uz.community.javacommunity.controller.dto.ArticleUpdateRequest;
import uz.community.javacommunity.service.ArticleService;

import java.security.Principal;
import java.util.UUID;

@Tag(name = "article")
@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    @PutMapping(value = "/{id}")
    @Operation(summary = "Update article")
    public ResponseEntity<?> update(
            @RequestBody @Validated ArticleUpdateRequest articleUpdateRequest,
            @PathVariable UUID id,
            Principal principal
            ) {
        String username = principal.getName();
        return ResponseEntity.ok(articleService.update(id, articleUpdateRequest, username));
    }

}
