package uz.community.javacommunity.controller.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

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
    UUID id;
    String name;
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
}
