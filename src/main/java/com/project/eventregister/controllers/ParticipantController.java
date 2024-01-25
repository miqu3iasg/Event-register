package com.project.eventregister.controllers;

import com.project.eventregister.dtos.ParticipantDTO;
import com.project.eventregister.dtos.ParticipantResponseDTO;
import com.project.eventregister.services.ParticipantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/participants")
public class ParticipantController {
  final ParticipantService participantService;

  public ParticipantController(ParticipantService participantService) {
    this.participantService = participantService;
  }

  @PostMapping("/create/{id}")
  ResponseEntity<Optional<ParticipantResponseDTO>> createRegistrationForAnEvent(@PathVariable(name = "id") UUID eventID,
                                                                                @RequestBody ParticipantDTO requestParticipantCredentials) {
    return new ResponseEntity<>(participantService
            .createRegistrationForAnEvent(eventID, requestParticipantCredentials), HttpStatus.CREATED);
  }

  @PutMapping("/cancel-registration/{id}")
  ResponseEntity<ParticipantResponseDTO> cancelRegistrationForAnEvent(@PathVariable(name = "id") UUID participantId) {
    return new ResponseEntity<>(participantService
            .cancelRegistrationForAnEvent(participantId), HttpStatus.OK);
  }

  @PutMapping("/update-credentials/{id}")
  ResponseEntity<Optional<ParticipantResponseDTO>> updateParticipantCredentials(@PathVariable(name = "id") UUID participantId,
                                                              @RequestBody ParticipantDTO requestParticipantCredentials) {
    return new ResponseEntity<>(participantService
            .updateParticipantCredentials(participantId, requestParticipantCredentials), HttpStatus.OK);
  }

  @GetMapping("/get-all")
  ResponseEntity<List<ParticipantResponseDTO>> getAllParticpants() {
    return new ResponseEntity<>(participantService.getAllParticipants(), HttpStatus.OK);
  }

  @GetMapping("/get-one/{id}")
  ResponseEntity<ParticipantResponseDTO> getJustOneParticipant(@PathVariable(name = "id") UUID participantId) {
    return new ResponseEntity<>(participantService.getJustOneParticipant(participantId), HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  ResponseEntity<String> unregisterAParticipant(@PathVariable(name = "id") UUID participantId) {
    participantService.unregisterAParticipant(participantId);
    return ResponseEntity.ok("Deleted registration of participant with successfuly.");
  }
}
