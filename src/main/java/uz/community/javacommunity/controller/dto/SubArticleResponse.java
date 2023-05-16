package uz.community.javacommunity.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@Builder
public class SubArticleResponse {
    private UUID id;
    private UUID articleId;
    private UUID parentSubArticleId;
    private String name;
    private String createdBy;
    private Instant createdDate;
    private String modifiedBy;
    private Instant modifiedDate;
    private List<SubArticleResponse> childSubArticleList;

    public SubArticleResponse(UUID id, UUID categoryId, UUID articleId, UUID parentSubArticleId,
                              String name, String createdBy, Instant createdDate, String modifiedBy,
                              Instant modifiedDate, List<SubArticleResponse> childSubArticleList) {
        this.id = id;
        this.articleId = articleId;
        this.parentSubArticleId = parentSubArticleId;
        this.name = name;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
        this.childSubArticleList = childSubArticleList;
    }
}
