package com.project.eventregister.services;

import com.project.eventregister.models.event.EventDTO;
import com.project.eventregister.exceptions.EventNotFoundException;
import com.project.eventregister.exceptions.InvalidDateRangeException;
import com.project.eventregister.models.event.Event;
import com.project.eventregister.repositories.EventRepository;
import jakarta.transaction.Transactional;
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
  @Transactional
  public void registerANewEvent(EventDTO request) {
    if(request.startDate().isAfter(request.endDate())
            || request.startDate().isEqual(request.endDate())) throw new InvalidDateRangeException();

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
  @Transactional
  public void unregisterAEvent(UUID eventId) {
    var eventExists = eventRepository.findById(eventId)
            .orElseThrow(EventNotFoundException::new);

    eventRepository.deleteById(eventId);
  }

  @Override
  public List<Event> getAllEvents() {
    return eventRepository.findAll();
  }

  @Override
  public Event getJustOneEvent(UUID eventId) throws RuntimeException {
    return eventRepository.findById(eventId)
            .orElseThrow(EventNotFoundException::new);
  }
}
