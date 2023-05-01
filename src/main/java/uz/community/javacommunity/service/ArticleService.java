package uz.community.javacommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.community.javacommunity.common.exception.RecordNotFoundException;
import uz.community.javacommunity.controller.domain.Article;
import uz.community.javacommunity.controller.dto.ArticleResponse;
import uz.community.javacommunity.controller.dto.ArticleUpdateRequest;
import uz.community.javacommunity.controller.repository.ArticleRepository;
import uz.community.javacommunity.controller.repository.CategoryRepository;
import uz.community.javacommunity.common.exception.AlreadyExistsException;
import uz.community.javacommunity.controller.dto.ArticleCreateRequest;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final CategoryService categoryService;
    private final JwtService jwtService;
    private final CategoryRepository categoryRepository;

    public Article create(ArticleCreateRequest articleCreateRequest, HttpServletRequest request) {
        final String categoryId = articleCreateRequest.getCategoryId();
        categoryService.throwIfCategoryCannotBeFound(UUID.fromString(categoryId));
        throwIfArticleAlreadyExists(articleCreateRequest.getName(), categoryId);
        String username = jwtService.getUsernameFromToken(request);
        Article article = Article.builder()
                .articleKey(Article.ArticleKey.of(UUID.randomUUID(), categoryId))
                .name(articleCreateRequest.getName())
                .createdBy(username)
                .createdDate(Instant.now())
                .build();
        return articleRepository.save(article);
    }

    private void throwIfArticleAlreadyExists(String name,String categoryId) {
        Optional<Article> article = articleRepository
                .findByNameAndAndArticleKeyId(name, categoryId);
        if (article.isPresent()) {
            throw new AlreadyExistsException("Article with name : '" +
                    name + "' already exists");
        }
    }


    public ArticleResponse update(UUID id, ArticleUpdateRequest articleUpdateRequest, String username){

        categoryRepository.findByCategoryKeyId(UUID.fromString(articleUpdateRequest.articleKey().getCategoryId()))
                .orElseThrow(() -> new RecordNotFoundException("Category not found"));

        Article articleById = articleRepository.findArticleByArticleKeyId(id)
                .orElseThrow(() -> new RecordNotFoundException("Article not found"));

        return ArticleResponse.from(articleRepository.save(Article.of(articleUpdateRequest, articleById, username)));
    }

}
