package uz.community.javacommunity.controller.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("user")
public class User {
    @PrimaryKey
    private String username;
    @Frozen
    private Set<String> roles;

    private int age;
    private String info;
    @Column("image_url")
    private String imageUrl;
}
