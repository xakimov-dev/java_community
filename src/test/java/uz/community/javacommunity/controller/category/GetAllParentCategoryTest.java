package uz.community.javacommunity.controller.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import uz.community.javacommunity.CommonIntegrationTest;
import uz.community.javacommunity.controller.dto.CategoryResponse;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Get all Parent category ( GET /category/get-all-parent )")
class GetAllParentCategoryTest extends CommonIntegrationTest {
    @Test
    @DisplayName(value = "Should get All Parent category")
    void shouldGetParentCategory() throws Exception {

        CategoryResponse categoryResponse = testDataHelperCategory.createCategory("accesuar", null);
        testDataHelperCategory.createCategory("phone", categoryResponse.getId(), 3);
        testDataHelperCategory.createCategory("Iphone",  2);
        RequestBuilder requestBuilder = testDataHelperCategory.listAllParentCategories();
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)));

    }
}
