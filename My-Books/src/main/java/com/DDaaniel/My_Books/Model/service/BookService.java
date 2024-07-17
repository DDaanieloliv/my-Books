package com.DDaaniel.My_Books.Model.service;

import com.DDaaniel.My_Books.Model.domain.dto.AuthorDTO;
import com.DDaaniel.My_Books.Model.domain.dto.BookDTO;
import com.DDaaniel.My_Books.Model.domain.dto.MessageResponseDTO;
import com.DDaaniel.My_Books.Model.domain.entity.Author;
import com.DDaaniel.My_Books.Model.domain.entity.Book;
import com.DDaaniel.My_Books.Model.exception.BookNotFoundException;
import com.DDaaniel.My_Books.Model.domain.repository.BookRepository;
//import com.DDaaniel.My_Books.Model.mapper.BookMapper;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/*
*   builder():

O método builder() é um método estático gerado pela anotação @Builder do Lombok. Ele
cria e retorna uma nova instância do Builder para a classe onde é chamado. Esse Builder
é um objeto auxiliar que facilita a configuração de um objeto da classe que contém o
método builder().

*   build():

O método build() é um método do Builder que finaliza a construção do objeto, utilizando
os valores que foram configurados no Builder, e retorna a instância do objeto
configurado.
*/


@Service
public class BookService /*implements BookService*/ {


    //private final BookMapper bookMapper;

    private final BookRepository booksRepository;

    //private final BookMapper bookMapper = BookMapper.INSTANCE;

    @Autowired
    public BookService( BookRepository bookRepository) {
        this.booksRepository = bookRepository;
    }


//    @Override
    public MessageResponseDTO create(BookDTO dto) {

        //Book bookToSave = bookMapper.toModel(dto);

        // Converter BookDTO para Book
        Book bookToSave = booksRepository.save(convertToEntity(dto));
        //Book book = booksRepository.save(bookToSave);
        return MessageResponseDTO.builder()
                .message("Book created with ID: " + bookToSave.getId()).build();
                //´.message()´ equivale a: ´setMessage()´//
    }

//    @Override
    public BookDTO findById(Long id) throws BookNotFoundException {
        Book book = booksRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        //return bookMapper.toDTO(book);
        return convertToDTO(book);
    }


    private Book convertToEntity(BookDTO bookDTO) {
        Author author = new Author();
        author.setId(bookDTO.getAuthor().getId());
        author.setName(bookDTO.getAuthor().getName());

        return Book.builder()
                .name(bookDTO.getName())
                .pages(bookDTO.getPages())
                .chapters(bookDTO.getChapters())
                .isbn(bookDTO.getIsbn())
                .publisherName(bookDTO.getPublisherName())
                .author(author)
                .build();
    }

    private BookDTO convertToDTO(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .name(book.getName())
                .pages(book.getPages())
                .chapters(book.getChapters())
                .isbn(book.getIsbn())
                .publisherName(book.getPublisherName())
                .author(AuthorDTO.builder()
                        .id(book.getAuthor().getId())
                        .name(book.getAuthor().getName())
                        .build())
                .build();
    }

}
