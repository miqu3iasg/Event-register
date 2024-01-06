package com.project.eventregister.exceptions;

public class ParticipantNotFoundException extends RuntimeException {
  private static final String DEFAULT_MESSAGE = "Participant not found.";

  public ParticipantNotFoundException(String message) {
    super(message);
  }

  public ParticipantNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public ParticipantNotFoundException() {
    super(DEFAULT_MESSAGE);
  }
}
