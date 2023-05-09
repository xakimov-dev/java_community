package uz.community.javacommunity.controller.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.cassandra.core.mapping.*;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.community.javacommunity.controller.dto.SubArticleContentRequest;

import java.time.Instant;
import java.util.UUID;

import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.CLUSTERED;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table("sub_article_content")
public class SubArticleContent {
    @PrimaryKey
    SubArticleContentKey subArticleContentKey;
    String content;
    @Column("is_paragraph")
    boolean isParagraph;
    @Column("created_by")
    String createdBy;
    @Column("created_date")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    Instant createdDate;
    @Column("modified_by")
    String modifiedBy;
    @Column("modified_date")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    Instant modifiedDate;

    public static SubArticleContent of(SubArticleContentRequest dto) {
        return SubArticleContent.builder()
                .subArticleContentKey(SubArticleContentKey.of(UUID.randomUUID(), dto.categoryId(), dto.articleId(), dto.subArticleId()))
                .content(dto.content())
                .isParagraph(dto.isParagraph())
                .createdBy(SecurityContextHolder.getContext().getAuthentication().getName())
                .createdDate(Instant.now())
                .modifiedBy(SecurityContextHolder.getContext().getAuthentication().getName())
                .modifiedDate(Instant.now())
                .build();
    }
    public static SubArticleContent of(SubArticleContentRequest dto, String content) {
        return SubArticleContent.builder()
                .subArticleContentKey(SubArticleContentKey.of(UUID.randomUUID(), dto.categoryId(), dto.articleId(), dto.subArticleId()))
                .content(content)
                .isParagraph(dto.isParagraph())
                .createdBy(SecurityContextHolder.getContext().getAuthentication().getName())
                .createdDate(Instant.now())
                .modifiedBy(SecurityContextHolder.getContext().getAuthentication().getName())
                .modifiedDate(Instant.now())
                .build();
    }

    @Data
    @Builder
    @AllArgsConstructor
    @PrimaryKeyClass
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class SubArticleContentKey {
        @PrimaryKeyColumn(name = "id", ordinal = 0, type = PARTITIONED)
        UUID id;
        @PrimaryKeyColumn(name = "category_id", ordinal = 1, type = CLUSTERED)
        UUID categoryId;
        @PrimaryKeyColumn(name = "article_id", ordinal = 2, type = CLUSTERED)
        UUID articleId;
        @PrimaryKeyColumn(name = "sub_article_id", ordinal = 3, type = CLUSTERED)
        UUID subArticleId;

        public static SubArticleContentKey of(UUID id, UUID categoryId, UUID articleId, UUID subArticleId) {
            return new SubArticleContentKey(id, categoryId, articleId, subArticleId);
        }
    }

}
