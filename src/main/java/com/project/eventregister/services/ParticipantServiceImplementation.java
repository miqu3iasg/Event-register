package com.project.eventregister.services;

import com.project.eventregister.dtos.ParticipantDTO;
import com.project.eventregister.exceptions.EventNotFoundException;
import com.project.eventregister.exceptions.ParticipantNotFoundException;
import com.project.eventregister.models.participant.Participant;
import com.project.eventregister.dtos.ParticipantResponseDTO;
import com.project.eventregister.repositories.EventRepository;
import com.project.eventregister.repositories.ParticipantRepository;
import com.project.eventregister.utils.ConvertToDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParticipantServiceImplementation implements ParticipantService {
  final ParticipantRepository participantRepository;
  final EventRepository eventRepository;

  public ParticipantServiceImplementation(ParticipantRepository participantRepository, EventRepository eventRepository) {
    this.participantRepository = participantRepository;
    this.eventRepository = eventRepository;
  }

  @Override
  public List<ParticipantResponseDTO> getAllParticipants() {
    return participantRepository.findAll()
            .stream().map(participantFound -> {
              return ConvertToDTO.convertParticipantToDTO(participantFound);
            }).collect(Collectors.toList());
  }

  @Override
  public ParticipantResponseDTO getJustOneParticipant(UUID participantId) {
    return participantRepository.findById(participantId)
            .map(participantFound -> {
              return ConvertToDTO.convertParticipantToDTO(participantFound);
            }).orElseThrow(ParticipantNotFoundException::new);
  }

  @Override
  public void unregisterAParticipant(UUID participantId) {
    participantRepository.findById(participantId)
            .ifPresentOrElse(participantFound -> {
              participantRepository.deleteById(participantId);
            }, (ParticipantNotFoundException::new));
  }

  @Override
  public Optional<ParticipantResponseDTO> updateParticipantCredentials(UUID participantId, ParticipantDTO requestParticipantCredentials) {
    return Optional.ofNullable(participantRepository.findById(participantId)
            .map(participantCredentialsFound -> {
              participantCredentialsFound.setFirstName(requestParticipantCredentials.firstName());
              participantCredentialsFound.setLastName(requestParticipantCredentials.lastName());
              participantCredentialsFound.setEmail(requestParticipantCredentials.email());
              participantCredentialsFound.setUpdatedAt(LocalDateTime.now());

              participantRepository.save(participantCredentialsFound);

              return ConvertToDTO.convertParticipantToDTO(participantCredentialsFound);
            }).orElseThrow(ParticipantNotFoundException::new));
  }

  @Override
  public Optional<ParticipantResponseDTO> createRegistrationForAnEvent(UUID eventId, ParticipantDTO requestParticipantCredentials) {
    return Optional.ofNullable(eventRepository.findById(eventId)
            .orElseThrow(EventNotFoundException::new))
            .map(eventFound -> {
              var participant = new Participant(
                      requestParticipantCredentials.firstName(),
                      requestParticipantCredentials.lastName(),
                      requestParticipantCredentials.email());

              participant.setEvent(eventFound);
              participant.setCreatedAt(LocalDateTime.now());
              participant.setUpdatedAt(LocalDateTime.now());

              participantRepository.save(participant);

              return ConvertToDTO.convertParticipantToDTO(participant);
            });
  }

  @Override
  public ParticipantResponseDTO cancelRegistrationForAnEvent(UUID participantId) {
    return participantRepository.findById(participantId)
            .map(participantFound -> {
              participantFound.setEvent(null);
              participantRepository.save(participantFound);
              return ConvertToDTO.convertParticipantToDTO(participantFound);
            }).orElseThrow(ParticipantNotFoundException::new);
  }

  public Optional<Participant> getById(UUID id) {
    return participantRepository.findById(id);
  }
}
