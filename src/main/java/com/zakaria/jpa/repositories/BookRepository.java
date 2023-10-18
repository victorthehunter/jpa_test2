package com.zakaria.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.zakaria.jpa.entities.Book;
import com.zakaria.jpa.entities.BookPK;

@Repository
public interface BookRepository extends JpaRepository<Book, BookPK>, JpaSpecificationExecutor<Book> {
	
}