package uz.community.javacommunity.controller.article.subArticleContent;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import uz.community.javacommunity.CommonIntegrationTest;
import uz.community.javacommunity.WithAuthentication;
import uz.community.javacommunity.controller.dto.*;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Create a new sub article content ( POST /article/sub/content )")
class CreateSubArticleContentTest extends CommonIntegrationTest {
    @Test
    @DisplayName(value = "Should create a new sub article content with 201 status")
    @WithAuthentication(username = "owner")
    void shouldCreateSubArticleContentFile() throws Exception {
        //GIVEN
        CategoryResponse category = testDataHelperCategory
                .createCategory("category", null);
        ArticleResponse article = testDataHelperArticle
                .createArticle("article", category.getId());
        SubArticleResponse subArticle = testDataHelperSubArticle
                .createSubArticle(category.getId(), article.getArticleId(),
                        null, "sub-article");
        SubArticleContentCreateRequest subArticleContent = new SubArticleContentCreateRequest(
                category.getId(), article.getArticleId(),
                subArticle.getId(), "test-text", true);
        RequestBuilder request = testDataHelperSubArticleContent
                .createSubArticleContentTextRequest(subArticleContent);

        //WHEN
        ResultActions resultActions = mockMvc.perform(request);

        //THEN
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoryId").value(category.getId().toString()))
                .andExpect(jsonPath("$.articleId").value(article.getArticleId().toString()))
                .andExpect(jsonPath("$.subArticleId").value(subArticle.getId().toString()))
                .andExpect(jsonPath("$.content").value("test-text"))
                .andExpect(jsonPath("$.isParagraph").value(true));

        //GIVEN
        SubArticleContentImageUrl imageUrl = testDataHelperSubArticleContent.subArticleContentImage(
                testDataHelperSubArticleContent.getImage());
        subArticleContent.setParagraph(false);
        subArticleContent.setContent(imageUrl.getImageUrl());
        request = testDataHelperSubArticleContent.createSubArticleContentTextRequest(subArticleContent);

        //WHEN
        resultActions = mockMvc.perform(request);

        //THEN
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoryId").value(category.getId().toString()))
                .andExpect(jsonPath("$.articleId").value(article.getArticleId().toString()))
                .andExpect(jsonPath("$.subArticleId").value(subArticle.getId().toString()))
                .andExpect(jsonPath("$.content").value(imageUrl.getImageUrl()))
                .andExpect(jsonPath("$.isParagraph").value(false));
    }

    @Test
    @DisplayName(value = "Should fail with 400 if required property is null or empty")
    @WithAuthentication(username = "owner")
    void shouldFailIfRequiredPropertyIsNull() throws Exception {
        //GIVEN
        CategoryResponse category = testDataHelperCategory
                .createCategory("category", null);
        ArticleResponse article = testDataHelperArticle
                .createArticle("article", category.getId());
        SubArticleResponse subArticle = testDataHelperSubArticle
                .createSubArticle(category.getId(), article.getArticleId(),
                        null, "sub-article");
        SubArticleContentCreateRequest subArticleContent = new SubArticleContentCreateRequest(
                category.getId(), article.getArticleId(),
                subArticle.getId(), null, true);
        RequestBuilder request = testDataHelperSubArticleContent
                .createSubArticleContentTextRequest(subArticleContent);

        //WHEN
        ResultActions resultActions = mockMvc.perform(request);

        //THEN

        //WHEN
        resultActions
                .andExpect(status().isBadRequest());

        //GIVEN
        request = testDataHelperSubArticleContent.createSubArticleContentImageRequest(
                        new MockMultipartFile("photo", new byte[0]));
        resultActions = mockMvc.perform(request);

        //THEN
        resultActions
                .andExpect(status().isBadRequest());

        //GIVEN
        subArticleContent = new SubArticleContentCreateRequest(
                null, article.getArticleId(),
                subArticle.getId(), "test-text", true);
        request = testDataHelperSubArticleContent
                .createSubArticleContentTextRequest(subArticleContent);

        //WHEN
        resultActions = mockMvc.perform(request);

        //THEN
        resultActions
                .andExpect(status().isBadRequest());

        //GIVEN
        subArticleContent = new SubArticleContentCreateRequest(
                category.getId(), null,
                subArticle.getId(), "test-text", true);
        request = testDataHelperSubArticleContent
                .createSubArticleContentTextRequest(subArticleContent);

        //WHEN
        resultActions = mockMvc.perform(request);

        //THEN
        resultActions
                .andExpect(status().isBadRequest());

        //GIVEN
        subArticleContent = new SubArticleContentCreateRequest(
                category.getId(), article.getArticleId(),
                null, "test-text", true);
        request = testDataHelperSubArticleContent
                .createSubArticleContentTextRequest(subArticleContent);

        //WHEN
        resultActions = mockMvc.perform(request);

        //THEN
        resultActions
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName(value = "Should fail if one or more required IDs cannot be found")
    @WithAuthentication(username = "owner")
    void shouldFailIfRequiredIdCannotBeFound() throws Exception {
        //GIVEN
        CategoryResponse category = testDataHelperCategory
                .createCategory("category", null);
        ArticleResponse article = testDataHelperArticle
                .createArticle("article", category.getId());
        SubArticleResponse subArticle = testDataHelperSubArticle
                .createSubArticle(category.getId(), article.getArticleId(), null, "sub-article");
        SubArticleContentCreateRequest subArticleContent = new SubArticleContentCreateRequest(
                UUID.randomUUID(), article.getArticleId(),
                subArticle.getId(), "test-text", true);
        RequestBuilder request = testDataHelperSubArticleContent
                .createSubArticleContentTextRequest(subArticleContent);

        //WHEN
        ResultActions resultActions = mockMvc.perform(request);

        //THEN
        resultActions
                .andExpect(status().isNotFound());

        //GIVEN
        subArticleContent = new SubArticleContentCreateRequest(
                category.getId(), UUID.randomUUID(),
                subArticle.getId(), "test-text", true);
        request = testDataHelperSubArticleContent
                .createSubArticleContentTextRequest(subArticleContent);

        //WHEN
        resultActions = mockMvc.perform(request);

        //THEN
        resultActions
                .andExpect(status().isNotFound());

        //GIVEN
        subArticleContent = new SubArticleContentCreateRequest(
                category.getId(), article.getArticleId(),
                UUID.randomUUID(), "test-text", true);
        request = testDataHelperSubArticleContent
                .createSubArticleContentTextRequest(subArticleContent);

        //WHEN
        resultActions = mockMvc.perform(request);

        //THEN
        resultActions
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName(value = "Should fail if user is not admin")
    @WithAuthentication(username = "owner", roles = "ROLE_USER")
    void shouldFailIfNotAdmin() throws Exception {
        //GIVEN
        SubArticleContentCreateRequest subArticleContent = new SubArticleContentCreateRequest(
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                "test-text", true);
        RequestBuilder request = testDataHelperSubArticleContent
                .createSubArticleContentTextRequest(subArticleContent);

        //WHEN
        ResultActions resultActions = mockMvc.perform(request);

        //THEN
        resultActions
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName(value = "Should fail with 401 status if user is not authorized")
    @WithAnonymousUser
    void shouldFailIfUnauthorized() throws Exception {
        //GIVEN
        SubArticleContentCreateRequest subArticleContent = new SubArticleContentCreateRequest(
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                "test-text", true);
        RequestBuilder request = testDataHelperSubArticleContent
                .createSubArticleContentTextRequest(subArticleContent);

        //WHEN
        ResultActions resultActions = mockMvc.perform(request);

        //THEN
        resultActions
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName(value = "Should delete or not sub article content image with status 200")
    @WithAuthentication(username = "owner")
    void shouldDeleteImage() throws Exception {
        //GIVEN
        SubArticleContentImageUrl imageUrl = testDataHelperSubArticleContent
                .subArticleContentImage(testDataHelperSubArticleContent.getImage());
        RequestBuilder request = testDataHelperSubArticleContent
                .deleteSubArticleImageRequest(imageUrl);

        //WHEN
        ResultActions resultActions = mockMvc.perform(request);

        //THEN
        resultActions
                .andExpect(status().isOk());
    }

}
