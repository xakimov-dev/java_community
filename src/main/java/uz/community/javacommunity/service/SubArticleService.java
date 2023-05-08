package uz.community.javacommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.community.javacommunity.controller.domain.Article;
import uz.community.javacommunity.controller.domain.Category;
import uz.community.javacommunity.controller.domain.SubArticle;
import uz.community.javacommunity.controller.dto.*;
import uz.community.javacommunity.controller.repository.SubArticleRepository;
import uz.community.javacommunity.validation.CommonSchemaValidator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubArticleService {

    private final SubArticleRepository repository;
    private final CommonSchemaValidator validator;



    public SubArticleResponse create(SubArticleRequest dto) {

        validator.validateSubArticleExist(dto.name());

        validator.validateCategory(dto.categoryId());

        validator.validateArticle(dto.articleId());

        SubArticle savedSubArticle = repository.save(SubArticle.of(dto));

        return SubArticleResponse.of(savedSubArticle);
    }

}
