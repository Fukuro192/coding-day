package com.codingday.GestionDesComptes.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;



@Entity
@Table(name = "Adresse")
public class Adresse {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name = "rue", length = 30)
	private String rue;
	
	@Column(name = "code_postal", length = 30)
	private String code_postal;
	
	@Column(name = "ville", length = 30)
	private String ville;
	
	@ManyToOne
	@JoinColumn(name = "personne", foreignKey = @ForeignKey(name = "fk_personne"))
	@NotFound(action = NotFoundAction.IGNORE)
	private Personne personne;

	public Adresse() {
		
	}

	public Adresse(String rue, String code_postal, String ville, Personne personne) {
		super();
		this.rue = rue;
		this.code_postal = code_postal;
		this.ville = ville;
		this.personne = personne;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCode_postal() {
		return code_postal;
	}

	public void setCode_postal(String code_postal) {
		this.code_postal = code_postal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}
	
	
}
