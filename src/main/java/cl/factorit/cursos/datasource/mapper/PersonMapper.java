package cl.factorit.cursos.datasource.mapper;

import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import cl.factorit.cursos.datasource.dto.PersonDTO;
import cl.factorit.cursos.datasource.model.Person;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor // <<--- nos permite usar el @bean que ya creamos en el package ModelMapperConf..
public class PersonMapper {

	private final ModelMapper modelMapper;
	
	//Recibimos un modelo person y lo mapeamos a un PersonDTO,  Genera: DataOutput
	public PersonDTO toDTO(Person person) {
		return Objects.isNull(person) ? null : modelMapper.map(person, PersonDTO.class);
	}
	
	// invertimos para convertir a un DTO a un person: Genera : DataInput
	public Person toModel(PersonDTO dto) {
		return Objects.isNull(dto) ? null : modelMapper.map(dto, Person.class);
	}
	
	//pasa una lista de modelo personas a una lista de PersonaDTO
	public List<PersonDTO> toDTOList(List<Person> persons){
		return Objects.isNull(persons) ? null : persons.stream().map(this::toDTO).toList();
	}

	
}
