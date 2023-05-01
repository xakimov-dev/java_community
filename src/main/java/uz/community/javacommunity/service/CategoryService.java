package uz.community.javacommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.community.javacommunity.controller.repository.CategoryRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public boolean categoryExists(UUID categoryId){
        return categoryRepository.existsById(categoryId);
    }
}
