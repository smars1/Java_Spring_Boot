package cl.factorit.cursos.datasource.dto;

import lombok.Data;

@Data
public class UserDTO {
	
	private Long id;
	
	private String username;
	
	private String password;
	
	private boolean enabled = true;

	//private Person person;
	private PersonDTO person;
	
	// creamos el atributo que nos pasa el JWT
	private String token;

}
