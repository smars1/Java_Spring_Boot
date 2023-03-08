package cl.factorit.cursos.datasource.model;

import javax.persistence.*;
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
// Ctrl + space ver autocompletar
@Entity 
@Table(name = "persons")	
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
	
	
}
