package com.project.eventregister.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class EventResponseDTO {
  private UUID id;
  private String name;
  private String description;
  private List<ParticipantResponseDTO> participants;
  private LocalDate startDate;
  private LocalDate endDate;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<ParticipantResponseDTO> getParticipants() {
    return participants;
  }

  public void setParticipants(List<ParticipantResponseDTO> participants) {
    this.participants = participants;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }
}
