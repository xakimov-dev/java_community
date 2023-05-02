package uz.community.javacommunity.controller.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import uz.community.javacommunity.controller.domain.SubArticle;

public interface SubArticleRepository extends CassandraRepository<SubArticle, SubArticle.SubArticleKey> {
    boolean existsByName(String name);
}
