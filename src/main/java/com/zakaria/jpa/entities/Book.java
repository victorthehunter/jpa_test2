package com.zakaria.jpa.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "books")
@IdClass(BookPK.class)
public class Book {

	@Id
    private Long userId;
	
    @Id
    private Long companyId;
	

	private String name;
	
	public Book(BookPK bookPK) {
		this.userId = bookPK.getUserId();
		this.companyId = bookPK.getCompanyId();
	}
}

