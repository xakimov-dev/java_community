package uz.community.javacommunity.controller.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import uz.community.javacommunity.controller.domain.SubArticleContent;

public interface SubArticleContentRepository extends CassandraRepository<SubArticleContent, SubArticleContent.SubArticleContentKey> {
}
