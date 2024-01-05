package com.project.eventregister.repositories;

import com.project.eventregister.models.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
  List<Event> findByStartDateBetween(LocalDate startDate, LocalDate endDate);
}
