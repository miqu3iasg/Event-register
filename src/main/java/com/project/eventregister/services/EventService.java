package com.project.eventregister.services;

import com.project.eventregister.models.event.EventDTO;
import com.project.eventregister.models.event.Event;

import java.util.List;
import java.util.UUID;

public interface EventService {
  void registerANewEvent(EventDTO request);
  void unregisterAEvent(UUID eventId);
  List<Event> getAllEvents();
  Event getJustOneEvent(UUID eventId);
}
