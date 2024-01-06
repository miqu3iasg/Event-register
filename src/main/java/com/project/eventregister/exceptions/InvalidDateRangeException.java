package com.project.eventregister.exceptions;

public class InvalidDateRangeException extends RuntimeException {
  private static final String DEFAULT_MESSAGE = "Invalid date range.";

  public InvalidDateRangeException(String message) {
    super(message);
  }

  public InvalidDateRangeException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidDateRangeException() {
    super(DEFAULT_MESSAGE);
  }
}
