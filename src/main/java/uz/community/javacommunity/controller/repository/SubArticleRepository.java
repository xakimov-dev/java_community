package uz.community.javacommunity.controller.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import uz.community.javacommunity.controller.domain.SubArticle;

import java.util.List;
import java.util.UUID;

public interface SubArticleRepository extends CassandraRepository<SubArticle, UUID> {
    @Query(allowFiltering = true)
    boolean existsByName(String name);
    @Query(allowFiltering = true)
    List<SubArticle> findAllByArticleId(UUID articleId);
    @Query(allowFiltering = true)
    List<SubArticle> findAllByParentSubArticleId(UUID parentSubArticleId);
}