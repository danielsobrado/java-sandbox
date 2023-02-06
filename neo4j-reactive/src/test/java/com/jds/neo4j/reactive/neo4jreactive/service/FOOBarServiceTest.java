package com.jds.neo4j.reactive.neo4jreactive.service;

import com.jds.neo4j.reactive.model.BAR;
import com.jds.neo4j.reactive.model.FOO;
import com.jds.neo4j.reactive.model.Location;
import com.jds.neo4j.reactive.service.FOOBarService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.Neo4jContainer;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FOOBarServiceTest {

    public static final Long INVALID_ID = 9999L;
    private static Neo4jContainer neo4jContainer;
    @Autowired
    private FOOBarService service;

    @Before
    public void setUp() {
        neo4jContainer.start();
    }

    @After
    public void tearDown() {
        neo4jContainer.stop();
    }

    @Test
    public void testCreateFOO() {
        BAR bar1 = BAR.builder().name("bar").createdBy("createdBy").createdDate(LocalDateTime.now()).updatedBy("updatedBy").updatedDate(LocalDateTime.now()).build();
        FOO foo1 = FOO.builder().name("foo").bar(bar1).createdBy("createdBy").createdDate(LocalDateTime.now()).updatedBy("updatedBy").updatedDate(LocalDateTime.now()).build();

        Mono<FOO> created = service.createFOO(foo1.getName(), bar1);
        StepVerifier.create(created)
                .expectNextMatches(foo -> foo.getName().equals("foo") && foo.getBar().equals(bar1))
                .verifyComplete();
    }

    @Test
    public void testUpdateFOO() {
        BAR bar1 = BAR.builder().name("bar").createdBy("createdBy").createdDate(LocalDateTime.now()).updatedBy("updatedBy").updatedDate(LocalDateTime.now()).build();
        FOO foo1 = FOO.builder().name("foo").bar(bar1).createdBy("createdBy").createdDate(LocalDateTime.now()).updatedBy("updatedBy").updatedDate(LocalDateTime.now()).build();

        Mono<FOO> created = service.createFOO(foo1.getName(), bar1);
        StepVerifier.create(created)
                .expectNextMatches(foo -> foo.getName().equals("foo") && foo.getBar().equals(bar1))
                .verifyComplete();

        Mono<FOO> updated = service.updateFOO(foo1.getId(), "newName");
        StepVerifier.create(updated)
                .expectNextMatches(foo -> foo.getName().equals("newName") && foo.getBar().equals(bar1))
                .verifyComplete();
    }

    @Test
    public void updateFOO_givenInvalidId_shouldThrowException() {
        Mono<Void> deletedFOO = service.deleteFOO(INVALID_ID);

        StepVerifier.create(deletedFOO)
                .expectErrorMatches(throwable -> throwable instanceof EmptyResultDataAccessException
                        && Objects.requireNonNull(throwable.getMessage()).contains("No class com.jds.neo4j.reactive.model.FOO entity with id " + INVALID_ID + " exists!"))
                .verify();
    }

    @Test
    public void testCreateLocation() {

        BAR bar1 = BAR.builder().name("bar").createdBy("createdBy").createdDate(LocalDateTime.now()).updatedBy("updatedBy").updatedDate(LocalDateTime.now()).build();
        FOO foo1 = FOO.builder().name("foo").bar(bar1).createdBy("createdBy").createdDate(LocalDateTime.now()).updatedBy("updatedBy").updatedDate(LocalDateTime.now()).build();
        Location location = Location.builder().name("city1").build();

        Mono<FOO> created = service.createFOO(foo1.getName(), bar1);
        StepVerifier.create(created)
                .expectNextMatches(foo -> foo.getName().equals("foo") && foo.getBar().equals(bar1))
                .verifyComplete();

        StepVerifier.create(service.createLocation(foo1.getId(), location))
                .expectNextMatches(updatedFOO -> updatedFOO.getLocations().size() == 1
                        && updatedFOO.getLocations().contains(location))
                .verifyComplete();
    }

    static class TestContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            neo4jContainer = new Neo4jContainer<>("neo4j:5").withoutAuthentication();
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
