package uz.community.javacommunity.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.community.javacommunity.controller.dto.SubArticleRequest;
import uz.community.javacommunity.controller.repository.ArticleRepository;
import uz.community.javacommunity.controller.repository.CategoryRepository;
import uz.community.javacommunity.controller.repository.SubArticleRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CommonSchemaValidator {

    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;
    private final SubArticleRepository subArticleRepository;

    public void validateCategory(String name) {
        if (!categoryRepository.existsByCategoryKeyName(name)) {
            throw new IllegalArgumentException(String.format("category not found for name %s", name));
        }
    }

    public void validateCategory(UUID id) {
        if (categoryRepository.findByCategoryKeyId(id).isEmpty()) {
            throw new IllegalArgumentException(String.format("category not found for id %s", id));
        }
    }

    public void validateArticle(UUID id) {
        if (!articleRepository.existsByArticleKey_Id(id)) {
            throw new IllegalArgumentException(String.format("category not found for id %s", id));
        }
    }

    public void validateSubArticleExist(String name) {
        if (subArticleRepository.existsByName(name)) {
            throw new IllegalArgumentException(String.format("sub article duplicate for name %s", name));
        }
    }
}
