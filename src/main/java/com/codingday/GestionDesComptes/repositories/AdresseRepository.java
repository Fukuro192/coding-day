package com.codingday.GestionDesComptes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codingday.GestionDesComptes.models.Adresse;
import com.codingday.GestionDesComptes.models.Personne;

public interface AdresseRepository extends JpaRepository<Adresse, Integer>{
	
	@Query("select a from Adresse a where a.personne = :personne")
	List<Adresse> findByPersonne(@Param("personne") Personne personne);
	
	@Query("select a from Adresse a where a.personne != :personne")
	List<Adresse> findByPersonneNotEqual(@Param("personne") Personne personne);
}
