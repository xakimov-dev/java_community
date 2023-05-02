package uz.community.javacommunity.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.community.javacommunity.controller.domain.Article;
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

    public void validateArticle(UUID id){
        Optional<Article> articleByArticleKeyId = articleRepository.findArticleByArticleKeyId(id);
        if(articleByArticleKeyId.isEmpty()){
            throw new IllegalArgumentException(String.format("article not found for id %s", id));
        }
    }
    public void validateSubArticleExist(String name) {
        if (subArticleRepository.existsByName(name)) {
            throw new IllegalArgumentException(String.format("sub article duplicate for name %s", name));
        }
    }
}
