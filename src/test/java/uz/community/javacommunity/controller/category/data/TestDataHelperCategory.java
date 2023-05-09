package uz.community.javacommunity.controller.category.data;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import uz.community.javacommunity.common.JsonConverter;
import uz.community.javacommunity.controller.dto.CategoryResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Component
@Profile("functionalTest")
@RequiredArgsConstructor
public class TestDataHelperCategory {

    private static final String BASE_PATH = "/category";
    private static final String GET_ALL_PARENT_ID_IS_NULL = "/get-all-Parent";
    private static final String BASE_PATH_child = "/category/child/";

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
    public RequestBuilder getChild(UUID id){
        return get(BASE_PATH_child+id);
    }

    public CategoryResponse createCategory(
            String categoryName,
            UUID parentId
    ) throws Exception {
        RequestBuilder request = createCategoryRequest(categoryName, parentId);
        String contentAsString = mockMvc.perform(request).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        return jsonConverter.convertFromString(contentAsString, CategoryResponse.class);
    }

    public List<CategoryResponse> createCategory(
            String categoryName,
            UUID parentId,
            int amount
    ) throws Exception {
        return IntStream.range(0, amount).mapToObj((i)-> {
            try {
                return createCategory(categoryName + i,  parentId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

    public List<CategoryResponse> createMainCategory(
            String categoryName,
            int amount
    ) throws Exception {
        return IntStream.range(0, amount).mapToObj((i)-> {
            try {
                return createCategory(categoryName + i,  null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

    public RequestBuilder listAllParentCategories() {
        return get(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON);
    }
}
