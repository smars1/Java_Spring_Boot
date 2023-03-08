package cl.factorit.cursos.datasource.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {
	
	@Bean
	ModelMapper modelMapper(){
		return new ModelMapper();
	}
	
}
