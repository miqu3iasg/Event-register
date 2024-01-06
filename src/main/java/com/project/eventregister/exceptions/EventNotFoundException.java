package com.project.eventregister.exceptions;

public class EventNotFoundException extends RuntimeException {
  private static final String DEFAULT_MESSAGE = "Event not found.";
  public EventNotFoundException(String message) {
    super(message);
  }

  public EventNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public EventNotFoundException() {
    super(DEFAULT_MESSAGE);
  }
}
