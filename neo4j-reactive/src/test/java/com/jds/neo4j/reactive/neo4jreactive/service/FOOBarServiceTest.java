package com.jds.neo4j.reactive.neo4jreactive.service;

import com.jds.neo4j.reactive.model.BAR;
import com.jds.neo4j.reactive.model.FOO;
import com.jds.neo4j.reactive.model.Location;
import com.jds.neo4j.reactive.service.FOOBarService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.Neo4jContainer;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FOOBarServiceTest {

    private static Neo4jContainer neo4jContainer;

    private static Driver driver;

    private static Location location;

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

             location = Location.builder().name("city1").build();

            try (Session session = driver.session()) {
                session.run("CREATE (:Location {name: $name})", Values.parameters("name", location.getName()));
            }
        }
    }

    @Autowired
    private FOOBarService service;

    private BAR bar;
    private FOO foo;

    @Before
    public void setUp() {
        neo4jContainer.start();
        bar = BAR.builder().name("bar").createdBy("createdBy").createdDate(LocalDateTime.now()).updatedBy("updatedBy").updatedDate(LocalDateTime.now()).build();
        foo = FOO.builder().name("foo").bar(bar).createdBy("createdBy").createdDate(LocalDateTime.now()).updatedBy("updatedBy").updatedDate(LocalDateTime.now()).build();
    }

    @After
    public void tearDown() {
        neo4jContainer.stop();
    }

    @Test
    public void testCreateFOO() {
        Mono<FOO> created = service.createFOO(foo.getId(), foo.getName(), bar);
        StepVerifier.create(created)
                .expectNextMatches(foo -> foo.getName().equals("foo") && foo.getBar().equals(bar))
                .verifyComplete();
    }

    @Test
    public void testUpdateFOO() {
        Mono<FOO> created = service.createFOO(foo.getId(), foo.getName(), bar);
        StepVerifier.create(created)
                .expectNextMatches(foo -> foo.getName().equals("foo") && foo.getBar().equals(bar))
                .verifyComplete();

        Mono<FOO> updated = service.updateFOO(foo.getId(), "newName");
        StepVerifier.create(updated)
                .expectNextMatches(foo -> foo.getName().equals("newName") && foo.getBar().equals(bar))
                .verifyComplete();
    }

    @Test
    public void testCreateLocation() {
        Mono<FOO> created = service.createFOO(foo.getId(), foo.getName(), bar);
        StepVerifier.create(created)
                .expectNextMatches(foo -> foo.getName().equals("foo") && foo.getBar().equals(bar))
                .verifyComplete();

        StepVerifier.create(service.createLocation(foo.getId(), location))
                .expectNextMatches(updatedFOO -> updatedFOO.getLocations().size() == 1
                        && updatedFOO.getLocations().contains(location))
                .verifyComplete();
    }

}
