package uz.community.javacommunity;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import uz.community.javacommunity.common.JsonConverter;
import uz.community.javacommunity.controller.dto.UserResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
@Profile("functionalTest")
@RequiredArgsConstructor
public class TestDataHelperArticle {
    private static final String BASE_PATH = "/article";
    private final JsonConverter jsonConverter;
    public RequestBuilder createArticleRequest(String name, String categoryId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name",name);
        payload.put("categoryId",categoryId);

        return post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.convertToString(payload));
    }

}
