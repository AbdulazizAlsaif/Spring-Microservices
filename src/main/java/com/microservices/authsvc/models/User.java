package com.microservices.authsvc.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @NoArgsConstructor  @AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private  Long id;
	private String name;
	private String password;
	

	
	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}

	
	  
	
	  
}


