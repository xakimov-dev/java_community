package uz.community.javacommunity.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.community.javacommunity.controller.domain.Article;
import uz.community.javacommunity.controller.domain.SubArticle;
import uz.community.javacommunity.controller.repository.ArticleRepository;
import uz.community.javacommunity.controller.repository.CategoryRepository;
import uz.community.javacommunity.controller.repository.SubArticleRepository;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CommonSchemaValidator {

    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;
    private final SubArticleRepository subArticleRepository;

    public void validateCategory(UUID id) {
        if (categoryRepository.findByCategoryKeyId(id).isEmpty()) {
            throw new IllegalArgumentException(String.format("category not found for id %s", id));
        }
    }

    public void validateArticle(UUID id) {
        Optional<Article> articleByArticleKeyId = articleRepository.findArticleByArticleKeyId(id);
        if (articleByArticleKeyId.isEmpty()) {
            throw new IllegalArgumentException(String.format("article not found for id %s", id));
        }
    }

    public void validateSubArticleExist(String name) {
        if (subArticleRepository.existsByName(name)) {
            throw new IllegalArgumentException(String.format("sub article duplicate for name %s", name));
        }
    }

    public void validateSubArticleExistForUpdate(String name, UUID id) {

        Optional<SubArticle> optionalSubArticle = subArticleRepository.findByName(name);
        if (optionalSubArticle.isPresent()) {
            SubArticle subArticle = optionalSubArticle.get();
            if (!subArticle.getSubArticleKey().getId().equals(id)) {
                throw new IllegalArgumentException(String.format("sub article duplicate for name %s", name));
            }
        }
    }

    public void validateSubArticle(UUID id) {

        if (id == null) {
            throw new IllegalArgumentException("sub article id is null");
        }

        if (!subArticleRepository.existsBySubArticleKeyId(id)) {
            throw new IllegalArgumentException(String.format("sub article not found for id %s", id));
        }
    }
}
