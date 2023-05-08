package uz.community.javacommunity.controller.article;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.RequestBuilder;
import uz.community.javacommunity.CommonIntegrationTest;
import uz.community.javacommunity.WithAuthentication;
import uz.community.javacommunity.controller.dto.ArticleResponse;
import uz.community.javacommunity.controller.dto.CategoryResponse;
import uz.community.javacommunity.controller.dto.SubArticleResponse;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Update a sub article ( PUT /article/sub/{id} )")
class UpdateSubArticleTest extends CommonIntegrationTest {

    @Test
    @DisplayName(value = "Should update a sub article")
    @WithAuthentication(username = "owner")
    void shouldUpdateSubArticle() throws Exception {

        CategoryResponse category1 = testDataHelperCategory.createCategory("Category1", null);

        ArticleResponse article1 = testDataHelperArticle.createArticle("Article1", category1.getId());

        CategoryResponse category2 = testDataHelperCategory.createCategory("Category2", null);

        ArticleResponse article2 = testDataHelperArticle.createArticle("Article2", category2.getId());

        SubArticleResponse subArticle1 = testDataHelperSubArticle
                .createSubArticle(category1.getId(), article1.getArticleId(), null, "subArticle1");

        SubArticleResponse subArticle2 = testDataHelperSubArticle
                .createSubArticle(category2.getId(), article2.getArticleId(), null, "subArticle2");


        RequestBuilder requestBuilder = testDataHelperSubArticle.updateSubArticleRequest(subArticle2.getId(), category1.getId(), article1.getArticleId(), subArticle1.getId(), "SUB-ARTICLE");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "Should not update a sub article when category id is null")
    @WithAuthentication(username = "owner")
    void shouldNotUpdateSubArticleBecauseOfCategoryId() throws Exception {

        CategoryResponse category1 = testDataHelperCategory.createCategory("Category1", null);

        ArticleResponse article1 = testDataHelperArticle.createArticle("Article1", category1.getId());

        CategoryResponse category2 = testDataHelperCategory.createCategory("Category2", null);

        ArticleResponse article2 = testDataHelperArticle.createArticle("Article2", category2.getId());

        SubArticleResponse subArticle1 = testDataHelperSubArticle
                .createSubArticle(category1.getId(), article1.getArticleId(), null, "subArticle1");

        SubArticleResponse subArticle2 = testDataHelperSubArticle
                .createSubArticle(category2.getId(), article2.getArticleId(), null, "subArticle2");


        RequestBuilder requestBuilder = testDataHelperSubArticle.updateSubArticleRequest(subArticle2.getId(), null, article1.getArticleId(), subArticle1.getId(), "SUB-ARTICLE");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }
}
