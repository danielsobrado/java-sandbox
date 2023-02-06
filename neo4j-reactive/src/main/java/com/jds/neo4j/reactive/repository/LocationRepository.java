package com.jds.neo4j.reactive.repository;

import com.jds.neo4j.reactive.model.Location;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface LocationRepository extends ReactiveNeo4jRepository<Location, Long> {
    Mono<Location> findByName(String name);
}
