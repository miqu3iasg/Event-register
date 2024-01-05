package com.project.eventregister.repositories;

import com.project.eventregister.models.participant.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ParticipantRepository extends JpaRepository<Participant, UUID> {
}
