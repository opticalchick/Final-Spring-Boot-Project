package activities.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Leader {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long leaderId;
	private String leaderFirstName;
	private String leaderLastName;
	private String leaderEmail;
	private String leaderPhone;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	//Creates many-to-one relationship between leaders and activity
	@ManyToOne(cascade = CascadeType.ALL)
	//Identifies the FK in leader table
	@JoinColumn(name = "activity_id")
	private Activity activity;
	
	

}


