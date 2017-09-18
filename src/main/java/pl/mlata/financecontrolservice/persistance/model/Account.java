package pl.mlata.financecontrolservice.persistance.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter 
@NoArgsConstructor
@EqualsAndHashCode
public class Account {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@Column
	private String type;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@OneToMany
	private List<Account> childAccounts;
	
	@ManyToOne
	@JoinColumn(name="parent_account_id", nullable=true)
	private Account parentAccount;
}
