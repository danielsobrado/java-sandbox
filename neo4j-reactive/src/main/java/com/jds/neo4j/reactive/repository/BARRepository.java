package com.jds.neo4j.reactive.repository;

import com.jds.neo4j.reactive.model.BAR;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BARRepository extends ReactiveNeo4jRepository<BAR, Long> {
    Mono<BAR> findByName(String name);

}
