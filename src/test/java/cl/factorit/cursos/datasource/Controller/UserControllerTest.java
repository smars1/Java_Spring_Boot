package cl.factorit.cursos.datasource.Controller;

import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import cl.factorit.cursos.datasource.dto.UserDTO;
import cl.factorit.cursos.datasource.model.user;
import cl.factorit.cursos.datasource.service.UserService;

@SpringBootTest
public class UserControllerTest {
	
	// Elegimos algun metodo a probar de UserController como por el ejemplo el save del register
	
	// el autorired nos ahorra el final por que nos genera la inyeccion por atributo  
	@Autowired
	private UserController userController;
	
	// con Mockbean podemos acceder a la capa de servicios
	@MockBean
	private UserService userService;
	
	
	//probamos que la password se encripte , que salte el error de que el findbyid devuelva un null,
	// se prueba a nivel codigo no a base de datos
	@Test
	void save() {
		user mockUser = new user();
		mockUser.setId(1L);
		Mockito.when(userService.findByUsername(anyString())).thenReturn(mockUser);
		
		//Test
		UserDTO dtoReq =  new UserDTO();
		dtoReq.setUsername("CharMander X");
		//ResponseEntity<UserDTO> dtoRet = userController.save(dtoReq);
				
		// llegamos al error justificado ya que nos da que le user ya existe y ese resultado es correcto
				
		// el metodo .assertThrows pide un ejecutable estos se escriben asi   () -> o llaves { } si son varios metodos
		HttpStatus status =  Assertions
					.assertThrows(ResponseStatusException.class, () -> userController.save(dtoReq)).getStatus();
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, status);
	}

}