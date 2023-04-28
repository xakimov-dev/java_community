package uz.community.javacommunity.controller.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryUpdateRequestDTO {
    String name;
    UUID parentId;
}
