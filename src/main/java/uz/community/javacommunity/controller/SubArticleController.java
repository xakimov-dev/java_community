package uz.community.javacommunity.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.community.javacommunity.controller.converter.SubArticleConverter;
import uz.community.javacommunity.controller.domain.SubArticle;
import uz.community.javacommunity.controller.dto.SubArticleCreateRequest;
import uz.community.javacommunity.controller.dto.SubArticleResponse;

import uz.community.javacommunity.service.SubArticleService;

import java.security.Principal;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("/article/sub")
@EnableMethodSecurity
public class SubArticleController {
    private final SubArticleService service;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubArticleResponse create(
            @RequestBody @Validated SubArticleCreateRequest subArticleCreateRequest,
            Principal principle
    ) {
        SubArticle subArticle = SubArticleConverter.convertToEntity(subArticleCreateRequest);
        return SubArticleConverter.from(service.create(subArticle,principle.getName()));
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SubArticleResponse update(
            @RequestBody @Validated SubArticleCreateRequest subArticleCreateRequest,
            @PathVariable UUID id,
            Principal principal
    ) {
        SubArticle subArticle =SubArticleConverter.convertToEntity(subArticleCreateRequest);
        return SubArticleConverter.from(service.update(subArticle,principal.getName(),id));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete entire SubArticle by Id")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable UUID id){
        service.delete(id);
    }

    @DeleteMapping("/byArticleId/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete entire SubArticle by ArticleId")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBYArticleId(@PathVariable UUID id){
        service.deleteBYArticleId(id);
    }
}
