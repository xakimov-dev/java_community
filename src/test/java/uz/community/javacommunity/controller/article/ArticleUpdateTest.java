package uz.community.javacommunity.controller.article;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import uz.community.javacommunity.CommonIntegrationTest;
import uz.community.javacommunity.WithAuthentication;
import uz.community.javacommunity.controller.dto.ArticleResponse;
import uz.community.javacommunity.controller.dto.CategoryResponse;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Update an article ( PUT /article/{id} )")
class ArticleUpdateTest extends CommonIntegrationTest {
    @Test
    @DisplayName(value = "Should be success, update an Article")
    @WithAuthentication(username = "owner")
    void shouldUpdateArticle() throws Exception{
        CategoryResponse category = testDataHelperCategory.createCategory("category", null);
        UUID categoryId = category.getId();
        ArticleResponse article = testDataHelperArticle.createArticle("java", categoryId);
        UUID articleId = article.getArticleId();
        CategoryResponse newCategory = testDataHelperCategory.createCategory("category2", null);
        UUID newCategoryId = newCategory.getId();
        RequestBuilder updateArticleRequest = testDataHelperArticle.updateArticleRequest(articleId, newCategoryId, "c++");
        ResultActions resultActions = mockMvc.perform(updateArticleRequest);

        resultActions
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "Should fail if the article not found")
    @WithAuthentication(username = "owner")
    void shouldFailArticleNotFound() throws Exception{
        //WHEN
        RequestBuilder request = testDataHelperArticle.updateArticleRequest(UUID.randomUUID(),UUID.randomUUID(),"java");
        //WHEN
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName(value = "Should fail if the category cannot be found")
    @WithAuthentication(username = "owner")
    void shouldFailCategoryNotFound() throws Exception{
        //GIVEN
        CategoryResponse category = testDataHelperCategory.createCategory("category", null);
        UUID categoryId = category.getId();
        ArticleResponse article = testDataHelperArticle.createArticle("java", categoryId);
        //WHEN
        UUID articleId = article.getArticleId();
        RequestBuilder updateArticleRequest = testDataHelperArticle.updateArticleRequest(articleId, UUID.randomUUID(), "c++");
        ResultActions resultActions = mockMvc.perform(updateArticleRequest);
        //THEN
        resultActions
                .andExpect(status().isNotFound());
    }
}
