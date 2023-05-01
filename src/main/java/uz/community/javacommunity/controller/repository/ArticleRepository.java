package uz.community.javacommunity.controller.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import uz.community.javacommunity.controller.domain.Article;

import java.util.Optional;
import java.util.UUID;

import static uz.community.javacommunity.controller.domain.Article.ArticleKey;

@Repository
public interface ArticleRepository extends CassandraRepository<Article, ArticleKey> {
    boolean existsByArticleKey_Id(UUID id);

    @Query(allowFiltering = true)
    Optional<Article> findByNameAndAndArticleKeyId(String name, String categoryId);
}
