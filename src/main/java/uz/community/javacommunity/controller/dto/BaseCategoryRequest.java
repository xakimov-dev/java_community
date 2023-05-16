package uz.community.javacommunity.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
@Builder
public class BaseCategoryRequest {
    @NotBlank(message = "Category name must not blank")
    String name;

    UUID parentId;
}
