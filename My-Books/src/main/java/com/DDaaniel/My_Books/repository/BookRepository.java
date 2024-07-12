package com.DDaaniel.My_Books.repository;

import com.DDaaniel.My_Books.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Long> {
}
