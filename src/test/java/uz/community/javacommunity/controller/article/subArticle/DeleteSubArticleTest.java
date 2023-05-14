package uz.community.javacommunity.controller.article.subArticle;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithAnonymousUser;
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
    @WithAuthentication(username = "owner")
    void shouldDeleteSubArticle() throws Exception {
        //GIVEN
        CategoryResponse category = testDataHelperCategory.createCategory("category", null);
        ArticleResponse article = testDataHelperArticle.createArticle("article", category.getId());
        SubArticleResponse subArticle = testDataHelperSubArticle.createSubArticle(category.getId(),
                article.getArticleId(), null, "sub_article");
        RequestBuilder request = testDataHelperSubArticle.deleteSubArticleRequest(subArticle.getId());
        //WHEN
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName(value = "Should fail if the SubArticle cannot be found")
    @WithAuthentication(username = "owner")
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
    @WithMockUser(username = "owner", roles = "USER")
    void shouldFailUserIsNotAdmin() throws Exception {
        //GIVEN
        RequestBuilder request = testDataHelperSubArticle.deleteSubArticleRequest(UUID.randomUUID());
        //WHEN
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName(value = "Should fail with 401 error code if not authorized")
    @SneakyThrows
    @WithAnonymousUser
    void shouldFailIfUnauthorized() {
        //GIVEN
        RequestBuilder request = testDataHelperSubArticle.deleteSubArticleRequest(UUID.randomUUID());
        //WHEN
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isUnauthorized());
    }

}
