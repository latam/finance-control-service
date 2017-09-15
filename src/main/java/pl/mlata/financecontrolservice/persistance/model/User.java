package pl.mlata.financecontrolservice.persistance.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter 
@NoArgsConstructor
public class User {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	@Column
	private String email;
	@Column
	private String password;
	@Column
	private String roles;
}
