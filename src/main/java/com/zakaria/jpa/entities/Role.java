package com.zakaria.jpa.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
//@ToString
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

//	@ManyToOne
////	@JoinColumn(name = "user_id")
//	private User user;
	
//	@OneToOne
//	private User user;
	
//	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "roles")
//    private List<User> users;
}