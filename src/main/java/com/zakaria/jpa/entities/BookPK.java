package com.zakaria.jpa.entities;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookPK implements Serializable {
	
	private Long userId;
	private Long companyId;
	
	public BookPK(Long userId, Long companyId) {
		this.userId = userId;
		this.companyId = companyId;
	}
	
}
