package cl.factorit.cursos.datasource.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;     ----->        ya viene en Jpa
import cl.factorit.cursos.datasource.model.Person;

// org.springframework.data.jpa.repository.JpaRepository repository ya incluye a @Repository 
//@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{
	
	List<Person>findAllByName(String name);
	
	//                    tabla alias : p
	@Query("select p from Person p where p.name = :name")
	List<Person>findName_1(String name);
	
	// Forma nativa, nos deja escribir SQL puro
	@Query(value = "select * from Persons  where name = :name",  nativeQuery = true)
	List<Person>findName_2(String name);

	

}