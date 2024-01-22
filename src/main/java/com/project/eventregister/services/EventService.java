package com.project.eventregister.services;

import com.project.eventregister.models.event.EventDTO;
import com.project.eventregister.models.event.Event;
import com.project.eventregister.models.event.EventResponseDTO;

import java.util.List;
import java.util.UUID;

public interface EventService {
  Event registerANewEvent(EventDTO request);
  void unregisterAEvent(UUID eventId);
  List<EventResponseDTO> getAllEvents();
  EventResponseDTO getJustOneEvent(UUID eventId);
}
