# Capasitacion Java Spring Boot Backend

Spring Tools 4 es la próxima generación de herramientas Spring para su entorno de codificación favorito. Reconstruido en gran parte desde cero, proporciona soporte de clase mundial para desarrollar aplicaciones empresariales basadas en Spring, ya sea que prefiera Eclipse, Visual Studio Code o Theia IDE.

``Nota Sobre micriservicios``: Una arquitectura de ``microsercios`` es un enfoque que para desarrollar una aplicacion de software como una serie de pequeños servicios, cada uno ejecutandose de forma autonoma y comonucicandose entre si, por ejemplo a traves de peticiones ``HTTP`` a sus ``API``

# Requisitos 
- Java 17 (jdk)
- Maven
- Eclipse STS (Spring Tool Suite)

# Contenido general 

[``Repositorio de dependencias``]()
[URL Repository / Dependencias para pom.xml]()
| ``Contenido sobre dependencias`` |
| ------ |
[Spring-boot-starter-web]()
[Swagger y Springdoc-openapi]()
[Spring Starter Security]()
[``Capas``]()


# URL Repository / Dependencias para pom.xml

- [ Spring-boot-starter ](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-parent)
- [lombok](https://mvnrepository.com/artifact/org.projectlombok/lombok)
- [spring-boot-starter-data-jpa](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa)
- [modelmapper](https://mvnrepository.com/artifact/org.modelmapper/modelmapper)
- [mysql-connector-j](https://mvnrepository.com/artifact/com.mysql/mysql-connector-j)
- [spring-boot-starter-web](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web)
- [springdoc-openapi-ui](https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-ui)



## Spring-boot-starter-web 

| Galery |
| ----- |
[Ques es spring-boot-starter-web? ]()
[Started Web Properties]()
[Anotaciones]()

Spring-boot-starter-web es una depenendencia que funciona y es utilizado principalmente como un iniciador para crear aplicaciones web, incluye RESTFull, utilizando Spring MVC, usa ``Tomcat`` como contenedor inscrustado predeterminado.  


Tiene dos caracteristicas importantes:
- Es compatible con el desarrollo web.
- Se puede configurar automaticamente.

### Started Web Properties
Despues de configurar las depenciaas preparar la propiedades application, ejemplo Metadata al que le damos el nombre de la aplicacion

```.properties
# Application
spring.application.name=ms-datasource

# Server/ starter web properties
server.port=${SERVER_PORT:8080}
server.servlet.context-path=/${spring.application.name}/v1.0
server.error.include-binding-errors=always
server.error.include-exception=true
server.error.include-message=always

```
### Anataciones
Esta dependencia nos permite utlizar anotaciones como ``@Restcontroller``, ``@ResquestMapping``.   Donde ``@Restcontroller`` marca la clase como un controlador donde cada método devuelve un objeto de dominio en lugar de una vista. Al anotar una clase con esta anotación, ya no necesita agregar ``@ResponseBody`` a todos los métodos ``@RequestMapping``. Por otro lado  ``@RequestMapping`` nos permite utlizar las anotaciones de los metedos de Restful Api 

|Metodos RestFul Api| Anotacion | 
|-----------------|---------------|
|POST: crear un recurso nuevo.| ``@PostMapping``| 
|PUT: modificar un recurso existente.| ``@PutMapping`` |
|GET: consultar información de un recurso.| ``@GetMapping`` |
|DELETE: eliminar un recurso determinado.| ``@DeleteMapping`` |
|PATCH: modificar solamente un atributo de un recurso| ``@PatchMapping``|


## Swagger y Springdoc-openapi

| Glosario | 
|-----------------|
[Documentamentacion oficial de Springdoc-openapi](https://springdoc.org/)
[Rest API Tutorial, Swagger UI](https://restfulapi.net/resource-naming/)
[Ejemplo de implementacion en la capa de configuracion]()
[Ejemplo implentacion de Springdoc-openapi en la capa Cotroller]()


Swagger es un especificacion abierta para definir las API REST. un documento de swagger es el equivalente de la API REST de un documento ``WSDL`` para un sevicio basado en ``SOAP``.

La dependencia de ``Springdoc-openapi`` ayuda a automatizar la generacion de documentacion de la API para los proyectos que utilizan ``Spring-Boot``.
``Springdoc-openapi`` funciona examinando la aplicacion en tiempo de ejecucion para inferir a la semantica de API en funcion de las configuraciones de ``Spring``, la estructura de ``clases`` y varias ``anotaciones``

### Ejemplo de implementacion en la capa de configuracion

Aqui se puede ver como implementamos dentro de la capa de configuracion una clase a la le con figuramos el ``@OpenAPIDefinition`` en el cual podemo agregar una descripcion de nuestra API y su version.
```.java
package cl.factorit.cursos.datasource.configuration;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Datasource API", version ="1.0", description = "Datasource Component API"))
public class SpringOpneAPIConfiguration {


}
```
### Ejemplo de implementacion en la capa Controller
Podemos utilizar la dependecia de ``Springdoc-openapi`` para documentar nuestras funciones o en este caso de ejemplo en particular podemos describir como funciona nuestro metodo ``register``, pero esto puede aplicarse a acualquier metodo.

```.java
//ahora al agregar las dependecias de Person que ya habiamos creado antes, podemos crear a la persona y despues al user en el mismo metodo de user controller
	@PostMapping("/register")
	@Operation(description = "operacion de inicio de sesion de usuario y su perfil. ", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, 
	content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
	@ExampleObject (value = """
			{                         
			 "username":"Char.Mander",
			 "password":"string",
			 "person":{
				 "name":"Diego Atzin",
				 "email":"diego.pineda@factorit.com", 
				 "birthDate":"1998-05-08",
				 "commune":{ 
				 "id":100
				 }
				}
			}
			""")})}))
	
	public ResponseEntity<UserDTO> save(@RequestBody UserDTO userDTO){
		user userfind = userService.findByUsername(userDTO.getUsername());
		//si el usuario no es null, es que ya existe y no permitimos que se repita ese username
		if (userfind != null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario ya existe.");
		user newuser = userMapper.toModel(userDTO);
		Person person = personMapper.toModel(userDTO.getPerson());
		person = personService.save(person);
		newuser.setPerson(person);
		//newuser.setPassword(passwordEncoder.encode(newuser.getPassword())); //<-- puede harce asi o para una mejor lectura de codigo
		String passEncrypt = passwordEncoder.encode(newuser.getPassword());
		newuser.setPassword(passEncrypt);
		newuser = userService.save(newuser);
		// no es buena practica devolver la password asi que para no devolverla le declaramos que despues de guardarla nos arroje un null password
		newuser.setPassword(null);
		return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDTO(newuser));
	}
```
## Spring Starter Security


| Glorario de Springframework.boot.autoconfigure.security | 
|------------------------------|
[Spring Starter Security]()
[Desabilitar la sseguridad por default]()


``Spring Security`` es un marco de autenticacion y control de acceso potente y altamente personalizable. Tambien es el estandar facto para proteger aplicaciones basadas``Spring Boot``. Este se enfoca en proporcionar auteticacion y autorizacion a las aplicaciones Java.
El potencial de ``Spring sucurity`` se encuentra en la facilidad con la que se puede ampliar para cumplir con sus requisitos personalizados.


## Implenentacion de Spring  Security
Deberemos agregar la seguridad en la capa de configuracion.

### Ejemplo de template de Spring Security
```.java
package cl.factorit.cursos.datasource.configuration;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
	
	//inyectamos tokenManager
	private final TokenManager tokenManager;
	
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/swagger-config", "/users/register", "/users/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterAfter(new OncePerRequestFilter() {
                	
                	// virificamos si el valor de la cabezera es o no nulo o si no comienza con la convencion "Bearer" no va generar la autentificacion
                    @Override
                    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
                        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                            filterChain.doFilter(request, response);
                            return;
                        }
                        // si hay un JWT y comienza con bearer, este se valida con el token manager, pero primero le limpiamos la convecion "Bearer" y luego lo validamos  
                        authHeader = authHeader.replace("Bearer ", "");
                        if (!tokenManager.validateJwtToken(authHeader)) {
                            filterChain.doFilter(request, response);
                            return;
                        }
                        // si el JWT es valido le pasamos el getContext a UsernamePasswordAuthenticationToken
                        SecurityContext sc = SecurityContextHolder.getContext(); 
                        UsernamePasswordAuthenticationToken auth =new UsernamePasswordAuthenticationToken(tokenManager.getSubject(authHeader), null, Collections.emptyList());
                        sc.setAuthentication(auth);
                        filterChain.doFilter(request, response);
                    }
                }, BasicAuthenticationFilter.class)
                

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .and()
                .csrf().disable();
        return http.build();
    }
	
    
    // nos permite encriptar lo utilzamos para la password
    @Bean
    PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
	
}
```

### Desabilitar la sseguridad por default

Una vez implemetada la configuracion de seguridad esta implementara un password por defecto cada vez que ejecutemos el codigo por lo que deberemos ir a nuestro main principal y excluir esta conficuracion, para asi nosotros poder  trabajar con nuestra configuracion. esto lo podemos hacer agregando la siguiente caracteristicas ``(exclude = {UserDetailsServiceAutoConfiguration.class})`` en la anotacion de ``@SpringBootApplication``, asi como se muestra debajo

```.java
// eliminamos la autoconfiguracion de sprinSecurity utilizanod el main 
@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class DatasourceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(DatasourceApplication.class, args);
	}
```


## IO JsonWebToken 
El ``JsonWebToken`` es un estandar de internet propuesto para crear datos con firma opcional y encriptacion opcional cuya carga util contiene un ``JSON`` que contiene cierta cantidad de reclamos los llamados claims en ingles. Los Token se firman mediante una clave secreta.

El ``JWT`` se utiliza cuando comunmente para manejar la auteticacion en aplicaciones web o moviles, dado que se utilza para enviar datos entre aplicaciones o servicios y garantizar que sean validos y seguros. 

- jjwt-gson
- jjwt-impl 

|Anotaciones| Descripcion | 
|-----------------|---------------|
| ``@SecurityScheme`` | Nos permite agregar una cabezara o en ingles header, de autentifacion|


## Agregano SecurityScheme

Debemos agregar @SecurityScheme a la configuracion de la documentacion de ``Swagger`` la cual es la que generamos con la dependencia de ``@OpenAPI``.

### Agregando @SecurityScheme 

```.java
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
```


### Template de implementacion de JWT
Podemos implementar el JWT  y confugurarlo, ya sea su algoritmo su valor o tiempo de duracion de validacion, el siguiente template puede ser usado para otros proyectos de las ultimas versiones, podemos esconder el secreto en la propiedades del proyecto.

```.java
package cl.factorit.cursos.datasource.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Component
public class TokenManager {
    public static final long TOKEN_VALIDITY = 3600;

    private final SecretKeySpec secretKeySpec;
    private final JwtParser jwtParser;
    
    public TokenManager(@Value("${jwt.secret}") String jwtSecret) {
        SignatureAlgorithm sa = SignatureAlgorithm.HS256;
        this.secretKeySpec = new SecretKeySpec(jwtSecret.getBytes(), sa.getJcaName());
        this.jwtParser = Jwts.parserBuilder().setSigningKey(secretKeySpec).build();
    }
	
	// Expiacion de token 
    public String generateJwtToken(String username) {
        return Jwts.builder().setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(secretKeySpec).compact();
    }
	// validamos que nuestro token sea valido, debe ser igual al que se genero y si el token no a expirado
    public boolean validateJwtToken(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        boolean isTokenExpired = claims.getExpiration().before(new Date());
        return !isTokenExpired;
    }

    public String getSubject(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

}
```




## Lombok-ide
- [lombok-ide](https://www.baeldung.com/lombok-ide)

| Galery |
| ----- |
[ Nomenclatura de nombre de packages ]()
[ Anotaciones Importantes ]()
[Agregar dependencias necesarias para trabajar con MySQL](https://github.com/smars1/Java_Spring_Boot/blob/main/README.md#agregar-dependencias-necesarias-para-trabajar-con-mysql)
[Realizar CRUD de un registro de una tabla en base de datos](https://github.com/smars1/Java_Spring_Boot/blob/main/README.md#realizar-crud-de-un-registro-de-una-tabla-en-base-de-datos)


# Nomenclatura de nombre de packages 
Para nombrar los packages se utilizara para este entorno el nombre del ``group`` con el nombre de mas el nombre del ``artifact`` como se puede ver en la siguiente imagen. 
![image](https://user-images.githubusercontent.com/42829215/214492948-178b033f-d763-45a3-a022-2f7afb0ed92f.png)
  
# Lombok 
Es  una libreria de java que a tra ves de anotaciones nos reduce codigo que codificamos, es decir nos ahorra tiempo y mejora la legibiidad del mismo. Las transformaciones que realizan se hacen en tiempo de ejecucion.


 # Anotaciones Importantes

| @Tags |
|-------|
[SpringBootApplication](https://github.com/smars1/Java_Spring_Boot/blob/main/README.md#springbootapplication)
[Creacion de Api rest](https://github.com/smars1/Java_Spring_Boot/blob/main/README.md#creacion-de-api-rest)
[Repositoy](https://github.com/smars1/Java_Spring_Boot/blob/main/README.md#repositoy)
[Service](https://github.com/smars1/Java_Spring_Boot/blob/main/README.md#service)
[Configuration](https://github.com/smars1/Java_Spring_Boot/blob/main/README.md#configuration)
[Bean](https://github.com/smars1/Java_Spring_Boot/blob/main/README.md#bean)
[Autowired](https://github.com/smars1/Java_Spring_Boot/blob/main/README.md#autowired)
[Value](https://github.com/smars1/Java_Spring_Boot/blob/main/README.md#value)


#  Desarrollo por capas una API 

|-- Capas --|
|-----------|
[main]()
[``@Entity`` en la creacio de modelos](https://github.com/smars1/Java_Spring_Boot#entity-en-la-creacion-de-modelos)
[Capa repository, uso de ``@Repository``](https://github.com/smars1/Java_Spring_Boot/blob/main/README.md#capa-repository-uso-de-repository)
[ Capa service, usando ``@Service``](https://github.com/smars1/Java_Spring_Boot/blob/main/README.md#capa-service-usando-service)
[Dto tier, usando ``@Data``](https://github.com/smars1/Java_Spring_Boot#dto-tier-usando-data-en-dto)
[Configuration tier, uso de ``@Configuration`` y ``@Bean``](https://github.com/smars1/Java_Spring_Boot/blob/main/README.md#configuration-tier-uso-de-configuration-y-bean)
[Mapper, usando ``@Component`` y ``@RequiredArgsConstructor``](https://github.com/smars1/Java_Spring_Boot/blob/main/README.md#mapper-usando-component-y-requiredargsconstructor)
[Capa Controller, uso de ``@Restcontroller``](https://github.com/smars1/Java_Spring_Boot#capa-controller-uso-de-restcontroller)




# ``@Entity``, en la creacion de modelos
 Utilizamos ``@Entity`` cuando marcamos una clase como una entidad en Java Persistence Api (JPA). Esta anotacion se usa para crear un modelo en la DB, es utilizada     
 para mapear las clases de una tabla de una DB y define las columnas de la tabla, tambien es usada para definir las caracteristicas del modelo y sus data types.


 - Mapeo de una tabla en una base de datos
 - No contiene metodos
 - Solo getters y setters
 - Contiene los mismos campos que la tabla en la bd
 - Contiene relaciones con otras entidades

 ### Ejemplo Creacion de un modelo de una tabla de DB llamada Persons
 ![image](https://user-images.githubusercontent.com/42829215/215180867-53ab8f90-fb2f-4e86-a156-6363d67fb44e.png)

# Capa repository, uso de ``@Repository``

``@Repository`` es una anotación utilizada para marcar una clase como repositorio de datos en la API de persistencia de Java (JPA). Esta anotación se utiliza para definir la interfaz para acceder y manipular datos en una base de datos. También se utiliza para proporcionar una capa de abstracción entre la aplicación y la base de datos, lo que facilita la realización de cambios en la base de datos sin afectar el código de la aplicación.
 
 - Usar ``@Repository``
 - Crear metodos que herenden de JpaRepository
 - Tiene metodos para obtener registros : findAll, findById, existById, save, deleteById, etc
 - No contiene metodos de logica de negocio
 - Recibe y envia entidades
 ### Ejemplo de creacion de repository
 
 ![image](https://user-images.githubusercontent.com/42829215/215205074-0fba585a-238d-47a3-8a9f-352a03317342.png)
 
# Capa service, usando ``@Service``

``@Service`` es una anotación utilizada para marcar una clase como servicio en Spring Framework. Esta anotación se usa para definir la interfaz para proporcionar servicios a otras clases en la aplicación. También se utiliza para proporcionar una capa de abstracción entre el código de la aplicación y la lógica empresarial de la aplicación.

 - se usa @Service en la clase Implement no en la interfaz
 - Contiene logica de negocio
 - Maneja DTO
 - Transforma DTO a entidad o viceversa
 - Intermediario entre Controller y Repository
 - Recibe entidades del Repository y manda DTO al Controller
 
 ### Ejemplo de una interfaz servicio
 ![image](https://user-images.githubusercontent.com/42829215/215207597-7e26386a-71c4-4f8a-8c21-662ba365e010.png)
 ### Ejamplo de una implementacion de sercio en una clase
 ![image](https://user-images.githubusercontent.com/42829215/215207882-bb17eea0-aede-4f5d-b5d9-3e4b5229b85c.png)
 
  # Dto tier, usando ``@Data`` en Dto
 La capa ``Dto`` es la encargada de transferir datos entre la base de datos y la aplicacion, El nivel DTO es responsable de asignar objetos de la aplicación a objetos de transferencia de datos (DTO) y viceversa. El nivel DTO también proporciona métodos para acceder y modificar datos en la base de datos.
 
 - No mapean tabla de bd
 - Solo getters y setters
 - Son la respuesta al request (endpoint)
 - Diseñadas para la vista
 - Puede contener info de muchas entidades
 
Nota: Cuando estamos trabajando en microservicios o un componente de acceso a la base de datos  tenemos una clase model, cuando nosostros queremos enviar una clase por un request, como buena practica no debemos enviar la clase si no que debemos convertir la clase en un ``DTO``, este son las clases que no llevan ningun tipo de logica negocios, solo sirven para transportar informacion por http. Podemos usar la dependencia de ``modelmapper`` para hacer menos complejo el mapaeado  de la clase a la clase ``DTO``, se puede de hacer otras formas pero usar ``modelmapper`` nos simplifica el trabajo.
 
 ``@Data`` es una anotación de Lombok que se usa en el nivel DTO para generar métodos getter y setter para una clase. Esta anotación genera el código repetitivo necesario para acceder y modificar campos en una clase DTO. Se utiliza para proporcionar una forma rápida y fácil de acceder y modificar datos en el nivel DTO de una aplicación.
 ### Ejemplo creacion de un Dto
 ![image](https://user-images.githubusercontent.com/42829215/215198795-739eb6bf-96db-4f75-a5a4-a0efca15549b.png)
 
  # Configuration tier, uso de ``@Configuration`` y ``@Bean``
``@Configuration`` es una anotación utilizada en Spring Framework para marcar una clase como clase de configuración. Esta anotación se utiliza para definir los ajustes de configuración de la aplicación, como los ajustes de conexión a la base de datos, los ajustes de autenticación o los ajustes de registro. También se utiliza para proporcionar una capa de abstracción entre el código de la aplicación y los ajustes de configuración.

``@Bean`` es una anotación utilizada en Spring-Boot para definir un bean en el contexto de la aplicación Spring. Se utiliza para crear y configurar beans en los archivos de configuración de Spring. ``@Bean`` permite a los desarrolladores acceder y manipular directamente las instancias de bean en su código. También se puede usar para registrar beans existentes con el contexto de la aplicación.

### Ejemplo teplapete de configution para mapper
![image](https://user-images.githubusercontent.com/42829215/215221494-f97d86d9-b015-4122-807c-cbf1b4cef19b.png)


 
 # Mapper, usando ``@Component`` y ``@RequiredArgsConstructor``
Debemos configurar una capa mapper para transformar los modelos ``DTO`` ya que cuando uno devuelve bien la respuesta debemos convertir a ``DTO``.
El nivel Mapper es responsable de mapear datos de un formato a otro. Se utiliza para convertir datos de un formato a otro, como de un formato de base de datos a un formato XML, o de un formato XML a un formato JSON. El nivel Mapper también proporciona métodos para transformar datos de un formato a otro, como mapear un objeto de base de datos a un objeto XML o mapear un objeto XML a un objeto JSON.

- Usa ``@Component``
- Usa ``@RequiredArgsConstructor``
  
 ### Ejemplo de un ``mapper model``
 ![image](https://user-images.githubusercontent.com/42829215/215220377-83474b9b-2d39-47b9-9693-f774a6e316ec.png)
 

# Capa controller, uso de ``@RestController``
El nivel de controlador es responsable de manejar las solicitudes del usuario de la aplicación. Es responsable de enrutar las solicitudes al servicio apropiado en la aplicación y de devolver la respuesta al usuario. El nivel de controlador también proporciona métodos de autenticación, autorización y validación de datos.

 - Usar @RestController
 - No contiene logicaa
 - Recibe y envia DTO
 - Intermediario entre Usuario y Service
 
 ``@RestController`` es una anotación utilizada en Spring Framework para marcar una clase como controlador para manejar solicitudes REST. Esta anotación se usa para definir la interfaz para manejar solicitudes REST, como solicitudes GET, POST, PUT y DELETE. También se utiliza para proporcionar una capa de abstracción entre el código de la aplicación y el protocolo HTTP.
 
 
 



## SpringBootApplication
SpringBootApplication es la etiqueta principal de Spring, define que el proyecto parta como un proyecto Spring no java
```.java
@SpringBootApplication
```

## Creacion de Api rest
``@RestController`` - ``@Controller`` son usados para la creacion de Api rest pero ``@RestController`` ya contiene encapsulado a ``@Controller``

```.java
@RestController
```





## Repositoy
Se usa para identificar la capa de acceso a datos, esta sera la que va hacer consultas a la base de datos

## Service
Se utiliza para la logica de negocios donde uno hace un calculo o variacion que viene desde el ``Front``

## Configuration
Es una notacion que se utiiza para configurar alguna clases  dependencia 
```.java
@Configuration
```

## Bean
Clases estatica dentro de la aplicacion
```.java
@Bean
```

## Autowired
Se puede inyectar en dependencia en spring y utilizar los beans
```.java
@Autowired
```

## Value
Se ocupa para poder rescatar valores de propiedades que estan en el archivo propertis de spring
```.java
@
```
# Anotaciones importantes

## Getter 

```.java
@Getter
```
## Setter 

```.java
@Setter
```

## ToString

```.java
@ToString
```

## Data

```.java
@Data
```

# Construciones

## RequiredArgsConstructor

```.java
@RequiredArgsConstructor
```


## AllArgsConstructor

```.java
@AllArgsConstructor
```

## Log4j2

```.java
@log4j2
```


# Creando un proyecto en spring tool

las carpetas principales son:

- ``src/main/java``
- ``src/main/resources``

son la misma carpeta pero con difente ruta en su 3 ramificacion para dentro. son las mas importantes a nivel carpeta

``target``Es solamente para cuando compilamos la aplicacion, aqui apareseran las clases compiladas, ``target`` por lo general no se 
sube al repositorio del codigo.

En HELP.md se pueden poner instrucciones o comentarios para que otro programdor pueda leerlos 


# Agregar dependencias necesarias para trabajar con MySQL

## Moldel mapper incluirlo a las dependencias
- Pagina de instalación 
- [Modelmapper](https://mvnrepository.com/artifact/org.modelmapper/modelmapper)
- Para este proyecto se instalo la [version : 3.1.1](https://mvnrepository.com/artifact/org.modelmapper/modelmapper/3.1.1)

# Realizar CRUD de un registro de una tabla en base de datos

## Step 1 
Despues de configurar las depenciaas preparar la propiedades se puede utilizar la plantilla en application.properties que dada para Jpa y MySQL que se nos dio el curso
![image](https://user-images.githubusercontent.com/42829215/215017979-0d3daf1e-4630-4234-9dcc-785c4edd1434.png)

```.properties
# Step 1 : despues de configurar las depenciaas preparar la propiedades
# Plantilla para JPA

# Application, Metadata al que le damos el nombre de la aplicacion

# Application
spring.application.name=ms-datasource

# Server
server.port=${SERVER_PORT:8080}
server.servlet.context-path=/${spring.application.name}/v1.0
server.error.include-binding-errors=always
server.error.include-exception=true
server.error.include-message=always

# JPA
spring.jpa.open-in-view=false
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
spring.jpa.show-sql = true

# DataSource
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.datasource.url=${DATASOURCE_URL:jdbc:mysql://<You IP>:3306/student_db}
spring.datasource.hikari.username=${DATASOURCE_USERNAME:student_user}
spring.datasource.hikari.password=${DATASOURCE_PASSWORD:student_pass}

# SQL
spring.sql.init.mode=always
spring.sql.init.continue-on-error=false



```

# step 2 : Creacion de un modelo

comenzamos a crear un modelo, creamos el package model para posteriormente crear la clase para este caso se llamara ``Commune`` lo que indicara que sera la tabla mas debil puesto que Commune significa que no tendra relacion con otras tablas. 

![image](https://user-images.githubusercontent.com/42829215/215018212-f14fe5ae-2040-4c7d-83ed-e7ba8d934dbd.png)

Tenemos 3 tablas 
-	Persons
  - ![image](https://user-images.githubusercontent.com/42829215/215018307-bee78310-fe63-4d28-af4e-237a37dece93.png)

-	Commune
  - ![image](https://user-images.githubusercontent.com/42829215/215018335-71e2157e-2680-4a1e-93ac-84a8deeb45f5.png)

 
-	Users


Es importante ver las características de las tablas dado a que debemos darle las características al comenzar a construir nuestro modelo.  Comenzaremos a construir con la tabla commune

### Mapeando una tabla

importamos @Entity y @Table a este se le pasa el nombre de la tabla 
![image](https://user-images.githubusercontent.com/42829215/215018396-a41c2a32-53e9-4a34-b832-ce662e804ac1.png)

```.java
@Id

0@Column
```
En las nuevas anotaciones le indicamos que es un id que viene en nuestra tabla person con @Id y en @Column le pasaremos los nombres y  caracteristicas que tienen las tablas que estamos mapeando.

```.java
@Id
@Column(name = "id", nullable = false)
```
![image](https://user-images.githubusercontent.com/42829215/215018970-fa0e449d-eaf5-4084-b296-c05b9af7f380.png)

Aquí ya hemos terminado el mapeado de una clase java hacía MySQL, aunque aun falta agregar los muteadores y adaptadores
Agregamos @Getter y @Setter de lombok para tener el mapeado completo, en este caso o para esto mas bien no es recomendable @Data.

![image](https://user-images.githubusercontent.com/42829215/215019022-0ddef824-8e96-41c1-b588-f9f877d0782d.png)

![image](https://user-images.githubusercontent.com/42829215/215019081-10c9bd35-0b56-497c-8c3b-2a837a38ec2a.png)

### Template del mode de tabla commune
```.java
package cl.factorit.cursos.datasource.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

// step 3 importamos @Entity y @Table a este se le pasa el nombre de la tabla

@Entity
@Table(name = "persons")
@Getter	
@Setter
public class Commune {
	//@Id specifies the primary key of an entity
	@Id
	@Column(name = "id", nullable = false)
	private Long id; 
	
	@Column(name = "name" )
	private String name;

}
```
Haremos el mismo procedimiento con las otras 2 tablas, solo que ahora agregaremos algunas anotaciones mas como @GeneratedValue la cual nos permitirá generar un id correlativo cada vez que se cree una nueva persona. Hay diferentes formas de generar el como se creara el id al crear un nuevo objeto en este caso persona para este se usara entity como se ve en la imagen anterior

```.java
@GeneratedValue(strategy = GenerationType.IDENTITY)
```
En este caso aquí le estamos diciendo con IDENTITY nos va crear una nueva id al momento de crear un usuario




### Template creado de: Modelo de la tabla Persons
```.java
package cl.factorit.cursos.datasource.model;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
@Entity 
@Table(name = "Persons") 
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Person {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id", nullable = false)
private Long id;
// relacionamos con la tabla commune ----------------------
@ManyToOne(fetch = FetchType.EAGER, optional = false)
@JoinColumn(name = "commune_id", nullable = false)
@ToString.Exclude
private Commune commune;
// relacionamos con la tabla commune ---------------------------
@Column(name = "name", nullable = false, length = 100)
private String name;
@Column(name ="email", nullable = false, length = 100)
private String email;
@Column(name = "birth_date", nullable = false)
private LocalDate birth_date;

```
Este fragmento de código nos permitira relacionar la columna commune_id de la tabla Commune con la de Persons

```.java
// relacionamos con la tabla commune ----------------------
@ManyToOne(fetch = FetchType.EAGER, optional = false)
@JoinColumn(name = "commune_id", nullable = false)
@ToString.Exclude
private Commune commune;
// relacionamos con la tabla commune ---------------------------
```

## Creacion de modelo de tabla Users
Ahora hacemos el modelo de Users solo que para importar las librerías de javax.persistence usaremos el comodin * para llamar a todas y no typear una por una.

```.java
package cl.factorit.cursos.datasource.model;
// el * es un comodin llama a todas las dependencias
import javax.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class user {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id", nullable = false)
private Long id;
@Column(name = "username", nullable = false, length = 100)
private String username;
@Column(name = "password", nullable = false, length = 100)
private String password;
@Column(name = "enabled", nullable = false)
private boolean enabled = true;
//Relacionamos la columna de person_id de la tabla Persons con la tabla Users
@ManyToOne(fetch = FetchType.EAGER, optional = false)
@JoinColumn(name = "person_id", nullable = false)
@ToString.Exclude
private Person person;
}
```
Aqui terminamos de mopaear nuestras tablas

# paso 3 : Creacion de repository y creacion de interfaces

Para poder editar nuestros datos deberemos crear interfases esta se crearan en ``Repository``, la actualización de los datos se hara en las interfaces 
Por lo que creamos otro package al que le llamaremos Repository y crearemos una interface dentro. Luego crearemos una lista de Person 

![image](https://user-images.githubusercontent.com/42829215/215020944-cd69e72f-5a45-449e-bf91-77b522a55603.png)

Extendemos el interfaz 

```.java

public interface PersonRepository extends JpaRepository<Person, Long>{
List<Person>
}

```

Aqui se pone el nombre del modelo y el tipo de atributo que tiene el id 
```.java
JpaRepository<Person, Long>
```
Estructura:
```.java
JpaRepository<Name Model, Id_atribute>
```
Ahora dentro de esta interfaz podremos hacer las @Query 

![image](https://user-images.githubusercontent.com/42829215/215021138-3fcb49de-5b40-4e79-b680-c0bd4a735266.png)
### Template de PersonRepository 
```.java
public interface PersonRepository extends JpaRepository<Person, Long>{
List<Person>findAllByName(String name);
// tabla alias
@Query("select p from Person p where p.name = :name")
List<Person>findName_1(String name);
// Forma nativa, nos deja escribir SQL puro
@Query(value = "select * from Persons where name = :name", nativeQuery = true)
List<Person>findName_2(String name);
}
```
Recordar importar su dependencia de Jpa, las las querys las podremos escribir en una combinación de lenguajes entre JQL y SQL
El ``:`` antes del name hace referencia a un argumento del mismo

Forma nativa para podre escribir en lenguaje SQL puro

```.java
// Forma nativa, nos deja escribir SQL puro
@Query(value = "select * from Persons where name = :name", nativeQuery = true)
List<Person>findAllByName(String name);
```

# step 4: creando una interfaz para PersonService
Ahora Podemos crear un nuevo package dentro datasource el cual es nuestro package principal y crearemos una interfaz, la  llamaremos PersonService

![image](https://user-images.githubusercontent.com/42829215/215021726-f3b1270c-8d7f-4529-8300-fea5f7418120.png)

Construimos la interfaz
![image](https://user-images.githubusercontent.com/42829215/215021771-9eb98bbf-956c-49b0-925e-54327dfc8161.png)

### Template de interfaz PersonService
```.java
package cl.factorit.cursos.datasource.service;
import java.util.List;
import cl.factorit.cursos.datasource.model.Person;
public interface PersonService {
List<Person> findAllName(String name);
}

```
Y posteriormente implementamos en una clase dentro de service por lo que creamos una clase dentro 
![image](https://user-images.githubusercontent.com/42829215/215021935-d563cbee-f36c-4b3e-8dd9-9defb29257a1.png)

Implementamos ``PersonService`` querando de la siguiente manera

![image](https://user-images.githubusercontent.com/42829215/215021995-09efddd7-81c3-4e65-85ff-b7096d01d3e7.png)

Hacemos click en l warning y damos add uniplemented methods para hacer @Override
Tambien importamos java.util.List;

![image](https://user-images.githubusercontent.com/42829215/215022046-b73d9037-4aae-4916-af2e-8597e1facfe4.png)

Como ``PersonRepository`` ahora forma parte del contexto spring, lo podemos ver como un @Bean ahora podemos inyectarlo y lo haremos para ello ahora definmos @Service

![image](https://user-images.githubusercontent.com/42829215/215022165-94a190b8-4fa8-4e16-abcc-3d7ff294e4e5.png)

```.java
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
//inyectamos PersonRepository
private final PersonRepository personRepository;

```
depues modificamos el @Override 
le quitamos el null y ponemos lo que inyectaremos

```.java
@Override
public List<Person> findAllName(String name) {
return personRepository.findAllByName(name);
}
```
![image](https://user-images.githubusercontent.com/42829215/215022322-f4d0cc8e-d6a9-49fd-93eb-9c197ea4599d.png)
Ahora Creamos el @Autowire, para ello nos dirigimos a datource en el package main

y ahi creamos el @Autowire 

```.java
// inyectamos el servicio @Bean
@Autowired
private final PersonService personService;
DetasourceApplication(PersonService personService) {
this.personService = personService;
}
```

posteriormente definimos ``@PostConstrucor`` y ``@Log4j2``
y creamos el constructor de la siguiente manera para imprimir el dato que estamos llamando

```.java
@PostConstruct
void test() {
log.info(personService.findAllName("Hector Pacheco"));
}

```
![image](https://user-images.githubusercontent.com/42829215/215026787-970f76b5-6479-4861-a85e-dd006d0a3d28.png)

Ahora en PersonService creamous un service para guardar una persona

```.java
// devolvemos la person que tendra nuestra id Creamos otro override en PersonServiceImpl para agregar este
Person save(Person person);

```
![image](https://user-images.githubusercontent.com/42829215/215031960-ad9a2c5d-a3db-4698-a7c8-fabee42899ee.png)
Ahora vamos y creamos el ``@Override`` correspondiente querando de la siguiente manera esl el ultimo
![image](https://user-images.githubusercontent.com/42829215/215031981-7029e672-38b7-4887-99a6-839293da7a48.png)
seteamos los parametros de las tablas en data source en test asi como el nuevo ``log.info(personService.findAllName("Hector Pacheco"));``
![image](https://user-images.githubusercontent.com/42829215/215032003-ab2a4c5a-650b-43b6-aab8-b38ec1e19249.png)

```.java
@PostConstruct
void test() {
//ahora modifocamos en test 
Person person = new Person();
person.setName("Ejemplo");
person.setEmail("Charmander@********");
person.setBirth_date(LocalDate.now());
person.setId(50L);
Commune commune = new Commune();
person.setCommune(commune );
personService.save(person);
log.info(personService.findAllName("Hector Pacheco"));
}

```

Y ahora podemos generar el método para ejecutarlo

```.java
List<Person>findAll();
```

![image](https://user-images.githubusercontent.com/42829215/215032187-a817243d-b3bf-48fa-94e8-4ace6e1ddbe8.png)
Y hacemos otro #Override para findAll() que creamos

![image](https://user-images.githubusercontent.com/42829215/215032234-dba21bb8-7ff8-4ab4-bbdc-ad8f6e72b5cb.png)

y agregamos lo siguiente een datasource
```.java
log.info(personService.findAll());
```
![image](https://user-images.githubusercontent.com/42829215/215032299-79bced19-a8a7-4f40-ac97-2567d4a5f87d.png)
Podemos jugar llamando a la funcion con el primer log con el segfundo llamara a todos sin especificar el servicio

