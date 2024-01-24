package com.project.eventregister.services;

import com.project.eventregister.models.event.EventDTO;
import com.project.eventregister.repositories.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class EventServiceImplementationTest {
  @Mock
  private EventRepository eventRepository;

  @InjectMocks
  private EventServiceImplementation service;

  @BeforeEach
  void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void shouldBeAbleToCreateANewEvent() {
    String name = "Programming event";
    String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed ut gravida quam. Nullam eget ullamcorper sapien. Integer eu justo nec felis fermentum rhoncus.";
    var startDate = LocalDate.now();
    var endDate = LocalDate.now().plusDays(4);

    var eventRequestDTO = new EventDTO(name, description, startDate, endDate);

    var event = service.registerANewEvent(eventRequestDTO);

    if(event != null) {
      var eventExists = service.getById(event.getId());
      assertThat(eventExists.isPresent()).isTrue();
    }
  }

  @Test
  void shouldNotBeAbleToCreateANewEventWithInvalidDateRange() {
  }

  @Test
  void shouldBeAbleToGetAEventsBetweenTheSpecifiedDates() {
  }
}