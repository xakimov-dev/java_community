package uz.community.javacommunity.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.community.javacommunity.controller.domain.SubArticleContent;
import uz.community.javacommunity.controller.dto.SubArticleContentImageUrl;
import uz.community.javacommunity.controller.dto.SubArticleContentRequest;
import uz.community.javacommunity.controller.repository.SubArticleContentRepository;
import uz.community.javacommunity.validation.CommonSchemaValidator;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubArticleContentService {
    private final SubArticleContentRepository repository;
    private final CommonSchemaValidator validator;

    @SneakyThrows
    public SubArticleContent create(SubArticleContentRequest request) {
        validator.validateCategory(request.getCategoryId());
        validator.validateArticle(request.getArticleId());
        validator.validateSubArticle(request.getSubArticleId());
        SubArticleContent subArticleContent = SubArticleContent.of(request);
        return repository.save(subArticleContent);
    }

    @SneakyThrows
    public String addImage(MultipartFile photo)  {
        if (Objects.isNull(photo)|| photo.isEmpty()) {
            throw new IllegalArgumentException("Image property cannot be null or empty value ");
        }
        String imageUrl = String.format("src/main/resources/images/%s.jpg",UUID.randomUUID());
        File file = new File(imageUrl);
        file.getParentFile().mkdirs();
        file.createNewFile();
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            byte[] mainContent = photo.getBytes();
            outputStream.write(mainContent);
        }
        return imageUrl;
    }

    public void deleteImage(SubArticleContentImageUrl subArticleContentImageUrl) {
        if(Objects.nonNull(subArticleContentImageUrl)) {
            File photo = new File(subArticleContentImageUrl.getImageUrl());
            photo.delete();
        }
    }

    public List<SubArticleContent> get(UUID id) {
        validator.validateUUID(id,"subArticle");
        List<SubArticleContent> subArticleContents =
                repository.findAllBySubArticleContentKeySubArticleId(id);
        if (subArticleContents.isEmpty()) {
            validator.validateSubArticle(id);
        }
        return subArticleContents;
    }
}
