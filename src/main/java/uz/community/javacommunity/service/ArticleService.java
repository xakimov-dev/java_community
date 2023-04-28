package uz.community.javacommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.community.javacommunity.common.exception.RecordNotFoundException;
import uz.community.javacommunity.controller.domain.Article;
import uz.community.javacommunity.controller.domain.Category;
import uz.community.javacommunity.controller.dto.ArticleResponse;
import uz.community.javacommunity.controller.dto.ArticleUpdateRequest;
import uz.community.javacommunity.controller.repository.ArticleRepository;
import uz.community.javacommunity.controller.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;

    public ArticleResponse update(Article.ArticleKey articleKey, ArticleUpdateRequest articleUpdateRequest, String username){
        categoryRepository.findById(articleKey.getCategoryId())
                .orElseThrow(() -> new RecordNotFoundException("Category not found"));

        Article articleById = articleRepository.findById(articleKey)
                .orElseThrow(() -> new RecordNotFoundException("Article not found"));

        return ArticleResponse.from(articleRepository.save(Article.of(articleUpdateRequest, articleById, username)));
    }
}
