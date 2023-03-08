package cl.factorit.cursos.datasource;


/*
 * import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
*/

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

//import java.time.LocalDate;


/*
 *	import cl.factorit.cursos.datasource.dto.CommuneDTO;
import cl.factorit.cursos.datasource.dto.PersonDTO;
import cl.factorit.cursos.datasource.dto.UserDTO;
import cl.factorit.cursos.datasource.mapper.CommuneMapper;
import cl.factorit.cursos.datasource.mapper.PersonMapper;
import cl.factorit.cursos.datasource.mapper.UserMapper;
import cl.factorit.cursos.datasource.model.Commune;
import cl.factorit.cursos.datasource.model.Person;
import cl.factorit.cursos.datasource.model.user;
import cl.factorit.cursos.datasource.service.CommuneService;
import cl.factorit.cursos.datasource.service.PersonService;
import cl.factorit.cursos.datasource.service.UserService;
 */

//import lombok.extern.log4j.Log4j2;
//@Log4j2

// eliminamos la autoconfiguracion de sprinSecurity utilizanod el main 
@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class DatasourceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(DatasourceApplication.class, args);
	}
	
	
	/*
	// inyectamos el servicio Bean
	@Autowired
	private PersonService personService;
	
	@Autowired
	private PersonMapper personMapper;

	//------------------------------------
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserMapper userMapper;
	
	//------------------------------------
	
	@Autowired
	private CommuneService communeService;
	
	@Autowired
	CommuneMapper communeMapper;

	public static void main(String[] args) {
		SpringApplication.run(DatasourceApplication.class, args);
	}
	
	@PostConstruct
	void test() {
		
	//-----------------------Buenas practicas ----------------------------------------------------
		
		
		//---------- Geramos un DataInput ------------------------------
		
		//Person
		Person person = personService.findById(8L);
		PersonDTO dto = personMapper.toDTO(person);
		log.info(dto);
		
		// User 
		user newuser = userService.findByid(9L);
		UserDTO userDTO = userMapper.toDTO(newuser);
		log.info(userDTO);
		
		//Commune
		Commune commnune = communeService.findByid(10L);
		CommuneDTO communeDTO = communeMapper.toDTO(commnune);
		log.info(communeDTO);

	}
 */
}
