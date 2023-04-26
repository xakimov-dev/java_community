package uz.community.javacommunity.controller.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import uz.community.javacommunity.CommonIntegrationTest;
import uz.community.javacommunity.controller.domain.User;
import uz.community.javacommunity.controller.repository.UserRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Log in a user ( POST /user/login )")
class UserLoginTest extends CommonIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should successfully log in a user")
    void shouldLoginSuccessfully() throws Exception {
        //GIVEN
        String username = "test_username";
        String password = "password";
        testDataHelperUser.createUser(username, "password");
        RequestBuilder request = testDataHelperUser.loginRequest(username, password);
        //WHEN
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    @DisplayName("Should return 401 code if password is not correct")
    void shouldFailIfIncorrectPassword() throws Exception {
        //GIVEN
        String username = "test_username";
        testDataHelperUser.createUser(username, "password");
        RequestBuilder request = testDataHelperUser.loginRequest(username, "invalid_password");
        //WHEN
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Should return 400 if login or password are empty")
    void shouldFailIfEmptyRequiredField() throws Exception {
        //GIVEN
        RequestBuilder request = testDataHelperUser.loginRequest("", "password");
        //WHEN
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isBadRequest());

        //GIVEN
        request = testDataHelperUser.loginRequest("username", "");
        //WHEN
        resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 401 if user can not be found by username")
    void shouldFailIfUserNotFound() throws Exception {
        //GIVEN
        RequestBuilder request = testDataHelperUser.loginRequest("username", "password");
        //WHEN
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Should return 401 if login can not be found by username")
    void shouldFailIfLoginNotFound() throws Exception {
        User user = User.builder().username("username").build();
        userRepository.save(user);
        //GIVEN
        RequestBuilder request = testDataHelperUser.loginRequest("username", "password");
        //WHEN
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isUnauthorized());
    }
}
