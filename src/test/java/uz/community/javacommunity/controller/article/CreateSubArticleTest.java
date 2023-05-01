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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Create a new sub article ( POST /article/sub )")
public class CreateSubArticleTest extends CommonIntegrationTest {

    @Test
    @DisplayName(value = "Should create a parent sub article")
    @WithAuthentication(username = "owner")
    void shouldCreateParentSubArticle() throws Exception {

        CategoryResponse category = testDataHelperCategory.createCategory("Category1", null);

        ArticleResponse article = testDataHelperArticle.createArticle("Article1", category.getId().toString());

        RequestBuilder subArticleRequest = testDataHelperSubArticle
                .createSubArticleRequest(category.getId(), UUID.fromString(article.getArticleId()), null, "subArticle1");

        ResultActions resultActions = mockMvc.perform(subArticleRequest);

        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("subArticle1"))
                .andExpect(jsonPath("$.createdBy").value("owner"))
                .andExpect(jsonPath("$.parentSubArticleId").doesNotExist())
                .andExpect(jsonPath("$.categoryId").value(category.getId()))
                .andExpect(jsonPath("$.articleId").value(article.getArticleId()));
    }

}
