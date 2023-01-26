# Capasitacion Java Spring Boot Backend

Spring Tools 4 es la próxima generación de herramientas Spring para su entorno de codificación favorito. Reconstruido en gran parte desde cero, proporciona soporte de clase mundial para desarrollar aplicaciones empresariales basadas en Spring, ya sea que prefiera Eclipse, Visual Studio Code o Theia IDE.

# Requisitos 
- Java 17 (jdk)
- Maven
- Eclipse STS (Spring Tool Suite)

# URL Repository

- [ Spring-boot-starter ](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-parent)
- [lombok](https://mvnrepository.com/artifact/org.projectlombok/lombok)
- [lombok-ide](https://www.baeldung.com/lombok-ide)

| Galery |
| ----- |
[ Nomenclatura de nombre de packages ]()
[ Anotaciones Importantes ]()


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













