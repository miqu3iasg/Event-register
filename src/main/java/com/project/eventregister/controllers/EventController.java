package com.project.eventregister.controllers;

import com.project.eventregister.dtos.EventDTO;
import com.project.eventregister.dtos.EventResponseDTO;
import com.project.eventregister.dtos.DateRangeRequestDTO;
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
  ResponseEntity<EventResponseDTO> registerANewEvent(@RequestBody EventDTO requestEventCreationData) {
    return new ResponseEntity<>(service.registerANewEvent(requestEventCreationData), HttpStatus.CREATED);
  }

  @DeleteMapping("/remove/{id}")
  ResponseEntity<String> unregisterAEvent(@PathVariable(name = "id") UUID eventId) {
    service.unregisterAEvent(eventId);
    return ResponseEntity.ok("Deleted event with successfully.");
  }

  @GetMapping("/get-all")
  ResponseEntity<List<EventResponseDTO>> getAllEvents() {
    return new ResponseEntity<>(service.getAllEvents(), HttpStatus.OK);
  }

  @GetMapping("/get-one/{id}")
  ResponseEntity<EventResponseDTO> getJustOneEvent(@PathVariable(name = "id") UUID eventId) {
    return new ResponseEntity<>(service.getJustOneEvent(eventId), HttpStatus.OK);
  }

  @GetMapping("/get-events-between-dates")
  ResponseEntity<List<EventResponseDTO>> getEventsBetweenDates(@RequestBody DateRangeRequestDTO dataRangeRequest) {
    return new ResponseEntity<>(service.findEventsBetweenDates(
            dataRangeRequest.startDate(),
            dataRangeRequest.endDate()), HttpStatus.OK);
  }
}
