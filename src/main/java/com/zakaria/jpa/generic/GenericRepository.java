package com.zakaria.jpa.generic;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<T, ID> extends JpaRepository<T, ID> {
	
	<T> Optional<T> findById(ID id, Class<T> selectorClass);
	
	<T> List<T> findAllById(ID id, Class<T> selectorClass);
	
}
