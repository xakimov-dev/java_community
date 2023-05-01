package uz.community.javacommunity.controller.repository;

import com.simba.cassandra.shaded.datastax.driver.mapping.annotations.Param;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import uz.community.javacommunity.controller.domain.Article;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static uz.community.javacommunity.controller.domain.Article.ArticleKey;

@Repository
public interface ArticleRepository extends CassandraRepository<Article, ArticleKey> {
    @Query(allowFiltering = true)
    Optional<Article> findArticleByNameAndArticleKey_CategoryId(String name, String categoryId);
}
