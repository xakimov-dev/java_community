package uz.community.javacommunity.controller.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import uz.community.javacommunity.controller.domain.Category;
import uz.community.javacommunity.controller.domain.keys.CategoryKey;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends CassandraRepository<Category, CategoryKey> {
    Optional<Category> findByCategoryKey(CategoryKey categoryKey);
    @Query(allowFiltering = true)
    Optional<Category> findByCategoryKey_Id(UUID id);
    @Query(allowFiltering = true)
    Optional<Category> findByCategoryKey_Name(String name);
}
