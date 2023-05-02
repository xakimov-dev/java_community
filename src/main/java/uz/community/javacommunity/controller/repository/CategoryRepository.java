package uz.community.javacommunity.controller.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import uz.community.javacommunity.controller.domain.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static uz.community.javacommunity.controller.domain.Category.CategoryKey;

@Repository
public interface CategoryRepository extends CassandraRepository<Category, CategoryKey> {
    @Query(allowFiltering = true)
    boolean existsByCategoryKeyName(String name);

    Optional<Category> findByCategoryKeyId(UUID id);

//    @Query("SELECT * FROM category WHERE parent_id IS NULL")
    List<Category> findAllBy();

}
