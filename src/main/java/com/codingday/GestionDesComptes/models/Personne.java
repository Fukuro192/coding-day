package com.codingday.GestionDesComptes.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Personne")
public class Personne {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name = "nom", nullable = false, unique = true, length = 30)
	private String nom;
	
	@Column(name = "prenom", nullable = false, length = 30)
	private String prenom;
	
	@Column(name = "sexe", length = 1)
	private String sexe;
	
	public Personne() {
		
	}

	public Personne(String nom, String prenom, String sexe) {
		this.nom = nom;
		this.prenom = prenom;
		this.sexe = sexe;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	
	
}
