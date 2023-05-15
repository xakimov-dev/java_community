package uz.community.javacommunity.controller.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.community.javacommunity.controller.domain.SubArticleContent;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
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
