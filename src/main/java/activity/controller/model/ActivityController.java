package activity.controller.model;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import activity.controller.model.ActivityData.ActivityParticipant;
import activity.controller.model.ActivityData.ActivityLeader;
import activity.service.ActivityService;

@RestController
@RequestMapping("/activity")
@Slf4j
public class ActivityController {

	@Autowired
	private ActivityService activityService;
	
	@PostMapping("")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ActivityData createActivity(@RequestBody ActivityData activityData) {
		log.info("Creating activity {}", activityData);
		return activityService.saveActivity(activityData);
	}
	
	@PutMapping("/{activityId}")
	public ActivityData updateActivity(@PathVariable Long activityId, 
			@RequestBody ActivityData activityData) {
		activityData.setActivityId(activityId);
		log.info("Updating activity {}", activityData);
		return activityService.saveActivity(activityData);
	}
	
	@PostMapping("/{activityId}/leader")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ActivityLeader insertLeader(@PathVariable Long activityId, 
			@RequestBody ActivityLeader activityLeader) {
		log.info("Creating leader {} for activity with ID: {}", activityLeader, activityId);
		return activityService.saveLeader(activityId, activityLeader);
	}
	
	@PostMapping("/{activityId}/participant")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ActivityParticipant insertParticipant(@PathVariable Long activityId, 
			@RequestBody ActivityParticipant activityParticipant) {
		log.info("Creating participant {} for activity with ID: {}", activityParticipant, activityId);
		return activityService.saveParticipant(activityId, activityParticipant);
	}
	
	@GetMapping() 
	public List<ActivityData> retrieveAllActivities() {
		log.info("Retrieving all activities.");
		return activityService.retrieveAllActivities();
	}
	
	@GetMapping("/{activityId}")
	public ActivityData retrieveActivityById(@PathVariable Long activityId) {
		log.info("Retrieving activity with ID: {}", activityId);
		return activityService.retrieveActivityById(activityId);
	}
	
	@GetMapping("/{activityId}/leadersandparticipants")
	public List<ActivityData> retrieveActivityWithParticipantsAndLeaders (@PathVariable Long activityId) {
		log.info("Retrieving activity with ID: {}, leaders, and participants", activityId);
		return activityService.retrieveActivityWithParticipantsAndLeaders(activityId);
	}
	
	@PutMapping("/{activityId}/{leaderId}")
	public ActivityLeader updateLeader(@PathVariable Long activityId, @PathVariable Long leaderId, 
			@RequestBody ActivityLeader activityLeader) {
		activityLeader.setLeaderId(leaderId);
		log.info("Updating leader with ID: {}", leaderId);
		return activityService.saveLeader(activityId, activityLeader);
		
	}
	
	@PutMapping("/{activityId}/{participantId}")
	public ActivityParticipant updateParticipant(@PathVariable Long activityId, @PathVariable Long participantId, 
			@RequestBody ActivityParticipant activityParticipant) {
		activityParticipant.setParticipantId(participantId);
		log.info("Updating participant with ID: {}", participantId);
		return activityService.saveParticipant(activityId, activityParticipant);
	}
	
	@DeleteMapping("/{activityId}")
	public Map<String, String> deleteActivityById(@PathVariable Long activityId) {
		log.info("Deleting activity with ID: {}", activityId);
		activityService.deleteActivityById(activityId);
		
		return Map.of("message", "Successfully deleted activity with ID: " + activityId);
	}
 }






 


