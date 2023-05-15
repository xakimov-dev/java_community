package uz.community.javacommunity.controller.converter;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import uz.community.javacommunity.controller.domain.SubArticleContent;
import uz.community.javacommunity.controller.dto.SubArticleContentRequest;
import uz.community.javacommunity.controller.dto.SubArticleContentResponse;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
@Component
public class SubArticleContentConverter implements Converter<SubArticleContent, SubArticleContentRequest, SubArticleContentResponse> {

    @Override
    public SubArticleContent convertRequestToEntity(SubArticleContentRequest subArticleContentRequest, String name, UUID id) {
        Instant now = Instant.now();
        return SubArticleContent.builder()
                .content(subArticleContentRequest.getContent())
                .articleId(subArticleContentRequest.getArticleId())
                .categoryId(subArticleContentRequest.getCategoryId())
                .isParagraph(subArticleContentRequest.isParagraph())
                .modifiedBy(name)
                .modifiedDate(now)
                .subArticleContentKey(SubArticleContent.SubArticleContentKey.of(id, subArticleContentRequest.getSubArticleId()))
                .build();

    }

    @Override
    public SubArticleContent convertRequestToEntity(SubArticleContentRequest subArticleContentRequest, String name) {
        Instant now = Instant.now();
        return SubArticleContent.builder()
                .content(subArticleContentRequest.getContent())
                .articleId(subArticleContentRequest.getArticleId())
                .categoryId(subArticleContentRequest.getCategoryId())
                .isParagraph(subArticleContentRequest.isParagraph())
                .createdBy(name)
                .modifiedBy(name)
                .createdDate(now)
                .modifiedDate(now)
                .subArticleContentKey(SubArticleContent.SubArticleContentKey.of(UUID.randomUUID(), subArticleContentRequest.getSubArticleId()))
                .build();
    }

    @Override
    public SubArticleContentResponse convertEntityToResponse(SubArticleContent subArticleContent) {
        return new SubArticleContentResponse(
                subArticleContent.getSubArticleContentKey().getId(),
                subArticleContent.getCategoryId(),
                subArticleContent.getArticleId(),
                subArticleContent.getSubArticleContentKey().getSubArticleId(),
                subArticleContent.getContent(),
                subArticleContent.isParagraph(),
                subArticleContent.getCreatedBy(),
                subArticleContent.getCreatedDate(),
                subArticleContent.getModifiedBy(),
                subArticleContent.getModifiedDate()
        );
    }

    @Override
    public List<SubArticleContentResponse> convertEntitiesToResponse(List<SubArticleContent> subArticleContents) {
        return subArticleContents.stream().map(this::convertEntityToResponse).toList();
    }
}
