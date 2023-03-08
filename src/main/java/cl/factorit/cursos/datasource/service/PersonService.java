package cl.factorit.cursos.datasource.service;

import java.util.List;

import cl.factorit.cursos.datasource.model.Person;

public interface PersonService {
	
	// devolvemos la person que tendra nuestra id  Creamos otro override en PersonServiceImpl para agregar este
	Person save(Person person);
	
	List<Person>findAllName(String name);
	

	List<Person> findAll();
	
	//eliminar por id, creamos funcion void por que no retorna nada// solo funciona para hacer pruebas compiladas
	void deleteCompilado(long id);
	

	// este delete ya podemos usarlo para hacer test en postman, ya que tendra como instancia la clase Person
	Person delete(long id);
	
	// eliminamos por peticion con cuerpo
	Person deletebybody(Person person);
	
	
	// Llamado desde PersonDTO con el modelMapper
	Person findById(long id);
	
}
