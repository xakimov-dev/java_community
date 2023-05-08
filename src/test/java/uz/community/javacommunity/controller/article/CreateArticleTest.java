package uz.community.javacommunity.controller.article;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import uz.community.javacommunity.CommonIntegrationTest;
import uz.community.javacommunity.WithAuthentication;
import uz.community.javacommunity.controller.dto.CategoryResponse;

import java.util.Set;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Create a new article ( POST /article )")
class CreateArticleTest extends CommonIntegrationTest {
    @Test
    @DisplayName(value = "Should be success, create a new Article")
    @WithAuthentication(username = "xakimov")
    void shouldCreateArticle() throws Exception {
        //GIVEN
        CategoryResponse category = testDataHelperCategory.createCategory("category", null);
        UUID categoryId = category.getId();
        RequestBuilder request = testDataHelperArticle.createArticleRequest("java", categoryId);
        //WHEN
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("java"))
                .andExpect(jsonPath("$.categoryId").value(categoryId.toString()))
                .andExpect(jsonPath("$.articleId").exists())
                .andExpect(jsonPath("$.createdDate").exists());
    }

    @Test
    @DisplayName(value = "Should fail if the article already exists")
    @WithAuthentication(username = "xakimov")
    void shouldFailArticleExists() throws Exception {
        //GIVEN
        CategoryResponse category = testDataHelperCategory.createCategory("category", null);
        UUID categoryId = category.getId();
        RequestBuilder request = testDataHelperArticle.createArticleRequest("java", categoryId);
        //WHEN
        mockMvc.perform(request);
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName(value = "Should fail if the category cannot be found")
    @WithAuthentication(username = "xakimov")
    void shouldFailCategoryNotFound() throws Exception {
        //GIVEN
        RequestBuilder request = testDataHelperArticle.createArticleRequest("java",
                UUID.randomUUID());
        //WHEN
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName(value = "Should fail if required fields are empty")
    @WithAuthentication(username = "xakimov")
    void shouldFailIfEmptyRequiredField() throws Exception {
        //GIVEN
        RequestBuilder request = testDataHelperArticle.createArticleRequest("java",
                null);
        //WHEN
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isBadRequest());

        //GIVEN
        RequestBuilder request2 = testDataHelperArticle.createArticleRequest("",
                UUID.fromString("56587e05-8911-4009-885a-98e2c7d51c87"));
        //WHEN
        ResultActions resultActions2 = mockMvc.perform(request2);
        //THEN
        resultActions2
                .andExpect(status().isBadRequest());

        //GIVEN
        RequestBuilder request3 = testDataHelperArticle.createArticleRequest("",
                null);
        //WHEN
        ResultActions resultActions3 = mockMvc.perform(request3);
        //THEN
        resultActions3
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName(value = "Should fail if user does not have authority")
    @WithAuthentication(username = "xakimov", roles = "USER")
    void shouldFailUserIsNotAdmin() throws Exception {
        //GIVEN
        CategoryResponse category = testDataHelperCategory.createCategory("category", null);
        RequestBuilder request = testDataHelperArticle.createArticleRequest("java", category.getId());
        //WHEN
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName(value = "Should fail with 401 error code if not authorized")
    @SneakyThrows
    void shouldFailIfUnauthorized() {
        //GIVEN
        String token = generateToken(Set.of("ADMIN"), true);
        CategoryResponse category = testDataHelperCategory.createCategory("category", null);
        UUID categoryId = category.getId();
        RequestBuilder request = testDataHelperArticle.createArticleRequest("java", categoryId)
                .header("Authorization", "Bearer " + token);
        //WHEN
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isUnauthorized());
    }

}
