package uz.community.javacommunity.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.community.javacommunity.common.exception.RecordNotFoundException;
import uz.community.javacommunity.controller.domain.SubArticleContent;
import uz.community.javacommunity.controller.dto.SubArticleContentRequest;
import uz.community.javacommunity.controller.dto.SubArticleContentResponse;
import uz.community.javacommunity.controller.repository.SubArticleContentRepository;
import uz.community.javacommunity.controller.repository.SubArticleRepository;
import uz.community.javacommunity.validation.CommonSchemaValidator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubArticleContentService {
    private final SubArticleRepository subArticleRepository;
    private final SubArticleContentRepository repository;
    private final CommonSchemaValidator validator;

    public SubArticleContentResponse create(String dto1, MultipartFile multipartFile) throws JsonProcessingException {
        SubArticleContent subArticleContent = null;
        SubArticleContentRequest dto = new ObjectMapper().readValue(dto1, SubArticleContentRequest.class);
        validator.validateCategory(dto.categoryId());
        validator.validateArticle(dto.articleId());
        validator.validateSubArticle(dto.subArticleId());
        if (dto.isParagraph()) {
            if (Objects.isNull(dto.content()))
                throwIfPropertyIsNull("Content");
            subArticleContent = SubArticleContent.of(dto);
        } else if (Objects.isNull(multipartFile)|| multipartFile.isEmpty()) {
           throwIfPropertyIsNull("Image");
        } else {
            String content = getImageUrl(multipartFile);
            subArticleContent = SubArticleContent.of(dto, content);
        }
        SubArticleContent articleContent = repository.save(subArticleContent);
        return SubArticleContentResponse.of(articleContent);
    }

    private String getImageUrl(MultipartFile multipartFile) {
        String FILE_PATH = "src/main/resources/images/";
        String imageUrl = FILE_PATH + UUID.randomUUID() + ".jpg";
        File file = new File(imageUrl);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            byte[] mainContent = multipartFile.getBytes();
            outputStream.write(mainContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageUrl;
    }

    public List<SubArticleContent> get(UUID id) {
        validator.validateUUID(id,"subArticle");
        List<SubArticleContent> subArticleContents = repository.findAllBySubArticleContentKeySubArticleId(id);
        if (subArticleContents.isEmpty()) {
            subArticleRepository.findBySubArticleKeyId(id).orElseThrow(() ->
                    new RecordNotFoundException(String.format("SubArticle with id %s cannot be found", id)));
        }
        return subArticleContents;
    }

    private void throwIfPropertyIsNull(String property){
        throw new IllegalArgumentException(String.format("%s property cannot be empty",property));
    }
}
