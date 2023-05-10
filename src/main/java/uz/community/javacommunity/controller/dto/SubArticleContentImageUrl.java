package uz.community.javacommunity.controller.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubArticleContentImageUrl {
    @NotBlank
    private String imageUrl;

    public static SubArticleContentImageUrl of(String imageUrl) {
        return new SubArticleContentImageUrl(imageUrl);
    }
}
