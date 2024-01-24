package com.project.eventregister.services;

import com.project.eventregister.models.event.EventDTO;
import com.project.eventregister.models.event.Event;
import com.project.eventregister.models.event.EventResponseDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface EventService {
  Event registerANewEvent(EventDTO requestEventCreationData);
  void unregisterAEvent(UUID eventId);
  List<EventResponseDTO> getAllEvents();
  EventResponseDTO getJustOneEvent(UUID eventId);
  List<EventResponseDTO> findEventsBetweenDates(LocalDate startDate, LocalDate endDate);
}
