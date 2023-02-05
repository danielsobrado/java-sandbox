package com.jds.neo4j.reactive.neo4jreactive.repository;

import com.jds.neo4j.reactive.model.BAR;
import com.jds.neo4j.reactive.model.FOO;
import com.jds.neo4j.reactive.repository.FOOBarRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.Neo4jContainer;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataNeo4jTest
@RunWith(SpringRunner.class)
@Transactional(propagation = Propagation.NEVER)
@ContextConfiguration(initializers = FOOBarRepositoryTest.TestContainerInitializer.class)
public class FOOBarRepositoryTest {

    static class TestContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            final Neo4jContainer<?> neo4jContainer = new Neo4jContainer<>("neo4j:5").withoutAuthentication();
            neo4jContainer.start();
            configurableApplicationContext
                    .addApplicationListener((ApplicationListener<ContextClosedEvent>) event -> neo4jContainer.stop());
            TestPropertyValues
                    .of(
                            "spring.neo4j.uri=" + neo4jContainer.getBoltUrl(),
                            "spring.neo4j.authentication.username=neo4j",
                            "spring.neo4j.authentication.password=" + neo4jContainer.getAdminPassword()
                    )
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Autowired
    private FOOBarRepository repository;

    @BeforeEach
    public void setup() throws IOException {
        this.repository.deleteAll()
                .then()
                .thenMany(testSaveMethod())
                .then()
                .thenMany(testFoundMethod()).log()
                .blockLast(Duration.ofSeconds(5));// to make the tests work
    }

    private Flux<FOO> testSaveMethod() {
        var data = Stream.of("FOO one", "FOO two")
                .map(name -> FOO.builder().name(name).build())
                .collect(Collectors.toList());
        return Flux.fromIterable(data)
                .flatMap(it -> this.repository.save(it));
    }

    private Flux<FOO> testFoundMethod() {
        return this.repository
                .findAll(Example.of(FOO.builder().name("FOO one").build()));
    }

    @Test
    void testAllFOOs() {
        repository.findAll().sort(Comparator.comparing(FOO::getName))
                .as(StepVerifier::create)
                .consumeNextWith(p -> assertEquals("FOO one", p.getName()))
                .consumeNextWith(p -> assertEquals("FOO two", p.getName()))
                .verifyComplete();
    }

    @Test
    public void testFindByName() {
        FOO foo = FOO.builder().name("foo1").build();
        repository.save(foo).block();

        StepVerifier.create(repository.findByName("foo1"))
                .expectNext(foo)
                .verifyComplete();
    }

    @Test
    public void testFindByBarName() {
        FOO foo1 = FOO.builder().name("foo1").build();
        FOO foo2 = FOO.builder().name("foo2").bar(BAR.builder().name("bar1").build()).build();
        repository.saveAll(Arrays.asList(foo1, foo2)).blockLast();

        StepVerifier.create(repository.findByBarName("bar1"))
                .expectNext(foo2)
                .verifyComplete();
    }

}

