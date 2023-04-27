package uz.community.javacommunity.controller.category.data;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import uz.community.javacommunity.common.JsonConverter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
@Profile("functionalTest")
@RequiredArgsConstructor
public class TestDataHelperCategory {

    private static final String BASE_PATH = "/api/v1/category";

    private final JsonConverter jsonConverter;

    public RequestBuilder createCategoryRequest(
            String categoryName,
            UUID parentId,
            String createdBy
    ) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", categoryName);
        payload.put("parentId", parentId);
        payload.put("createdBy", createdBy);

        return post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.convertToString(payload));
    }
}
