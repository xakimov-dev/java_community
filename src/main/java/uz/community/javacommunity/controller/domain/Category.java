package uz.community.javacommunity.controller.domain;

import com.simba.cassandra.shaded.datastax.driver.core.DataType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;
import uz.community.javacommunity.controller.domain.keys.CategoryKey;

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
}
