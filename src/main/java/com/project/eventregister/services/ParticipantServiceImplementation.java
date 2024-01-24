package com.project.eventregister.services;

import com.project.eventregister.models.participant.ParticipantDTO;
import com.project.eventregister.exceptions.EventNotFoundException;
import com.project.eventregister.exceptions.ParticipantNotFoundException;
import com.project.eventregister.models.event.Event;
import com.project.eventregister.models.participant.Participant;
import com.project.eventregister.repositories.EventRepository;
import com.project.eventregister.repositories.ParticipantRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParticipantServiceImplementation implements ParticipantService {
  final ParticipantRepository participantRepository;
  final EventRepository eventRepository;

  public ParticipantServiceImplementation(ParticipantRepository participantRepository, EventRepository eventRepository) {
    this.participantRepository = participantRepository;
    this.eventRepository = eventRepository;
  }

  @Override
  public List<Participant> getAllParticipants() {
    return participantRepository.findAll();
  }

  @Override
  public Participant getJustOneParticipant(UUID participantId) {
    return participantRepository.findById(participantId)
            .orElseThrow(ParticipantNotFoundException::new);
  }

  @Override
  public void unregisterAParticipant(UUID participantId) {
    var participantExists = participantRepository.findById(participantId)
            .orElseThrow(ParticipantNotFoundException::new);

    participantRepository.deleteById(participantId);
  }

  @Override
  public Participant updateParticipantCredentials(UUID participantId, ParticipantDTO requestParticipantCredentials) {
    Optional<Participant> participantExists = Optional.ofNullable(participantRepository.findById(participantId)
            .orElseThrow(ParticipantNotFoundException::new));

    participantExists.get().setFirstName(requestParticipantCredentials.firstName());
    participantExists.get().setLastName(requestParticipantCredentials.lastName());
    participantExists.get().setEmail(requestParticipantCredentials.email());

    participantExists.get().setUpdatedAt(LocalDateTime.now());

    var participantWithUpdatedCredentials = participantExists.get();

    BeanUtils.copyProperties(requestParticipantCredentials, participantWithUpdatedCredentials);

    return participantRepository.save(participantWithUpdatedCredentials);
  }

  @Override
  public Participant createRegistrationForAnEvent(UUID eventId, ParticipantDTO requestParticipantCredentials) {
    Optional<Event> eventExists = Optional.ofNullable(eventRepository.findById(eventId)
            .orElseThrow(EventNotFoundException::new));

    var participant = new Participant(
            requestParticipantCredentials.firstName(),
            requestParticipantCredentials.lastName(),
            requestParticipantCredentials.email());

    var event = eventExists.get();

    participant.setEvent(event);
    participant.setCreatedAt(LocalDateTime.now());
    participant.setUpdatedAt(LocalDateTime.now());

    return participantRepository.save(participant);
  }

  @Override
  public void cancelRegistrationForAnEvent(UUID participantId) {
    Optional<Participant> participantExists = Optional.ofNullable(participantRepository.findById(participantId)
            .orElseThrow(ParticipantNotFoundException::new));

    var participant = participantExists.get();
    participant.setEvent(null);

    participantRepository.save(participant);
  }
}
