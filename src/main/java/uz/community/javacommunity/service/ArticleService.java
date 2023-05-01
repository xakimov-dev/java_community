package uz.community.javacommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Service;
import uz.community.javacommunity.common.exception.AlreadyExistsException;
import uz.community.javacommunity.common.exception.RecordNotFoundException;
import uz.community.javacommunity.controller.domain.Article;
import uz.community.javacommunity.controller.dto.ArticleCreateRequest;
import uz.community.javacommunity.controller.repository.ArticleRepository;

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

    public Article create(ArticleCreateRequest articleCreateRequest, HttpServletRequest request) {
        UUID categoryId = UUID.fromString(articleCreateRequest.getCategoryId());
        if (!categoryService.categoryExists(categoryId)) {
            throw new RecordNotFoundException("Category with id : '"+categoryId + "' cannot be found!");
        }
        Optional<Article> foundArticle = articleRepository
                .findArticleByNameAndArticleKey_CategoryId(articleCreateRequest.getName(), categoryId.toString());
        if(foundArticle.isPresent()){
            throw new AlreadyExistsException("Article with name : '"+articleCreateRequest.getName()+"' already exists");
        }
        String username  = jwtService.getUsernameFromToken(request);
        try {
            articleRepository.insertArticle(UUID.randomUUID(), categoryId.toString(),
                    username, Instant.now(), articleCreateRequest.getName());
        }catch (Exception e){
            e.printStackTrace();
        }
        return Article.builder()
                .articleKey(Article.ArticleKey.of(UUID.randomUUID(), categoryId))
                .name(articleCreateRequest.getName())
                .createdBy(username)
                .createdDate(Instant.now())
                .build();
    }
}
