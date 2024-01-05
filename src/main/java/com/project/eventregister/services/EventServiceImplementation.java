package com.project.eventregister.services;

import com.project.eventregister.dtos.EventDTO;
import com.project.eventregister.models.event.Event;
import com.project.eventregister.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class EventServiceImplementation implements EventService {
  final EventRepository eventRepository;

  public EventServiceImplementation(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  @Override
  public void registerANewEvent(EventDTO request) {
    if(request.startDate().isAfter(request.endDate())
            && request.startDate().isEqual(request.endDate())) throw new RuntimeException();

    var event = new Event(
            request.name(),
            request.description(), 
            request.startDate(),
            request.endDate());

    event.setCreatedAt(LocalDateTime.now());
    event.setUpdatedAt(LocalDateTime.now());

    eventRepository.save(event);
  }

  @Override
  public void unregisterAEvent(UUID eventId) {
    eventRepository.deleteById(eventId);
  }

  @Override
  public List<Event> getAllEvents() {
    return eventRepository.findAll();
  }

  @Override
  public Event getJustOneEvent(UUID eventId) throws RuntimeException {
    return eventRepository.findById(eventId)
            .orElseThrow(RuntimeException::new);
  }
}
