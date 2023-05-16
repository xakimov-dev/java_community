package uz.community.javacommunity.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.community.javacommunity.controller.converter.SubArticleContentConverter;
import uz.community.javacommunity.controller.domain.SubArticleContent;
import uz.community.javacommunity.controller.dto.SubArticleContentImageUrl;
import uz.community.javacommunity.controller.dto.SubArticleContentCreateRequest;
import uz.community.javacommunity.controller.dto.SubArticleContentResponse;
import uz.community.javacommunity.service.SubArticleContentService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@EnableMethodSecurity
@RequestMapping("/article/sub/content")
@PreAuthorize("hasRole('ADMIN')")
public class SubArticleContentController {
    private final SubArticleContentService service;
    private final SubArticleContentConverter subArticleContentConverter;

    @PostMapping(value = "/image",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create an image (NOTE : can be used for any purpose to save image)")
    public SubArticleContentImageUrl addImage(
            @RequestParam(value = "photo") MultipartFile photo) {
        return SubArticleContentImageUrl.of(service.addImage(photo));
    }

    @DeleteMapping(value = "/image")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete an unsubbited image (NOTE : can be used for any purpose to delete an image)")
    public void deleteImage(
            @RequestBody SubArticleContentImageUrl subArticleContentImageUrl) {
        service.deleteImage(subArticleContentImageUrl);
    }

    @PostMapping(value = "/text")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new Sub Article Content")
    public SubArticleContentResponse create(
            @RequestBody @Validated SubArticleContentCreateRequest subArticleContentCreateRequest,
            Principal principal
    ) {
        SubArticleContent subArticleContent = subArticleContentConverter.convertRequestToEntity(subArticleContentCreateRequest, principal.getName());
        SubArticleContent response = service.create(subArticleContent);
        return subArticleContentConverter.convertEntityToResponse(response);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get an article.")
    @PreAuthorize(value = "permitAll()")
    public List<SubArticleContentResponse> getSubArticle(
            @PathVariable UUID id){
        List<SubArticleContent> subArticleContents = service.get(id);
        return subArticleContentConverter.convertEntitiesToResponse(subArticleContents);
    }
}
