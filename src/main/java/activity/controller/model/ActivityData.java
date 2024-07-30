package activity.controller.model;

import java.util.HashSet;
import java.util.Set;

import activity.entity.Activity;
import activity.entity.Leader;
import activity.entity.Participant;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class ActivityData {
	private Long activityId;
	private String activityName;
	private String activityLocation;
	private String activityDate;
	private String activityTime;
	private Set<ActivityLeader> leaders = new HashSet<>();
	private Set<ActivityParticipant> participants = new HashSet<>();
	
	public ActivityData(Activity activity) {
		activityId = activity.getActivityId();
		activityName = activity.getActivityName();
		activityLocation = activity.getActivityLocation();
		activityDate = activity.getActivityDate();
		activityTime = activity.getActivityTime();
		
		for(Leader leader : activity.getLeaders()) {
			leaders.add(new ActivityLeader(leader));
		}
		
		for(Participant participant : activity.getParticipants()) {
			participants.add(new ActivityParticipant(participant));
		}	
	}
	
	@Data
	@NoArgsConstructor
	public static class ActivityParticipant {
		private Long participantId;
		private String participantFirstName;
		private String participantLastName;
		private String participantEmail;
		private String participantPhone;
	
		public ActivityParticipant(Participant participant) {
			participantId = participant.getParticipantId();
			participantFirstName = participant.getParticipantFirstName();
			participantLastName = participant.getParticipantLastName();
			participantEmail = participant.getParticipantEmail();
			participantPhone = participant.getParticipantPhone();
		}
	}

	@Data
	@NoArgsConstructor
	public static class ActivityLeader {
		private Long leaderId;
		private String leaderFirstName;
		private String leaderLastName;
		private String leaderEmail;
		private String leaderPhone;
	
		public ActivityLeader(Leader leader) {
			leaderId = leader.getLeaderId();
			leaderFirstName = leader.getLeaderFirstName();
			leaderLastName = leader.getLeaderLastName();
			leaderEmail = leader.getLeaderEmail();
			leaderPhone = leader.getLeaderPhone();
		}
	}
}
