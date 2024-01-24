package com.project.eventregister.services;

import com.project.eventregister.models.participant.ParticipantDTO;
import com.project.eventregister.models.participant.Participant;

import java.util.List;
import java.util.UUID;

public interface ParticipantService {
  List<Participant> getAllParticipants();
  Participant getJustOneParticipant(UUID participantId);
  Participant updateParticipantCredentials(UUID participantId, ParticipantDTO requestParticipantCredentials);
  void unregisterAParticipant(UUID participantId);
  Participant createRegistrationForAnEvent(UUID eventId, ParticipantDTO requestParticipantCredentials);
  Participant cancelRegistrationForAnEvent(UUID participantId);
}
