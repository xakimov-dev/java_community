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
@Table("sub_article")
public class SubArticle {
    @PrimaryKey
    SubArticleKey subArticleKey;
    String name;
    @Column("parent_sub_article_id")
    UUID parentSubArticleId;
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
                .subArticleKey(SubArticleKey.of(UUID.randomUUID(), dto.categoryId(), dto.articleId()))
                .parentSubArticleId(dto.parentSubArticleId())
                .name(dto.name())
                .createdBy(SecurityContextHolder.getContext().getAuthentication().getName())
                .createdDate(Instant.now())
                .modifiedBy(SecurityContextHolder.getContext().getAuthentication().getName())
                .modifiedDate(Instant.now())
                .build();
    }

    public void update(SubArticleRequest dto, UUID id) {
        setSubArticleKey(SubArticleKey.of(id, dto.categoryId(), dto.articleId()));
        setName(dto.name());
        setParentSubArticleId(dto.parentSubArticleId());
        setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        setModifiedDate(Instant.now());
    }

    @Data
    @Builder
    @AllArgsConstructor
    @PrimaryKeyClass
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class SubArticleKey {
        @PrimaryKeyColumn(name = "id", ordinal = 0, type = PARTITIONED)
        UUID id;
        @PrimaryKeyColumn(name = "category_id", ordinal = 1, type = CLUSTERED)
        UUID categoryId;
        @PrimaryKeyColumn(name = "article_id", ordinal = 2, type = CLUSTERED)
        UUID articleId;

        public static SubArticleKey of(UUID id, UUID categoryId, UUID articleId) {
            return new SubArticleKey(id, categoryId, articleId);
        }
    }
}
