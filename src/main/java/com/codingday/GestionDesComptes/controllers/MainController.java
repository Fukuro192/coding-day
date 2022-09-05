package com.codingday.GestionDesComptes.controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.codingday.GestionDesComptes.models.Adresse;
import com.codingday.GestionDesComptes.models.Personne;
import com.codingday.GestionDesComptes.repositories.AdresseRepository;
import com.codingday.GestionDesComptes.repositories.PersonneRepository;

@Controller
public class MainController {
	
	@PersistenceContext
	EntityManager entityManager;
	AdresseRepository adresseRepository;
	PersonneRepository personneRepository;
	
	public MainController(AdresseRepository adresseRepository, PersonneRepository personneRepository) {
		this.adresseRepository = adresseRepository;
		this.personneRepository = personneRepository;
	}
	
	@GetMapping("/")
	public ModelAndView home() {
		return new ModelAndView("index");
	}
	
	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return new ModelAndView("redirect:/");
		
	}
	
	@PostMapping("/")
	public ModelAndView getNomAndPrenom(HttpServletRequest request,@RequestParam("nom") String nom, @RequestParam("prenom") String prenom) {
		Personne personne = personneRepository.findByNomAndPrenom(nom, prenom);
		HttpSession session = request.getSession();
		session.setAttribute("nom", nom);
		session.setAttribute("prenom", prenom);
		if(personne == null) {
			ModelAndView model = new ModelAndView("index");
			model.addObject("nom", nom);
			model.addObject("prenom", prenom);
			model.addObject("error","Vos identifiants sont incorrects.");
			return model;
		}
		session.setAttribute("id_personne", personne.getId());
		return new ModelAndView("redirect:/gestioncompte").addObject("welcome", "Bienvenue, " + personne.getNom() + " " + personne.getPrenom());
	}
	
	@GetMapping("/inscription")
	public ModelAndView inscription() { 
		return new ModelAndView("inscription");
	}
	
	@PostMapping("/inscription")
	public ModelAndView getInscription(HttpServletRequest request, @RequestParam("nom") String nom, @RequestParam("prenom") String prenom
			, @RequestParam("sexe") String sexe) {
		HttpSession session = request.getSession();
		session.setAttribute("nom", nom);
		session.setAttribute("prenom", prenom);
		if(nom == null || prenom == null || sexe == null || nom.length() > 30 || prenom.length() > 30 || sexe.length() > 1) {
			ModelAndView model = new ModelAndView("inscription");
			model.addObject("nom", nom);
			model.addObject("prenom", prenom);
			model.addObject(sexe, "selected");
			model.addObject("error","Vos données sont incorrects. Il faut que le nom et le prénom contiennent au maximum 30 caractères");
			return model;
		}
		
		Personne personne = personneRepository.findByNomAndPrenom(nom, prenom);
		if(personne != null) {
			ModelAndView model = new ModelAndView("inscription");
			model.addObject("nom", nom);
			model.addObject("prenom", prenom);
			model.addObject(sexe, "selected");
			model.addObject("error","Les données entrées existent déjà.");
			return model;
		}
		
		personne = new Personne(nom, prenom, sexe);
		personne = personneRepository.save(personne);
		session.setAttribute("id_personne", personne.getId());
		return new ModelAndView("redirect:/gestioncompte").addObject("welcome", "Bienvenue, " + personne.getNom() + " " + personne.getPrenom());
	}
	
	@GetMapping("/gestioncompte")
	public ModelAndView mainApplication(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Integer id_personne = (Integer)session.getAttribute("id_personne");
		if(id_personne == null)return new ModelAndView("redirect:/");
		ModelAndView model = new ModelAndView("gestioncompte");
		model.addObject("welcome", request.getParameter("welcome"));
		return model;
	}
	
	@PostMapping("/gestioncompte")
	public ModelAndView modifyPersonne(HttpServletRequest request, @RequestParam("nom") String nom
			, @RequestParam("prenom") String prenom, @RequestParam("sexe") String sexe) {
		HttpSession session = request.getSession();
		Integer id_personne = (Integer)session.getAttribute("id_personne");
		if(id_personne == null)return new ModelAndView("redirect:/");
		Personne personne = personneRepository.getOne(id_personne);
		if(personne == null) return new ModelAndView("redirect:/");
		if(nom == null || prenom == null || sexe == null || nom.length() > 30 || prenom.length() > 30 || sexe.length() > 1) {
			ModelAndView model = new ModelAndView("gestioncompte");
			model.addObject("nom", nom);
			model.addObject("prenom", prenom);
			model.addObject(sexe, "selected");
			model.addObject("error","Vos données sont incorrects. Il faut que le nom et le prénom contiennent au maximum 30 caractères");
			return model;
		}
		personne.setId(id_personne);
		personne.setNom(nom);
		personne.setPrenom(prenom);
		personne.setSexe(sexe);
		personneRepository.save(personne);
		session.setAttribute("nom", nom);
		session.setAttribute("prenom", prenom);
		session.setAttribute(sexe, "selected");
		ModelAndView model = new ModelAndView("gestioncompte");
		return model;
	}
	
	@GetMapping("/delete")
	public ModelAndView delete(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Integer id_personne = (Integer)session.getAttribute("id_personne");
		if(id_personne != null) {
			personneRepository.deleteById(id_personne);
		}
		session.invalidate();
		return new ModelAndView("redirect:/");
	}
	
	@GetMapping("/gestionAffectation")
	public ModelAndView gestionAffectation(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Integer id_personne = (Integer) session.getAttribute("id_personne");
		if(id_personne == null) return new ModelAndView("redirect:/");
		Personne personne = personneRepository.getOne(id_personne);
		if(personne == null) return new ModelAndView("redirect:/");
		
		List<Adresse> adresses_personne = adresseRepository.findByPersonne(personne);
		List<Adresse> adresses_non_personne = adresseRepository.findByPersonneNotEqual(personne);
		ModelAndView model = new ModelAndView("gestionAffectation");
		model.addObject("adresses_personne", adresses_personne);
		model.addObject("adresses_non_personne", adresses_non_personne);
		return model;
	}
	
	@PostMapping("/addadresse")
	public ModelAndView addAdresse(HttpServletRequest request, @RequestParam("rue") String rue
			, @RequestParam("code_postal") String code_postal, @RequestParam("ville") String ville) {
		HttpSession session = request.getSession();
		Integer id_personne = (Integer)session.getAttribute("id_personne");
		if(id_personne == null) return new ModelAndView("redirect:/");
		Personne personne = personneRepository.getOne(id_personne);
		if(personne == null) return new ModelAndView("redirect:/");
		if(rue == null || code_postal == null || ville == null || rue.length() > 30 || code_postal.length() > 30 || ville.length() > 30) {
			ModelAndView model = new ModelAndView("gestionAffectation");
			model.addObject("rue", rue);
			model.addObject("code_postal", code_postal);
			model.addObject("ville", ville);
			model.addObject("error","Vos données sont incorrects. Il faut que les données contiennent au maximum 30 caractères");
			List<Adresse> adresses_personne = adresseRepository.findByPersonne(personne);
			List<Adresse> adresses_non_personne = adresseRepository.findByPersonneNotEqual(personne);
			model.addObject("adresses_personne", adresses_personne);
			model.addObject("adresses_non_personne", adresses_non_personne);
			return model;
		}
		Adresse adresse = new Adresse(rue, code_postal, ville, personne);
		adresseRepository.save(adresse);
		return new ModelAndView("redirect:/gestionAffectation");
	}
	
	@GetMapping("/affecter/{id}")
	public ModelAndView affecter(HttpServletRequest request, @PathVariable("id") Integer id) {
		HttpSession session = request.getSession();
		Integer id_personne = (Integer)session.getAttribute("id_personne");
		if(id_personne == null) return new ModelAndView("redirect:/");
		Personne personne = personneRepository.getOne(id_personne);
		if(personne == null) return new ModelAndView("redirect:/");
		Adresse adresse = adresseRepository.getOne(id);
		adresse.setPersonne(personne);
		adresseRepository.save(adresse);
		return new ModelAndView("redirect:/gestionAffectation"); //TODO
	}
	
	@GetMapping("/gestionadresses")
	public ModelAndView gestionAdresses(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Integer id_personne = (Integer)session.getAttribute("id_personne");
		if(id_personne == null) return new ModelAndView("redirect:/");
		Personne personne = personneRepository.getOne(id_personne);
		if(personne == null) return new ModelAndView("redirect:/");
		List<Adresse> adresses = adresseRepository.findByPersonne(personne);
		ModelAndView model = new ModelAndView("gestionAdresses");
		model.addObject("adresses", adresses);
		return model;
	}
	
	@GetMapping("/modifyadresse/{id}")
	public ModelAndView getModifyAdresse(HttpServletRequest request, @PathVariable("id") Integer id) {
		HttpSession session = request.getSession();
		Integer id_personne = (Integer)session.getAttribute("id_personne");
		if(id_personne == null) return new ModelAndView("redirect:/");
		Personne personne = personneRepository.getOne(id_personne);
		if(personne == null) return new ModelAndView("redirect:/");
		Adresse adresse = adresseRepository.getOne(id);
		if(adresse == null) return new ModelAndView("redirect:/gestionadresses");
		ModelAndView model = new ModelAndView("detailsAdresse");
		model.addObject("adresse", adresse);
		return model;
	}
	
	@PostMapping("/modifyadresse/{id}")
	public ModelAndView modifyAdresse(HttpServletRequest request, @PathVariable("id") Integer id, @RequestParam("rue") String rue
			, @RequestParam("code_postal") String code_postal, @RequestParam("ville") String ville) {
		HttpSession session = request.getSession();
		Integer id_personne = (Integer)session.getAttribute("id_personne");
		if(id_personne == null) return new ModelAndView("redirect:/");
		Personne personne = personneRepository.getOne(id_personne);
		if(personne == null) return new ModelAndView("redirect:/");
		Adresse adresse = adresseRepository.getOne(id);
		if(adresse == null) return new ModelAndView("redirect:/gestionadresses");
		adresse.setVille(ville);
		adresse.setRue(rue);
		adresse.setCode_postal(code_postal);
		adresseRepository.save(adresse);
		return new ModelAndView("redirect:/gestionadresses");
	}
	
	@GetMapping("/deleteadresse/{id}")
	public ModelAndView deleteAdresse(HttpServletRequest request, @PathVariable("id") Integer id) {
		HttpSession session = request.getSession();
		Integer id_personne = (Integer)session.getAttribute("id_personne");
		if(id_personne == null) return new ModelAndView("redirect:/");
		Personne personne = personneRepository.getOne(id_personne);
		if(personne == null) return new ModelAndView("redirect:/");
		adresseRepository.deleteById(id);
		return new ModelAndView("redirect:/gestionadresses");
	}
	
	
	
}
