package com.zakaria.jpa.dtos;

import com.zakaria.jpa.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserProjection {

	private int id;
	private String email;
	private Role role;

}