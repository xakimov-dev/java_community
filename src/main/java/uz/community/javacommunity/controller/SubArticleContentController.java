package uz.community.javacommunity.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.community.javacommunity.controller.dto.SubArticleContentResponse;
import uz.community.javacommunity.service.SubArticleContentService;



@RequiredArgsConstructor
@RestController
@RequestMapping("/article/sub/content")
public class SubArticleContentController {

    private final SubArticleContentService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public SubArticleContentResponse create(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("text") String dto
    ) throws JsonProcessingException {
        return service.create(dto, photo);
    }

}
