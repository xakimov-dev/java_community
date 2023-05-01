package uz.community.javacommunity.controller.article;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uz.community.javacommunity.controller.domain.Article;
import uz.community.javacommunity.controller.domain.Category;
import uz.community.javacommunity.controller.dto.ArticleResponse;
import uz.community.javacommunity.controller.dto.ArticleUpdateRequest;
import uz.community.javacommunity.controller.repository.ArticleRepository;
import uz.community.javacommunity.controller.repository.CategoryRepository;
import uz.community.javacommunity.service.ArticleService;


import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleUpdateTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ArticleService articleService;

    private Article article;
    private Category category;

    @BeforeEach
    public void setUp() {
        category = new Category(
                UUID.randomUUID(),
                "Category 1",
                null,
                "a",
                Instant.now(),
                "",
                Instant.now()
                );
        article = new Article(
                new Article.ArticleKey(),
                "Test Article",
                "a",
                Instant.now(),
                null,
                Instant.now()
        );
    }

    @Test
    void update() {

        String username = "a";
        ArticleUpdateRequest updateRequest = new ArticleUpdateRequest(new Article.ArticleKey(), username);

        when(categoryRepository.save(category)).thenReturn(category);
        when(articleRepository.findById(article.getArticleKey())).thenReturn(Optional.of(article));
        when(articleRepository.save(article)).thenReturn(article);

        ArticleResponse updatedArticle = articleService.update(article.getArticleKey(), updateRequest, username);

        verify(articleRepository, times(1)).findById(article.getArticleKey());
        verify(articleRepository, times(1)).save(article);

        assertEquals(article.getArticleKey().getId(), updatedArticle.getArticleKey().getId());

    }
}