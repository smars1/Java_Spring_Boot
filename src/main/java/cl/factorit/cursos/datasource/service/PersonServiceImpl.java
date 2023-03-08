package cl.factorit.cursos.datasource.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.factorit.cursos.datasource.model.Person;
import cl.factorit.cursos.datasource.repository.PersonRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
	
	//inyectamos PersonRepository
	private final PersonRepository	personRepository;
	
	@Override
	public List<Person> findAllName(String name) {
		// findname_2 is a native query than created it in PersonRepository
		return personRepository.findName_2(name);
	}

	@Override
	public Person save(Person person) {
		// TODO Auto-generated method stub
		//return null; ahora guardamos una persona, ahora vamos al constructor al main datasource
		return personRepository.save(person);
	}

	@Override
	public List<Person> findAll() {
		// TODO Auto-generated method stub
		return personRepository.findAll();
	}
	
	// no nos permite usarlo en endpoints debido a que no instancia la clase Person, solo nos permite usarlo al compilar
	@Override
	public void deleteCompilado(long id) {
		// TODO Auto-generated method stub
		personRepository.deleteById(id);
	}

	
	// LLamado desde el PersonDTO con modelMapper
	@Override
	public Person findById(long id) {
		// TODO Auto-generated method stub  cambiamos .get() por orElse(null); ahora podemos recibir nulos cuando un recurso no existe en la base de datos
		return personRepository.findById(id).orElse(null);
	}

	// Este nos permite usarlo en los endpoints dado que esta instanciando la clase Person
	@Override
	public Person delete(long id) {
		// TODO Auto-generated method stub
		personRepository.deleteById(id);
		return null;
	}
	
	// delete por peticion con cuerpo
	@Override
	public Person deletebybody(Person person) {
		// TODO Auto-generated method stub
		personRepository.delete(person);
		return null;
	}


}
