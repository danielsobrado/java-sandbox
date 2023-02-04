package com.jds.neo4j.reactive.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Node;


@Data
@Node
public class BAR {
    private Long id;
    private String name;

    @Relationship(type = "CHILD_OF")
    private FOO foo;
}