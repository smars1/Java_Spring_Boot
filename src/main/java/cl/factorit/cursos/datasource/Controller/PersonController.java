package cl.factorit.cursos.datasource.Controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.factorit.cursos.datasource.dto.PersonDTO;
import cl.factorit.cursos.datasource.mapper.PersonMapper;
import cl.factorit.cursos.datasource.model.Person;
import cl.factorit.cursos.datasource.service.PersonService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
// SecurityRequirement hace que la autentificacion se presente en todos los metodos de esta clase, se puede usar a nivel clase como aqui por ejemplo o a nivel metodo
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
//agregamos el log para ver por consola, Nota: solo para fines practicos
//@Log4j2
public class PersonController {
	
	private final PersonService personService;
	private final PersonMapper personMapper;
	
	
	//obtenemos la lista de personas de persons
	@GetMapping
	public ResponseEntity<List<PersonDTO>> findAll(){
		//log.info(auth);
		List<Person> persons = personService.findAll();
		List<PersonDTO> dtos = personMapper.toDTOList(persons);
		return ResponseEntity.ok(dtos);
	}
	
	//devolvemos la creacion de un registro nuevo	
	@PostMapping
	public ResponseEntity<PersonDTO> save(@RequestBody PersonDTO dto) {
		Person person = personMapper.toModel(dto);
		person = personService.save(person);
		//return ResponseEntity.ok(null); is a better practice use status(HttpStatus.CREATED)  than  ok(null);
		return ResponseEntity.status(HttpStatus.CREATED).body(personMapper.toDTO(person));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable("id")long id, @RequestBody PersonDTO dto){
		Person find = personService.findById(id);
		if (find == null)
			return ResponseEntity.notFound().build();
		dto.setId(id);
		Person person = personMapper.toModel(dto);
		person = personService.save(person);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("delete-{id}")
	public ResponseEntity<PersonDTO> delete(@PathVariable("id")long id, @RequestBody PersonDTO dto){
		Person find = personService.findById(id); 
		if(find == null)
			return ResponseEntity.notFound().build();
		Person person = personService.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(personMapper.toDTO(person));
		//return ResponseEntity.ok().build();
	}
	
	// this endpoint doing delete person by body 
	@DeleteMapping("deletebybody-{id}")
	public ResponseEntity<PersonDTO> deletebody(@PathVariable("id") long id , @RequestBody PersonDTO dto) {
		Person find = personService.findById(id);
		if(find == null)
			return ResponseEntity.notFound().build();
		Person person = personMapper.toModel(dto);
		person = personService.deletebybody(person);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(personMapper.toDTO(person));
	}
	
	
}
