package uz.community.javacommunity.controller.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.community.javacommunity.controller.domain.Category;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse implements Serializable {

    UUID id;

    String name;

    UUID parentId;

    String createdBy;

    Instant createdDate;

    String modifiedBy;

    Instant modifiedDate;


    public static CategoryResponse of(Category category) {
        return CategoryResponse
                .builder()
                .id(category.getCategoryKey().getId())
                .name(category.getCategoryKey().getName())
                .createdBy(category.getCreatedBy())
                .createdDate(category.getCreatedDate())
                .parentId(category.getParentId())
                .build();
    }
}
