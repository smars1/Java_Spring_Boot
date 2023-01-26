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
[SpringBootApplication]()
[Creacion de Api rest]()
[Repositoy]()
[Service]()
[Configuration]()
[Bean]()
[Autowired]()
[Value]()

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

## Service

## Configuration

## Bean

## Autowired

## Value

















