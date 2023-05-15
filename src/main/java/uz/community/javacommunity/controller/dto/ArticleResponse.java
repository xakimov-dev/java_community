package uz.community.javacommunity.controller.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.community.javacommunity.controller.domain.Article;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ArticleResponse {
    private UUID id;
    private String name;
    private UUID categoryId;
    private String createdBy;
    private String createdDate;
    private List<SubArticleResponse> subArticleResponseList;
}
