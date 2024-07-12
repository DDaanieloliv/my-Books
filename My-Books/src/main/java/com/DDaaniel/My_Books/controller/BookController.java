package com.DDaaniel.My_Books.controller;


import com.DDaaniel.My_Books.dto.BookDTO;
import com.DDaaniel.My_Books.dto.MessageResponseDTO;
import com.DDaaniel.My_Books.exception.BookNotFoundException;
import com.DDaaniel.My_Books.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @PostMapping
    public MessageResponseDTO create(@RequestBody @Valid BookDTO book) {

       return bookService.create(book);

    }

    @GetMapping("/{id}")
    public BookDTO findById(@PathVariable Long id) throws BookNotFoundException {
        return bookService.findById(id);
    }

}
