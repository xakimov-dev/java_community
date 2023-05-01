package uz.community.javacommunity.controller.dto;

import com.simba.cassandra.shaded.datastax.driver.core.utils.UUIDs;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import uz.community.javacommunity.controller.domain.Category;
import uz.community.javacommunity.controller.domain.keys.CategoryKey;

import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryRequest {

    @NotBlank(message = "Category name must not blank")
    String name;

    UUID parentId;

    public static Category from(CategoryRequest categoryRequest, String createdBy) {
        return Category
                .builder()
                .categoryKey(new CategoryKey(UUIDs.timeBased(),
                        categoryRequest.getName()))
                .createdDate(Instant.now())
                .modifiedDate(Instant.now())
                .createdBy(createdBy)
                .modifiedBy(createdBy)
                .parentId(categoryRequest.getParentId())
                .build();
    }

}
