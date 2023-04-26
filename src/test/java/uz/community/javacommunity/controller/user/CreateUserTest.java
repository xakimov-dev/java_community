package uz.community.javacommunity.controller.user;

import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import uz.community.javacommunity.CommonIntegrationTest;

import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Create a new user ( POST /user )")
class CreateUserTest  extends CommonIntegrationTest {

    @Test
    @DisplayName("Should create a user")
    void shouldCreateUser() throws Exception {
        //GIVEN
        RequestBuilder request = testDataHelperUser.createUserRequest("test_name", "test_password", "test_tenant_id",
                10, Set.of("role1"));
        //WHEN
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("test_name"))
                .andExpect(jsonPath("$.password").value("test_password"))
                .andExpect(jsonPath("$.info").value("test_tenant_id"))
                .andExpect(jsonPath("$.age").value(10))
                .andExpect(jsonPath("$.roles").isArray())
                .andExpect(jsonPath("$.roles", IsCollectionWithSize.hasSize(1)));
    }

    @Test
    @DisplayName("Should fail if the user already exists")
    void shouldFailIfUserExists() throws Exception {
        //GIVEN
        RequestBuilder request = testDataHelperUser.createUserRequest("test_name", "test_password", "test_tenant_id",
                10, Set.of("role1"));
        //WHEN
        mockMvc.perform(request);
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isBadRequest());
    }
}