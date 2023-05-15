package uz.community.javacommunity.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.community.javacommunity.common.exception.RecordNotFoundException;
import uz.community.javacommunity.controller.domain.Article;
import uz.community.javacommunity.controller.repository.*;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CommonSchemaValidator {
    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;
    private final SubArticleRepository subArticleRepository;
    private final UserRepository userRepository;
    private final LoginRepository loginRepository;

    public void validateCategory(UUID id) {
        validateUUID(id,"category");
        if (categoryRepository.findByCategoryKeyId(id).isEmpty()) {
            throw new RecordNotFoundException(String.format("category not found for id %s", id));
        }
    }

    public void validateArticle(UUID id) {
        validateUUID(id,"article");
        Optional<Article> articleByArticleKeyId = articleRepository.findByArticleKey_Id(id);
        if (articleByArticleKeyId.isEmpty()) {
            throw new RecordNotFoundException(String.format("article not found for id %s", id));
        }
    }

    public void validateSubArticleExist(String name) {
        if (subArticleRepository.existsByName(name)) {
            throw new RecordNotFoundException(String.format("sub article duplicate for name %s", name));
        }
    }

    public void validateSubArticle(UUID id) {
        validateUUID(id,"subArticle");
        if (!subArticleRepository.existsBySubArticleKeyId(id)) {
            throw new RecordNotFoundException(String.format("sub article not found for id %s", id));
        }
    }

    public void validateUsername(String username) {
        if (loginRepository.existsById(username)) {
            throw new IllegalArgumentException(String.format("user with username %s already exists", username));
        }
    }
    
    public void validateUUID(UUID id,String propertyName){
        if(Objects.isNull(id)){
            throw new IllegalArgumentException(String.format("%s id cannot be null value",propertyName));
        }
    }
}
