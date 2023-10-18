package com.zakaria.jpa.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private Byte byteId = 10;
//	private Short shortId = 1000;
//	private Integer intId = 100000;
//	private Long longId = 1000000000L;
//	private Character charId = 'A';
//	private Float floatId = 3.14f;
//	private Double doubleId = 3.14159265359;
//	private BigInteger bigIntId = BigInteger.valueOf(123);
//	private BigDecimal bigDecId = BigDecimal.valueOf(123.333);

	private String email;
	private boolean isOk = true;
	
//	@Column(columnDefinition = "json")
//    private String jsonData;
	
//	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Role role;
	
//	@OneToOne(cascade = CascadeType.ALL)
//    private Role role;
	
//	@OneToMany
//	private List<Role> roles;
	
	@ManyToMany(cascade = CascadeType.ALL)
    private List<Role> roles;
	
//	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
//    private List<Role> roles;
	
//	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Role role;

//	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
////    @BatchSize(size = 5)
//    private List<Role> roles;
}

