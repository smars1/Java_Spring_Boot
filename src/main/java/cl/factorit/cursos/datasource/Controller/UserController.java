package cl.factorit.cursos.datasource.Controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import cl.factorit.cursos.datasource.configuration.TokenManager;
import cl.factorit.cursos.datasource.dto.UserDTO;
import cl.factorit.cursos.datasource.mapper.PersonMapper;
import cl.factorit.cursos.datasource.mapper.UserMapper;
import cl.factorit.cursos.datasource.model.Person;
import cl.factorit.cursos.datasource.model.user;
import cl.factorit.cursos.datasource.service.PersonService;
import cl.factorit.cursos.datasource.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	
	private final PersonService personService;
	private final PersonMapper personMapper;
	
	private final UserService userService;
	private final UserMapper userMapper;
	
	private final PasswordEncoder passwordEncoder;
	private final TokenManager tokenManager;
	
	//ahora al agregar las dependecias de Person que ya habiamos creado antes, podemos crear a la persona y despues al user en el mismo metodo de user controller
	@PostMapping("/register")
	@Operation(description = "operacion de inicio de sesion de usuario y su perfil. ", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, 
	content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
	@ExampleObject (value = """
			{                         
			 "username":"Char.Mander",
			 "password":"string",
			 "person":{
				 "name":"Diego Atzin",
				 "email":"diego.pineda@factorit.com", 
				 "birthDate":"1998-05-08",
				 "commune":{ 
				 "id":100
				 }
				}
			}
			""")})}))
	
	public ResponseEntity<UserDTO> save(@RequestBody UserDTO userDTO){
		user userfind = userService.findByUsername(userDTO.getUsername());
		//si el usuario no es null, es que ya existe y no permitimos que se repita ese username
		if (userfind != null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario ya existe.");
		user newuser = userMapper.toModel(userDTO);
		Person person = personMapper.toModel(userDTO.getPerson());
		person = personService.save(person);
		newuser.setPerson(person);
		//newuser.setPassword(passwordEncoder.encode(newuser.getPassword())); //<-- puede harce asi o para una mejor lectura de codigo
		String passEncrypt = passwordEncoder.encode(newuser.getPassword());
		newuser.setPassword(passEncrypt);
		newuser = userService.save(newuser);
		// no es buena practica devolver la password asi que para no devolverla le declaramos que despues de guardarla nos arroje un null password
		newuser.setPassword(null);
		return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDTO(newuser));
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserDTO> login(@RequestBody UserDTO userDTO){
		user newuser = userService.findByUsername(userDTO.getUsername());
		// validamos el si el usaurio no existe o es nulo
		if (newuser == null)
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		// validamos si la password hace match con la password encriptada de nuestra DB, si no hace match nos arroja un HttpStatus.UNAUTHORIZED
		if (passwordEncoder.matches(userDTO.getPassword(), newuser.getPassword()))
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		// si, si existe devuelve datos a si que antes de devolver le pasamos null a password a si no se devuelve esta
		newuser.setPassword(null);
		String token = tokenManager.generateJwtToken(newuser.getUsername()); // <-- necesitamos trabajar con el dto, debemos enviarle el jwt por UserDTO
		userDTO = userMapper.toDTO(newuser);
		userDTO.setToken(token);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(userDTO);
	}
	
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll(){
		List<user> users = userService.findAll();
		List<UserDTO> dtos = userMapper.toDTOList(users);
		return ResponseEntity.status(HttpStatus.OK).body(dtos);
		//return ResponseEntity.ok(dtos);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable("id") long id, @RequestBody UserDTO userDTO){
		user find = userService.findByid(id);
		if (find == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userMapper.toDTO(find));
		user newuser = userMapper.toModel(userDTO);
		newuser = userService.save(newuser);
		return ResponseEntity.status(HttpStatus.OK).body(userMapper.toDTO(newuser));
	}
	
	@DeleteMapping("delete-{id}")
	public ResponseEntity<UserDTO> delete(@PathVariable("id") long id, @RequestBody UserDTO userDTO){
		user find = userService.findByid(id);
		if (find == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userMapper.toDTO(find));
		user newuser = userService.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userMapper.toDTO(newuser));
	}
	
	


	@DeleteMapping("/curso-delete")
	@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
	public void deleteL(long id) {
		// TODO Auto-generated method stub
		userService.deleteL(id);
	}

	@PutMapping("/curso-user")
	@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
	public void save(long id, UserDTO userDTO) {
		// TODO Auto-generated method stub
		userDTO.setId(id);
		user newuser = userMapper.toModel(userDTO);
		userService.save(newuser);
	}

	@GetMapping("/curso-user")
	@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
	public UserDTO findByUsername(String username) {
		user newuser = userService.findByUsername(username);
		return userMapper.toDTO(newuser);
	}
	

}
