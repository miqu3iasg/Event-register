package com.project.eventregister.models.event;

import java.time.LocalDate;

public record EventDTO(String name, String description, LocalDate startDate, LocalDate endDate) {
}
