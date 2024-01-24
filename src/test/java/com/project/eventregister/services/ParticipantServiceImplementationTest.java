package com.project.eventregister.services;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.project.eventregister.exceptions.EventNotFoundException;
import com.project.eventregister.models.event.Event;
import com.project.eventregister.models.event.EventDTO;
import com.project.eventregister.models.participant.Participant;
import com.project.eventregister.models.participant.ParticipantDTO;
import com.project.eventregister.repositories.EventRepository;
import com.project.eventregister.repositories.ParticipantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.UUIDEditor;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class ParticipantServiceImplementationTest {
  @Autowired
  private EventRepository eventRepository;

  @Autowired
  private ParticipantRepository participantRepository;

  @Autowired
  private EventServiceImplementation eventService;

  @Autowired
  private ParticipantServiceImplementation participantService;


  @BeforeEach
  void setup() {
    eventRepository.deleteAll();
  }

  @Test
  void shouldBeAbleToCreateARegistrationOfAParticipantInAnEvent() {
    var firstName = "Jhon";
    var lastName = "Doe";
    var email = "jhon@gmail.com";

    var requestParticipantCredentials = new ParticipantDTO(firstName, lastName, email);

    var event = createFakeEvent();

    var participantRegistrationInAnEvent = participantService
            .createRegistrationForAnEvent(event.getId(), requestParticipantCredentials);

    assertThat(participantRegistrationInAnEvent).isNotNull();

    assertThat(participantRegistrationInAnEvent.getEvent()).isNotNull();

    var retrievedParticipant = participantService.getById(participantRegistrationInAnEvent.getId());
    assertThat(retrievedParticipant).isPresent();

    assertThat(retrievedParticipant.get().getFirstName()).isEqualTo(firstName);
    assertThat(retrievedParticipant.get().getLastName()).isEqualTo(lastName);
    assertThat(retrievedParticipant.get().getEmail()).isEqualTo(email);

    assertThat(participantRepository.count()).isEqualTo(1);
  }

  @Test
  void shouldNotBeAbleToCreateARegistrationOfAParticipantInAnEventIfEventDoNotExists() {
    var firstName = "Jhon";
    var lastName = "Doe";
    var email = "jhon@gmail.com";

    var requestParticipantCredentials = new ParticipantDTO(firstName, lastName, email);

    var invalidEventFakeId = UUID.randomUUID();

    assertThatThrownBy(() -> {
      participantService.createRegistrationForAnEvent(
              invalidEventFakeId,
              requestParticipantCredentials);
    }).isInstanceOf(EventNotFoundException.class)
            .hasMessageContaining("Event not found.");
  }

  @Test
  void shouldBeAbleToUnregisterAParticipantFromAnEvent() {
    var participantRegistrationInAnEvent = createFakeRegistrationParticipant();

    assertThat(participantRegistrationInAnEvent.getEvent()).isNotNull();

    var canceledParticipantRegistration = participantService
            .cancelRegistrationForAnEvent(participantRegistrationInAnEvent.getId());

    assertThat(canceledParticipantRegistration.getEvent()).isNull();
  }

  private final Event createFakeEvent() {
    String name = "Programming event";
    String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
    var startDate = LocalDate.now();
    var endDate = LocalDate.now().plusDays(4);

    var eventRequestDTO = new EventDTO(name, description, startDate, endDate);

    return eventService.registerANewEvent(eventRequestDTO);
  }

  private final Participant createFakeRegistrationParticipant() {
    var firstName = "Jhon";
    var lastName = "Doe";
    var email = "jhon@gmail.com";

    var requestParticipantCredentials = new ParticipantDTO(firstName, lastName, email);

    var event = createFakeEvent();

    return participantService
            .createRegistrationForAnEvent(event.getId(), requestParticipantCredentials);
  }
}