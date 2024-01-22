package com.project.eventregister.utils;

import com.project.eventregister.models.event.Event;
import com.project.eventregister.models.event.EventResponseDTO;
import com.project.eventregister.models.participant.Participant;
import com.project.eventregister.models.participant.ParticipantResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertToDTO {
  public static EventResponseDTO convertEventToDTO(Event event) {
    var eventDTO = new EventResponseDTO();
    eventDTO.setId(event.getId());
    eventDTO.setName(event.getName());
    eventDTO.setDescription(event.getDescription());
    eventDTO.setParticipants(convertParticipantsToDTO(event.getParticipants()));
    eventDTO.setStartDate(event.getStartDate());
    eventDTO.setEndDate(event.getEndDate());
    return eventDTO;
  }

  public static List<EventResponseDTO> convertEventsToDTO(List<Event> events) {
    return events.stream().map(ConvertToDTO::convertEventToDTO).collect(Collectors.toList());
  }

  public static ParticipantResponseDTO convertParticipantsToDTO(Participant participant) {
    var participantDTO = new ParticipantResponseDTO();
    participantDTO.setId(participant.getId());
    participantDTO.setFirstName(participant.getFirstName());
    participantDTO.setLastName(participant.getLastName());
    participantDTO.setEmail(participant.getEmail());
    return participantDTO;
  }

  public static List<ParticipantResponseDTO> convertParticipantsToDTO(List<Participant> participants) {
    return participants.stream().map(ConvertToDTO::convertParticipantsToDTO).collect(Collectors.toList());
  }
}
