package uz.community.javacommunity.controller.article;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import uz.community.javacommunity.CommonIntegrationTest;
import uz.community.javacommunity.WithAuthentication;
import uz.community.javacommunity.controller.dto.ArticleResponse;
import uz.community.javacommunity.controller.dto.CategoryResponse;
import uz.community.javacommunity.controller.dto.SubArticleResponse;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Create a new sub article ( POST /article/sub )")
class CreateSubArticleTest extends CommonIntegrationTest {

    @Test
    @DisplayName(value = "Should create a parent sub article")
    @WithAuthentication(username = "owner")
    void shouldCreateParentSubArticle() throws Exception {

        CategoryResponse category = testDataHelperCategory.createCategory("Category1", null);

        ArticleResponse article = testDataHelperArticle.createArticle("Article1", category.getId());

        RequestBuilder subArticleRequest = testDataHelperSubArticle
                .createSubArticleRequest(category.getId(), article.getArticleId(), null, "subArticle1");

        ResultActions resultActions = mockMvc.perform(subArticleRequest);

        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("subArticle1"))
                .andExpect(jsonPath("$.createdBy").value("owner"))
                .andExpect(jsonPath("$.parentSubArticleId").doesNotExist())
                .andExpect(jsonPath("$.categoryId").value(category.getId().toString()))
                .andExpect(jsonPath("$.articleId").value(article.getArticleId().toString()));
    }

    @Test
    @DisplayName(value = "Should create a child sub article")
    @WithAuthentication(username = "owner")
    void shouldCreateChildSubArticle() throws Exception {

        CategoryResponse category = testDataHelperCategory.createCategory("Category1", null);

        ArticleResponse article = testDataHelperArticle.createArticle("Article1", category.getId());

        SubArticleResponse parentsubArticle1 = testDataHelperSubArticle.createSubArticle(category.getId(), article.getArticleId(), null, "parentsubArticle1");

        RequestBuilder subArticleRequest = testDataHelperSubArticle
                .createSubArticleRequest(category.getId(), article.getArticleId(), parentsubArticle1.id(), "subArticle12");

        ResultActions resultActions = mockMvc.perform(subArticleRequest);

        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("subArticle12"))
                .andExpect(jsonPath("$.createdBy").value("owner"))
                .andExpect(jsonPath("$.parentSubArticleId").value(parentsubArticle1.id().toString()))
                .andExpect(jsonPath("$.categoryId").value(category.getId().toString()))
                .andExpect(jsonPath("$.articleId").value(article.getArticleId().toString()));
    }

    @Test
    @DisplayName(value = "Should not create a sub article when article name is duplicate")
    @WithAuthentication(username = "owner")
    void shouldNotCreateSubArticle() throws Exception {

        CategoryResponse category = testDataHelperCategory.createCategory("Category1", null);

        ArticleResponse article = testDataHelperArticle.createArticle("Article1", category.getId());

        SubArticleResponse parentsubArticle1 = testDataHelperSubArticle.createSubArticle(category.getId(), article.getArticleId(), null, "art");

        RequestBuilder subArticleRequest = testDataHelperSubArticle
                .createSubArticleRequest(category.getId(), article.getArticleId(), parentsubArticle1.id(), "art");

        ResultActions resultActions = mockMvc.perform(subArticleRequest);

        resultActions
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName(value = "Should not create a sub article by category null")
    @WithAuthentication(username = "owner")
    void shouldNotCreateSubArticleByCategoryNull() throws Exception {

        CategoryResponse category = testDataHelperCategory.createCategory("Category1", null);

        ArticleResponse article = testDataHelperArticle.createArticle("Article1", category.getId());

        RequestBuilder subArticleRequest = testDataHelperSubArticle
                .createSubArticleRequest(null, article.getArticleId(), null, "art");

        ResultActions resultActions = mockMvc.perform(subArticleRequest);

        resultActions
                .andExpect(status().isBadRequest());
    }

}
