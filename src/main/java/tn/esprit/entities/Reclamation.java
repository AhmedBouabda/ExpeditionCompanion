package tn.esprit.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
public class Reclamation {
	
	private String subject;
	private String description;
	
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Enumerated (EnumType.STRING)
	private ClaimCategory Category;

	@Enumerated (EnumType.STRING)
	private ClaimStatus status;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@JoinColumn(name="email")
	private String email;

	@JoinColumn(name="idUser", referencedColumnName="id_user")
	@ManyToOne(cascade=CascadeType.MERGE)
	 User user;
	
	@ManyToOne
	User complainer;
	@ManyToOne
	User complainedAbout;
	
	
	

	public Reclamation(String subject, String description, Date date, ClaimCategory category, ClaimStatus status,
			Long id, String email, User user, User complainer, User complainedAbout) {
		super();
		this.subject = subject;
		this.description = description;
		this.date = date;
		Category = category;
		this.status = status;
		this.id = id;
		this.email = email;
		this.user = user;
		this.complainer = complainer;
		this.complainedAbout = complainedAbout;
	}

	public User getComplainer() {
		return complainer;
	}

	public void setComplainer(User complainer) {
		this.complainer = complainer;
	}

	public User getComplainedAbout() {
		return complainedAbout;
	}

	public void setComplainedAbout(User complainedAbout) {
		this.complainedAbout = complainedAbout;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ClaimCategory getCategory() {
		return Category;
	}

	public void setCategory(ClaimCategory category) {
		Category = category;
	}

	public ClaimStatus getStatus() {
		return status;
	}

	public void setStatus(ClaimStatus status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Reclamation(String subject, String description, Date date, ClaimCategory category, ClaimStatus status,
			Long id, String email, User user) {
		super();
		this.subject = subject;
		this.description = description;
		this.date = date;
		Category = category;
		this.status = status;
		this.id = id;
		this.email = email;
		this.user = user;
	}

	public Reclamation() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Reclamation [subject=" + subject + ", description=" + description + ", date=" + date + ", Category="
				+ Category + ", status=" + status + ", id=" + id + ", email=" + email + ", user=" + user
				+ ", complainer=" + complainer + ", complainedAbout=" + complainedAbout + "]";
	}
	
	
	


	
}
