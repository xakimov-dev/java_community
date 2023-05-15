package uz.community.javacommunity.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uz.community.javacommunity.controller.domain.SubArticle;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@Builder
public class SubArticleResponse {
    private UUID id;
    private UUID categoryId;
    private UUID articleId;
    private UUID parentSubArticleId;
    private String name;
    private String createdBy;
    private Instant createdDate;
    private String modifiedBy;
    private Instant modifiedDate;
    private List<SubArticleResponse> childSubArticleList;
}
