package com.zakaria.jpa.dtos;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageData<T> {

	public String status;
	public String message;
	public Page<T> data;
	public Boolean success;
	
}