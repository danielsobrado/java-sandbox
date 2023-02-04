package com.jds.neo4j.reactive.service;


import com.jds.neo4j.reactive.model.BAR;
import com.jds.neo4j.reactive.model.FOO;
import com.jds.neo4j.reactive.repository.FOOBarRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FOOBarService {
    private final FOOBarRepository repository;

    public FOOBarService(FOOBarRepository repository) {
        this.repository = repository;
    }

    public Mono<FOO> createFOO(Long id, String name, BAR bar) {
        return repository.save(new FOO(id, name, bar));
    }

    public Mono<FOO> updateFOO(Long id, String name) {
        return repository.findById(id)
                .map(foo -> {
                    foo.setName(name);
                    return foo;
                })
                .flatMap(repository::save);
    }

    public Mono<Void> deleteFOO(Long id) {
        return repository.deleteById(id);
    }

    public Flux<FOO> findFOOsByBarName(String name) {
        return repository.findByBarName(name);
    }
}