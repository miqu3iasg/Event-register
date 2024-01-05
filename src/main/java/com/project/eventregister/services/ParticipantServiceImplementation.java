package com.project.eventregister.services;

import com.project.eventregister.dtos.ParticipantDTO;
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
            .orElseThrow(RuntimeException::new);
  }

  @Override
  public void unregisterAParticipant(UUID participantId) {
    Optional<Participant> participantExists = Optional.ofNullable(participantRepository.findById(participantId)
            .orElseThrow(RuntimeException::new));

    if(participantExists.isPresent()) participantRepository.deleteById(participantId);
  }

  @Override
  public void updateParticipantCredentials(UUID participantId, ParticipantDTO participantCredentials) {
    Optional<Participant> participantExists = Optional.ofNullable(participantRepository.findById(participantId)
            .orElseThrow(RuntimeException::new));

    participantExists.get().setFirstName(participantCredentials.firstName());
    participantExists.get().setLastName(participantCredentials.lastName());
    participantExists.get().setEmail(participantCredentials.email());

    participantExists.get().setUpdatedAt(LocalDateTime.now());

    var participantWithUpdatedCredentials = participantExists.get();

    BeanUtils.copyProperties(participantExists, participantWithUpdatedCredentials);

    participantRepository.save(participantWithUpdatedCredentials);
  }

  @Override
  public void createRegistrationForAnEvent(UUID eventId, ParticipantDTO participantCredentials) {
    Optional<Event> eventExists = eventRepository.findById(eventId);

    if(eventExists.isEmpty()) throw new RuntimeException("This event not exists.");

    var participant = new Participant(participantCredentials.firstName(), participantCredentials.lastName(), participantCredentials.email());

    var event = eventExists.get();

    participant.setEvent(event);
    participant.setCreatedAt(LocalDateTime.now());
    participant.setUpdatedAt(LocalDateTime.now());

    participantRepository.save(participant);
  }

  @Override
  public void cancelRegistrationForAnEvent(UUID participantId) {
    Optional<Participant> participantExists = Optional.ofNullable(participantRepository.findById(participantId)
            .orElseThrow(RuntimeException::new));

    var participant = participantExists.get();
    participant.setEvent(null);

    participantRepository.save(participant);
  }
}
