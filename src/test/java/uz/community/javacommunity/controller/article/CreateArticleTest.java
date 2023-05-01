package uz.community.javacommunity.controller.article;

import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import uz.community.javacommunity.CommonIntegrationTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Create a new article ( POST /article )")
public class CreateArticleTest extends CommonIntegrationTest {
    @Test
    @DisplayName(value = "Should Create a new Article")
    @WithMockUser(roles = "ADMIN")
    void shouldCreateArticle() throws Exception{
        //GIVEN
        RequestBuilder request = testDataHelperArticle.createArticleRequest("java","0c389384-8a10-43b7-aa99-a495cdd405e8");
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("java"))
                .andExpect(jsonPath("$."))
    }

}
