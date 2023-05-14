package uz.community.javacommunity.controller.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.cassandra.core.mapping.*;

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
    @Column("category_id")
    UUID categoryId;
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

    @Data
    @Builder
    @AllArgsConstructor
    @PrimaryKeyClass
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class SubArticleKey {
        @PrimaryKeyColumn(name = "id", ordinal = 0, type = PARTITIONED)
        UUID id;
        @PrimaryKeyColumn(name = "parent_sub_article_id", ordinal = 1, type = CLUSTERED)
        UUID parentSubArticleId;
        @PrimaryKeyColumn(name = "article_id", ordinal = 2, type = CLUSTERED)
        UUID articleId;

        public static SubArticleKey of(UUID id, UUID parentSubArticleId, UUID articleId) {
            return new SubArticleKey(id, parentSubArticleId, articleId);
        }
    }
}
