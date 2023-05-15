package uz.community.javacommunity.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.community.javacommunity.controller.domain.SubArticleContent;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubArticleContentResponse{
        UUID id;
        UUID categoryId;
        UUID articleId;
        UUID subArticleId;
        String content;
        Boolean isParagraph;
        String createdBy;
        Instant createdDate;
        String modifiedBy;
        Instant modifiedDate;

}
