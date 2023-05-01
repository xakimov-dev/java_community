package uz.community.javacommunity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.community.javacommunity.controller.dto.SubArticleRequest;
import uz.community.javacommunity.controller.dto.SubArticleResponse;
import uz.community.javacommunity.service.SubArticleService;


@RequiredArgsConstructor
@RestController
@RequestMapping("/article/sub")
public class SubArticleCategory {

    private final SubArticleService service;

    @PostMapping
    public SubArticleResponse create(@RequestBody @Validated SubArticleRequest dto) {
        return service.create(dto);
    }

}
