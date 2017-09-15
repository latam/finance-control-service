package pl.mlata.financecontrolservice.persistance.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter 
@NoArgsConstructor
public class Operation {
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
	private LocalDateTime date;
	
	@ManyToOne
	@JoinColumn(name="to_account_id", nullable=false)
	private Account toAccount;
	@ManyToOne
	@JoinColumn(name="from_account_id", nullable=true)
	private Account fromAccount;
	
	@Column
	private BigDecimal amount;
}
