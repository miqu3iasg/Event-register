package com.project.eventregister.dtos;

import java.time.LocalDate;

public record EventDTO(String name, String description, LocalDate startDate, LocalDate endDate) {
}
