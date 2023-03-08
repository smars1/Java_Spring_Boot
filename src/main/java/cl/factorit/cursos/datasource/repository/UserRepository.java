package cl.factorit.cursos.datasource.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



import cl.factorit.cursos.datasource.model.user;

public interface UserRepository extends JpaRepository<user, Long> {

	// agregamos para login 
	user findByUsername(String username);
	
	// solucionado escribi mal el metodo ojo ya esta corregido
	List<user>findAllByUsername(String username);
	
	// Forma nativa, nos deja escribir SQL puro
	@Query(value = "select * from users  where username = :username",  nativeQuery = true)
	List<user>findUsername(String username);
	
	
}
