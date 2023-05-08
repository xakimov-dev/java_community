package uz.community.javacommunity.controller.article.data;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import uz.community.javacommunity.common.JsonConverter;
import uz.community.javacommunity.controller.dto.SubArticleResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
@Profile("functionalTest")
@RequiredArgsConstructor
public class TestDataHelperSubArticle {

    private static final String BASE_PATH = "/article/sub";
    private final JsonConverter jsonConverter;
    private final MockMvc mockMvc;

    public RequestBuilder createSubArticleRequest(
            UUID categoryId,
            UUID articleId,
            UUID parentSubArticleId,
            String name
    ) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("categoryId", categoryId);
        payload.put("articleId", articleId);
        payload.put("parentSubArticleId", parentSubArticleId);
        payload.put("name", name);

        return post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.convertToString(payload));
    }

    public RequestBuilder updateSubArticleRequest(
            UUID id,
            UUID categoryId,
            UUID articleId,
            UUID parentSubArticleId,
            String name
    ) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("categoryId", categoryId);
        payload.put("articleId", articleId);
        payload.put("parentSubArticleId", parentSubArticleId);
        payload.put("name", name);

        return put(BASE_PATH + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.convertToString(payload));
    }

    public SubArticleResponse createSubArticle(
            UUID categoryId,
            UUID articleId,
            UUID parentSubArticleId,
            String name
    ) throws Exception {
        RequestBuilder request = createSubArticleRequest(categoryId, articleId, parentSubArticleId, name);
        String contentAsString = mockMvc.perform(request).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        return jsonConverter.convertFromString(contentAsString, SubArticleResponse.class);
    }
}
