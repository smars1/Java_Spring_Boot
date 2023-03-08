package cl.factorit.cursos.datasource.service;

import java.util.List;

import cl.factorit.cursos.datasource.model.Commune;

public interface CommuneService {
	
	Commune save(Commune commune);
	
	List<Commune>findByname(String name);
	
	List<Commune>findAll();
	
	void deleteCompilado(long id);
	
	
	//dto
	Commune findByid(long id);
	
	Commune delete(long id);
}
