package uz.community.javacommunity.controller.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import uz.community.javacommunity.controller.domain.SubArticleContent;

import java.util.Optional;
import java.util.UUID;

public interface SubArticleContentRepository extends CassandraRepository<SubArticleContent, SubArticleContent.SubArticleContentKey> {
    @Query(allowFiltering = true)
    Optional<SubArticleContent> findBySubArticleContentKeySubArticleId(UUID uuid);
}
