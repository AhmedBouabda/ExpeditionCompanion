package tn.esprit.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Dictionnary implements Serializable {
	
	private static final long serialVersionUID = -6236517548335858347L;

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String description;

//********************************************************************************************************************
	public Dictionnary(Long id, String description) {
		super();
		this.id = id;
		this.description = description;
}

//********************************************************************************************************************

	public Long getId() {
		return id;
}

	public void setId(Long id) {
		this.id = id;
}

	public String getDescription() {
		return description;
}

	public void setDescription(String description) {
		this.description = description;
}
//*********************************************************************************************************************

	public int hashCode() {
		return Objects.hash(description, id);
	}

//*********************************************************************************************************************
	
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Dictionnary other = (Dictionnary) obj;
		if (description==null) {
			
			if(other.description!=null)
				return false;
		}else if (!description.equals(other.description))
			return false;
		return true;
	} 




}
