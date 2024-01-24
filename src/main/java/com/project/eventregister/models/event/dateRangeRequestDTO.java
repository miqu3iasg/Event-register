package com.project.eventregister.models.event;

import java.time.LocalDate;

public record dateRangeRequestDTO(LocalDate startDate, LocalDate endDate) {
}
