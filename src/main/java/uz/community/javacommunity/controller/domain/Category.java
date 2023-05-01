package uz.community.javacommunity.controller.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table("category")
public class Category {

    @PrimaryKey
    CategoryKey categoryKey;
    @Column("parent_id")
    UUID parentId;
    @Column("created_by")
    String createdBy;
    @Column("created_date")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    Instant createdDate;
    @Column("modified_by")
    String modifiedBy;
    @Column("modified_date")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    Instant modifiedDate;

    @Data
    @Builder
    @PrimaryKeyClass
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CategoryKey {

        @PrimaryKeyColumn(name = "id", ordinal = 0, type =
                PrimaryKeyType.PARTITIONED)
        @CassandraType(type = CassandraType.Name.UUID)
        private UUID id;

        @PrimaryKeyColumn(name = "name", ordinal = 1, type =
                PrimaryKeyType.CLUSTERED)
        @CassandraType(type = CassandraType.Name.TEXT)
        private String name;

        public static CategoryKey of(UUID id, String name){
            return new CategoryKey(id, name);
        }
    }
}
