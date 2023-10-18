package com.zakaria.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.zakaria.jpa.entities.BookEm;
import com.zakaria.jpa.entities.BookPkEm;

@Repository
public interface BookEmRepository extends JpaRepository<BookEm, BookPkEm>, JpaSpecificationExecutor<BookEm> {
	
}