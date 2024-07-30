package activity.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import activity.entity.Activity;

public interface ActivityDao extends JpaRepository<Activity, Long> {

}
