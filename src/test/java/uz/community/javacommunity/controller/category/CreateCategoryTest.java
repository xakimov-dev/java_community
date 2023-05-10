package uz.community.javacommunity.controller.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import uz.community.javacommunity.CommonIntegrationTest;
import uz.community.javacommunity.WithAuthentication;
import uz.community.javacommunity.controller.dto.CategoryResponse;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author :Xojiakbar Abiyorov
 */

@DisplayName("Create a new category ( POST /category )")
class CreateCategoryTest extends CommonIntegrationTest {


    @Test
    @DisplayName(value = "Should create a parent category")
    @WithAuthentication(username = "owner")
    void shouldCreateParentCategory() throws Exception {

        RequestBuilder categoryRequest = testDataHelperCategory
                .createCategoryRequest("test",null );

        ResultActions resultActions = mockMvc.perform(categoryRequest);
        resultActions.toString();

        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.createdBy").value("owner"))
                .andExpect(jsonPath("$.parentId").doesNotExist());
    }

    @Test
    @DisplayName(value = "Should create a child category")
    @WithAuthentication(username = "owner1")
    void shouldCreateChildCategory() throws Exception {

        CategoryResponse categoryResponse = testDataHelperCategory.createCategory("test", null);

        RequestBuilder categoryRequest = testDataHelperCategory
                .createCategoryRequest("test1", categoryResponse.getId());

        ResultActions perform = mockMvc.perform(categoryRequest);

        perform.andExpect(status().isCreated())
                .andExpect(jsonPath("$.createdBy").value("owner1"))
                .andExpect(jsonPath("$.name").value("test1"))
                .andExpect(jsonPath("$.parentId").value(categoryResponse.getId().toString()))
                .andExpect(jsonPath("$.createdDate").exists())
                .andExpect(jsonPath("$.modifiedDate").exists())
                .andExpect(jsonPath("$.modifiedBy").value("owner1"));


    }



    @Test
    @DisplayName(value = "Should get unauthorized ")
    @WithAnonymousUser
    void shouldThrowAuthenticationException() throws Exception {

        RequestBuilder categoryRequest = testDataHelperCategory
                .createCategoryRequest("test",null);

        ResultActions resultActions = mockMvc.perform(categoryRequest);

        resultActions
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName(value = "Should get 4xx status if fields Blank ")
    @WithAuthentication
    void shouldThrowFieldValidationException() throws Exception {

        RequestBuilder categoryRequest = testDataHelperCategory
                .createCategoryRequest("",null);

        ResultActions resultActions = mockMvc.perform(categoryRequest);

        resultActions
                .andExpect(status().is4xxClientError());
    }
}
