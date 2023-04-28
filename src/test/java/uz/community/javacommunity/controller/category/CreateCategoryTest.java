package uz.community.javacommunity.controller.category;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import uz.community.javacommunity.CommonIntegrationTest;
import uz.community.javacommunity.WithAuthentication;
import uz.community.javacommunity.controller.dto.CategoryResponse;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author :Xojiakbar Abiyorov
 */

@DisplayName("Create a new category ( POST /api/v1/category )")
public class CreateCategoryTest extends CommonIntegrationTest {


    @Test
    @DisplayName(value = "Should create a parent category")
    @WithAuthentication(username = "owner")
    public void shouldCreateParentCategory() throws Exception {

        RequestBuilder categoryRequest = testDataHelperCategory
                .createCategoryRequest("test",null );

        ResultActions resultActions = mockMvc.perform(categoryRequest);

        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.createdBy").value("owner"));
    }

    @Test
    @DisplayName(value = "Should create a child category")
    @WithAuthentication(username = "owner1")
    public void shouldCreateChildCategory() throws Exception {
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);

        RequestBuilder categoryRequest = testDataHelperCategory
                .createCategoryRequest("test",null);

        ResultActions resultActions = mockMvc.perform(categoryRequest);

        String content = resultActions.andReturn().getResponse().getContentAsString();

        CategoryResponse categoryResponse = mapper.readValue(content, CategoryResponse.class);
        UUID id = categoryResponse.getId();

        RequestBuilder categoryRequest1 = testDataHelperCategory
                .createCategoryRequest("test1", id);

        ResultActions perform = mockMvc.perform(categoryRequest1);

        perform.andExpect(status().isCreated())
                .andExpect(jsonPath("$.createdBy").value("owner1"))
                .andExpect(jsonPath("$.name").value("test1"))
                .andExpect(jsonPath("$.parentId").value(id.toString()));

    }



    @Test
    @DisplayName(value = "Should get unauthorized ")
    @WithAnonymousUser
    public void shouldThrowAuthenticationException() throws Exception {

        RequestBuilder categoryRequest = testDataHelperCategory
                .createCategoryRequest("test",null);

        ResultActions resultActions = mockMvc.perform(categoryRequest);

        resultActions
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName(value = "Should get 4xx status if fields Blank ")
    @WithAuthentication
    public void shouldThrowFieldValidationException() throws Exception {

        RequestBuilder categoryRequest = testDataHelperCategory
                .createCategoryRequest("",null);

        ResultActions resultActions = mockMvc.perform(categoryRequest);

        resultActions
                .andExpect(status().is4xxClientError());
    }



}
