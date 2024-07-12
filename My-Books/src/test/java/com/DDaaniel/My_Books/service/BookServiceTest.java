package com.DDaaniel.My_Books.service;

import com.DDaaniel.My_Books.dto.BookDTO;
import com.DDaaniel.My_Books.entity.Book;
import com.DDaaniel.My_Books.exception.BookNotFoundException;
import com.DDaaniel.My_Books.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.DDaaniel.My_Books.utils.BookUtils.createFakeBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void whenGivenExistingIdThenReturnThisBook() throws BookNotFoundException {
        Book expectedFoundBook = createFakeBook();

        when(bookRepository.findById(expectedFoundBook.getId())).thenReturn(Optional.of(expectedFoundBook));

        BookDTO bookDTO = bookService.findById(expectedFoundBook.getId());

        assertEquals(expectedFoundBook.getName(), bookDTO.getName());
        assertEquals(expectedFoundBook.getIsbn(), bookDTO.getIsbn());
        assertEquals(expectedFoundBook.getPublisherName(), bookDTO.getPublisherName());
    }
/*
--EXPLICAÇÃO DO MÉTODO--

_________

1. Anotação @Test:

´´´
@Test
´´´

    **  Indica que este método é um caso de teste que será executado pelo framework de
        testes JUnit.


2. Assinatura do Método:

´´´
void whenGivenExistingIdThenReturnThisBook() throws BookNotFoundException {
´´´

    **  O método é definido como void, pois não retorna nenhum valor.

    **  O nome do método (whenGivenExistingIdThenReturnThisBook) é descritivo e segue
        uma convenção comum em testes de descrever o cenário e o resultado esperado.

    **  Declara que pode lançar uma exceção BookNotFoundException.


3. Criação de um Livro Falso:

´´´
Book expectedFoundBook = createFakeBook();
´´´

    **  Cria um objeto Book simulado que será usado no teste.

    **  createFakeBook() é um método auxiliar (não mostrado aqui) que provavelmente
        cria e retorna uma instância de Book com dados de teste.


4. Mock do Comportamento do Repositório:

´´´
when(bookRepository.findById(expectedFoundBook.getId())).thenReturn(Optional.of(expectedFoundBook));
´´´

    **  Usa o Mockito para definir o comportamento esperado do bookRepository.

    **  Quando o método findById for chamado com o ID do livro expectedFoundBook.getId(),
        ele deve retornar um Optional contendo o expectedFoundBook.

    **  Isso simula a busca de um livro existente no repositório.


5. Chamada do Serviço:

´´´
BookDTO bookDTO = bookService.findById(expectedFoundBook.getId());
´´´

    **  Chama o método findById do bookService com o ID do livro esperado.

    **  Espera-se que o serviço retorne um BookDTO correspondente ao livro encontrado.


6. Asserções:

´´´
    assertEquals(expectedFoundBook.getName(), bookDTO.getName());
    assertEquals(expectedFoundBook.getIsbn(), bookDTO.getIsbn());
    assertEquals(expectedFoundBook.getPublisherName(), bookDTO.getPublisherName());
´´´

    **  Verifica se os valores no BookDTO retornado pelo serviço são iguais aos
        valores do expectedFoundBook.

    **  Usa o método assertEquals do JUnit para comparar os valores esperados e
        os valores reais.

    **  Se os valores não forem iguais, o teste falhará, indicando que o serviço
        não se comportou conforme o esperado.


Resumo:

Este método de teste verifica se o serviço bookService retorna corretamente os dados de um
livro quando um ID existente é fornecido. Ele faz isso simulando o comportamento do
repositório com Mockito para garantir que o método findById do repositório retorna um livro
específico. Em seguida, o teste verifica se os dados do BookDTO retornado pelo serviço
correspondem aos dados do livro esperado.

_________
*/



    @Test
    void whenGivenUnexistingIdThenNotFindThrowAnException() {
        var invalidId = 10L;

        when(bookRepository.findById(invalidId))
                .thenReturn(Optional.ofNullable(any(Book.class)));

        assertThrows(BookNotFoundException.class, () -> bookService.findById(invalidId));
    }
/*
---EXPLICAÇÃO DO MÉTODO---


_______

1. Anotação @Test:

´´´
@Test
´´´

    **  Indica que este método é um caso de teste que será executado pelo
        framework de testes JUnit.


2. Assinatura do Método:

´´´
void whenGivenUnexistingIdThenNotFindThrowAnException() {
´´´

    **  O método é definido como void, pois não retorna nenhum valor.

    **  O nome do método (whenGivenUnexistingIdThenNotFindThrowAnException) é
        descritivo e segue uma convenção comum em testes de descrever o cenário
        e o resultado esperado.


3. Definição de um ID Inválido:

´´´
var invalidId = 10L;
´´´

    **  Define uma variável invalidId com um valor 10L (um long). Este ID é
        considerado inválido ou inexistente para os fins do teste.


4. Mock do Comportamento do Repositório:

´´´
when(bookRepository.findById(invalidId))
        .thenReturn(Optional.ofNullable(any(Book.class)));
´´´

    **  Usa o Mockito para definir o comportamento esperado do bookRepository.

    **  Quando o método findById for chamado com o invalidId, ele deve retornar
        um Optional vazio. Isso é simulado com Optional.ofNullable(any(Book.class)),
        que essencialmente retorna Optional.empty() já que any(Book.class) não
        cria uma instância válida de Book.


5. Asserção para Verificar a Exceção:

´´´
    assertThrows(BookNotFoundException.class, () -> bookService.findById(invalidId));
´´´

    **  Usa o método assertThrows do JUnit para verificar se a exceção
        BookNotFoundException é lançada quando o método findById do bookService é
        chamado com o invalidId.

    **  assertThrows recebe dois argumentos:
            *   A classe da exceção esperada (BookNotFoundException.class).

            *   Uma expressão lambda que chama o método que deve lançar a
                exceção (() -> bookService.findById(invalidId)).


Resumo:

Este método de teste verifica se o serviço bookService lança a exceção
BookNotFoundException quando um ID inexistente é fornecido. Ele faz isso simulando o
comportamento do repositório com Mockito para garantir que o método findById do
repositório retorna um Optional vazio. Em seguida, o teste verifica se a chamada ao
método findById do serviço com o ID inválido lança a exceção esperada. Se a exceção não
for lançada, o teste falhará, indicando que o serviço não se comportou conforme o esperado.

_______
*/
}
