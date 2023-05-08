package uz.community.javacommunity.controller.article;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import uz.community.javacommunity.CommonIntegrationTest;
import uz.community.javacommunity.WithAuthentication;
import uz.community.javacommunity.controller.dto.ArticleResponse;
import uz.community.javacommunity.controller.dto.CategoryResponse;
import uz.community.javacommunity.controller.dto.SubArticleResponse;

import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Delete subArticle ( DELETE /subArticle )")
class DeleteSubArticleTest extends CommonIntegrationTest {
    @Test
    @DisplayName(value = "Should delete a SubArticle")
    @WithAuthentication(username = "xakimov")
    void shouldDeleteSubArticle() throws Exception {
        //GIVEN
        CategoryResponse category = testDataHelperCategory.createCategory("category", null);
        ArticleResponse article = testDataHelperArticle.createArticle("article", category.getId());
        SubArticleResponse subArticle = testDataHelperSubArticle.createSubArticle(category.getId(),
                article.getArticleId(), null, "sub_article");
        RequestBuilder request = testDataHelperSubArticle.deleteSubArticleRequest(subArticle.id());
        //WHEN
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName(value = "Should fail if the SubArticle cannot be found")
    @WithAuthentication(username = "xakimov")
    void shouldFailCategoryNotFound() throws Exception {
        //GIVEN
        RequestBuilder request = testDataHelperSubArticle.deleteSubArticleRequest(UUID.randomUUID());
        //WHEN
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName(value = "Should fail if user does not have authority")
    @WithMockUser(username = "xakimov", roles = "USER")
    void shouldFailUserIsNotAdmin() throws Exception {
        //GIVEN
        CategoryResponse category = testDataHelperCategory.createCategory("category", null);
        ArticleResponse article = testDataHelperArticle.createArticle("article", category.getId());
        SubArticleResponse subArticle = testDataHelperSubArticle.createSubArticle(category.getId(),
                article.getArticleId(), null, "sub_article");
        RequestBuilder request = testDataHelperSubArticle.deleteSubArticleRequest(subArticle.id());
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
        ArticleResponse article = testDataHelperArticle.createArticle("article", category.getId());
        SubArticleResponse subArticle = testDataHelperSubArticle.createSubArticle(category.getId(),
                article.getArticleId(), null, "sub_article");
        RequestBuilder request = testDataHelperSubArticle.deleteSubArticleRequest(UUID.randomUUID())
                .header("Authorization", "Bearer " + token);
        //WHEN
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isUnauthorized());
    }

}
