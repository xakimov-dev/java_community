package uz.community.javacommunity.controller.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import uz.community.javacommunity.CommonIntegrationTest;
import uz.community.javacommunity.controller.dto.CategoryResponse;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Get all Parent category ( GET /category/get-all-parent )")
class GetAllParentCategoryTest extends CommonIntegrationTest {
    @Test
    @DisplayName(value = "Should get All Parent category")
    void shouldGetParentCategory() throws Exception {
        //GIVEN
        CategoryResponse categoryResponse1 = testDataHelperCategory.createCategory("Kotlin Overview", null);
        CategoryResponse categoryResponse2 = testDataHelperCategory.createCategory("Iphone", null);

        testDataHelperCategory.createCategory("AA", categoryResponse1.getId(), 3);
        testDataHelperCategory.createCategory("BB", categoryResponse2.getId(), 3);

        testDataHelperArticle.createArticle("Kotlin Multiplatform", categoryResponse1.getId());
        testDataHelperArticle.createArticle("Kotlin Native", categoryResponse1.getId());
        testDataHelperArticle.createArticle("Kotlin for Android", categoryResponse1.getId());

        testDataHelperArticle.createArticle("Kotlin Multiplatform", categoryResponse2.getId());
        testDataHelperArticle.createArticle("Kotlin Native", categoryResponse2.getId());
        testDataHelperArticle.createArticle("Kotlin for Android", categoryResponse2.getId());

        RequestBuilder requestBuilder = testDataHelperCategory.listAllParentCategories();
        //WHEN
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        //THEN
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].articleResponseList").isArray())
                .andExpect(jsonPath("$[0].articleResponseList", hasSize(3)))
                .andExpect(jsonPath("$[1].articleResponseList").isArray())
                .andExpect(jsonPath("$[1].articleResponseList", hasSize(3)))
                .andExpect(jsonPath("$[0].childCategoryResponseList").isArray())
                .andExpect(jsonPath("$[0].childCategoryResponseList", hasSize(3)))
                .andExpect(jsonPath("$[1].childCategoryResponseList").isArray())
                .andExpect(jsonPath("$[1].childCategoryResponseList", hasSize(3)));
    }
}
