package uz.community.javacommunity.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uz.community.javacommunity.common.exception.AlreadyExistsException;
import uz.community.javacommunity.common.exception.RecordNotFoundException;
import uz.community.javacommunity.controller.domain.Category;
import uz.community.javacommunity.controller.domain.User;
import uz.community.javacommunity.controller.dto.CategoryDTO;
import uz.community.javacommunity.controller.dto.CategoryRequest;
import uz.community.javacommunity.controller.dto.CategoryUpdateRequestDTO;
import uz.community.javacommunity.controller.repository.CategoryRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public CategoryDTO saveCategory(CategoryRequest categoryRequest,String username) {
        final String categoryName = categoryRequest.getName();
        String MESSAGE = String.format("Category with name [%s] already exists", categoryName);

        categoryRepository.findByName(categoryName).ifPresent((category) -> {
            throw new AlreadyExistsException(MESSAGE);
        });

        Category category = Category.builder()
                .name(categoryRequest.getName())
                .parentId(categoryRequest.getParentId())
                .createdBy(username)
                .build();

        categoryRepository.save(category);

        return CategoryDTO.of(category);
    }

    public CategoryDTO updateCategory(CategoryUpdateRequestDTO categoryDTO, UUID categoryId, String username) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RecordNotFoundException(" Category not found "));
        if (!category.getCreatedBy().equals(username)) {
            throw new AlreadyExistsException(" You can not edit this category !!!");
        }

        if (categoryDTO.getName() != null && !categoryDTO.getName().isBlank()) {
            category.setName(categoryDTO.getName());
        }

        if (category.getParentId() != null && categoryDTO.getParentId() == null ||
                categoryDTO.getParentId() != null
        ) {
            category.setParentId(categoryDTO.getParentId());
        }
        Category category1 = categoryRepository.save(category);
        return CategoryDTO.of(category1);
    }


    public List<CategoryDTO> getChildListByParentId(UUID id) {
        List<Category> childList = categoryRepository.getCategoriesByParentId(id);
     return childList.stream().map(CategoryDTO::of).collect(Collectors.toList());
    }
}
