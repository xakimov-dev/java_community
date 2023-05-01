package uz.community.javacommunity.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.community.javacommunity.controller.repository.CategoryRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CommonSchemaValidator {

    private final CategoryRepository categoryRepository;
    public void validateCategory(String name){
        if (!categoryRepository.existsByCategoryKeyName(name)){
            throw new IllegalArgumentException(String.format("category not found for name %s", name));
        }
    }

    public void validateCategory(UUID id){
        if (categoryRepository.findByCategoryKeyId(id).isEmpty()){
            throw new IllegalArgumentException(String.format("category not found for id %s", id));
        }
    }
}
