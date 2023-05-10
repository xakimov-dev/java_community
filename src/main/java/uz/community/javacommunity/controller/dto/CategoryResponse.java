package uz.community.javacommunity.controller.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.community.javacommunity.controller.domain.Category;

import java.time.Instant;
import java.util.List;
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
    List<ArticleResponse> articleResponseList;
    List<CategoryResponse> childCategoryResponseList;


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
    public static List<CategoryResponse>getChildList(List<Category> categoryList){
        return categoryList.stream().map(CategoryResponse::from).toList();
    }
}
