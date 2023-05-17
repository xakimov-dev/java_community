package uz.community.javacommunity.controller.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import uz.community.javacommunity.controller.domain.Category;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends CassandraRepository<Category, UUID> {
    @Query(allowFiltering = true)
    boolean existsByName(String name);

    @Query(allowFiltering = true)
    boolean existsByNameAndParentIdAndIdNot(String name, UUID parentId, UUID id);

    @Query(allowFiltering = true)
    List<Category> findAllByParentId(UUID id);
}
