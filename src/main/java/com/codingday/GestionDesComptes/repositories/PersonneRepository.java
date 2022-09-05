package com.codingday.GestionDesComptes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codingday.GestionDesComptes.models.Personne;

public interface PersonneRepository extends JpaRepository<Personne, Integer>{
	
	@Query("select p from Personne p where p.nom = :nom and p.prenom = :prenom")
	Personne findByNomAndPrenom(@Param("nom") String nom, @Param("prenom") String prenom);
}
