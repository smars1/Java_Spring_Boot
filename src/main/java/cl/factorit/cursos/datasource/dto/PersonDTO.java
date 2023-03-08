package cl.factorit.cursos.datasource.dto;

import java.time.LocalDate;

import cl.factorit.cursos.datasource.model.Commune;
import lombok.Data;

@Data
public class PersonDTO {
	// Notas para Git Doc: en el controller declaramos como se trabajaran los dto, el objeto que se va guardar en la solicitud con un atributo json tiene que hacer match
	// con el nombre del atributo del DTO
	// en java por convencion no deberiamos tener "_" en los nombres de nuestros atributos, tomar en cuenta para futuros proyectos,
	// si no usar una Mays para separar birthDate, 
	
	/* Si tenemos atributos fuera de la convencion de java podemos utilar la anotacion @JsonProperty("birthDate") esto puede sobrescribir un atributo que este fuera de la convencion de java
	
	* Ejemplo de uso:
	* 	
	* 	@JsonProperty("birthDate")
	* 	private LocalDate birh_date;
	* 
	*/ 
	private Long id;
	
	// relacionamos con la tabla commune ----------------------
	private Commune commune;
	// relacionamos con la tabla commune ---------------------------

	private String name;
	
	private String email;
	
	private LocalDate birth_date;
	
}
