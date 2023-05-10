package uz.community.javacommunity.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.community.javacommunity.controller.domain.SubArticleContent;
import uz.community.javacommunity.controller.dto.SubArticleContentResponse;
import uz.community.javacommunity.service.SubArticleContentService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@EnableMethodSecurity
@RequestMapping("/article/sub/content")
@PreAuthorize("hasRole('ADMIN')")
public class SubArticleContentController {
    private final SubArticleContentService service;
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public SubArticleContentResponse create(
            @RequestParam(value = "photo",required = false) MultipartFile photo,
            @RequestParam("text") String subArticleContentRequest
    ) throws JsonProcessingException {
        return service.create(subArticleContentRequest, photo);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get an article.")
    public List<SubArticleContentResponse> getSubArticle(
            @PathVariable UUID id){
        List<SubArticleContent> subArticleContents = service.get(id);
        return SubArticleContentResponse.of(subArticleContents);
    }
}
