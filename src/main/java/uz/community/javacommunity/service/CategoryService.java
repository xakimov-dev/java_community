package uz.community.javacommunity.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uz.community.javacommunity.common.exception.AlreadyExistsException;
import uz.community.javacommunity.controller.dto.CategoryDTO;
import uz.community.javacommunity.controller.dto.CategoryRequest;
import uz.community.javacommunity.controller.repository.CategoryRepository;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public CategoryDTO saveCategory(CategoryRequest categoryRequest) {
        final String categoryName = categoryRequest.getName();
        String MESSAGE = String.format("Category with name [%s] already exists",categoryName);

        categoryRepository.findByName(categoryName).ifPresent((category)-> {throw new AlreadyExistsException(MESSAGE);});

        return CategoryDTO.of(null);
    }
}
