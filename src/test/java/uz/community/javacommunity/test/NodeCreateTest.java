//package uz.community.javacommunity.test;
//
//import org.apache.commons.lang3.RandomStringUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Named;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.ResultActions;
//
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.stream.Stream;
//
//import static com.qad.noderegistry.WithAuthentication.TEST_TENANT;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@DisplayName("Create a new node ( POST /nodes/{tenantid} )")
//class NodeCreateTest extends CommonIntegrationTest {
//
//    @Test
//    @DisplayName("Should create a node")
//    void schemaTypeCreateSuccessPath() throws Exception {
//        //GIVEN
//        ConnectionTypeResponse connectionType = testDataHelperConnectionType.createConnectionType(TEST_TENANT);
//        RequestBuilder request = testDataHelperNode.createNodeRequest(TEST_TENANT, payload -> payload.put("connectionType", connectionType.getType()));
//        //WHEN
//        ResultActions resultActions = mockMvc.perform(request);
//        //THEN
//        resultActions
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.tenantId").value(TEST_TENANT))
//                .andExpect(jsonPath("$.type").value("application"))
//                .andExpect(jsonPath("$.uri").value("urn:application:erp:europe"))
//                .andExpect(jsonPath("$.subtype").value("erp"))
//                .andExpect(jsonPath("$.instance").value("europe"))
//                .andExpect(jsonPath("$.name").value("Europe ERP"))
//                .andExpect(jsonPath("$.description").value("Primary European ERP instance"))
//                .andExpect(jsonPath("$.location").value("AWS"))
//                .andExpect(jsonPath("$.connectionType").value("QADAUX"))
//                .andExpect(jsonPath("$.connectionDetails").value(""));
//    }
//
//    @Test
//    @DisplayName("Should return 400 when connection type doesn't exist")
//    void return400WhenNonExistingConnectionTypeProvided() throws Exception {
//        //GIVEN
//        ConnectionTypeResponse connectionType = testDataHelperConnectionType.createConnectionType(TEST_TENANT);
//        RequestBuilder request = testDataHelperNode.createNodeRequest(TEST_TENANT, payload -> payload.put("connectionType", connectionType.getType() + " "));
//        //WHEN
//        ResultActions resultActions = mockMvc.perform(request);
//        //THEN
//        resultActions
//                .andExpect(status().isBadRequest());
//    }
//
//    @ParameterizedTest(name = "{index} when node instance contains unsafe characters {0} should return 400")
//    @MethodSource("invalidUnsafeCharactersRequestData")
//    void shouldReturn400ForUnsafeCharactersInInstance(String unsafeChar) throws Exception {
//        //GIVEN
//        ConnectionTypeResponse connectionType = testDataHelperConnectionType.createConnectionType(TEST_TENANT);
//        RequestBuilder request = testDataHelperNode.createNodeRequest(TEST_TENANT, payload -> {
//            payload.put("instance", unsafeChar);
//            payload.put("connectionType", connectionType.getType());
//        });
//        //WHEN
//        ResultActions resultActions = mockMvc.perform(request);
//        //THEN
//        resultActions.andExpect(status().isBadRequest());
//    }
//
//    @ParameterizedTest(name = "{index} when node instance contains uppercase letters {0} should return 400")
//    @MethodSource("invalidUppercaseRequestData")
//    void shouldReturn400ForUppercaseLettersInInstance(String letter) throws Exception {
//        //GIVEN
//        ConnectionTypeResponse connectionType = testDataHelperConnectionType.createConnectionType(TEST_TENANT);
//        RequestBuilder request = testDataHelperNode.createNodeRequest(TEST_TENANT, payload -> {
//            payload.put("instance", letter);
//            payload.put("connectionType", connectionType.getType());
//        });
//        //WHEN
//        ResultActions resultActions = mockMvc.perform(request);
//        //THEN
//        resultActions.andExpect(status().isBadRequest());
//    }
//
//    @ParameterizedTest(name = "{index} when node subtype contains unsafe characters {0} should return 400")
//    @MethodSource("invalidUnsafeCharactersRequestData")
//    void shouldReturn400ForUnsafeCharactersInSubtype(String unsafeChar) throws Exception {
//        //GIVEN
//        ConnectionTypeResponse connectionType = testDataHelperConnectionType.createConnectionType(TEST_TENANT);
//        RequestBuilder request = testDataHelperNode.createNodeRequest(TEST_TENANT, payload -> {
//            payload.put("instance", unsafeChar);
//            payload.put("connectionType", connectionType.getType());
//        });
//        //WHEN
//        ResultActions resultActions = mockMvc.perform(request);
//        //THEN
//        resultActions.andExpect(status().isBadRequest());
//    }
//
//    @ParameterizedTest(name = "{index} when node subtype contains uppercase letters {0} should return 400")
//    @MethodSource("invalidUppercaseRequestData")
//    void shouldReturn400ForUppercaseLettersInSubtype(String letter) throws Exception {
//        //GIVEN
//        ConnectionTypeResponse connectionType = testDataHelperConnectionType.createConnectionType(TEST_TENANT);
//        RequestBuilder request = testDataHelperNode.createNodeRequest(TEST_TENANT, payload -> {
//            payload.put("instance", letter);
//            payload.put("connectionType", connectionType.getType());
//        });
//        //WHEN
//        ResultActions resultActions = mockMvc.perform(request);
//        //THEN
//        resultActions.andExpect(status().isBadRequest());
//    }
//
//    @Test
//    @DisplayName("Should return 400 when invalid type")
//    void shouldReturn400WhenInvalidType() throws Exception {
//        //GIVEN
//        ConnectionTypeResponse connectionType = testDataHelperConnectionType.createConnectionType(TEST_TENANT);
//        RequestBuilder request = testDataHelperNode.createNodeRequest(TEST_TENANT, payload -> {
//            payload.put("type", "unsupported");
//            payload.put("connectionType", connectionType.getType());
//        });
//        //WHEN
//        ResultActions resultActions = mockMvc.perform(request);
//        //THEN
//        resultActions.andExpect(status().isBadRequest());
//    }
//
//    @ParameterizedTest(name = "{index} {0} is {1} value")
//    @MethodSource("invalidRequestDataForRequiredParameters")
//    void shouldReturn400WhenRequiredParameterIsBlank(String parameterName, Object overrideValue) throws Exception {
//        //GIVEN
//        RequestBuilder request = testDataHelperNode.createNodeRequest(TEST_TENANT, payload -> payload.put(parameterName, overrideValue));
//        //WHEN
//        ResultActions resultActions = mockMvc.perform(request);
//        //THEN
//        resultActions.andExpect(status().isBadRequest());
//    }
//
//    private static Stream<Arguments> invalidRequestDataForRequiredParameters() {
//        return Stream.of(
//                Arguments.of("type", null),
//                Arguments.of("type", Named.of("empty", StringUtils.EMPTY)),
//                Arguments.of("subtype", null),
//                Arguments.of("subtype", Named.of("empty", StringUtils.EMPTY)),
//                Arguments.of("name", null),
//                Arguments.of("name", Named.of("empty", StringUtils.EMPTY)),
//                Arguments.of("instance", null),
//                Arguments.of("instance", Named.of("empty", StringUtils.EMPTY)),
//                Arguments.of("location", null),
//                Arguments.of("location", Named.of("empty", StringUtils.EMPTY)),
//                Arguments.of("description", null),
//                Arguments.of("description", Named.of("empty", StringUtils.EMPTY)),
//                Arguments.of("connectionType", null),
//                Arguments.of("connectionType", Named.of("empty", StringUtils.EMPTY))
//        );
//    }
//
//    private static Stream<Arguments> invalidUnsafeCharactersRequestData() {
//        Collection<String> urlUnsafeChars = Arrays.asList(" ", "\"", "<", ">", "#", "%", "{", "}", "|", "\\", "^", "~", "[", "]", "`");
//        return urlUnsafeChars.stream()
//                .map(s -> {
//                    String ran = RandomStringUtils.random(3, true, true).toLowerCase();
//                    return Arguments.of(ran + s + ran);
//                });
//    }
//
//    private static Stream<Arguments> invalidUppercaseRequestData() {
//        Collection<String> upperCaseChars = Arrays.asList("A", "B", "C", "D", "E");
//        return upperCaseChars.stream()
//                .map(s -> {
//                    String ran = RandomStringUtils.random(3, true, true).toLowerCase();
//                    return Arguments.of(ran + s + ran);
//                });
//    }
//}
