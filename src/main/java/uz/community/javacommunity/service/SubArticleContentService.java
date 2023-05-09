package uz.community.javacommunity.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.community.javacommunity.controller.domain.SubArticleContent;
import uz.community.javacommunity.controller.dto.SubArticleContentRequest;
import uz.community.javacommunity.controller.dto.SubArticleContentResponse;
import uz.community.javacommunity.controller.repository.SubArticleContentRepository;
import uz.community.javacommunity.validation.CommonSchemaValidator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubArticleContentService {

    private final SubArticleContentRepository repository;
    private final CommonSchemaValidator validator;

    public SubArticleContentResponse create(String dto1, MultipartFile multipartFile) throws JsonProcessingException {

        SubArticleContent subArticleContent;

        SubArticleContentRequest dto = new ObjectMapper().readValue(dto1, SubArticleContentRequest.class);

        validator.validateCategory(dto.categoryId());

        validator.validateArticle(dto.articleId());

        validator.validateSubArticle(dto.subArticleId());

        if (dto.isParagraph()) {
            subArticleContent = SubArticleContent.of(dto);
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
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                if (multipartFile != null) {
                    byte[] mainContent = multipartFile.getBytes();
                    outputStream.write(mainContent);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageUrl;
    }


}
