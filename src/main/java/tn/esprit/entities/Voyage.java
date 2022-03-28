package tn.esprit.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Voyage {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_voyage;

	public Long getId_voyage() {
		return id_voyage;
	}

	public void setId_voyage(Long id_voyage) {
		this.id_voyage = id_voyage;
	}

	public Voyage(Long id_voyage) {
		super();
		this.id_voyage = id_voyage;
	}

	public Voyage() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Voyage [id_voyage=" + id_voyage + "]";
	}

}
