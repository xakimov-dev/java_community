package uz.community.javacommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.community.javacommunity.common.exception.RecordNotFoundException;
import uz.community.javacommunity.controller.domain.SubArticle;
import uz.community.javacommunity.controller.repository.SubArticleContentRepository;
import uz.community.javacommunity.controller.repository.SubArticleRepository;
import uz.community.javacommunity.validation.CommonSchemaValidator;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubArticleService {
    private final SubArticleRepository repository;
    private final SubArticleContentService subArticleContentService;
    private final CommonSchemaValidator commonSchemaValidator;

    public SubArticle create(SubArticle subArticle, String createdBy) {
        UUID parentSubArticleId = subArticle.getParentSubArticleId();
        if (Objects.nonNull(parentSubArticleId)) {
            commonSchemaValidator.validateSubArticle(parentSubArticleId);
            commonSchemaValidator.validateSubArticleExistByParentId(
                    subArticle.getName(),parentSubArticleId,null);
        } else {
            commonSchemaValidator.validateSubArticleExistByArticleId(
                    subArticle.getName(),subArticle.getArticleId(),null);
        }
        Instant now = Instant.now();
        subArticle.setId(UUID.randomUUID());
        subArticle.setCreatedBy(createdBy);
        subArticle.setCreatedDate(now);
        subArticle.setModifiedBy(createdBy);
        subArticle.setModifiedDate(now);
        return repository.save(subArticle);
    }

    public SubArticle update(SubArticle subArticle, String updatedBy, UUID id) {
        UUID parentSubArticleId = subArticle.getParentSubArticleId();
        if (Objects.nonNull(parentSubArticleId)) {
            commonSchemaValidator.validateSubArticle(parentSubArticleId);
            commonSchemaValidator.validateSubArticleExistByParentId(
                    subArticle.getName(),parentSubArticleId,id);
        } else {
            commonSchemaValidator.validateSubArticleExistByArticleId(
                    subArticle.getName(),subArticle.getArticleId(),id);
        }
        SubArticle subArticleEntity = getById(id);
        subArticleEntity.setName(subArticle.getName());
        subArticleEntity.setArticleId(subArticle.getArticleId());
        subArticleEntity.setParentSubArticleId(parentSubArticleId);
        subArticleEntity.setArticleId(subArticle.getArticleId());
        subArticleEntity.setModifiedBy(updatedBy);
        subArticleEntity.setModifiedDate(Instant.now());
        return repository.save(subArticleEntity);
    }

    public void delete(UUID subArticleId) {
        commonSchemaValidator.validateSubArticle(subArticleId);
        subArticleContentService.deleteBySubArticleId(subArticleId);
        List<SubArticle> subArticles = repository.findAllByParentSubArticleId(subArticleId);
        if (!subArticles.isEmpty()){
            subArticles.stream().forEach(subArticle -> delete(subArticle.getId()));
        }
        repository.deleteById(subArticleId);
    }

    public void deleteBYArticleId(UUID articleId) {
        commonSchemaValidator.validateArticle(articleId);
        List<SubArticle> subArticles = repository.findAllByArticleId(articleId);
        subArticles.forEach(subArticle -> delete(subArticle.getId()));
    }

    public SubArticle getById(UUID id) {
        commonSchemaValidator.validateUUID(id, "subArticleId");
        return repository.findById(id).orElseThrow(() ->
                new RecordNotFoundException(String.format("SubArticle with id %s cannot be found", id)));
    }
}
