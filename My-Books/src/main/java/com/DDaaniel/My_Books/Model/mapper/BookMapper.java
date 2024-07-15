package com.DDaaniel.My_Books.Model.mapper;

import com.DDaaniel.My_Books.Model.domain.dto.BookDTO;
import com.DDaaniel.My_Books.Model.domain.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import javax.management.DescriptorAccess;

/*
Quando eu usamos ´Mappers.getMapper(BookMapper.class);´ e em especial
´BookMapper.class´ estamos fazendo uma referencia à propria interface ´BookMapper´,
dizendo que quero que seja gerada uma implementação(classe que implementa
os metodos de converção citados dentro da interface 'BookMapper') essa
implementação será feita em tempo de compilação e sera gerada dentro do
metodo 'Mappers.getMappers()' e atribuido a variavel 'INSTANCE' com tanto
que ambas as classes/Objetos que participarao da converção possuam os
mesmos atributos, fazendo com que ao acessar a interface seja possivel
acessar a variavel ´INSTANCE´ e utilizar os metodos de converção que ela possui.
*/

@Mapper(componentModel = "spring")
public interface BookMapper {



    /*
       -Mappers.getMapper(BookMapper.class):
           Este método chama o MapStruct para obter a implementação gerada da interface BookMapper.

       ´ MapStruct, uma biblioteca de mapeamento de objetos em Java, gera automaticamente o código
       de implementação das interfaces anotadas com @Mapper em tempo de compilação. Isso significa
       que, para a interface BookMapper, o MapStruct cria uma classe concreta que implementa os
       métodos definidos na interface (neste caso, os métodos toModel e toDTO). Tornando possivel
        fazer as converções. ´

       -BookMapper INSTANCE:
           Declara uma variável de tipo BookMapper.


       ** Convenção: (definir essa variavel como ´final´ e ´static´)

       -static:
           Garante que a variável pertence à classe BookMapper e pode ser acessada diretamente
           através da classe.

       -final:
           Assegura que a variável não pode ser modificada após ser inicializada.
       */

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    //A implementação que será gerada será uma implantação da interf. 'BookMapper.class'

    // Método para converter BookDTO em Book

    Book toModel(BookDTO bookDTO);

    // Método para converter Book em BookDTO
    BookDTO toDTO(Book book);

}


//  A lib MapStruct é uma biblioteca de mapeamento de Java que ajuda a converter entre
//  diferentes tipos de objetos de forma simples e eficiente. Ele gera automaticamente
//  o código de mapeamento durante a compilação, com base nas interfaces e anotações
//  definidas pelo desenvolvedor. Aqui estão os principais pontos sobre o que a
//  biblioteca MapStruct faz e como ela funciona:
//
//
// **Principais Funcionalidades do MapStruct
//
//    -Geração Automática de Código-
//        MapStruct gera o código de mapeamento em tempo de compilação, o que significa
//        que você não precisa escrever manualmente o código de conversão. Isso
//        economiza tempo e reduz a possibilidade de erros.
//
//    -Segurança de Tipo-
//        A conversão de tipos é verificada em tempo de compilação, o que ajuda a
//        garantir que todos os mapeamentos estejam corretos e que você não tenha
//        erros em tempo de execução devido a conversões incorretas.
//
//    -Desempenho-
//        Como o código de mapeamento é gerado em tempo de compilação, ele é
//        executado de maneira muito eficiente, semelhante ao código de conversão
//        escrito manualmente.
//
//    -Simplicidade-
//        Você define interfaces simples com métodos para os mapeamentos desejados,
//        e o MapStruct cuida do resto. Isso torna o código mais limpo e fácil
//        de manter.


//  ## Como Funciona o MapStruct
//
//  Definindo uma Interface de Mapper
//
//  Você começa definindo uma interface com métodos de mapeamento.
//
//
//  ** Anotação @Mapper
//
//  A anotação @Mapper informa ao MapStruct que esta interface deve ser usada para
//  gerar uma implementação de mapeamento. O MapStruct gera automaticamente uma
//  classe de implementação dessa interface em tempo de compilação.
//
//
//  ** Obtendo uma Instância do Mapper
//
//  A linha ´BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);´´ usa o
//  MapStruct para obter uma instância da implementação gerada. Isso cria um singleton
//  da implementação do mapeador.
//
//
//  ** Métodos de Mapeamento
//
//  Os métodos dentro da interface definem como converter de um tipo para outro.
//  Por exemplo:
//
//  ´´´
//  Book toModel(BookDTO bookDTO);
//  BookDTO toDTO(Book book);
//  ´´´
//
//  MapStruct gerará o código necessário para mapear um BookDTO para um Book e
//  vice-versa.


//  ** Exemplo de Uso
//
//  Vamos supor que você tenha as seguintes classes:
//
//  ´´´
//  public class Book {
//      private Long id;
//      private String name;
//      private Integer pages;
//      private Integer chapter;
//      private String isbn;
//      private String publisherName;
//      private Author author;
//      // Getters and setters
//  }
//  ´´´
//
//  ´´´
//  public class BookDTO {
//      private Long id;
//      private String name;
//      private Integer pages;
//      private Integer chapter;
//      private String isbn;
//      private String publisherName;
//      private AuthorDTO author;
//      // Getters and setters
//  }
//  ´´´
//
//  Você pode usar o BookMapper para converter entre Book e BookDTO assim:
//
//  ´´´
//  BookDTO bookDTO = new BookDTO(1L, "Some Book", 200, 10, "1234567890",
//                  "Some Publisher", new AuthorDTO(1L, "Author", 40));
//  Book book = BookMapper.INSTANCE.toModel(bookDTO);
//  BookDTO newBookDTO = BookMapper.INSTANCE.toDTO(book);
//  ´´´
//
//
//  Neste exemplo, BookMapper.INSTANCE.toModel(bookDTO) converte um BookDTO em um
//  Book, e BookMapper.INSTANCE.toDTO(book) faz o caminho inverso.
//
//
//  ** Benefícios do MapStruct
//
//    -Reduz o Código Boilerplate:
//      Automatiza o processo de conversão, reduzindo a quantidade de código repetitivo.
//
//    -Manutenção Facilitada:
//      Assegura que o código de mapeamento seja consistente e mais fácil de manter.
//
//    -Desempenho Eficiente:
//      Gera código eficiente em tempo de compilação, garantindo um desempenho
//      comparável ao código escrito manualmente.
//
//    -Verificações em Tempo de Compilação:
//      Detecta problemas de mapeamento em tempo de compilação, aumentando a segurança
//      do tipo.
//
//
//  ** Conclusão
//
//  MapStruct é uma ferramenta poderosa que simplifica o processo de mapeamento entre
//  objetos em Java. Ele economiza tempo, reduz erros e melhora a legibilidade e a
//  manutenção do código, tornando-o uma escolha popular para projetos que exigem
//  conversões frequentes entre diferentes tipos de objetos.



/*
##  O que é uma implementação de mapeamento?

Uma implementação de mapeamento é uma classe que contém a lógica para converter um
objeto de um tipo em um objeto de outro tipo. Por exemplo, se você tiver um objeto
BookDTO e quiser convertê-lo em um objeto Book, a implementação de mapeamento terá
o código que faz essa conversão.

-Exemplo-

Dada a interface BookMapper:

´´´
@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    Book toModel(BookDTO bookDTO);

    BookDTO toDTO(Book book);
}
´´´


** O que o MapStruct faz?

    *   Análise da Interface:
        Quando o código é compilado, o MapStruct analisa a interface BookMapper
        anotada com @Mapper.

    *   Geração de Código:
        Com base na interface, o MapStruct gera uma classe concreta que implementa
        BookMapper.

Essa classe concreta contém o código necessário para converter um BookDTO em um Book
e vice-versa.


** Exemplo de Implementação Gerada:
        Se fizéssemos isso manualmente, a implementação poderia se parecer com o
        seguinte (simplificado para entendimento):

´´´
public class BookMapperImpl implements BookMapper {

    @Override
    public Book toModel(BookDTO bookDTO) {
        if (bookDTO == null) {
            return null;
        }

        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setName(bookDTO.getName());
        book.setPages(bookDTO.getPages());
        book.setChapter(bookDTO.getChapter());
        book.setIsbn(bookDTO.getIsbn());
        book.setPublisherName(bookDTO.getPublisherName());
        book.setAuthor(new Author()); // Assumindo uma criação básica
        // Código adicional para mapear AuthorDTO para Author pode ser adicionado aqui

        return book;
    }

    @Override
    public BookDTO toDTO(Book book) {
        if (book == null) {
            return null;
        }

        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setName(book.getName());
        bookDTO.setPages(book.getPages());
        bookDTO.setChapter(book.getChapter());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setPublisherName(book.getPublisherName());
        bookDTO.setAuthor(new AuthorDTO()); // Assumindo uma criação básica
        // Código adicional para mapear Author para AuthorDTO pode ser adicionado aqui

        return bookDTO;
    }
}
´´´


** Como funciona na prática:

    *   Definição da Interface:
        O desenvolvedor define uma interface BookMapper com os métodos de mapeamento
        necessários.

    *   Compilação:
        Durante a compilação, o MapStruct gera automaticamente uma classe
        BookMapperImpl (ou nome semelhante) que implementa a interface BookMapper.

    *   Uso da Instância:
        ´BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);´
            O MapStruct fornece uma instância singleton da implementação gerada.
            Este singleton é então usado em todo o código para realizar os mapeamentos.

    *   Chamada dos Métodos:

        -Quando BookMapper.INSTANCE.toModel(bookDTO) é chamado, ele usa a
        implementação gerada para converter BookDTO em Book.

        -Quando BookMapper.INSTANCE.toDTO(book) é chamado, ele usa a implementação
        gerada para converter Book em BookDTO.


** Benefícios

    *   Redução de Código Boilerplate:
        Elimina a necessidade de escrever manualmente o código de conversão entre
        tipos, reduzindo o código repetitivo e sujeito a erros.

    *   Consistência:
        Garante que o mapeamento entre tipos seja consistente em todo o código base.

    *   Verificação em Tempo de Compilação:
        O código de mapeamento é verificado em tempo de compilação, o que reduz a
        chance de erros de mapeamento em tempo de execução.

    *   Desempenho:
        O código gerado é altamente otimizado e executa de maneira eficiente,
        comparável ao código escrito manualmente.


** Conclusão

"Gerar uma implementação de mapeamento" significa que o MapStruct analisa as
interfaces de mapeamento definidas pelo desenvolvedor e automaticamente cria
classes concretas que realizam as conversões entre os tipos especificados.
Isso facilita a manutenção do código, melhora a consistência e reduz a possibilidade
de erros.
*/