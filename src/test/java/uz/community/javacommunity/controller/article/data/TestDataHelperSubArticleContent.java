package uz.community.javacommunity.controller.article.data;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import uz.community.javacommunity.common.JsonConverter;
import uz.community.javacommunity.controller.dto.SubArticleContentImageUrl;
import uz.community.javacommunity.controller.dto.SubArticleContentRequest;
import uz.community.javacommunity.controller.dto.SubArticleContentResponse;

import java.util.Objects;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
@Profile("functionalTest")
@RequiredArgsConstructor
public class TestDataHelperSubArticleContent {

    private static final String BASE_PATH = "/article/sub/content/";
    private final JsonConverter jsonConverter;
    private final MockMvc mockMvc;

    @SneakyThrows
    public RequestBuilder createSubArticleContentTextRequest(SubArticleContentRequest request) {
        return post(BASE_PATH + "text")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.convertToString(request));
    }

    @SneakyThrows
    public RequestBuilder createSubArticleContentImageRequest(MockMultipartFile photo) {
        MockMultipartHttpServletRequestBuilder request = MockMvcRequestBuilders
                .multipart(BASE_PATH + "image");
        return Objects.nonNull(photo)?request.file(photo):request;
    }

    public RequestBuilder getSubArticleContentRequest(UUID id) {
        return get(BASE_PATH + id);
    }

    public RequestBuilder deleteSubArticleImageRequest(SubArticleContentImageUrl imageUrl) {
        return delete(BASE_PATH + "image")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.convertToString(imageUrl));
    }

    @SneakyThrows
    public SubArticleContentResponse createSubArticleContent(SubArticleContentRequest subArticle) {
        RequestBuilder request = createSubArticleContentTextRequest(subArticle);
        String subArticleContentImage = mockMvc.perform(request).andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return jsonConverter.convertFromString(subArticleContentImage, SubArticleContentResponse.class);
    }

    @SneakyThrows
    public SubArticleContentImageUrl subArticleContentImage(MockMultipartFile photo) {
        RequestBuilder request = createSubArticleContentImageRequest(photo);
        String imageUrl = mockMvc.perform(request).andExpect(status()
                .isCreated()).andReturn().getResponse().getContentAsString();
        return jsonConverter.convertFromString(imageUrl, SubArticleContentImageUrl.class);
    }

    @SneakyThrows
    public MockMultipartFile getImage() {
        ClassPathResource imageResource = new ClassPathResource("images/test.jpg");
        return new MockMultipartFile("photo",
                imageResource.getFilename(),
                MediaType.IMAGE_JPEG_VALUE,
                imageResource.getInputStream());
    }
}
