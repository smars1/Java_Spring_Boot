package cl.factorit.cursos.datasource.Controller;

import java.util.List;

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

import cl.factorit.cursos.datasource.dto.CommuneDTO;
import cl.factorit.cursos.datasource.mapper.CommuneMapper;
import cl.factorit.cursos.datasource.model.Commune;
import cl.factorit.cursos.datasource.service.CommuneService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/communes")
@RequiredArgsConstructor
public class CommuneController {

	private final CommuneService communeService;
	private final CommuneMapper communeMapper;
	
	@PostMapping
	public ResponseEntity<CommuneDTO> save(@RequestBody CommuneDTO communeDTO){
		Commune commune = communeMapper.toModel(communeDTO);
		commune = communeService.save(commune);
		return ResponseEntity.status(HttpStatus.CREATED).body(communeMapper.toDTO(commune));
	}
	
	@GetMapping
	public ResponseEntity<List<CommuneDTO>> findAll(){
		List<Commune> communes = communeService.findAll();
		List<CommuneDTO> dtos = communeMapper.toDTOList(communes);
		return ResponseEntity.status(HttpStatus.OK).body(dtos);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CommuneDTO> update(@PathVariable("id") long id, @RequestBody CommuneDTO communeDTO){
		Commune find = communeService.findByid(id);
		if (find == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(communeMapper.toDTO(find));
		Commune commune = communeMapper.toModel(communeDTO);
		commune = communeService.save(commune);
		return ResponseEntity.status(HttpStatus.OK).body(communeMapper.toDTO(commune));
	}
	
	@DeleteMapping("/delete-{id}")
	public ResponseEntity<CommuneDTO> delete(@PathVariable("id") long id, @RequestBody CommuneDTO communeDTO){
		Commune find = communeService.findByid(id);
		if  (find == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(communeMapper.toDTO(find));
		Commune commune = communeService.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(communeMapper.toDTO(commune));
	}
}
