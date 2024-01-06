package com.project.eventregister.controllers;

import com.project.eventregister.models.event.EventDTO;
import com.project.eventregister.models.event.Event;
import com.project.eventregister.services.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("events")
public class EventController {
  final EventService service;

  public EventController(EventService service) {
    this.service = service;
  }

  @PostMapping("/create")
  ResponseEntity<HttpStatus> registerANewEvent(@RequestBody EventDTO request) {
    service.registerANewEvent(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping("/remove/{id}")
  ResponseEntity<HttpStatus> unregisterAEvent(@PathVariable(name = "id") UUID eventId) {
    service.unregisterAEvent(eventId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetMapping("/get-all")
  ResponseEntity<List<Event>> getAllEvents() {
    List<Event> events = service.getAllEvents();
    return ResponseEntity.status(HttpStatus.OK).body(events);
  }

  @GetMapping("/get-one/{id}")
  ResponseEntity<Event> getJustOneEvent(@PathVariable(name = "id") UUID eventId) {
    var event = service.getJustOneEvent(eventId);
    return ResponseEntity.status(HttpStatus.OK).body(event);
  }
}
