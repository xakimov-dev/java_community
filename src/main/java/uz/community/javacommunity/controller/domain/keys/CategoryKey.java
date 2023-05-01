package uz.community.javacommunity.controller.domain.keys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.util.UUID;

@Data
@AllArgsConstructor
@PrimaryKeyClass
public class CategoryKey {

    @PrimaryKeyColumn(name = "id", ordinal = 0, type =
            PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID id;

    @PrimaryKeyColumn(name = "name", ordinal = 1, type =
            PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.TEXT)
    private String name;
}
