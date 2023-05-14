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
    @Column("category_id")
    UUID categoryId;
    @Column("article_id")
    UUID articleId;

    @Data
    @Builder
    @AllArgsConstructor
    @PrimaryKeyClass
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class SubArticleContentKey {
        @PrimaryKeyColumn(name = "id", ordinal = 0, type = PARTITIONED)
        UUID id;
        @PrimaryKeyColumn(name = "sub_article_id", ordinal = 3, type = CLUSTERED)
        UUID subArticleId;

        public static SubArticleContentKey of(UUID id, UUID subArticleId) {
            return new SubArticleContentKey(id, subArticleId);
        }
    }

}
