package uz.community.javacommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.community.javacommunity.common.exception.RecordNotFoundException;
import uz.community.javacommunity.controller.domain.SubArticle;
import uz.community.javacommunity.controller.repository.SubArticleRepository;
import uz.community.javacommunity.validation.CommonSchemaValidator;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubArticleService {
    private final SubArticleRepository repository;
    private final CommonSchemaValidator validator;
    public SubArticle create(SubArticle subArticle) {

        validator.validateSubArticleExist(subArticle.getName());

        UUID parentSubArticleId = subArticle.getSubArticleKey().getParentSubArticleId();
        if (parentSubArticleId!=null) validator.validateSubArticle(parentSubArticleId);
        validator.validateCategory(subArticle.getCategoryId());

        validator.validateArticle(subArticle.getSubArticleKey().getArticleId());

        return repository.save(subArticle);

    }

    public void update(SubArticle subArticle) {

        validator.validateCategory(subArticle.getCategoryId());

        validator.validateArticle(subArticle.getSubArticleKey().getArticleId());

        UUID parentSubArticleId = subArticle.getSubArticleKey().getParentSubArticleId();
        if (parentSubArticleId != null) {
            validator.validateSubArticle(parentSubArticleId);
        }
        exitsById(subArticle.getSubArticleKey().getId());
        repository.save(subArticle);
    }

    public void delete(UUID subArticleId){
        SubArticle subArticle = repository.findBySubArticleKeyId(subArticleId).orElseThrow(() -> {
            throw new RecordNotFoundException(String.format("SubArticle with id %s cannot be found",subArticleId));});
        repository.delete(subArticle);
    }

    public SubArticle getById(UUID id) {
        return repository.findBySubArticleKeyId(id).orElseThrow(() ->
                new RecordNotFoundException("Sub article not found by id: " + id));
    }

    public void exitsById(UUID id) {
        if (!repository.existsBySubArticleKeyId(id)) {
            throw new RecordNotFoundException("Sub article not found by id: " + id);
        }
    }
}
