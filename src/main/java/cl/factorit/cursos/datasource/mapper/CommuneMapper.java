package cl.factorit.cursos.datasource.mapper;

import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import cl.factorit.cursos.datasource.dto.CommuneDTO;
import cl.factorit.cursos.datasource.model.Commune;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommuneMapper {
	
	private final ModelMapper modelMapper;
	
	// recibimos un modelo de communes y lo mapeamos a un dto, generamos un DTO
	public CommuneDTO toDTO(Commune commune) {
		return Objects.isNull(commune) ? null : modelMapper.map(commune, CommuneDTO.class);
		
	}
	
	// // Convertimos un DTO  a  modelo de users: genereamos un model
	public Commune toModel(CommuneDTO communeDTO) {
		return Objects.isNull(communeDTO) ? null : modelMapper.map(communeDTO, Commune.class);
	}
	
	// pasa una lista de modelo users a una lista de UserDTO, llamamos al model users en el toDTOList
		public List<CommuneDTO> toDTOList(List<Commune> communes) {
			return Objects.isNull(communes) ? null : communes.stream().map(this::toDTO).toList();
		}
	
	
		

}
