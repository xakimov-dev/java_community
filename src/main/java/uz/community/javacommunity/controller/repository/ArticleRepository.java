package uz.community.javacommunity.controller.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import uz.community.javacommunity.controller.domain.Article;

import java.util.Optional;
import java.util.UUID;

import static uz.community.javacommunity.controller.domain.Article.ArticleKey;

@Repository
public interface ArticleRepository extends CassandraRepository<Article, ArticleKey> {
    Optional<Article> findArticleByArticleKeyId(UUID id);
}
