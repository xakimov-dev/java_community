package uz.community.javacommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.community.javacommunity.common.exception.AlreadyExistsException;
import uz.community.javacommunity.common.exception.RecordNotFoundException;
import uz.community.javacommunity.controller.domain.Article;
import uz.community.javacommunity.controller.dto.ArticleCreateRequest;
import uz.community.javacommunity.controller.dto.ArticleUpdateRequest;
import uz.community.javacommunity.controller.repository.ArticleRepository;
import uz.community.javacommunity.controller.repository.SubArticleRepository;
import uz.community.javacommunity.validation.CommonSchemaValidator;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final SubArticleRepository subArticleRepository;
    private final CategoryService categoryService;
    private final JwtService jwtService;
    private final CommonSchemaValidator commonSchemaValidator;

    public Article create(ArticleCreateRequest articleCreateRequest, String currentUser) {
        UUID categoryId = articleCreateRequest.getCategoryId();
        commonSchemaValidator.validateCategory(categoryId);
        throwIfArticleAlreadyExists(articleCreateRequest.getName(), categoryId);

        Instant now = Instant.now();
        Article article = Article.builder()
                .articleKey(Article.ArticleKey.of(UUID.randomUUID(), categoryId))
                .name(articleCreateRequest.getName())
                .createdBy(currentUser)
                .modifiedBy(currentUser)
                .createdDate(now)
                .modifiedDate(now)
                .build();
        return articleRepository.save(article);
    }

    private void throwIfArticleAlreadyExists(String name, UUID categoryId) {
        Optional<Article> article = articleRepository
                .findByNameAndArticleKey_CategoryId(name, categoryId);
        if (article.isPresent()) {
            throw new AlreadyExistsException("Article with name : '" +
                    name + "' already exists");
        }
    }

    public Article update(UUID id, ArticleUpdateRequest articleUpdateRequest, String username) {
        UUID categoryId = articleUpdateRequest.getCategoryId();
        commonSchemaValidator.validateCategory(categoryId);
        Article article = articleRepository.findArticleByArticleKeyId(id).orElseThrow(() -> new RecordNotFoundException(String.format("Article not found for id %s", id)));

        article.setArticleKey(Article.ArticleKey.of(id, categoryId));
        article.setName(articleUpdateRequest.getName());
        article.setModifiedBy(username);
        article.setModifiedDate(Instant.now());

        return articleRepository.save(article);
    }

    public List<Article> getAllByCategoryId(UUID categoryId) {
        return articleRepository.findAllByArticleKey_CategoryId(categoryId);
    }
}
