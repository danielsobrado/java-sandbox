package com.jds.neo4j.reactive.model;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Node;

import java.time.LocalDateTime;


@Node
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BAR {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @Relationship(type = "CHILD_OF")
    private FOO foo;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;
}