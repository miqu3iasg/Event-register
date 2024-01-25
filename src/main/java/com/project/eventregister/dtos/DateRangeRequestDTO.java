package com.project.eventregister.dtos;

import java.time.LocalDate;

public record DateRangeRequestDTO(LocalDate startDate, LocalDate endDate) {
}
