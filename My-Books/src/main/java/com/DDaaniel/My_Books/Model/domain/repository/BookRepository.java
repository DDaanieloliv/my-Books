package com.DDaaniel.My_Books.Model.domain.repository;

import com.DDaaniel.My_Books.Model.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Long> {
}
