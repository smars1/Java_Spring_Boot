package cl.factorit.cursos.datasource.Controller;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import cl.factorit.cursos.datasource.dto.PersonDTO;
import cl.factorit.cursos.datasource.service.PersonService;

@SpringBootTest
public class PersonControllerTest {
	
	@Autowired
	private PersonController personController;
	
	@MockBean
	private PersonService personService;
	
	// Elegimos algun metodo a probar de UserController como por ejemplo el findAll() del @Get
	
	@Test
	void findAllTest() {
		// resultado de la capa de servicios
		
		Mockito.when(personService.findAll()).thenReturn(Collections.emptyList());
		
		ResponseEntity<List<PersonDTO>> personList = personController.findAll();
		Assertions.assertTrue(personList.getBody().isEmpty());
	}
	
}
