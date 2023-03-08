package cl.factorit.cursos.datasource.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.factorit.cursos.datasource.model.user;
import cl.factorit.cursos.datasource.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
// RequiredArgsConstructor nos permite inyectar el repositorio mediante constructor
@RequiredArgsConstructor
public class UserSeriveImpl implements UserService {
	
	private final UserRepository userRepository;
	
	
	@Override
	public user save(user newuser) {
		// TODO Auto-generated method stub
		return userRepository.save(newuser);
	}

	@Override
	public List<user> findAllByUsers(String username) {
		// TODO Auto-generated method stub
		return userRepository.findUsername(username);
	}

	@Override
	public List<user> findAll() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public void deleteCompilado(long id) {
		// TODO Auto-generated method stub
		userRepository.deleteById(id);
	}
	
	// llamdo del dto
	@Override
	public user findByid(long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public user delete(long id) {
		// TODO Auto-generated method stub
		userRepository.deleteById(id);
		return null;
	}
// ----------- login
	@Override
	public void save(long id, user newuser) {
		// TODO Auto-generated method stub
		newuser.setId(id);
		userRepository.save(newuser);
	}

@Override
public user findByUsername(String username) {
	// TODO Auto-generated method stub
	return userRepository.findByUsername(username);
}

@Override
public void deleteL(long id) {
	// TODO Auto-generated method stub
	userRepository.deleteById(id);
}






	
	

}
