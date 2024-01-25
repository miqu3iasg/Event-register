package com.project.eventregister.services;

import com.project.eventregister.dtos.EventDTO;
import com.project.eventregister.exceptions.EventNotFoundException;
import com.project.eventregister.exceptions.InvalidDateRangeException;
import com.project.eventregister.models.event.Event;
import com.project.eventregister.dtos.EventResponseDTO;
import com.project.eventregister.repositories.EventRepository;
import com.project.eventregister.utils.ConvertToDTO;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventServiceImplementation implements EventService {
  private final EventRepository eventRepository;
  private static final Logger log = LoggerFactory.getLogger(EventServiceImplementation.class);

  public EventServiceImplementation(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  @Override
  @Transactional
  public EventResponseDTO registerANewEvent(EventDTO requestEventCreationData) {
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

    eventRepository.save(event);

    return ConvertToDTO.convertEventToDTO(event);
  }

  @Override
  @Transactional
  public void unregisterAEvent(UUID eventId) {
    eventRepository.findById(eventId)
            .ifPresentOrElse(eventFound -> {
              eventRepository.deleteById(eventId);
            }, (EventNotFoundException::new));
  }

  @Override
  public List<EventResponseDTO> getAllEvents() {
    return eventRepository.findAll()
            .stream().map(event -> {
              return ConvertToDTO.convertEventToDTO(event);
            }).collect(Collectors.toList());
  }

  @Override
  public EventResponseDTO getJustOneEvent(UUID eventId) throws RuntimeException {
    return eventRepository.findById(eventId)
            .map(eventFound -> {
              return ConvertToDTO.convertEventToDTO(eventFound);
            }).orElseThrow(EventNotFoundException::new);
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
