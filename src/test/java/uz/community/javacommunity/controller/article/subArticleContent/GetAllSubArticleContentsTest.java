package uz.community.javacommunity.controller.article.subArticleContent;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import uz.community.javacommunity.CommonIntegrationTest;
import uz.community.javacommunity.WithAuthentication;
import uz.community.javacommunity.controller.dto.ArticleResponse;
import uz.community.javacommunity.controller.dto.CategoryResponse;
import uz.community.javacommunity.controller.dto.SubArticleContentResponse;
import uz.community.javacommunity.controller.dto.SubArticleResponse;

import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Get all sub article contents ( GET /article/sub/content/{id} )")
class GetAllSubArticleContentsTest extends CommonIntegrationTest {
    @Test
    @DisplayName(value = "Should get all sub article contents with 200 status")
    @WithAuthentication(username = "owner")
    void shouldGetAllSubArticleContents() throws Exception {
        //GIVEN
        CategoryResponse category = testDataHelperCategory
                .createCategory("category", null);
        ArticleResponse article = testDataHelperArticle
                .createArticle("article", category.getId());
        SubArticleResponse subArticle = testDataHelperSubArticle
                .createSubArticle(category.getId(), article.getArticleId(),
                        null, "sub-article");
        RequestBuilder request = testDataHelperSubArticleContent.getSubArticleContentRequest(subArticle.getId());

        //WHEN
        ResultActions resultActions = mockMvc.perform(request);

        //THEN
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty())
                .andExpect(jsonPath("$",hasSize(0)));

        //GIVEN
        String subArticleContentRequest = testDataHelperSubArticleContent
                .subArticleContentRequest(category.getId(), article.getArticleId(),
                        subArticle.getId(), null, false);
        SubArticleContentResponse subArticleContent = testDataHelperSubArticleContent.createSubArticleContent(
                testDataHelperSubArticleContent.getImage(), subArticleContentRequest);
        request = testDataHelperSubArticleContent.getSubArticleContentRequest(subArticle.getId());

        //WHEN
        resultActions = mockMvc.perform(request);

        //THEN
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].categoryId").value(category.getId().toString()))
                .andExpect(jsonPath("$[0].articleId").value(article.getArticleId().toString()))
                .andExpect(jsonPath("$[0].subArticleId").value(subArticle.getId().toString()))
                .andExpect(jsonPath("$[0].content").value(subArticleContent.content()))
                .andExpect(jsonPath("$[0].isParagraph").value(false));
        //GIVEN
        subArticleContentRequest = testDataHelperSubArticleContent
                .subArticleContentRequest(category.getId(), article.getArticleId(),
                        subArticle.getId(), "test-text", true);
        SubArticleContentResponse subArticleContent1 = testDataHelperSubArticleContent.createSubArticleContent(
                null, subArticleContentRequest);

        //WHEN
        resultActions = mockMvc.perform(request);

        //THEN
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[1].categoryId").value(subArticleContent1.categoryId().toString()))
                .andExpect(jsonPath("$[1].articleId").value(subArticleContent1.articleId().toString()))
                .andExpect(jsonPath("$[1].subArticleId").value(subArticleContent1.subArticleId().toString()))
                .andExpect(jsonPath("$[1].content").value(subArticleContent1.content()))
                .andExpect(jsonPath("$[1].isParagraph").value(true));
    }

    @Test
    @DisplayName(value = "Should fail with 404 status if sub article cannot be found")
    @WithAuthentication(username = "owner")
    void shouldFailIfSubArticleNotFound() throws Exception {
        //GIVEN
        RequestBuilder request = testDataHelperSubArticleContent
                .getSubArticleContentRequest(UUID.randomUUID());

        //WHEN
        ResultActions resultActions = mockMvc.perform(request);

        //THEN
        resultActions
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName(value = "Should fail with 403 status if user has not authority")
    @WithAuthentication(username = "owner",roles = "ROLE_USER")
    void shouldFailIfNotAdmin() throws Exception{
        //GIVEN
        RequestBuilder request = testDataHelperSubArticleContent
                .getSubArticleContentRequest(UUID.randomUUID());

        //WHEN
        ResultActions resultActions = mockMvc.perform(request);

        //THEN
        resultActions
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName(value = "Should fail with 401 status if user is not authorized")
    @WithAnonymousUser
    void shouldFailIfUnauthorized() throws Exception{
        //GIVEN
        RequestBuilder request = testDataHelperSubArticleContent
                .getSubArticleContentRequest(UUID.randomUUID());

        //WHEN
        ResultActions resultActions = mockMvc.perform(request);

        //THEN
        resultActions
                .andExpect(status().isUnauthorized());
    }

}
