package uz.community.javacommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.community.javacommunity.controller.domain.SubArticleContent;
import uz.community.javacommunity.controller.dto.SubArticleContentRequest;
import uz.community.javacommunity.controller.dto.SubArticleContentResponse;
import uz.community.javacommunity.controller.repository.SubArticleContentRepository;
import uz.community.javacommunity.validation.CommonSchemaValidator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubArticleContentService {

    private final SubArticleContentRepository repository;
    private final CommonSchemaValidator validator;

    public SubArticleContentResponse create(SubArticleContentRequest dto, MultipartHttpServletRequest request) {
        SubArticleContent subArticleContent;

        validator.validateCategory(dto.categoryId());

        validator.validateArticle(dto.articleId());

        validator.validateSubArticle(dto.subArticleId());

        if (dto.isParagraph()) {
            subArticleContent = SubArticleContent.of(dto);
        } else {
            String content = getImageUrl(request);
            subArticleContent = SubArticleContent.of(dto, content);
        }
        SubArticleContent articleContent = repository.save(subArticleContent);

        return SubArticleContentResponse.of(articleContent);
    }

    private String getImageUrl(MultipartHttpServletRequest request) {
        String FILE_PATH = "/src/main/resources/images/";
        String imageUrl = FILE_PATH + UUID.randomUUID() + ".png";
        try (FileOutputStream outputStream = new FileOutputStream(imageUrl)) {
            String fileName = request.getFileNames().next();
            MultipartFile picture = request.getFile(fileName);
            if (picture != null) {
                byte[] mainContent = picture.getBytes();
                outputStream.write(mainContent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageUrl;
    }

}
