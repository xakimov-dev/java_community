package uz.community.javacommunity.controller.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import uz.community.javacommunity.controller.domain.Article;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArticleRepository extends CassandraRepository<Article, UUID> {
    @Query(allowFiltering = true)
    List<Article> findAllByCategoryId(UUID id);
    @Query(allowFiltering = true)
    boolean existsByNameAndCategoryId(String name, UUID categoryId);
    @Query(allowFiltering = true)
    boolean existsByNameAndCategoryIdAndIdNot(String name, UUID categoryId, UUID id);
}
