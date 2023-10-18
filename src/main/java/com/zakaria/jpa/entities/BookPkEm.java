package com.zakaria.jpa.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class BookPkEm implements Serializable {
	
	private Long userId2;
	private Long companyId2;
	
	public BookPkEm(Long userId2, Long companyId2) {
		this.userId2 = userId2;
		this.companyId2 = companyId2;
	}
	
}
