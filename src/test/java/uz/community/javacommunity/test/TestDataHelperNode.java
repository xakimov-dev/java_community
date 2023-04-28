package uz.community.javacommunity.test;

import com.qad.noderegistry.common.JsonConverter;
import com.qad.noderegistry.node.controller.dto.*;
import com.qad.noderegistry.node.domain.enums.NodeType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
@Profile("functionalTest")
@RequiredArgsConstructor
public class TestDataHelperNode {
    private static final String BASE_PATH = "/nodes/{tenantId}";
    private static final String SINGLE_RECORD_PATH = "/nodes/{tenantId}/{uri}";
    private final MockMvc mockMvc;
    private final JsonConverter jsonConverter;
    private final AtomicInteger nodeCounter = new AtomicInteger(1);

    public RequestBuilder createNodeRequest(String tenant) {
        return createNodeRequest(tenant, null);
    }

    public RequestBuilder createNodeRequest(String tenant, @Nullable Consumer<Map<String, Object>> payloadModifier) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", "Europe ERP");
        payload.put("type", "application");
        payload.put("subtype", "erp");
        payload.put("instance", "europe");
        payload.put("description", "Primary European ERP instance");
        payload.put("location", "AWS");
        payload.put("connectionType", "QADERP");
        payload.put("connectionDetails", "");
        if (payloadModifier != null) {
            payloadModifier.accept(payload);
        }
        return post(BASE_PATH, tenant).contentType(MediaType.APPLICATION_JSON).content(jsonConverter.convertToString(payload));
    }

    public NodeResponse createNode(String tenant) {
        return createNode(tenant, null);
    }

    @SneakyThrows
    public NodeResponse createNode(String tenant, @Nullable Consumer<Map<String, Object>> payloadModifier) {
        RequestBuilder request = createNodeRequest(tenant, payloadModifier);
        String jsonResponse = mockMvc.perform(request).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        return jsonConverter.convertFromString(jsonResponse, NodeResponse.class);
    }

    public List<NodeResponse> createNode(String tenant, String connectionType, int amount) {
        return IntStream.range(0, amount).mapToObj(i -> createNode(tenant, payload -> {
                    payload.put("name", "Europe ERP." + nodeCounter.getAndIncrement());
                    payload.put("instance", "europe" + nodeCounter.get());
                    payload.put("connectionType", "" + connectionType);
                }))
                .collect(Collectors.toList());
    }

    public RequestBuilder listAllNodeRequest(String tenant) {
        return get(BASE_PATH, tenant);
    }

    public NodeCreateRequest createNodeRequestDTO(String connectionType) {
        NodeCreateRequest nodeCreateRequest
                = new NodeCreateRequest();
        nodeCreateRequest.setName("Europe ERP");
        nodeCreateRequest.setType(NodeType.APPLICATION);
        nodeCreateRequest.setInstance("europe");
        nodeCreateRequest.setSubtype("erp");
        nodeCreateRequest.setDescription("Primary European ERP instance");
        nodeCreateRequest.setLocation("AWS");
        nodeCreateRequest.setConnectionType(connectionType);
        return nodeCreateRequest;
    }

    public ConnectionTypeCreateRequest connectionTypeCreateRequestDTO(String type) {
        ConnectionTypeCreateRequest connectionTypeCreateRequest
                = new ConnectionTypeCreateRequest();
        connectionTypeCreateRequest.setDescription("QAD ERP Adaptive UX");
        connectionTypeCreateRequest.setType(type);
        return connectionTypeCreateRequest;
    }
    public RequestBuilder updateEndpointRequest(String tenantId, String uri, @Nullable Consumer<Map<String, Object>> payloadModifier) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", "UK");
        payload.put("description", "UK Domain");
        payload.put("connectionType", "QADERP");
        payload.put("connectionDetails", "");
        if (payloadModifier != null) {
            payloadModifier.accept(payload);
        }

        return put(SINGLE_RECORD_PATH, tenantId, uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.convertToString(payload));
    }

    public RequestBuilder updateEndpointRequest(String tenantId, String uri) {
        return updateEndpointRequest(tenantId, uri, null);
    }

    public RequestBuilder deleteNodeRequest(String tenantId, String uri) {
        return delete(SINGLE_RECORD_PATH, tenantId, uri);
    }
    public RequestBuilder getSingleNodeWithEndpointsOrEndpoint(String tenantId, String uri) {
        return get(SINGLE_RECORD_PATH, tenantId, uri);
    }

    public RequestBuilder updateNodeRequest(String tenantId, String uri, @Nullable Consumer<Map<String, Object>> payloadModifier) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", "North America ERP");
        payload.put("description", "North American ERP instance");
        payload.put("location", "GCS");
        payload.put("connectionType", "QADERP");
        payload.put("connectionDetails", "{\"url\": \"https:\\\\dev1.com\\qaderp\"}");
        if (payloadModifier != null) {
            payloadModifier.accept(payload);
        }

        return put(SINGLE_RECORD_PATH, tenantId, uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.convertToString(payload));
    }

    public RequestBuilder updateNodeRequest(String tenantId, String uri) {
        return updateNodeRequest(tenantId, uri, null);
    }
}
