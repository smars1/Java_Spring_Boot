package cl.factorit.cursos.datasource.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.factorit.cursos.datasource.model.Commune;
import cl.factorit.cursos.datasource.repository.CommuneRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommuneServiceImpl implements CommuneService {
	
	private final CommuneRepository communeRepository;
	
	@Override
	public Commune save(Commune commune) {
		// TODO Auto-generated method stub
		return communeRepository.save(commune);
	}

	@Override
	public List<Commune> findByname(String name) {
		// TODO Auto-generated method stub
		return communeRepository.findNameCommune(name);
	}

	@Override
	public void deleteCompilado(long id) {
		// TODO Auto-generated method stub
		communeRepository.deleteById(id);
	}

	@Override
	public Commune findByid(long id) {
		// TODO Auto-generated method stub
		return communeRepository.findById(id).orElse(null);
	}

	@Override
	public List<Commune> findAll() {
		// TODO Auto-generated method stub
		return communeRepository.findAll();
	}

	@Override
	public Commune delete(long id) {
		// TODO Auto-generated method stub
		communeRepository.deleteById(id);
		return null;
	}
	
}
