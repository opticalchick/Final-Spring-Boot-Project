package activity.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import activity.entity.Leader;

public interface LeaderDao extends JpaRepository<Leader, Long> {

}
