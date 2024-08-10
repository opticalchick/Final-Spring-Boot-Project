package activity.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import activity.controller.model.ActivityData;
import activity.controller.model.ActivityData.ActivityLeader;
import activity.controller.model.ActivityData.ActivityParticipant;
import activity.dao.ActivityDao;
import activity.dao.LeaderDao;
import activity.dao.ParticipantDao;
import activity.entity.Activity;
import activity.entity.Leader;
import activity.entity.Participant;



//Service class is used to manage transaction performed in the Dao interface

//Denotes to Spring that this is a service class and creates the bean for ActivityService
@Service
public class ActivityService {

	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private LeaderDao leaderDao;
	
	@Autowired 
	private ParticipantDao participantDao;
	
	public ActivityData saveActivity(ActivityData activityData) {
		Activity activity = findOrCreateActivity(activityData.getActivityId());
		copyActivityFields(activity, activityData);
		
		Activity dbActivity = activityDao.save(activity);
		return new ActivityData(dbActivity);
	}
	
	private void copyActivityFields(Activity activity, ActivityData activityData) {
		activity.setActivityId(activityData.getActivityId());
		activity.setActivityName(activityData.getActivityName());
		activity.setActivityLocation(activityData.getActivityLocation());
		activity.setActivityDate(activityData.getActivityDate());
		activity.setActivityTime(activityData.getActivityTime());
	}
	
	// adds new activity if Id is null, otherwise calls findActivityById method to retrieve from db
	private Activity findOrCreateActivity(Long activityId) {
		Activity activity;
		
		if(Objects.isNull(activityId)) {
			activity = new Activity();
		} else {
			activity = findActivityById(activityId);
		}
		
		return activity;
	}
	//retrieve activity from db using Id.  If Id is not valid, it will throw an exception	
	private Activity findActivityById(Long activityId) {
		return activityDao.findById(activityId)
				.orElseThrow(() -> new NoSuchElementException("Activity with ID: " + activityId +
						" does not exist."));
	}

	//save leader info in leader table using save() method from LeaderDao interface
	@Transactional(readOnly = false)
	public ActivityLeader saveLeader(Long activityId, ActivityLeader activityLeader) {
		Activity activity = findActivityById(activityId);
		Leader leader = findOrCreateLeader(activityId, activityLeader.getLeaderId());
		copyLeaderFields(leader, activityLeader);
		leader.setActivity(activity);
		activity.getLeaders().add(leader);
		
		Leader dbLeader = leaderDao.save(leader);
		
		return new ActivityLeader(dbLeader);
	}
	
	//sets values or Leader fields to values of ActivityLeader fields
	private void copyLeaderFields(Leader leader, ActivityLeader activityLeader) {
		leader.setLeaderId(activityLeader.getLeaderId());
		leader.setLeaderFirstName(activityLeader.getLeaderFirstName());
		leader.setLeaderLastName(activityLeader.getLeaderLastName());
		leader.setLeaderEmail(activityLeader.getLeaderEmail());
		leader.setLeaderPhone(activityLeader.getLeaderPhone());
	}

	//creates new leader if Id is null, otherwise retrieves leader by Id
	private Leader findOrCreateLeader(Long activityId, Long leaderId) {
		Leader leader;
		
		if(Objects.isNull(leaderId)){
			leader = new Leader();
		} else {
			leader = findLeaderById(activityId, leaderId);
		}
		
		return leader;
	}
	
	//finds leader in db by Id and return leader, otherwise it will throw an exception
	private Leader findLeaderById(Long activityId, Long leaderId) {
		Leader leader = leaderDao.findById(leaderId)
				.orElseThrow(() -> new NoSuchElementException 
						("Leader with ID: " + leaderId + " does not exist."));
		
		if(leader.getActivity().getActivityId() == activityId) {
			return leader;
		} else {
			throw new IllegalArgumentException
			("Activity with ID: " + activityId + " does not have a leader with ID: " + leaderId);
		}
	}
	
	//saves participant info in participant table using save() method from CustomerDao interface
	@Transactional(readOnly = false)
	public ActivityParticipant saveParticipant(Long activityId, ActivityParticipant activityParticipant) {
		Activity activity = findActivityById(activityId);
		Participant participant= findOrCreateParticipant(activityId, activityParticipant.getParticipantId());
		copyParticipantFields(participant, activityParticipant);
		participant.getActivities().add(activity);
		activity.getParticipants().add(participant);
		
		Participant dbParticipant = participantDao.save(participant);
		
		return new ActivityParticipant(dbParticipant);
		}
	
	//sets values of Participant fields to values of activityParticipant fields
	private void copyParticipantFields(Participant participant, ActivityParticipant activityParticipant) {
		participant.setParticipantId(activityParticipant.getParticipantId());
		participant.setParticipantFirstName(activityParticipant.getParticipantFirstName());
		participant.setParticipantLastName(activityParticipant.getParticipantLastName());
		participant.setParticipantEmail(activityParticipant.getParticipantEmail());
		participant.setParticipantPhone(activityParticipant.getParticipantPhone());
	}
	
	//finds participant by Id or creates a new participant if Id is null
	private Participant findOrCreateParticipant(Long activityId, Long participantId) {
		Participant participant;
		
		if(Objects.isNull(participantId)) {
			participant = new Participant();
		} else {
			participant = findParticipantById(activityId, participantId);
		}
		
		return participant;
	}
	
	//finds participant in db by Id and returns participant, otherwise throws and exception. 
	private Participant findParticipantById(Long activityId, Long participantId) {
		boolean activityIdMatch = false;
		
		Participant participant = participantDao.findById(participantId).orElseThrow(() -> 
			new NoSuchElementException("Participant with ID: " + participantId + " does not exist."));
		
		Set<Activity> activities = participant.getActivities();
		for (Activity activity : activities) {
			if(activity.getActivityId() == activityId) {
				activityIdMatch = true;
			}
		}
		
		if(activityIdMatch) {
			return participant;
		} else {
			throw new IllegalArgumentException("Activity with ID: " + activityId + 
					" does not have a participant with ID: " + participantId);
		}
	}
	
	//retrieves all activities only and removes leader and participant data before returning the activities only
	@Transactional
	public List<ActivityData> retrieveAllActivities() {
		List<Activity> activities = activityDao.findAll();
		List<ActivityData>  results = new LinkedList<>();
		
		for(Activity activity : activities) {
			ActivityData activityData = new ActivityData(activity);
			
			activityData.getLeaders().clear();
			activityData.getParticipants().clear();
			
			results.add(activityData);
		}
		
		return results;
	}
	
	@Transactional
	public List<ActivityData> retrieveActivityWithParticipantsAndLeaders(Long activityId){
		Activity activity = findActivityById(activityId);
		List<ActivityData> results2 = new LinkedList<>();
		
		ActivityData activityData2 = new ActivityData(activity);
		
		activityData2.getLeaders();
		activityData2.getParticipants();
		
		results2.add(activityData2);
		
		return results2;
		
	}
	
	
	//retrieves activity using activity Id
	public ActivityData retrieveActivityById(Long activityId) {
		Activity activity = findActivityById(activityId);
		activity.getLeaders().clear();
		activity.getParticipants().clear();
		return new ActivityData(activity);
	}
	
	//deletes activity using Id
	public void deleteActivityById(Long activityId) {
		Activity activity = findActivityById(activityId);
		activityDao.delete(activity);
	}
}


