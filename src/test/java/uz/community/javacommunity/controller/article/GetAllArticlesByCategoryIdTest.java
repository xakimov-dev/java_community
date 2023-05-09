package uz.community.javacommunity.controller.article;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import uz.community.javacommunity.CommonIntegrationTest;
import uz.community.javacommunity.WithAuthentication;
import uz.community.javacommunity.controller.dto.ArticleResponse;
import uz.community.javacommunity.controller.dto.CategoryResponse;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("get all articles by categoryId ( POST /article/{categoryId} )")
class GetAllArticlesByCategoryIdTest extends CommonIntegrationTest {
    @Test
    @DisplayName("should get all articles by categoryId")

    void shouldGetAllArticlesByCategoryId() throws Exception {
        CategoryResponse category = testDataHelperCategory.createCategory("Category", null);
        testDataHelperArticle.createArticle("Article1", category.getId());
        testDataHelperArticle.createArticle("Article2", category.getId());
        testDataHelperArticle.createArticle("Article3", category.getId());
        RequestBuilder articlesByCategoryIdRequest = testDataHelperArticle.getArticlesByCategoryIdRequest(category.getId());
        ResultActions resultActions = mockMvc.perform(articlesByCategoryIdRequest);
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)));
    }
}
