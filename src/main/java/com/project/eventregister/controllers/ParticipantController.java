package com.project.eventregister.controllers;

import com.project.eventregister.models.participant.ParticipantDTO;
import com.project.eventregister.models.participant.Participant;
import com.project.eventregister.services.ParticipantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/participants")
public class ParticipantController {
  final ParticipantService participantService;

  public ParticipantController(ParticipantService participantService) {
    this.participantService = participantService;
  }

  @PostMapping("/create/{id}")
  ResponseEntity<HttpStatus> createRegistrationForAnEvent(@PathVariable(name = "id") UUID eventID,
                                                          @RequestBody ParticipantDTO participantCredentials) {

    participantService.createRegistrationForAnEvent(eventID, participantCredentials);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/cancel-registration/{id}")
  ResponseEntity<HttpStatus> cancelRegistrationForAnEvent(@PathVariable(name = "id") UUID participantId) {
    participantService.cancelRegistrationForAnEvent(participantId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PutMapping("/update-credentials/{id}")
  ResponseEntity<HttpStatus> updateParticipantCredentials(@PathVariable(name = "id") UUID participantId,
                                                          @RequestBody ParticipantDTO participantCredentials) {

    participantService.updateParticipantCredentials(participantId, participantCredentials);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetMapping("/get-all")
  ResponseEntity<List<Participant>> getAllEvents() {
    List<Participant> participants = participantService.getAllParticipants();
    return ResponseEntity.status(HttpStatus.OK).body(participants);
  }

  @GetMapping("/get-one/{id}")
  ResponseEntity<Participant> getJustOneParticipant(@PathVariable(name = "id") UUID participantId) {
    var participant = participantService.getJustOneParticipant(participantId);
    return ResponseEntity.status(HttpStatus.OK).body(participant);
  }
}
