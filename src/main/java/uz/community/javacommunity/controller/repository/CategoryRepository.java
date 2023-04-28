package uz.community.javacommunity.controller.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import uz.community.javacommunity.controller.domain.Category;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends CassandraRepository<Category, UUID> {
    Optional<Category> findByName(String categoryName);
    Optional<Category> findById(UUID id);
}