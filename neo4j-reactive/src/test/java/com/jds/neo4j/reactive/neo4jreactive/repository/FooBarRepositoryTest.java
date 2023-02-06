package com.jds.neo4j.reactive.neo4jreactive.repository;

import com.jds.neo4j.reactive.model.BAR;
import com.jds.neo4j.reactive.model.FOO;
import com.jds.neo4j.reactive.repository.BARRepository;
import com.jds.neo4j.reactive.repository.FOORepository;
import com.jds.neo4j.reactive.repository.LocationRepository;
import org.apache.commons.compress.utils.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.Neo4jContainer;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataNeo4jTest
@RunWith(SpringRunner.class)
@Transactional(propagation = Propagation.NEVER)
@ContextConfiguration(initializers = FooBarRepositoryTest.TestContainerInitializer.class)
public class FooBarRepositoryTest {

    private static Neo4jContainer neo4jContainer;

    @Autowired
    private FOORepository fooRepository;

    @Autowired
    private BARRepository barRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Before
    public void setUp() {
        neo4jContainer.start();
    }

    @After
    public void tearDown() {
        neo4jContainer.stop();
    }

    @BeforeEach
    public void setup() throws IOException {
        this.fooRepository.deleteAll()
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
                .flatMap(it -> this.fooRepository.save(it));
    }

    private Flux<FOO> testFoundMethod() {
        return this.fooRepository
                .findAll(Example.of(FOO.builder().name("FOO one").build()));
    }

    @Test
    void testAllFOOs() {
        fooRepository.findAll().sort(Comparator.comparing(FOO::getName))
                .as(StepVerifier::create)
                .consumeNextWith(p -> assertEquals("FOO one", p.getName()))
                .consumeNextWith(p -> assertEquals("FOO two", p.getName()))
                .verifyComplete();
    }

    @Test
    public void testFindByName() {
        FOO fooFind = FOO.builder().name("fooFind").bar(BAR.builder().name("bar").build()).createdBy("createdBy").createdDate(LocalDateTime.now()).updatedBy("updatedBy").updatedDate(LocalDateTime.now()).build();
        fooRepository.save(fooFind).block();

        StepVerifier.create(fooRepository.findByName("fooFind"))
                .assertNext(foo -> {
                    assertEquals("fooFind", foo.getName());
                })
                .verifyComplete();
    }

    @Test
    public void testFindByBarName() {
        BAR bar1 = BAR.builder().name("bar1").build();
        barRepository.save(bar1).block();

        FOO foo1 = FOO.builder().name("foo1").locations(Lists.newArrayList()).createdBy("createdBy").createdDate(LocalDateTime.now()).updatedBy("updatedBy").updatedDate(LocalDateTime.now()).build();
        FOO foo2 = FOO.builder().name("foo2").locations(Lists.newArrayList()).bar(bar1).createdBy("createdBy").createdDate(LocalDateTime.now()).updatedBy("updatedBy").updatedDate(LocalDateTime.now()).build();
        fooRepository.saveAll(Arrays.asList(foo1, foo2)).blockLast();

        StepVerifier.create(fooRepository.findByBarName("bar1"))
                .assertNext(foo -> {
                    assertEquals("foo2", foo.getName());
                    assertEquals("bar1", foo.getBar().getName());
                })
                .verifyComplete();
    }

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

}

