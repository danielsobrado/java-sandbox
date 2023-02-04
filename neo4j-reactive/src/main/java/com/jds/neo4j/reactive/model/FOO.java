package com.jds.neo4j.reactive.model;


import lombok.AllArgsConstructor;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import lombok.Data;

@Data
@Node
public class FOO {
    private Long id;
    private String name;

    @Relationship(type = "PARENT_OF")
    private BAR bar;

    public FOO(Long id, String name, BAR bar) {
    }
}