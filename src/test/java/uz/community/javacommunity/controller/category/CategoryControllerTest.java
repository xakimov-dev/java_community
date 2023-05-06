package uz.community.javacommunity.controller.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import uz.community.javacommunity.CommonIntegrationTest;
import uz.community.javacommunity.WithAuthentication;
import uz.community.javacommunity.controller.dto.CategoryResponse;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Create a new category ( GET/category/child )")
class CategoryControllerTest extends CommonIntegrationTest {

    @Test
    @DisplayName(value = "Should get Child List by parent Id")
    @WithAuthentication()
    void getChildList() throws Exception {
        CategoryResponse test = testDataHelperCategory.createCategory("test", null);
        testDataHelperCategory.createCategory("test1",test.getId());
        testDataHelperCategory.createCategory("test2",test.getId());
        testDataHelperCategory.createCategory("test3",test.getId());
        testDataHelperCategory.createCategory("test4", test.getId());
        RequestBuilder child = testDataHelperCategory.getChild(test.getId());
        ResultActions resultActions = mockMvc.perform(child);
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

    }
}