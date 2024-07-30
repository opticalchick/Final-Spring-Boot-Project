package activity.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Activity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long activityId;
	private String activityName;
	private String activityLocation;
	private String activityDate;
	private String activityTime;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	//This creates the many-to-many relationship between activities and participants
	@ManyToMany(cascade = CascadeType.PERSIST)
	
	//This gives the join table name and columns on which to join
	@JoinTable(name = "activity_participant", joinColumns = @JoinColumn(name = "activity_id"), 
	inverseJoinColumns = @JoinColumn(name = "participant_id"))
	private Set<Participant> participants = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	//This creates the one-to-many relationship between activity and leaders
	@OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Leader> leaders = new HashSet<>();
	

}


