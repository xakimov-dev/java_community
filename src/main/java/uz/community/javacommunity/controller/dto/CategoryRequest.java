package uz.community.javacommunity.controller.dto;

import com.simba.cassandra.shaded.datastax.driver.core.utils.UUIDs;
import io.swagger.v3.oas.models.media.UUIDSchema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.community.javacommunity.controller.domain.Category;
import uz.community.javacommunity.controller.domain.keys.CategoryKey;

import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.UUID;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryRequest {

    @NotBlank(message = "Category name must not blank")
    String name;

    UUID parentId;

    @NotBlank(message = "Category owner's name must not blank")
    String createdBy;


    public static Category of(CategoryRequest categoryRequest) {
        return Category
                .builder()
                .categoryKey(new CategoryKey(UUIDs.timeBased(),
                        categoryRequest.getName()))
                .createdDate(Instant.now())
                .modifiedDate(Instant.now())
                .createdBy(categoryRequest.getCreatedBy())
                .modifiedBy(categoryRequest.getCreatedBy())
                .parentId(categoryRequest.getParentId())
                .build();
    }

}
