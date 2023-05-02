package uz.community.javacommunity.controller.category.data;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import uz.community.javacommunity.common.JsonConverter;
import uz.community.javacommunity.controller.dto.CategoryResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Component
@Profile("functionalTest")
@RequiredArgsConstructor
public class TestDataHelperCategory {

    private static final String BASE_PATH = "/category";
    private static final String GET_ALL_PARENT_ID_IS_NULL = "/get-all-Parent";

    private final JsonConverter jsonConverter;
    private final MockMvc mockMvc;

    public RequestBuilder createCategoryRequest(
            String categoryName,
            UUID parentId
    ) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", categoryName);
        payload.put("parentId", parentId);

        return post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.convertToString(payload));
    }

    public CategoryResponse createCategory(
            String categoryName,
            UUID parentId
    ) throws Exception {
        RequestBuilder request = createCategoryRequest(categoryName, parentId);
        String contentAsString = mockMvc.perform(request).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        return jsonConverter.convertFromString(contentAsString, CategoryResponse.class);
    }
}
