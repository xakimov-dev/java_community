package uz.community.javacommunity.controller.article.data;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import uz.community.javacommunity.common.JsonConverter;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Component
@Profile("functionalTest")
@RequiredArgsConstructor
public class TestDataHelperSubArticleContent {

    private static final String BASE_PATH = "/article/sub/content/";
    private final JsonConverter jsonConverter;
    private final MockMvc mockMvc;
    public RequestBuilder getSubArticleContentRequest(UUID id) {
        return get("/article/"+ id)
                .contentType(MediaType.APPLICATION_JSON);
    }
}
