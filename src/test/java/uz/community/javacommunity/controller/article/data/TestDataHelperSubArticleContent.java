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
import uz.community.javacommunity.controller.dto.SubArticleContentResponse;

import java.util.Objects;
import java.util.UUID;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Component
@Profile("functionalTest")
@RequiredArgsConstructor
public class TestDataHelperSubArticleContent {

    private static final String BASE_PATH = "/article/sub/content/";
    private final JsonConverter jsonConverter;
    private final MockMvc mockMvc;

    @SneakyThrows
    public RequestBuilder createSubArticleContentRequest(MockMultipartFile multipartFile, String subArticleContentRequest) {
        MockMultipartHttpServletRequestBuilder request = MockMvcRequestBuilders
                .multipart(BASE_PATH);
        request = (Objects.isNull(multipartFile))?request:request.file(multipartFile);
        return request
                .param("text", subArticleContentRequest);
    }

    public RequestBuilder getSubArticleContentRequest(UUID id) {
        return get(BASE_PATH + id)
                .contentType(MediaType.APPLICATION_JSON);
    }

    @SneakyThrows
    public MockMultipartFile getImage() {
        ClassPathResource imageResource = new ClassPathResource("images/test.jpg");
        return new MockMultipartFile("photo",
                imageResource.getFilename(),
                MediaType.IMAGE_JPEG_VALUE,
                imageResource.getInputStream());
    }

    public String subArticleContentRequest(
            UUID categoryId, UUID articleId,
            UUID subArticleId, String content, boolean isParagraph) {
        StringBuilder subArticleContentRequest = new StringBuilder();
        subArticleContentRequest.append("{\"categoryId\":\"")
                .append(categoryId)
                .append("\",\"articleId\":\"")
                .append(articleId)
                .append("\",\"subArticleId\":\"")
                .append(subArticleId)
                .append("\",\"content\":")
                .append((Objects.isNull(content)) ? null : ('"' + content + '"'))
                .append(",\"isParagraph\":")
                .append(isParagraph).append('}');
        return subArticleContentRequest.toString();
    }

    @SneakyThrows
    public SubArticleContentResponse createSubArticleContent(MockMultipartFile photo, String subArticleContentRequest) {
        RequestBuilder request = createSubArticleContentRequest(photo, subArticleContentRequest);
        String string = mockMvc.perform(request).andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return jsonConverter.convertFromString(string, SubArticleContentResponse.class);
    }
}
