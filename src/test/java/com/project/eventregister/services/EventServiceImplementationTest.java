package com.project.eventregister.services;

import com.project.eventregister.exceptions.EventNotFoundException;
import com.project.eventregister.exceptions.InvalidDateRangeException;
import com.project.eventregister.models.event.Event;
import com.project.eventregister.models.event.EventDTO;
import com.project.eventregister.repositories.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.InvalidIsolationLevelException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class EventServiceImplementationTest {
  @Autowired
  private EventRepository eventRepository;

  @Autowired
  private EventServiceImplementation service;

  @BeforeEach
  void setup() {
    eventRepository.deleteAll();
  }

  @Test
  void shouldBeAbleToCreateANewEvent() {
    String name = "Programming event";
    String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
    var startDate = LocalDate.now();
    var endDate = LocalDate.now().plusDays(4);

    var eventRequestDTO = new EventDTO(name, description, startDate, endDate);

    var createdEvent = service.registerANewEvent(eventRequestDTO);

    assertThat(createdEvent).isNotNull();

    var retrievedEvent = service.getById(createdEvent.getId());
    assertThat(retrievedEvent).isPresent();

    assertThat(retrievedEvent.get().getName()).isEqualTo(name);
    assertThat(retrievedEvent.get().getDescription()).isEqualTo(description);
    assertThat(retrievedEvent.get().getStartDate()).isEqualTo(startDate);
    assertThat(retrievedEvent.get().getEndDate()).isEqualTo(endDate);

    assertThat(eventRepository.count()).isEqualTo(1);
  }

  @Test
  void shouldNotBeAbleToCreateANewEventWithInvalidDateRange() {
    String name = "Programming event";
    String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
    var startDate = LocalDate.now();
    var endDateEqual = startDate;
    var endDateBefore = startDate.minusDays(4);

    assertThatThrownBy(() -> {
      var invalidEventRequestDTO = new EventDTO(name, description, startDate, endDateEqual);
      service.registerANewEvent(invalidEventRequestDTO);
    }).isInstanceOf(InvalidDateRangeException.class)
            .hasMessageContaining("Invalid date range.");

    assertThatThrownBy(() -> {
      var invalidEventRequestDTO = new EventDTO(name, description, startDate, endDateBefore);
      service.registerANewEvent(invalidEventRequestDTO);
    }).isInstanceOf(InvalidDateRangeException.class).hasMessageContaining("Invalid date range.");
  }

  @Test
  void shouldBeAbleToGetAEventsBetweenTheSpecifiedDates() {
    String name = "Programming event";
    String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
    var eventStartDate = LocalDate.now();
    var eventEndDate = LocalDate.now().plusDays(14);

    var eventRequestDTO = new EventDTO(name, description, eventStartDate, eventEndDate);

    var createdEvent = service.registerANewEvent(eventRequestDTO);

    assertThatCode(() -> {
      service.findEventsBetweenDates(eventStartDate, eventEndDate);
    }).doesNotThrowAnyException();
  }
}