package cl.factorit.cursos.datasource.service;

import java.util.List;

import cl.factorit.cursos.datasource.model.user;

public interface UserService {

	// nota instanciamos, es buena practica que la clase sea con capital masyucula y la instancia solo lower 
	// solo que esta vez por error se declaro la clase en lower
	
	
	
	List<user>findAllByUsers(String username);
	// uso del administrador para poder ver que usuarios existen en el sistema
	List<user>findAll();
	
	void deleteCompilado(long id);
	
	
	
	// guardamos al nuevo user
	user save(user newuser);
	
	// este lo utilizamos para lo que seria el put que viene hacer el metodo que hace el update
    void save(long id, user newuser);
	
	// dto mapping 
	user findByid(long id);
	
    user delete(long id);
    
    user findByUsername(String username);
	
    void deleteL(long id);
	
}
