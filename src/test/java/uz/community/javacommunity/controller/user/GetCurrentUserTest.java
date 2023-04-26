package uz.community.javacommunity.controller.user;

import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import uz.community.javacommunity.CommonIntegrationTest;
import uz.community.javacommunity.WithAuthentication;
import uz.community.javacommunity.controller.dto.UserResponse;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Get current user ( GET /user/current )")
class GetCurrentUserTest extends CommonIntegrationTest {

    private static final String USERNAME = "test_username";

    @Test
    @DisplayName("Should return current user")
    @WithAuthentication(username = USERNAME)
    void shouldReturnCurrentUser() throws Exception {
        //GIVEN
        UserResponse user = testDataHelperUser.createUser(USERNAME, "password");
        RequestBuilder request = testDataHelperUser.getCurrentUser();
        //WHEN
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value(user.getUsername()))
                .andExpect(jsonPath("$.age").value(user.getAge()))
                .andExpect(jsonPath("$.roles").isArray())
                .andExpect(jsonPath("$.roles", IsCollectionWithSize.hasSize(1)))
                .andExpect(jsonPath("$.roles", IsCollectionWithSize.hasSize(user.getRoles().size())));
    }

    @Test
    @DisplayName("Should fail with 401 error code if not authorized")
    @WithAnonymousUser
    void shouldFailIfUnauthorized() throws Exception {
        //GIVEN
        RequestBuilder request = testDataHelperUser.getCurrentUser();
        //WHEN
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Should fail with 404 error code if user does not exist")
    @WithAuthentication(username = USERNAME)
    void shouldFailIfUserNotFound() throws Exception {
        //GIVEN
        RequestBuilder request = testDataHelperUser.getCurrentUser();
        //WHEN
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isNotFound());
    }
}
