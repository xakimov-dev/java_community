package uz.community.javacommunity.controller.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.community.javacommunity.controller.domain.Category;

import java.time.Instant;
import java.util.UUID;
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse  {

    UUID id;

    String name;

    UUID parentId;

    String createdBy;

    Instant createdDate;

    String modifiedBy;

    Instant modifiedDate;


    public static CategoryResponse from(Category category) {
        return CategoryResponse
                .builder()
                .id(category.getCategoryKey().getId())
                .name(category.getCategoryKey().getName())
                .createdBy(category.getCreatedBy())
                .createdDate(category.getCreatedDate())
                .modifiedDate(category.getModifiedDate())
                .modifiedBy(category.getModifiedBy())
                .parentId(category.getParentId())
                .build();
    }
}
