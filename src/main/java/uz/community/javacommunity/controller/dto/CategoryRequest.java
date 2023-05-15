package uz.community.javacommunity.controller.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
public record CategoryRequest(
        @NotBlank(message = "Category name must not blank") String name,
        UUID parentId
) {

}
