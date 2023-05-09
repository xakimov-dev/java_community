package uz.community.javacommunity.controller.article.data;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import uz.community.javacommunity.common.JsonConverter;
import uz.community.javacommunity.controller.dto.ArticleResponse;
import uz.community.javacommunity.controller.dto.ArticleUpdateRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
@Profile("functionalTest")
@RequiredArgsConstructor
public class TestDataHelperArticle {
    private static final String BASE_PATH = "/article";
    private final JsonConverter jsonConverter;
    private final MockMvc mockMvc;
    public RequestBuilder createArticleRequest(String name, UUID categoryId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name",name);
        payload.put("categoryId",categoryId);

        return post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.convertToString(payload));
    }

//    public RequestBuilder updateArticleRequest(UUID id, ArticleUpdateRequest articleUpdateRequest){
//        Map<String, Object> payload = new HashMap<>();
//        payload.put("id", id);
//        payload.put("categoryId", articleUpdateRequest.articleKey().getCategoryId());
//        payload.put("name", articleUpdateRequest.name());
//
//        return post("/article/{id}", id)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonConverter.convertToString(payload));
//    }

    public ArticleResponse createArticle(
            String name,
            UUID categoryId
    ) throws Exception {
        RequestBuilder request = createArticleRequest(name, categoryId);
        String contentAsString = mockMvc.perform(request).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        return jsonConverter.convertFromString(contentAsString, ArticleResponse.class);
    }

}
