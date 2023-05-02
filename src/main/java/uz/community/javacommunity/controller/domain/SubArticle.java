package uz.community.javacommunity.controller.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.cassandra.core.mapping.*;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.community.javacommunity.controller.dto.SubArticleRequest;

import java.time.Instant;
import java.util.UUID;

import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.CLUSTERED;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table("article")
public class SubArticle {
    @PrimaryKey
    SubArticleKey subArticleKey;
    String name;
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

    public static SubArticle of(SubArticleRequest dto) {
        return SubArticle.builder()
                .subArticleKey(SubArticleKey.of(UUID.randomUUID(), dto.categoryId(), dto.articleId(), dto.parentSubArticleId()))
                .name(dto.name())
                .createdBy(SecurityContextHolder.getContext().getAuthentication().getName())
                .createdDate(Instant.now())
                .modifiedBy(SecurityContextHolder.getContext().getAuthentication().getName())
                .modifiedDate(Instant.now())
                .build();
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class SubArticleKey {
        @PrimaryKeyColumn(name = "id", ordinal = 0, type = PARTITIONED)
        UUID id;
        @PrimaryKeyColumn(name = "category_id", ordinal = 1, type = CLUSTERED)
        UUID categoryId;
        @PrimaryKeyColumn(name = "article_id", ordinal = 0, type = PARTITIONED)
        UUID articleId;
        @PrimaryKeyColumn(name = "parent_sub_article_id", ordinal = 0, type = PARTITIONED)
        UUID parentSubArticleId;

        public static SubArticleKey of(UUID id, UUID categoryId, UUID articleId, UUID parentSubArticleId) {
            return new SubArticleKey(id, categoryId, articleId, parentSubArticleId);
        }
    }
}
