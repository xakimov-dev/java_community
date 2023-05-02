package uz.community.javacommunity.controller.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import uz.community.javacommunity.controller.domain.SubArticle;

import java.util.Optional;
import java.util.UUID;

public interface SubArticleRepository extends CassandraRepository<SubArticle, SubArticle.SubArticleKey> {
    boolean existsByName(String name);
    Optional<SubArticle> findBySubArticleKeyId(UUID id);
}
