package com.zakaria.jpa.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "book_em")
public class BookEm {

	@EmbeddedId
    private BookPkEm bookPkEm;
	
	private String name;
	
}

