package uz.community.javacommunity.controller.article;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import uz.community.javacommunity.CommonIntegrationTest;
import uz.community.javacommunity.WithAuthentication;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Create a new sub article ( POST /article/sub )")
public class CreateSubArticleTest extends CommonIntegrationTest {

    @Test
    @DisplayName(value = "Should create a parent sub article")
    @WithAuthentication(username = "owner")
    void shouldCreateParentSubArticle() throws Exception {

        RequestBuilder categoryRequest = testDataHelperSubArticle
                .createSubArticleRequest();

        ResultActions resultActions = mockMvc.perform(categoryRequest);

        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.createdBy").value("owner"))
                .andExpect(jsonPath("$.parentId").doesNotExist());
    }

}
