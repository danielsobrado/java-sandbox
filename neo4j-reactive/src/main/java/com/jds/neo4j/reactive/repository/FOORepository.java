package com.jds.neo4j.reactive.repository;

import com.jds.neo4j.reactive.model.FOO;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface FOORepository extends ReactiveNeo4jRepository<FOO, Long> {
    Mono<FOO> findByName(String name);
    Flux<FOO> findByBarName(String name);
}
