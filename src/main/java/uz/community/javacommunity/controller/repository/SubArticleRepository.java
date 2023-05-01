package uz.community.javacommunity.controller.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import uz.community.javacommunity.controller.domain.SubArticle;

public interface SubArticleRepository extends CassandraRepository<SubArticle, SubArticle.SubArticleKey> {
    @Query(allowFiltering = true)
    boolean existsByName(String name);
}
