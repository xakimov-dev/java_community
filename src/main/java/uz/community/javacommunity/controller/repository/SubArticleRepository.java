package uz.community.javacommunity.controller.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import uz.community.javacommunity.controller.domain.SubArticle;

import java.util.Optional;
import java.util.UUID;

public interface SubArticleRepository extends CassandraRepository<SubArticle, SubArticle.SubArticleKey> {
    @Query(allowFiltering = true)
    boolean existsByName(String name);

    @Query(allowFiltering = true)
    Optional<SubArticle> findByName(String name);

    @Query(allowFiltering = true)
    Optional<SubArticle> findBySubArticleKeyId(UUID id);

    @Query(allowFiltering = true)
    boolean existsBySubArticleKeyId(UUID id);
}
