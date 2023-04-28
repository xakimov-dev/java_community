package uz.community.javacommunity.controller.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import uz.community.javacommunity.controller.domain.Category;

import java.time.Instant;
import java.util.UUID;
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDTO {

    UUID id;

    String name;

    UUID parentId;

    String createdBy;

    Instant createdDate;

    String modifiedBy;

    Instant modifiedDate;

    public static CategoryDTO of(Category category) {
        return CategoryDTO
                .builder()
                .id(category.getId())
                .name(category.getName())
                .createdBy(category.getCreatedBy())
                .createdDate(category.getCreatedDate())
                .parentId(category.getParentId())
                .build();
    }
}
