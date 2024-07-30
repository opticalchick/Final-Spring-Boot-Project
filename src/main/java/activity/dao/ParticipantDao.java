package activity.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import activity.entity.Participant;

public interface ParticipantDao extends JpaRepository<Participant, Long> {

}
