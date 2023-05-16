package uz.community.javacommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.community.javacommunity.common.exception.RecordNotFoundException;
import uz.community.javacommunity.controller.domain.SubArticle;
import uz.community.javacommunity.controller.repository.SubArticleRepository;
import uz.community.javacommunity.validation.CommonSchemaValidator;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubArticleService {
    private final SubArticleRepository repository;
    private final CommonSchemaValidator commonSchemaValidator;
    public SubArticle create(SubArticle subArticle, String createdBy) {
        commonSchemaValidator.validateSubArticleExist(subArticle.getName());
        UUID parentSubArticleId = subArticle.getParentSubArticleId();
        if (parentSubArticleId!=null) commonSchemaValidator.validateSubArticle(parentSubArticleId);
        commonSchemaValidator.validateArticle(subArticle.getArticleId());
        Instant now = Instant.now();
        subArticle.setCreatedBy(createdBy);
        subArticle.setCreatedDate(now);
        subArticle.setModifiedBy(createdBy);
        subArticle.setModifiedDate(now);
        return repository.save(subArticle);
    }

    public SubArticle update(SubArticle subArticle,String updatedBy,UUID id) {
        commonSchemaValidator.validateArticle(subArticle.getArticleId());
        UUID parentSubArticleId = subArticle.getParentSubArticleId();
        if (parentSubArticleId != null) {
            commonSchemaValidator.validateSubArticle(parentSubArticleId);
        }
        SubArticle subArticleEntity = getById(id);
        subArticleEntity.setModifiedBy(updatedBy);
        subArticleEntity.setModifiedDate(Instant.now());
        return repository.save(subArticleEntity);
    }

    public void delete(UUID subArticleId){
        SubArticle subArticle = getById(subArticleId);
        repository.delete(subArticle);
    }

    public SubArticle getById(UUID id) {
        return repository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("SubArticle with id %s cannot be found",subArticleId));
    }

    public void exitsById(UUID id) {
        if (!repository.existsBySubArticleKeyId(id)) {
            throw new RecordNotFoundException("Sub article not found by id: " + id);
        }
    }
}
