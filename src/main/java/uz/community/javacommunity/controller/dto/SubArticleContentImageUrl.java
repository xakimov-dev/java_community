package uz.community.javacommunity.controller.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubArticleContentImageUrl {
    @NotBlank
    String imageUrl;

    public static SubArticleContentImageUrl of(String imageUrl) {
        return new SubArticleContentImageUrl(imageUrl);
    }
}
