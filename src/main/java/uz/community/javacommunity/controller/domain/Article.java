package uz.community.javacommunity.controller.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.cassandra.core.mapping.*;
import uz.community.javacommunity.controller.dto.ArticleUpdateRequest;

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
public class Article {
    @PrimaryKey
    ArticleKey articleKey;
    String name;
//    @Column("parent_id")
//    UUID parentId;
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
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ArticleKey{
        @PrimaryKeyColumn(name = "id", ordinal = 0, type = PARTITIONED)
        UUID id;
        @PrimaryKeyColumn(name = "category_id", ordinal = 1, type = CLUSTERED)
        UUID categoryId;

        public static ArticleKey of(UUID id, UUID categoryId){
            return new ArticleKey(id, categoryId);
        }
    }

    public static Article of(ArticleUpdateRequest articleUpdateRequest, Article articleById, String username){
        return Article.builder()
                .articleKey(articleUpdateRequest.articleKey())
                .name(articleUpdateRequest.name())
                .createdBy(articleById.createdBy)
                .createdDate(articleById.createdDate)
                .modifiedBy(username)
                .modifiedDate(Instant.now())
                .build();
    }

}
