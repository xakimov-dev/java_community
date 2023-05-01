package uz.community.javacommunity.controller.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.community.javacommunity.controller.domain.Category;
import uz.community.javacommunity.controller.dto.CategoryDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends CassandraRepository<Category, UUID> {
    Optional<Category> findByName(String categoryName);
    Optional<Category> findById(UUID id);


//    @Query("SELECT * FROM category WHERE parent_id  =  :parentId")
//    List<Category> getChildList(@Param("parentId") String parentId);

    List<Category>getCategoriesByParentId(UUID parentId);
}
