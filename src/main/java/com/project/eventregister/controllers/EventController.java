package com.project.eventregister.controllers;

import com.project.eventregister.models.event.EventDTO;
import com.project.eventregister.models.event.Event;
import com.project.eventregister.models.event.EventResponseDTO;
import com.project.eventregister.services.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/events")
public class EventController {
  final EventService service;

  public EventController(EventService service) {
    this.service = service;
  }

  @PostMapping("/create")
  ResponseEntity<Event> registerANewEvent(@RequestBody EventDTO request) {
    var event = service.registerANewEvent(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(event);
  }

  @DeleteMapping("/remove/{id}")
  ResponseEntity<HttpStatus> unregisterAEvent(@PathVariable(name = "id") UUID eventId) {
    service.unregisterAEvent(eventId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetMapping("/get-all")
  ResponseEntity<List<EventResponseDTO>> getAllEvents() {
    var events = service.getAllEvents();
    return ResponseEntity.status(HttpStatus.OK).body(events);
  }

  @GetMapping("/get-one/{id}")
  ResponseEntity<EventResponseDTO> getJustOneEvent(@PathVariable(name = "id") UUID eventId) {
    var event = service.getJustOneEvent(eventId);
    return ResponseEntity.status(HttpStatus.OK).body(event);
  }
}
