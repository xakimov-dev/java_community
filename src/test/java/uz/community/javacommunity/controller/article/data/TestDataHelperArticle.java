package uz.community.javacommunity.controller.article.data;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import uz.community.javacommunity.common.JsonConverter;
import uz.community.javacommunity.controller.dto.ArticleResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
@Profile("functionalTest")
@RequiredArgsConstructor
public class TestDataHelperArticle {
    private static final String BASE_PATH = "/article";
    private static final String GET_ALL_ARTICLES_BY_CATEGORY_ID_PATH = "/article/{categoryId}";
    private final JsonConverter jsonConverter;
    private final MockMvc mockMvc;
    public RequestBuilder getArticlesByCategoryIdRequest(UUID categoryId){
        return get(GET_ALL_ARTICLES_BY_CATEGORY_ID_PATH, categoryId)
                .contentType(MediaType.APPLICATION_JSON);
    }
    public RequestBuilder createArticleRequest(String name, UUID categoryId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name",name);
        payload.put("categoryId",categoryId);

        return post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.convertToString(payload));
    }

    public RequestBuilder updateArticleRequest(UUID id, UUID categoryId, String name){
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", id);
        payload.put("categoryId", categoryId);
        payload.put("name", name);


        return put("/article/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.convertToString(payload));
    }

    public RequestBuilder deleteArticleRequest(UUID id){
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", id);

        return delete("/article/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.convertToString(payload));
    }


    public ArticleResponse createArticle(
            String name,
            UUID categoryId
    ) throws Exception {
        RequestBuilder request = createArticleRequest(name, categoryId);
        String contentAsString = mockMvc.perform(request).andExpect(status()
                .isCreated()).andReturn().getResponse().getContentAsString();
        return jsonConverter.convertFromString(contentAsString, ArticleResponse.class);
    }

}
