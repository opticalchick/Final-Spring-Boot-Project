package activity.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Participant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long participantId;
	private String participantFirstName;
	private String participantLastName;
	private String participantEmail;
	private String participantPhone;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	//This creates many-to-many relationship with participants and activities.
	@ManyToMany(mappedBy = "participants", cascade = CascadeType.PERSIST)
	private Set<Activity> activities = new HashSet<>();
}


