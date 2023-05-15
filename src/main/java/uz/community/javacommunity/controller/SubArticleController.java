package uz.community.javacommunity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.community.javacommunity.controller.converter.SubArticleConverter;
import uz.community.javacommunity.controller.domain.SubArticle;
import uz.community.javacommunity.controller.dto.SubArticleRequest;
import uz.community.javacommunity.controller.dto.SubArticleResponse;

import uz.community.javacommunity.service.SubArticleService;

import java.security.Principal;
import java.time.Instant;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("/article/sub")
@EnableMethodSecurity
public class SubArticleController {
    private final SubArticleService service;
    private final SubArticleConverter subArticleConverter;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubArticleResponse create(
            @RequestBody @Validated SubArticleRequest dto,
            Principal principle
    ) {
        SubArticle subArticle = subArticleConverter.convertRequestToEntity(dto,principle.getName());
        return subArticleConverter.convertEntityToResponse(service.create(subArticle));
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(
            @RequestBody @Validated SubArticleRequest dto,
            @PathVariable UUID id,
            Principal principal
    ) {
        SubArticle subArticle = subArticleConverter.convertRequestToEntity(dto, principal.getName(), id);
        service.update(subArticle);
    }
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable UUID id){
        service.delete(id);
    }
}
