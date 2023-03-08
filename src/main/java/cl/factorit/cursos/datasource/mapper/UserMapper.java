package cl.factorit.cursos.datasource.mapper;

import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import cl.factorit.cursos.datasource.dto.UserDTO;
import cl.factorit.cursos.datasource.model.user;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper {
	
	private final ModelMapper modelMapper;
	
	// recibimos un modelo de users y lo mapeamos a un dto, generamos un dataOutput
	public UserDTO toDTO(user newuser) {
		return Objects.isNull(newuser) ? null : modelMapper.map(newuser, UserDTO.class);
	}
	
	// Convertimos un DTO  a  modelo de users: genereamos un DataInput
	public user toModel(UserDTO userDTO){
		return Objects.isNull(userDTO) ? null : modelMapper.map(userDTO, user.class);
	}
	
	// pasa una lista de modelo users a una lista de UserDTO, llamamos al model users en el toDTOList
	public List<UserDTO> toDTOList(List<user> users){
		return Objects.isNull(users) ? null : users.stream().map(this::toDTO).toList();
	}

}
