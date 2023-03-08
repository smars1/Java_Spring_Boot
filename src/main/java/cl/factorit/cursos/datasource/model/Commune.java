package cl.factorit.cursos.datasource.model;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

// step 3 importamos @Entity y @Table a este se le pasa el nombre de la tabla

@Entity
@Table(name = "communes")
@Getter	
@Setter
public class Commune {
	//@Id specifies the primary key of an entity
	@Id
	@Column(name = "id", nullable = false)
	private Long id; 
	
	@Column(name = "name", nullable = false  )
	private String name;

}
