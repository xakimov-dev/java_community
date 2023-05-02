package uz.community.javacommunity;

import com.datastax.oss.driver.api.core.CqlSession;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.lifecycle.Startables;

import uz.community.javacommunity.common.JsonConverter;
import uz.community.javacommunity.common.controller.handler.pojo.FieldErrorResponse;
import uz.community.javacommunity.controller.article.data.TestDataHelperArticle;
import uz.community.javacommunity.controller.category.data.TestDataHelperCategory;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Slf4j
@WithAuthentication
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("functionalTest")
@AutoConfigureMockMvc
public abstract class CommonIntegrationTest {

    @Autowired
    protected TestDataHelperUser testDataHelperUser;
    @Autowired
    protected TestDataHelperArticle testDataHelperArticle;
    @Autowired
    protected TestDataHelperCategory testDataHelperCategory;
    private static final String IMAGE_NAME = "cassandra:3.11.2";
    private static final String KEYSPACE_NAME = "java_community";
    private static final CassandraContainer<?> cassandra;

    @Autowired
    protected ObjectMapper objectMapper;

    static {
        //noinspection rawtypes
        cassandra = (CassandraContainer<?>) new CassandraContainer(IMAGE_NAME)
                .withInitScript("cql/create-keyspace.cql")
                .withEnv("MAX_HEAP_SIZE", "256M")
                .withEnv("HEAP_NEWSIZE", "128M")
                .withReuse(true);

        Startables.deepStart(cassandra).join();
    }

    @Autowired
    protected CqlSession cqlSession;
    @Autowired
    protected CassandraOperations cassandraTemplate;
    @Autowired
    protected TestCassandraScriptUtils testCassandraScriptUtils;
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected JsonConverter jsonConverter;

    @AfterEach
    void testDataCleanUp() {
        Instant cleanUpStart = Instant.now();
        testCassandraScriptUtils.execute("truncate user");
        testCassandraScriptUtils.execute("truncate login");
        testCassandraScriptUtils.execute("truncate category");
        Instant cleanUpEnd = Instant.now();
        long durationSecs = Duration.between(cleanUpStart, cleanUpEnd).toMillis();
        if (durationSecs > 1500) {
            log.warn("DB truncate took: {} millis", durationSecs);
        } else {
            log.info("DB truncate took: {} millis", durationSecs);
        }
    }

    @DynamicPropertySource
    static void setupCassandraConnectionProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.cassandra.local-datacenter", () -> "datacenter1");
        registry.add("spring.data.cassandra.schema-action", () -> String.valueOf(SchemaAction.NONE));
        registry.add("spring.data.cassandra.keyspace-name", () -> KEYSPACE_NAME);
        registry.add("spring.data.cassandra.contact-points", cassandra::getHost);
        registry.add("spring.data.cassandra.port", () -> String.valueOf(cassandra.getMappedPort(CassandraContainer.CQL_PORT)));
    }

    @SneakyThrows
    protected List<FieldErrorResponse> readErrors(MvcResult mvcResult) {
        return jsonConverter.convertFromString(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
    }
}
