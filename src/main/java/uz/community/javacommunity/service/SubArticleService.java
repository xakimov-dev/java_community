package uz.community.javacommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.community.javacommunity.common.exception.RecordNotFoundException;
import uz.community.javacommunity.common.exception.AlreadyExistsException;
import uz.community.javacommunity.common.exception.RecordNotFoundException;
import uz.community.javacommunity.controller.domain.SubArticle;
import uz.community.javacommunity.controller.dto.SubArticleRequest;
import uz.community.javacommunity.controller.dto.SubArticleResponse;
import uz.community.javacommunity.controller.repository.SubArticleRepository;
import uz.community.javacommunity.validation.CommonSchemaValidator;

import java.util.UUID;

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

    public void update(SubArticleRequest dto, UUID id) {
        validator.validateSubArticleExistForUpdate(dto.name(), id);

        validator.validateCategory(dto.categoryId());

        validator.validateArticle(dto.articleId());

        validator.validateSubArticle(dto.parentSubArticleId());

        SubArticle subArticle = repository.findBySubArticleKeyId(id).orElseThrow(() -> new RecordNotFoundException("Sub article not found by id: " + id));

        subArticle.update(dto, id);

        repository.save(subArticle);
    }
    public SubArticle delete(UUID subArticleId){
        SubArticle subArticle = repository.findBySubArticleKeyId(subArticleId).orElseThrow(() -> {
            throw new RecordNotFoundException("SubArticle with id '" + subArticleId + "' cannot be found");});
        repository.delete(subArticle);
        return subArticle;
    }

}
