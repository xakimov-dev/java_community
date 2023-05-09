package uz.community.javacommunity.controller.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import uz.community.javacommunity.CommonIntegrationTest;
import uz.community.javacommunity.controller.dto.CategoryResponse;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Update a category ( PUT /category/{id} )")
public class CategoryUpdateTest extends CommonIntegrationTest {
    @Test
    @DisplayName(value = "Should be success, update a Category")
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateCategory() throws Exception{
        //GIVEN
        CategoryResponse category = testDataHelperCategory.createCategory("category", null);
        UUID categoryId = category.getId();
        RequestBuilder updateCategoryRequest = testDataHelperCategory.updateCategoryRequest(categoryId, "new category", null);
        ResultActions resultActions = mockMvc.perform(updateCategoryRequest);

        resultActions
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "Should be success, update a Category")
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateCategoryWithParentId() throws Exception{
        //GIVEN
        CategoryResponse category = testDataHelperCategory.createCategory("category", null);
        UUID categoryId = category.getId();

        CategoryResponse subCategory = testDataHelperCategory.createCategory("subCategory", categoryId);
        UUID subCategoryId = subCategory.getId();

        CategoryResponse newParentCategory = testDataHelperCategory.createCategory("newParentCategory", categoryId);
        UUID newParentCategoryId = newParentCategory.getId();

        RequestBuilder updateCategoryRequest = testDataHelperCategory.updateCategoryRequest(subCategoryId, "new category", newParentCategoryId);
        ResultActions resultActions = mockMvc.perform(updateCategoryRequest);

        resultActions
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "Should fail if the category cannot be found")
    @WithMockUser(roles = "ADMIN")
    void shouldFailCategoryNotFound() throws Exception{
        //GIVEN
        CategoryResponse category = testDataHelperCategory.createCategory("category", null);
        UUID categoryId = category.getId();
        RequestBuilder updateCategoryRequest = testDataHelperCategory.updateCategoryRequest(categoryId, "new category", UUID.randomUUID());
        ResultActions resultActions = mockMvc.perform(updateCategoryRequest);

        resultActions
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName(value = "Should be success, update a Category")
    @WithMockUser(roles = "ADMIN")
    void shouldFailCategoryNotFoundWithParentId() throws Exception{
        //GIVEN
        CategoryResponse category = testDataHelperCategory.createCategory("category", null);
        UUID categoryId = category.getId();

        CategoryResponse subCategory = testDataHelperCategory.createCategory("subCategory", categoryId);
        UUID subCategoryId = subCategory.getId();

        RequestBuilder updateCategoryRequest = testDataHelperCategory.updateCategoryRequest(subCategoryId, "new category", UUID.randomUUID());
        ResultActions resultActions = mockMvc.perform(updateCategoryRequest);

        resultActions
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName(value = "Should fail if user does not have authority")
    @WithMockUser(roles = "USER")
    void shouldFailUserIsNotAdmin() throws Exception{
        //GIVEN
        CategoryResponse category = testDataHelperCategory.createCategory("category", null);
        UUID categoryId = category.getId();
        RequestBuilder updateCategoryRequest = testDataHelperCategory.updateCategoryRequest(categoryId, "new category", null);
        ResultActions resultActions = mockMvc.perform(updateCategoryRequest);
        //THEN
        resultActions
                .andExpect(status().isForbidden());
    }
}
