package uz.community.javacommunity.controller.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import uz.community.javacommunity.controller.domain.SubArticleContent;

import java.util.List;
import java.util.UUID;

public interface SubArticleContentRepository extends CassandraRepository<SubArticleContent, SubArticleContent.SubArticleContentKey> {
    @Query(allowFiltering = true)
    List<SubArticleContent> findAllBySubArticleContentKeySubArticleId(UUID uuid);
}