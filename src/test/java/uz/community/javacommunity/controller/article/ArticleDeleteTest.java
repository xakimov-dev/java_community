package uz.community.javacommunity.controller.article;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import uz.community.javacommunity.CommonIntegrationTest;
import uz.community.javacommunity.controller.dto.ArticleResponse;
import uz.community.javacommunity.controller.dto.CategoryResponse;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Delete an article ( DELETE /article/{id} )")
class ArticleDeleteTest extends CommonIntegrationTest {

    @Test
    @DisplayName(value = "Should be success, delete an Article")
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteArticle() throws Exception {
        CategoryResponse category = testDataHelperCategory.createCategory("category", null);
        UUID categoryId = category.getId();
        ArticleResponse article = testDataHelperArticle.createArticle("java", categoryId);
        UUID articleId = article.getArticleId();

        RequestBuilder requestBuilder = testDataHelperArticle.deleteArticleRequest(articleId);
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        resultActions
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "Article should not exist")
    @WithMockUser(roles = "ADMIN")
    void shouldArticleNotFound() throws Exception {
        RequestBuilder requestBuilder = testDataHelperArticle.deleteArticleRequest(UUID.randomUUID());
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        resultActions
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName(value = "Should fail if user does not have authority")
    @WithMockUser(roles = "USER")
    void shouldFailUserIsNotAdmin() throws Exception{
        RequestBuilder requestBuilder = testDataHelperArticle.deleteArticleRequest(UUID.randomUUID());
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        resultActions
                .andExpect(status().isForbidden());
    }
}