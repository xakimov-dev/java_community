package uz.community.javacommunity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.community.javacommunity.controller.dto.CategoryResponse;
import uz.community.javacommunity.controller.dto.SubArticleRequest;
import uz.community.javacommunity.controller.dto.SubArticleResponse;

import uz.community.javacommunity.service.SubArticleService;

import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("/article/sub")
@EnableMethodSecurity
public class SubArticleController {

    private final SubArticleService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubArticleResponse create(@RequestBody @Validated SubArticleRequest dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody @Validated SubArticleRequest dto, @PathVariable UUID id) {
        service.update(dto, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable UUID id){
        service.delete(id);
    }
}
