package uz.community.javacommunity.controller.article;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import uz.community.javacommunity.CommonIntegrationTest;
import uz.community.javacommunity.common.JsonConverter;
import uz.community.javacommunity.controller.domain.Article;
import uz.community.javacommunity.controller.dto.ArticleResponse;
import uz.community.javacommunity.controller.dto.ArticleUpdateRequest;
import uz.community.javacommunity.controller.dto.CategoryResponse;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("Update an article ( PUT /article/{id} )")
@RequiredArgsConstructor
class ArticleUpdateTest extends CommonIntegrationTest {
    private final JsonConverter jsonConverter;
    @Test
    @DisplayName(value = "Should be success, update a Article")
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateArticle() throws Exception{
        //GIVEN
        CategoryResponse category = testDataHelperCategory.createCategory("category", null);
        UUID categoryId = category.getId();
        shouldFailCategoryNotFound(categoryId);
        RequestBuilder article = testDataHelperArticle.createArticleRequest("java",categoryId);
        //WHEN
        String contentAsString = mockMvc.perform(article).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        ArticleResponse articleResponse = jsonConverter.convertFromString(contentAsString, ArticleResponse.class);


        Article.ArticleKey articleKey = Article.ArticleKey.of(articleResponse.getArticleId(), articleResponse.getCategoryId());
        ArticleUpdateRequest articleUpdateRequest = new ArticleUpdateRequest(
                articleKey,
                "new article name"
        );


        RequestBuilder requestBuilder = testDataHelperArticle.updateArticleRequest(articleResponse.getArticleId(), articleUpdateRequest);

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        resultActions
                .andExpect(status().isUpgradeRequired())
                .andExpect(jsonPath("$.name").value("new article name"))
                .andExpect(jsonPath("$.categoryId").value(articleResponse.getCategoryId()))
                .andExpect(jsonPath("$.createdBy").exists())
                .andExpect(jsonPath("$.modifiedBy").exists())
                .andExpect(jsonPath("$.modifiedDate").exists());

    }


    @Test
    @DisplayName(value = "Should fail if the category cannot be found")
    @WithMockUser(roles = "ADMIN")
    void shouldFailCategoryNotFound(UUID id) throws Exception{
        //GIVEN
        RequestBuilder request = testDataHelperArticle.createArticleRequest("java",
              (id));
        //WHEN
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName(value = "Should fail if user does not have authority")
    @WithMockUser(roles = "USER")
    void shouldFailUserIsNotAdmin() throws Exception{
        //GIVEN
        CategoryResponse category = testDataHelperCategory.createCategory("category", null);
        UUID categoryId = category.getId();
        RequestBuilder request = testDataHelperArticle.createArticleRequest("java",categoryId);
        //WHEN
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isForbidden());
    }

}
