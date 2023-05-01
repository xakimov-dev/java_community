package uz.community.javacommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.community.javacommunity.common.exception.AlreadyExistsException;
import uz.community.javacommunity.common.exception.RecordNotFoundException;
import uz.community.javacommunity.controller.domain.Article;
import uz.community.javacommunity.controller.dto.ArticleCreateRequest;
import uz.community.javacommunity.controller.repository.ArticleRepository;
import uz.community.javacommunity.controller.repository.CategoryRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final JwtService jwtService;

    public Article create(ArticleCreateRequest articleCreateRequest, HttpServletRequest request) {
        final String categoryId = articleCreateRequest.getCategoryId();
        categoryRepository.findByCategoryKey_Id(UUID.fromString(categoryId)).orElseThrow(
                ()-> new RecordNotFoundException("Category with id : '" +
                        categoryId + "' cannot be found!"));
        String username = jwtService.getUsernameFromToken(request);
        Article article = Article.builder()
                .articleKey(Article.ArticleKey.of(UUID.randomUUID(), categoryId))
                .name(articleCreateRequest.getName())
                .createdBy(username)
                .createdDate(Instant.now())
                .build();
        articleRepository.findArticleByNameAndArticleKey_CategoryId(
                articleCreateRequest.getName(), categoryId).ifPresentOrElse(
                (obj) -> articleRepository.save(article),
                () -> {
                    throw new AlreadyExistsException("Article with name : '" +
                            articleCreateRequest.getName() + "' already exists");
                });
        return article;
    }
}
