package uz.community.javacommunity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.community.javacommunity.controller.dto.SubArticleRequest;
import uz.community.javacommunity.controller.dto.SubArticleResponse;
import uz.community.javacommunity.service.SubArticleService;


@RequiredArgsConstructor
@RestController
@RequestMapping("/article/sub")
public class SubArticleController {

    private final SubArticleService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubArticleResponse create(@RequestBody @Validated SubArticleRequest dto) {
        return service.create(dto);
    }

}