package com.jds.neo4j.reactive.neo4jreactive.service;

import com.jds.neo4j.reactive.model.BAR;
import com.jds.neo4j.reactive.model.FOO;
import com.jds.neo4j.reactive.model.Location;
import com.jds.neo4j.reactive.service.FOOBarService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FOOBarServiceMockitoTest {

    public static final Long INVALID_ID = 99L;

    @Test
    public void testCreateFOO() {
        BAR bar1 = BAR.builder().name("bar").createdBy("createdBy").createdDate(LocalDateTime.now()).updatedBy("updatedBy").updatedDate(LocalDateTime.now()).build();
        FOO foo1 = FOO.builder().name("foo").bar(bar1).createdBy("createdBy").createdDate(LocalDateTime.now()).updatedBy("updatedBy").updatedDate(LocalDateTime.now()).build();
        FOOBarService service = mock(FOOBarService.class);
        when(service.createFOO(foo1.getName(), bar1)).thenReturn(Mono.just(foo1));

        Mono<FOO> created = service.createFOO(foo1.getName(), bar1);
        StepVerifier.create(created)
                .expectNextMatches(foo -> foo.getName().equals("foo") && foo.getBar().equals(bar1))
                .verifyComplete();

        verify(service, times(1)).createFOO(foo1.getName(), bar1);
    }

    @Test
    public void testUpdateFOO() {
        BAR bar1 = BAR.builder().name("bar").createdBy("createdBy").createdDate(LocalDateTime.now()).updatedBy("updatedBy").updatedDate(LocalDateTime.now()).build();
        FOO foo1 = FOO.builder().name("foo").bar(bar1).createdBy("createdBy").createdDate(LocalDateTime.now()).updatedBy("updatedBy").updatedDate(LocalDateTime.now()).build();
        FOOBarService service = mock(FOOBarService.class);
        when(service.updateFOO(foo1.getId(), "newName")).thenReturn(Mono.just(foo1.toBuilder().name("newName").build()));

        Mono<FOO> updated = service.updateFOO(foo1.getId(), "newName");
        StepVerifier.create(updated)
                .expectNextMatches(foo -> foo.getName().equals("newName") && foo.getBar().equals(bar1))
                .verifyComplete();

        verify(service, times(1)).updateFOO(foo1.getId(), "newName");
    }

    @Test
    public void updateFOO_givenInvalidId_shouldThrowException() {
        FOOBarService service = mock(FOOBarService.class);
        Mono<Void> deletedFOO = Mono.error(new EmptyResultDataAccessException(1));
        when(service.deleteFOO(INVALID_ID)).thenReturn(deletedFOO);

        Mono<Void> returnedDeletedFOO = service.deleteFOO(INVALID_ID);

        StepVerifier.create(returnedDeletedFOO)
                .expectErrorMatches(throwable -> throwable instanceof EmptyResultDataAccessException
                        && throwable.getMessage().equals("Incorrect result size: expected 1, actual 0"))
                .verify();
    }


    @Test
    public void testCreateLocation() {
        FOOBarService service = mock(FOOBarService.class);

        BAR bar1 = BAR.builder().name("bar").createdBy("createdBy").createdDate(LocalDateTime.now()).updatedBy("updatedBy").updatedDate(LocalDateTime.now()).build();
        FOO foo1 = FOO.builder().name("foo").bar(bar1).createdBy("createdBy").createdDate(LocalDateTime.now()).updatedBy("updatedBy").updatedDate(LocalDateTime.now()).build();
        Location location = Location.builder().name("city1").build();

        when(service.createFOO(foo1.getName(), bar1)).thenReturn(Mono.just(foo1));
        when(service.createLocation(foo1.getId(), location)).thenAnswer(invocation -> {
            foo1.addLocation(location);
            return Mono.just(foo1);
        });

        Mono<FOO> created = service.createFOO(foo1.getName(), bar1);
        StepVerifier.create(created)
                .expectNextMatches(foo -> foo.getName().equals("foo") && foo.getBar().equals(bar1))
                .verifyComplete();

        StepVerifier.create(service.createLocation(foo1.getId(), location))
                .expectNextMatches(updatedFOO -> updatedFOO.getLocations().size() == 1
                        && updatedFOO.getLocations().contains(location))
                .verifyComplete();
    }

}
