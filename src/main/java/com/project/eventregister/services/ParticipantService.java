package com.project.eventregister.services;

import com.project.eventregister.dtos.ParticipantDTO;
import com.project.eventregister.dtos.ParticipantResponseDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParticipantService {
  List<ParticipantResponseDTO> getAllParticipants();
  ParticipantResponseDTO getJustOneParticipant(UUID participantId);
  Optional<ParticipantResponseDTO> updateParticipantCredentials(UUID participantId, ParticipantDTO requestParticipantCredentials);
  void unregisterAParticipant(UUID participantId);
  Optional<ParticipantResponseDTO> createRegistrationForAnEvent(UUID eventId, ParticipantDTO requestParticipantCredentials);
  ParticipantResponseDTO cancelRegistrationForAnEvent(UUID participantId);
}
