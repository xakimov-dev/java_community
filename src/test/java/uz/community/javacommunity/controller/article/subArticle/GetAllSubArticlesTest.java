package uz.community.javacommunity.controller.article.subArticle;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import uz.community.javacommunity.controller.dto.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import uz.community.javacommunity.CommonIntegrationTest;
import uz.community.javacommunity.controller.dto.CategoryResponse;

@DisplayName("Get all subarticles category ( GET /article/sub )")
public class GetAllSubArticlesTest extends CommonIntegrationTest {
    @Test
    @DisplayName(value = "Should get All subarticles category")
    void shouldGetSubArticle() throws Exception {
        CategoryResponse category = testDataHelperCategory.createCategory("Category1", null);

        ArticleResponse article = testDataHelperArticle.createArticle("Article1", category.getId());

        SubArticleResponse parentsubArticle1 = testDataHelperSubArticle.createSubArticle(category.getId(), article.getArticleId(), null, "parentsubArticle1");
        SubArticleResponse parentsubArticle2 = testDataHelperSubArticle.createSubArticle(category.getId(), article.getArticleId(), null, "parentsubArticle2");
        SubArticleResponse parentsubArticle3 = testDataHelperSubArticle.createSubArticle(category.getId(), article.getArticleId(), null, "parentsubArticle3");
        SubArticleResponse parentsubArticle4 = testDataHelperSubArticle.createSubArticle(category.getId(), article.getArticleId(), null, "parentsubArticle4");
        SubArticleResponse parentsubArticle5 = testDataHelperSubArticle.createSubArticle(category.getId(), article.getArticleId(), null, "parentsubArticle5");
        SubArticleResponse parentsubArticle6 = testDataHelperSubArticle.createSubArticle(category.getId(), article.getArticleId(), null, "parentsubArticle6");
        RequestBuilder requestBuilder = testDataHelperSubArticle.getSubArticles(article.getArticleId());
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        resultActions
                .andExpect(status().isOk());

    }
}
