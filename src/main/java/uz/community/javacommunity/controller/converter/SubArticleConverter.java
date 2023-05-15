package uz.community.javacommunity.controller.converter;

import org.springframework.stereotype.Component;
import uz.community.javacommunity.controller.domain.SubArticle;
import uz.community.javacommunity.controller.dto.SubArticleRequest;
import uz.community.javacommunity.controller.dto.SubArticleResponse;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
@Component
public  class SubArticleConverter implements Converter<SubArticle,SubArticleRequest,SubArticleResponse> {
    public  SubArticle convertRequestToEntity(SubArticleRequest subArticleRequest, String name, UUID id){
        Instant now = Instant.now();
        return SubArticle.builder()
                .name(subArticleRequest.name())
                .categoryId(subArticleRequest.categoryId())
                .subArticleKey(SubArticle.SubArticleKey.of(id, subArticleRequest.parentSubArticleId(), subArticleRequest.articleId()))
                .modifiedBy(name)
                .modifiedDate(now)
                .build();
    }
    public  SubArticle convertRequestToEntity(SubArticleRequest subArticleRequest, String name){
        Instant now = Instant.now();
        return SubArticle.builder()
                .name(subArticleRequest.name())
                .categoryId(subArticleRequest.categoryId())
                .subArticleKey(SubArticle.SubArticleKey.of(UUID.randomUUID(), subArticleRequest.parentSubArticleId(), subArticleRequest.articleId()))
                .createdBy(name)
                .modifiedBy(name)
                .createdDate(now)
                .modifiedDate(now)
                .build();
    }
    public  SubArticleResponse convertEntityToResponse(SubArticle subArticle){
        return SubArticleResponse.builder()
                .articleId(subArticle.getSubArticleKey().getArticleId())
                .id(subArticle.getSubArticleKey().getId())
                .parentSubArticleId(subArticle.getSubArticleKey().getParentSubArticleId())
                .createdDate(subArticle.getCreatedDate())
                .modifiedDate(subArticle.getModifiedDate())
                .createdBy(subArticle.getCreatedBy())
                .modifiedBy(subArticle.getModifiedBy())
                .categoryId(subArticle.getCategoryId())
                .name(subArticle.getName())
                .build();
    }

    @Override
    public List<SubArticleResponse> convertEntitiesToResponse(List<SubArticle> subArticles) {
        return subArticles.stream().map(this::convertEntityToResponse).toList();
    }
}
