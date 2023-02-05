package com.jds.neo4j.reactive.service;


import com.jds.neo4j.reactive.model.BAR;
import com.jds.neo4j.reactive.model.FOO;
import com.jds.neo4j.reactive.model.Location;
import com.jds.neo4j.reactive.repository.FOOBarRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Service
public class FOOBarService {
    private final FOOBarRepository repository;

    public FOOBarService(FOOBarRepository repository) {

        this.repository = repository;
    }

    public Mono<FOO> createFOO(Long id, String name, BAR bar) {
        return repository.save(FOO.builder().name(name).bar(bar).build());

    }

    public Mono<FOO> updateFOO(Long id, String name) {
        return repository.findById(id).map(foo -> {
            foo.setName(name);
            return foo;
        }).flatMap(repository::save);
    }

    public Mono<Void> deleteFOO(Long id) {

        return repository.deleteById(id);
    }

    public Flux<FOO> findFOOsByBarName(String name) {

        return repository.findByBarName(name);
    }

    public Mono<FOO> createLocation(Long id, Location location) {
        return repository.findById(id)
                .map(foo -> {
                    foo.getLocations().add(location);
                    return foo;
                })
                .flatMap(repository::save);
    }
}