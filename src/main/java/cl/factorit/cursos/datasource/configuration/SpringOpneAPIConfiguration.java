package cl.factorit.cursos.datasource.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Datasource API", version ="1.0", description = "Datasource Component API"))
// agregamo una cabezera de autentificacion, Nota: bearer es una convecion para los Authorization token 
@SecurityScheme(bearerFormat = "jwt", name = HttpHeaders.AUTHORIZATION, scheme = "bearer", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER)
public class SpringOpneAPIConfiguration {

}
