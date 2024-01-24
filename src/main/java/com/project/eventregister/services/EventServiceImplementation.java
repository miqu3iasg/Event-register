package com.project.eventregister.services;

import com.project.eventregister.models.event.EventDTO;
import com.project.eventregister.exceptions.EventNotFoundException;
import com.project.eventregister.exceptions.InvalidDateRangeException;
import com.project.eventregister.models.event.Event;
import com.project.eventregister.models.event.EventResponseDTO;
import com.project.eventregister.repositories.EventRepository;
import com.project.eventregister.utils.ConvertToDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventServiceImplementation implements EventService {
  final EventRepository eventRepository;

  public EventServiceImplementation(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  @Override
  @Transactional
  public Event registerANewEvent(EventDTO requestEventCreationData) {
    if (requestEventCreationData.startDate().isAfter(requestEventCreationData.endDate()) || requestEventCreationData.startDate().isEqual(requestEventCreationData.endDate())) {
      throw new InvalidDateRangeException();
    }

    var event = new Event(
            requestEventCreationData.name(),
            requestEventCreationData.description(),
            requestEventCreationData.startDate(),
            requestEventCreationData.endDate());

    event.setCreatedAt(LocalDateTime.now());
    event.setUpdatedAt(LocalDateTime.now());

    return eventRepository.save(event);
  }

  @Override
  @Transactional
  public void unregisterAEvent(UUID eventId) {
    var eventExists = eventRepository.findById(eventId)
            .orElseThrow(EventNotFoundException::new);

    eventRepository.deleteById(eventId);
  }

  @Override
  public List<EventResponseDTO> getAllEvents() {
    var events = eventRepository.findAll();
    return ConvertToDTO.convertEventsToDTO(events);
  }

  @Override
  public EventResponseDTO getJustOneEvent(UUID eventId) throws RuntimeException {
    var event = eventRepository.findById(eventId)
            .orElseThrow(EventNotFoundException::new);
    return ConvertToDTO.convertEventToDTO(event);
  }

  @Override
  public List<EventResponseDTO> findEventsBetweenDates(LocalDate startDate, LocalDate endDate) {
    var events = eventRepository.findEventsBetweenDates(startDate, endDate);

    if(events.isEmpty()) throw new EventNotFoundException("No events in date range.");

    return ConvertToDTO.convertEventsToDTO(events);
  }

  public Optional<Event> getById(UUID eventId) {
    return eventRepository.findById(eventId);
  }
}
