package cl.factorit.cursos.datasource.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cl.factorit.cursos.datasource.model.Commune;

public interface CommuneRepository extends JpaRepository<Commune, Long> {
	
	List<Commune>findByName(String name);
	
	@Query(value = "Select * From communes where name = :name", nativeQuery = true)
	List<Commune>findNameCommune(String name);

	
}
