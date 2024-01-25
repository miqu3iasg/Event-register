package com.project.eventregister.services;

import com.project.eventregister.exceptions.EventNotFoundException;
import com.project.eventregister.dtos.EventDTO;
import com.project.eventregister.dtos.EventResponseDTO;
import com.project.eventregister.dtos.ParticipantDTO;
import com.project.eventregister.dtos.ParticipantResponseDTO;
import com.project.eventregister.repositories.EventRepository;
import com.project.eventregister.repositories.ParticipantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

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

    assertThat(participantRegistrationInAnEvent.get().getEvent()).isNotNull();

    assertThat(participantRegistrationInAnEvent).isNotNull();

    var retrievedParticipant = participantService.getById(participantRegistrationInAnEvent.get().getId());
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

    assertThat(participantRegistrationInAnEvent.get().getEvent()).isNotNull();

    var canceledParticipantRegistration = participantService
            .cancelRegistrationForAnEvent(participantRegistrationInAnEvent.get().getId());

    assertThat(canceledParticipantRegistration.getEvent()).isNull();
  }

  @Test
  void shouldBeAbleToUpdateParticipantCredentials() {
    var newParticipantFirstName = "Tom";
    var newParticipantLastName = "Cruise";
    var newParticipantEmail = "cruise@gmail.com";

    var newParticipantCredentials = new ParticipantDTO(
            newParticipantFirstName,
            newParticipantLastName,
            newParticipantEmail);

    var participantCredentials = createFakeRegistrationParticipant();

    var updatedParticipantCredentials = participantService.updateParticipantCredentials(
            participantCredentials.get().getId(),
            newParticipantCredentials);

    assertThat(updatedParticipantCredentials.get().getFirstName()).isEqualTo(newParticipantFirstName);
    assertThat(updatedParticipantCredentials.get().getLastName()).isEqualTo(newParticipantLastName);
    assertThat(updatedParticipantCredentials.get().getEmail()).isEqualTo(newParticipantEmail);
  }

  private final EventResponseDTO createFakeEvent() {
    String name = "Programming event";
    String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
    var startDate = LocalDate.now();
    var endDate = LocalDate.now().plusDays(4);

    var eventRequestDTO = new EventDTO(name, description, startDate, endDate);

    return eventService.registerANewEvent(eventRequestDTO);
  }

  private final Optional<ParticipantResponseDTO> createFakeRegistrationParticipant() {
    var firstName = "Jhon";
    var lastName = "Doe";
    var email = "jhon@gmail.com";

    var requestParticipantCredentials = new ParticipantDTO(firstName, lastName, email);

    var event = createFakeEvent();

    return participantService
            .createRegistrationForAnEvent(event.getId(), requestParticipantCredentials);
  }
}