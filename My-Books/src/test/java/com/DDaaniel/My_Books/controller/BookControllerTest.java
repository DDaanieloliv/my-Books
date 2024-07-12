package com.DDaaniel.My_Books.controller;

import com.DDaaniel.My_Books.dto.BookDTO;
import com.DDaaniel.My_Books.dto.MessageResponseDTO;
import com.DDaaniel.My_Books.service.BookService;
import org.hamcrest.core.Is;
//Utilizada para criar matcher que verifica se um valor é igual a outro.

import org.junit.jupiter.api.BeforeEach;
//Marca um método que deve ser executado antes de cada método de
// teste na classe.

import org.junit.jupiter.api.Test;
//Marca um método como um método de teste.

import org.junit.jupiter.api.extension.ExtendWith;
// Registra extensões para a classe de teste, permitindo
// personalizar o comportamento do JUnit, como a
// inicialização de mocks com o Mockito.

import org.mockito.InjectMocks;
//Cria uma instância da classe e injeta os mocks anotados com @Mock nesta instância.

import org.mockito.Mock;
//Cria um mock para a classe ou interface anotada.

import org.mockito.junit.jupiter.MockitoExtension;
// Inicializa automaticamente mocks, injetando-os nas
// instâncias anotadas com @InjectMocks.

import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
//Resolve métodos de controladores
// que aceitam argumentos do tipo
// Pageable, usado para paginação
// em requisições.

import org.springframework.http.MediaType;
// Representa tipos de mídia MIME, como application/json.
// Utilizada para especificar o tipo de conteúdo em requisições e
// respostas HTTP.

import org.springframework.test.web.servlet.MockMvc;
// Simula requisições HTTP para testar controladores
// Spring MVC em um ambiente de teste sem necessidade
// de um servidor real.

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
// Constrói uma instância de MockMvc que
// pode ser usada para simular requisições
// HTTP.

import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
// Renderiza objetos como JSON em
// respostas HTTP, utilizando a
// biblioteca Jackson para
// conversão.

import static com.DDaaniel.My_Books.utils.BookUtils.asJsonString;
// Converte um objeto Java em uma string
// JSON. No contexto de testes de
// controladores Spring MVC, isso é
// útil para configurar o corpo da
// requisição HTTP em formato JSON.

import static com.DDaaniel.My_Books.utils.BookUtils.createFakeBookDTO;
// Cria um objeto BookDTO falso
// para usar em testes, ajudando a
// isolar o teste e fornecer dados
// consistentes e controlados.

import static org.mockito.Mockito.when;
//Configura comportamentos esperados para mocks, definindo o que deve
// acontecer quando um método específico for chamado.

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// Cria uma requisição
// HTTP POST para usar com o
// MockMvc, simulando uma chamada
// POST ao controlador.

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// Verifica partes
// específicas da resposta JSON,
// assegurando que os dados retornados
// pelo controlador sejam como esperado.

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//Verifica o status
// da resposta HTTP, como se a
// resposta foi 200 OK ou 400 Bad Request.


// Usar static nos imports permite que você utilize métodos e campos estáticos sem precisar qualificar cada
// chamada com o nome da classe. Isso torna o código mais legível e conciso. Aqui está um exemplo que
// ilustra a diferença:
//
//
//** Sem imports estáticos:
//
//´´´
//mockMvc.perform(MockMvcRequestBuilders.post(BOOK_API_URL_PATH)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(.BookUtilsasJsonString(bookDTO)))
//       .andExpect(MockMvcResultMatchers.status().isOk())
//       .andExpect(MockMvcResultMatchers.jsonPath("$.message", Is.is(expectedMessageResponse.getMessage())));
//´´´
//
//
//** Com imports estáticos:
//
//´´´
//mockMvc.perform(post(BOOK_API_URL_PATH)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(bookDTO)))
//       .andExpect(status().isOk())
//       .andExpect(jsonPath("$.message", Is.is(expectedMessageResponse.getMessage())));
//´´´


@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    public static final String BOOK_API_URL_PATH = "/api/v1/books";
    //Constante que armazena o caminho da URL base para a API de livros.

    private MockMvc mockMvc;
    // Objeto MockMvc usado para simular chamadas HTTP ao controlador.

    @Mock
    private BookService bookService;
    // Cria um mock do serviço BookService que será injetado no controlador.

    @InjectMocks
    private BookController bookController;
    // Cria uma instância do controlador BookController e injeta o mock BookService nele.

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
                .build();
    }
    // O @BeforeEach Indica que o método anotado deve ser executado antes de cada teste
    // individual na classe de teste. E Garante que o ambiente de teste esteja configurado
    // corretamente e consistentemente antes da execução de cada teste.
    // O ´.setCustomArgumentResolvers´ Configurar a capacidade de resolver parâmetros
    // de paginação em métodos de controlador.
    // Ja o ´.setViewResolvers´ setta o tipo de resolvedorDeView que converá cada
    // Objeto retornado pelo controllador em JSON.


/*
Em testes unitarios como sempre queremos isolar a parte que deseja testar, faz-se
necessario criar ou fazer o uso de alguns componentes que simulam uma interação real
para o artefato que queremos testar. Para isso criamos uma classe para testar o
controller.

Nesse caso como se trata de um controller e para acessa-lo é necessario uma url,
salvamos sua url em uma variavel ´BOOK_API_URL_PATH = "/api/v1/books";´, depois como
estamos lidando com um controllador, para testa-lo é necessario fazer uma requisição a
ele, e como se trata de um teste unitário temos que testa-lo(BookController) de maneira
isolada ou seja sem precisar de todo o 'contexto do spring', para isso vamos usar uma
classe que servirá para simular uma requisição HTTP ou seja um cliente, essa classe ja
é definada no spring cujo nome é ´MockMvc´ ela permite executar toda a cadeia de
processamento de uma requisição típica em um ambiente Spring MVC, incluindo filtros e
DispatcherServlet, mas tudo isso acontece dentro do contexto do teste, sem a necessidade
de um servidor web real.

Logo depois a anotação ´@InjectMocks´ cria uma instancia da classe anotada e injeta
as dependencias(CAMPOS ANOTADOS COM @Mock ou @Spy) necessarias para a execução do teste
em questão, pois para testarmos certos metodos do controller fazemos o uso de alguns
metodos implementados no ´service´, no caso ele injeta o ´bookService´ mockado( OBJETO
QUE IMITA O COMPORTAMENTO DA TAL CLASSE ANOTADA COM @Mock ) no controlador.

Apoz isso o metodo setUp(), que devido estar anotado com @BeforeEach e sempre executado
antes dos outros testes, configura o nosso simulador/gerador de requisiçoes
definindo ´standaloneSetup(bookController)´ que ele deve  ficar stand-alone ou seja deixa
nosso controlador autonomo sem a necessidade de carregar todo o contexto do spring para
configura-lo e apoz definirmos isso começamos a costumizar o nosso simulador de
requisiçoes, usando o
´.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())´ para adicionar
um resolvedor de argumentos que resolve argumentos do tipo Pageable acabando por
Configurar a capacidade de resolver parâmetros de paginação em métodos de controlador
durante os testes, tambem adicionamos um renderizador de respostas http,
'.setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())' ,
um ´ViewResolver´ ja costumizado  do tipo MappingJackson2JsonView como  view resolver,
o que significa que durante os testes, quando o controlador retorna um objeto, ele será
automaticamente convertido em JSON.

No setViewResolvers, você está fornecendo um lambda que implementa a interface
ViewResolver. O método resolveViewName dessa interface aceita viewName e locale como
parâmetros e retorna uma instância de View.

** Observe que viewName: O nome da view que está sendo resolvida. Não é usado no corpo do
    lambda porque MappingJackson2JsonView não depende do nome da view. É simplesmente uma
    formalidade de interface e
    locale: A localização da solicitação. Novamente, não é usado no corpo do lambda porque
    MappingJackson2JsonView também não depende da localização.

Como MappingJackson2JsonView serializa objetos Java em JSON, ele não precisa de informações
de localização para isso. Apoz tudo isso chamamos o metodo build o builder compila todas
as configurações e cria uma instância final de MockMvc com todas as configurações
aplicadas  pronta para simular chamadas HTTP.
*/


    @Test
    void testWhenPOSTisCalledThenABookShouldBeCreated() throws Exception {
        BookDTO bookDTO = createFakeBookDTO();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Book created with ID " + bookDTO.getId())
                .build();

        when(bookService.create(bookDTO)).thenReturn(expectedMessageResponse);

        mockMvc.perform(post(BOOK_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", Is.is(expectedMessageResponse.getMessage())));
    }
/*
Iniciamos um metodo ´testWhenPOSTisCalledThenABookShouldBeCreated´ e o anotamos com ´@Test´
para que seja explicitado que o metodo em questão é um teste, apos isso instanciamos uma
classe chamada ´BookDTO´ que recebe dados falsos usando um metodo utilitário ´createFakeBookDTO´
que gera um ´BookDTO´ com dados de testes pré-definidos(fakes) com a ajuda de uma lib
chamada ´javafaker.Faker´.

Como estamos criando um metodo para teste do metodo HTTP Post do ´BookController´ para
imitar-mos oque se passa no metodo POST do Controller instanciamos de ´MessageResponseDTO´
utilizando a BIBLIOTECA DO LOMBOK ´@Builder´ que nos permite usar o metodo ´.bilder()´
para construirmos a instancia do nosso objeto, e settamos uma mensagem´.message´ que,
conforme um uso real do metodo POST, será exibida quando o Book for criado com sucesso.
Essa menssagem simula oque a API deve fazer, apoz isso terminamos o instanciamento de
´MessageResponseDTO´ com o metodo ´.build()´ que é um metodo proveniente do metodo
´.bilder()´

Agora apoz termos definido os dados de entrada, iremos dizer o comportameto que os ´Moks´
(dependencias do ´BookController´) deve ter, pois como nos não estamos fazendo o uso de
um ´BookService´ injetado pelo spring e sim estamos fazendo o uso de de uma classe que
simula o ´bookService´, precisamos dizer para nosso ´Mock´/(classe que simula bookService)
oque ele deve fazer / como ele deve agir, pelo motivo de por padrão ao mockarmos a chamda do
bookService toda a implementação dele não será chamada -> {Se isso não acontecesse isso
violaria os principiios de um teste unitário}.

{ Mock não possui o amparo do contexto do Spring:
    Quando usamos mocks, estamos isolando a unidade de código que estamos testando. Isso
    significa que o mock não tem acesso ao contexto completo do Spring, que normalmente
    gerencia a injeção de dependências, a criação de beans, a transação e outros aspectos
    do ciclo de vida dos componentes.

  Controle sobre o Comportamento:
    Definindo o comportamento dos mocks, garantimos que o teste é determinístico, ou seja,
    ele terá o mesmo resultado sempre que for executado, independentemente de fatores
    externos.

  Teste mais rápido e eficiente:
    Mocks são mais rápidos do que os componentes reais porque evitam operações complexas e
    demoradas, como acessos a banco de dados, chamadas de rede ou processamento intensivo. }


´when(bookService.create(bookDTO)).thenReturn(expectedMessageResponse);´: Define que quando
por boockService for chamada a criação de um BookDTO 'bookService.create(bookDTO)', então
ele(bookService) irá retornanar ´expectedMessageResponse´ como descrito nessa parte
´.thenReturn(expectedMessageResponse)´, ou seja será retornado para nos a menssagem de
sucesso imitando ação de API ao fazer o salvamento de um livro.


Terminado a determinação de comportamento, iniciamos a simulação de uma chamada http POST,
atravez do uso do MockMvc ´mockMvc.perform(post(BOOK_API_URL_PATH)´, para a URL definida
em BOOK_API_URL_PATH, em segui por meio do metodo ´.contentType()´ definimos o tipo de
conteudo que será enviado na requisição POST(informando ao servidor que o corpo da
requisição será em formato JSON), tipo que é definido como JSON com explicitado
´.contentType(MediaType.APPLICATION_JSON)´, e definimos o conteúdo do corpo da requisição
com a representação JSON do objeto bookDTO.

O .content é usado para definir o corpo da requisição. asJsonString(bookDTO) é um método
utilitário da classe ´BookUtils´ que converte o objeto bookDTO em uma string JSON.

O metodo ´.andExpect(status().isOk())´ é usado para definir as expectativas sobre a
resposta da requisição. status().isOk() verifica se o status HTTP da resposta é 200, isso
garante que a requisição foi bem-sucedida e o controlador retornou a resposta correta.

Apos isso, ´.andExpect(jsonPath("$.message", Is.is(expectedMessageResponse.getMessage())))´,
utilizamos o metodo ´.andExpect()´ para verificar se o corpo da resposta JSON contém um
campo message com o valor esperado, usando jsonPath para navegar na resposta JSON e
verificar se o campo message contém o valor ´expectedMessageResponse.getMessage()´ onde
'jsonPath("$.message"...' refere-se ao campo message na raiz do objeto JSON e
'Is.is(expectedMessageResponse.getMessage())' verifica se o valor do campo message é
igual ao valor esperado, garantindo que a resposta do controlador não só retornou um
status 200, mas também contém a mensagem correta no corpo da resposta.
*/



    @Test
    void testWhenPOSTwithInvalidISBNIsCalledThenBadRequestShouldBeReturned() throws Exception {
        BookDTO bookDTO = createFakeBookDTO();
        bookDTO.setIsbn("invalid isbn");

        mockMvc.perform(post(BOOK_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookDTO)))
                .andExpect(status().isBadRequest());
    }
/*
Aqui definimos o metodo ´testWhenPOSTwithInvalidISBNIsCalledThenBadRequestShouldBeReturned,
metodo esse que será anotado com o ´@Test´ fazendo com que o JUnit execute este método como
parte da suite de testes. Logo em siguida inctanciamos o Objeto ´BookDTO´ falso (dummy) e
utilizamos o ´createFakeBookDTO()´, método utilitário que retorna um objeto BookDTO
preenchido com dados fictícios. Isso serve como entrada para o teste.

Como estamos criando esse metodo para testar o metodo POST quando o ´ISBN´ for invalido,
retorna como esperado um ´BadRequest´, ´bookDTO.setIsbn("invalid isbn");´ settamos um
ISBN invalido aqui para forçar o erro e conferir se a ´BadRequest´ foi lançada.
Ou seja Simula um cenário em que o ISBN fornecido é inválido, configurando o bookDTO
para refletir esse estado.

Apartir da classe ´MockMvc´, classe que permite a simulação de requisições HTTP, chamamos
o metodo ´perform´{executar} no qual atravez da classe ´MockMvcRequestBuilders´ (Que é
uma classe utilitária do Spring Framework usada para construir requisições HTTP em testes
unitários e de integração, ela fornece métodos estáticos que facilitam a criação de
requisições mock (simuladas) que podem ser enviadas para o MockMvc durante os testes.)
passamos toda uma costumização de como a requisição será(ao cria-la) para o parametro
´RequestBuilder´(ARGUMENTO DO METODO ´.perform´) ou seja passamos um Construtor de
Solicitação como argumento, tal ´Construtor de Solicitação´ passsado como argumento permite
voce customizar e criar diferentes tipos de requisições HTTP, para o método perform de
MockMvc onde definimos o tipo de metodo HTTP que é POST e o tipo de midia que sera passado
ou seja indicamos que o corpo da requisição está em formato JSON e convertemos bookDTO em
uma string JSON tornando assim o bookDTO(Convertido em JSON) em nosso BODY da requisição:

"MockMvcRequestBuilders em si não é instanciada porque seus métodos são estáticos. Em vez
de criar uma instância da classe MockMvcRequestBuilders, você chama os métodos estáticos da
classe ´MockMvcRequestBuilders´ diretamente. Esses métodos estáticos retornam instâncias
de MockHttpServletRequestBuilder ou outros tipos de RequestBuilder, que são usados para
construir requisições HTTP simuladas.

Ou seja Os métodos estáticos como post, get, put, etc., em MockMvcRequestBuilders
instanciam e retornam objetos de MockHttpServletRequestBuilder, e
MockHttpServletRequestBuilder implementa RequestBuilder. Como MockHttpServletRequestBuilder
é uma classe que implementa outras interfaces e no fundo EXTENDEM a interfaceRequestBuilder.
Isso significa que um objeto de MockHttpServletRequestBuilder pode ser
tratado como um RequestBuilder Tornando possivel passar, de maneira grossamente falando,
MockMvcRequestBuilders como argumento de perform.

( Mas sabendo que oque na verdade é passado
como argumento para o metodo ´perform´ é uma instancia de ´MockHttpServletRequestBuilder´
{ Classe que implementa interfaces que extendem ´RequestBuilder´, significando que um objeto
de MockHttpServletRequestBuilder pode ser tratado como um RequestBuilder } feita por meio
de um metodo interno(post) da classe MockMvcRequestBuilders )

[
' MockHttpServletRequestBuilder não implemente diretamente a interface RequestBuilder, ele
ainda pode ser tratado como um RequestBuilder devido à hierarquia de interfaces que ele
implementa. '
]

Quando você usa o MockMvcRequestBuilders para criar uma requisição, ele retorna uma
instância de um "RequestBuilder", que é então passado como argumento para o método perform
do MockMvc.

´´´
MockMvcRequestBuilders.post(BOOK_API_URL_PATH)
  .contentType(MediaType.APPLICATION_JSON)
  .content(BookUtils.asJsonString(bookDTO))
´´´

Esse metodo(.perform) recebendo tudo isso que passamos para o ´Construtor de Solicitação´(
RequestBuilder), dentro dele ele faz o uso de tudo isso que passamos para construir um
MockHttpServletRequest {

_EXPLICANDO_COMO_MockHttpServletRequest_SIMULA_UMA_REQUISIÇÃO_HTTP_
-----
O MockHttpServletRequest é uma classe do Spring Framework utilizada para simular uma
requisição HTTP em testes. Ele é uma implementação da interface HttpServletRequest e
permite que você crie e manipule requisições HTTP de forma programática, sem a necessidade
de um servidor real (REQUISIÇÃO ESSA QUE TERÁ QUE PASSAR PELO DispatcherServlet SIMULADO
E PELOS FILTROS CONFIGURADOS simulando assim o processamento de uma requisição HTTP real,
permitindo que você teste o comportamento dos controladores da sua aplicação em um
ambiente controlado.). Isso é particularmente útil em testes unitários e de integração, onde
você deseja testar a lógica de seus controladores e outros componentes da aplicação sem
depender de um ambiente de servidor completo.

""" MockHttpServletRequest consegue imitar ser uma requisição pois como dito
( MockHttpServletRequest implementa todos os métodos definidos pela interface
HttpServletRequest. Isso inclui métodos para acessar parâmetros de requisição, cabeçalhos,
atributos de sessão, contexto do servlet. ) metodos esses que comunicam ao servlet ou seja
informa ao servlet suas caracteristicas(CARACTERISTICAS DA REQUISIÇÃO / ATRIBUTOS) como
cabeçalhos e outros atributos de sessão como tambem se a requisição será um POST ou um GET.
Tornando possivel que qualquer código que espera um objeto ´HttpServletRequest´ pode
trabalhar com MockHttpServletRequest.
UM EXEMPLO DE CODIGO OU INSTANCIA QUALQUER QUE ESPERE OBJETO DO TIPO HttpServletRequest
SERIA UM ´servlet Simulado´. """

Tal passagem pelo DispatcherServlet acontecerá nessa parte do condigo em ´MockMvc.class´:

´´´
MockFilterChain filterChain = new MockFilterChain(this.servlet, this.filters);
filterChain.doFilter(request, (ServletResponse)servletResponse);
´´´

"Este trecho de código é onde a requisição simulada (MockHttpServletRequest) passa pelo
DispatcherServlet simulado e pelos filtros configurados. O MockFilterChain encadeia os
filtros e o servlet, simulando o processo de filtragem e despacho de uma requisição real."
-----

}:

´´´
MockHttpServletRequest request = requestBuilder.buildRequest(this.servletContext);
´´´

O método buildRequest do RequestBuilder cria uma instância de MockHttpServletRequest usando
o contexto do servlet (servletContext), o ServletContext é uma interface que define um
conjunto de métodos que os servlets utilizam para se comunicar com o contêiner de servlet
(por exemplo, Tomcat, Jetty) porem nesse caso, metodos que serao usados para se comunicar
com o DispatcherServlet Simulado(Container servlet) usado no teste, que foi criado impli-
citamente ao configurarmos o ´MockMvc´ {

´MockMvcBuilders.standaloneSetup(bookController)´

} Este método configura um MockMvc standalone com o controlador fornecido (bookController).
Ele cria uma instância de TestDispatcherServlet, uma classe interna usada especificamente
para testes, que herda de DispatcherServlet.
No construtor da classe MockMvc, você pode ver que a instância do TestDispatcherServlet
(que é uma forma de DispatcherServlet personalizada para testes) é passada como parâmetro:

´´´
MockMvc(TestDispatcherServlet servlet, Filter... filters) {
    Assert.notNull(servlet, "DispatcherServlet is required");
    Assert.notNull(filters, "Filters cannot be null");
    Assert.noNullElements(filters, "Filters cannot contain null values");
    this.servlet = servlet;
    this.filters = filters;
    this.servletContext = servlet.getServletContext();
}
´´´

"O ServletContext fornece um ambiente de execução simulado para a aplicação( Explicando
melhor, Fornece metodos para que haja comunicação entre componentes como
MockHttpServletRequest e DispatcherServlet. ). Isso permite
que a aplicação e seus componentes (como servlets e filtros) sejam testados de maneira
realista. { o ServletContext simulado atua como um facilitador para essa comunicação e para
o compartilhamento de dados globais. Ele é essencial para criar um ambiente de teste que
se aproxime ao máximo de um ambiente de execução real. }"

Apos isso, ´MockHttpServletResponse mockResponse;´ uma MockHttpServletResponse é criada
para capturar a resposta simulada. E logo em seguida ´request´, que recebe as informações
que definimos e passamos no parametro ´Construtor de solicitações´, nossa simulação de
requisição HTTP é processada, um MockFilterChain é criado para simular a cadeia de filtros,
e a requisição é processada através do DispatcherServlet e dos filtros configurados, o
resultado desse processamento da requisição é recebido em ´mvcResult´ onde pode ser feitas
asserções para verificar se o comportamento foi conforme o esperado (status HTTP,
conteúdo da resposta, etc.).


Explicando mais o passo a passo: Apoz  o objeto HttpServletRequest ser construido nos
efetuamos algumas ´Checagens de Contexto Assíncrono´ e Se tiver, obtém a resposta do
contexto assíncrono e assegura que seja uma instância de MockHttpServletResponse e Se não
tiver, cria uma nova instância de MockHttpServletResponse. Instanciado mockResponse,
seguimos fazendo algumas configuraçoes e dps passamos tanto o  objeto request quanto
mockResponse como argumento em DefaultMvcResult(classe que atribuida a mvcResult, ou seja
, Cria um MvcResult que contém a requisição (request) e a resposta (mockResponse).
E Define esse resultado como um atributo da requisição. ) dps Salvamos os atributos de
requisição atuais. E Definimos novos atributos de requisição para a requisição e resposta
simuladas. Logo em seguida Criamos uma cadeia de filtros (MockFilterChain) que inclui o
DispatcherServlet e quaisquer filtros configurados. E Passamos a requisição (request) e a
resposta (servletResponse) pela cadeia de filtros. E depois Realizamos qualquer finalização
necessária e aplica matchers e handlers de resultado padrão. E dps Retornamos um
ResultActions que permite verificações adicionais e acesso ao MvcResult.


Request e mockResponse sao dois componentes a parte esses dois sao passados na classe
´DefaultMvcResult´(classe que implementa mvcResult), que é atribida ha mvcResult( O MvcResult
é uma interface na biblioteca Spring MVC Test que representa o resultado de uma requisição
MVC. Ele encapsula todos os aspectos importantes de uma requisição e resposta simuladas,
permitindo que você acesse e verifique esses detalhes em seus testes. Como dito O
MvcResult armazena o HttpServletRequest e o HttpServletResponse, permitindo que você
acesse esses objetos depois que a requisição foi processada, Ele armazena dados
importantes( Dados da Requisição (MockHttpServletRequest), Dados da Resposta
(MockHttpServletResponse) e etc.) sobre a execução e sobre o resultado do processamento
da requisição, como o controlador que foi chamado, os atributos do modelo, os erros de
validação, entre outros. E Permite que você use verificadores (Matchers) para confirmar
que a resposta gerada pelo controlador está correta. ), apoz sao passados no filtro e
sevlet. Portanto, durante os testes unitários, a captura e o salvamento da resposta da
requisição em MockHttpServletResponse ocorrem durante o processamento pelo DispatcherServlet
simulado. O MvcResult encapsula tanto a requisição quanto a resposta, permitindo que você
verifique facilmente o resultado do processamento da requisição HTTP simulada. A comunicação
direta entre HttpServletRequest e HttpServletResponse é facilitada pelo DispatcherServlet
durante o teste, garantindo que você possa verificar a lógica e os resultados esperados
da sua aplicação Spring MVC.

MockHttpServletResponse armazena o resultado da requisição. Ele é uma implementação simulada
da interface HttpServletResponse, que é usada nos testes para capturar e armazenar todos os
aspectos da resposta HTTP gerada pelo processamento da requisição.

--Processamento da Requisição:

O MockHttpServletRequest é passado para o controlador da aplicação (ou para o DispatcherServlet
se você estiver usando o Spring MVC). Durante o processamento, o controlador manipula a
requisição, executa a lógica de negócio necessária, interage com os serviços e repositórios, e
finalmente prepara uma resposta.

--Captura da Resposta com MockHttpServletResponse:

O MockHttpServletResponse é utilizado para capturar todos os detalhes da resposta gerada durante
o processamento da requisição. Ele armazena o código de status, cabeçalhos, corpo da resposta,
cookies, etc.


Request e mockResponse sao dois componentes a parte e quando esses dois sao passados no
filtro e sevlet, o mockResponse  é utilizado para consultar e modificar alguns atributos
ou seja seus metodos são chamados conforme a aplicação processa a requisição e prepara
a resposta. Por exemplo:

    setStatus pode ser chamado para definir o código de status HTTP.
    addHeader pode ser chamado para definir cabeçalhos de resposta.
    getWriter pode ser chamado para escrever o corpo da resposta.

E Após o processamento, os métodos da MockHttpServletResponse são usados para verificar
se a resposta foi gerada conforme o esperado:

    assertEquals(200, mockResponse.getStatus());
    assertEquals("application/json", mockResponse.getHeader("Content-Type"));
    assertEquals("{\"message\":\"Hello, World!\"}", mockResponse.getContentAsString());

Durante o processamento da requisição simulada pelo servlet e pela cadeia de filtros, os
métodos do MockHttpServletResponse são chamados conforme necessário para construir a
resposta.

A classe MockMvcResultMatchers é uma classe utilitária que fornece métodos estáticos para
criar instâncias de verificadores (Matchers). Esses verificadores são implementados por
classes como StatusResultMatchers, que retornam instâncias da interface ResultMatcher.

Conclusão

A classe MockMvcResultMatchers fornece métodos estáticos que retornam instâncias de
classes como StatusResultMatchers. Estas classes, por sua vez, implementam métodos que
retornam instâncias de ResultMatcher. Assim, quando você chama
.andExpect(MockMvcResultMatchers.status().isBadRequest()), está, na verdade, passando um
ResultMatcher para o método andExpect. Esse design permite verificar se a resposta da
requisição simulada corresponde às expectativas definidas nos testes.
*/

}




/*
--FUNCIONAMENTO DO METODO '.perform'--

_______
1. Construção da Requisição (HttpServletRequest)

´´´
MockHttpServletRequest request = requestBuilder.buildRequest(this.servletContext);
´´´

    ** RequestBuilder:
        Um RequestBuilder é usado para criar a requisição. Pode ser um
        MockHttpServletRequestBuilder ou qualquer outra implementação que
        construa a requisição com os parâmetros desejados.

    ** ServletContext:
        O ServletContext é passado para a requisição, permitindo que ela
        tenha acesso a informações do contexto do servlet.

    ** MockHttpServletRequest:
        O objeto MockHttpServletRequest é construído com base nas especificações
        fornecidas pelo RequestBuilder.


2. Checagem de Contexto Assíncrono e Criação da Resposta (HttpServletResponse)

´´´
AsyncContext asyncContext = request.getAsyncContext();
MockHttpServletResponse mockResponse;
Object servletResponse;
if (asyncContext != null) {
    servletResponse = (HttpServletResponse)asyncContext.getResponse();
    mockResponse = this.unwrapResponseIfNecessary((ServletResponse)servletResponse);
} else {
    mockResponse = new MockHttpServletResponse();
    servletResponse = mockResponse;
}
´´´

    ** AsyncContext:
        Verifica se a requisição possui um contexto assíncrono (AsyncContext).

    ** Resposta Assíncrona:
        Se existir um contexto assíncrono, obtém a resposta dele e garante que
        seja uma instância de MockHttpServletResponse.

    ** Resposta Síncrona:
        Se não houver contexto assíncrono, cria uma nova instância de
        MockHttpServletResponse.


3. Configuração do Character Encoding

´´´
if (this.defaultResponseCharacterEncoding != null) {
    mockResponse.setDefaultCharacterEncoding(this.defaultResponseCharacterEncoding.name());
}
´´´

    ** Character Encoding:
        Define a codificação de caracteres padrão da resposta, se configurada.
        Isso é importante para garantir que a resposta seja codificada corretamente.


4. Post-processamento da Requisição (Opcional)

´´´
if (requestBuilder instanceof SmartRequestBuilder smartRequestBuilder) {
    request = smartRequestBuilder.postProcessRequest(request);
}
´´´

    ** SmartRequestBuilder:
        Se o RequestBuilder for uma instância de SmartRequestBuilder, aplica
        um processamento adicional à requisição. Isso permite ajustes finais
        na requisição antes de ser enviada ao servlet.

5. Criação do Resultado MVC (MvcResult)

´´´
final MvcResult mvcResult = new DefaultMvcResult(request, mockResponse);
request.setAttribute(MVC_RESULT_ATTRIBUTE, mvcResult);
´´´

    ** MvcResult:
        Cria um objeto MvcResult que contém a requisição (request) e a
        resposta (mockResponse).

    ** Atributo da Requisição:
        Define o MvcResult como um atributo na requisição, permitindo acesso
        posterior a ele.


6. Manuseio de Atributos de Requisição

´´´
RequestAttributes previousAttributes = RequestContextHolder.getRequestAttributes();
RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request, (HttpServletResponse)servletResponse));
´´´

    ** Atributos Anteriores:
        Salva os atributos de requisição atuais.

    ** Novos Atributos:
        Define novos atributos de requisição para a requisição e resposta
        simuladas. Isso garante que a requisição simulada tenha o contexto
        correto para ser processada.


7. Passagem pelo DispatcherServlet e Cadeia de Filtros

´´´
MockFilterChain filterChain = new MockFilterChain(this.servlet, this.filters);
filterChain.doFilter(request, (ServletResponse)servletResponse);
´´´

    ** MockFilterChain:
        Cria uma cadeia de filtros (MockFilterChain) que inclui o
        DispatcherServlet e quaisquer filtros configurados.

    ** Processamento:
        A requisição (request) e a resposta (servletResponse) são passadas
        pela cadeia de filtros. Isso simula o comportamento de um contêiner
        de servlet real, onde a requisição passa por vários filtros antes de
        ser processada pelo servlet.


8. Finalização Assíncrona (Se Aplicável)

´´´
if (DispatcherType.ASYNC.equals(request.getDispatcherType()) && asyncContext != null && !request.isAsyncStarted()) {
    asyncContext.complete();
}
´´´

    ** DispatcherType.ASYNC:
        Verifica se a requisição é assíncrona.

    ** Completar Contexto Assíncrono:
        Se a requisição é assíncrona e o contexto assíncrono não foi iniciado,
        o método complete() do AsyncContext é chamado para completar a requisição.


9. Aplicação de Ações de Resultado Padrão (Matchers e Handlers)

´´´
this.applyDefaultResultActions(mvcResult);
´´´

    ** Matchers e Handlers:
        Aplica todos os matchers e handlers configurados por padrão ao
        resultado (MvcResult).

    ** Iteração:
        Itera sobre listas de ResultMatcher e ResultHandler e aplica cada
        um ao mvcResult. Isso garante que todas as verificações e ações
        configuradas sejam executadas.


10. Restauração de Atributos de Requisição Anteriores

´´´
RequestContextHolder.setRequestAttributes(previousAttributes);
´´´

    ** Restauração:
        Após o processamento da requisição, restaura os atributos de requisição
        originais. Isso garante que o contexto da requisição seja revertido ao
        estado anterior, evitando efeitos colaterais em requisições subsequentes.


11. Retorno de ResultActions

´´´
return new ResultActions() {
    public ResultActions andExpect(ResultMatcher matcher) throws Exception {
        matcher.match(mvcResult);
        return this;
    }

    public ResultActions andDo(ResultHandler handler) throws Exception {
        handler.handle(mvcResult);
        return this;
    }

    public MvcResult andReturn() {
        return mvcResult;
    }
};
´´´

    ** ResultActions:
        Retorna um ResultActions que permite encadear operações para verificar
        o resultado da requisição (MvcResult).

    ** andExpect(ResultMatcher matcher):
        Permite adicionar verificações adicionais ao resultado. O método
        matcher.match(mvcResult) aplica o ResultMatcher ao MvcResult.

    ** andDo(ResultHandler handler):
        Permite executar ações adicionais no resultado. O método
        handler.handle(mvcResult) aplica o ResultHandler ao MvcResult.

    ** andReturn():
        Retorna o MvcResult final, que contém a requisição e a resposta processadas.


Resumo Final do Processo

    ** Construção da Requisição:
        Um MockHttpServletRequest é construído.

    ** Criação da Resposta:
        Um MockHttpServletResponse é criado, seja de um contexto assíncrono
        ou diretamente.

    ** Configurações:
        Configura a codificação da resposta e possivelmente faz um
        post-processamento da requisição.

    ** Criação do Resultado MVC:
        Cria um MvcResult que contém a requisição e a resposta.

    ** Manipulação de Atributos de Requisição:
        Define atributos de requisição necessários.

    ** Execução da Cadeia de Filtros:
        A requisição e a resposta são processadas pela cadeia de filtros que inclui
        o DispatcherServlet.

    ** Finalização Assíncrona:
        Se aplicável, completa a requisição assíncrona.

    ** Aplicação de Ações de Resultado Padrão:
        Aplica matchers e handlers de resultado padrão ao MvcResult.

    ** Restauração de Atributos de Requisição Anteriores:
        Restaura os atributos de requisição originais.

    ** Retorno:
        Retorna um ResultActions que permite verificações adicionais e acesso
        ao MvcResult.

Este processo simula uma requisição HTTP completa passando por um servlet container real,
permitindo que você teste controladores e outros componentes de sua aplicação Spring de
forma realista.
_______
























"""

Passo a Passo do Processo:

    ***Construção da Requisição Simulada:

´´´
MockHttpServletRequest request = requestBuilder.buildRequest(this.servletContext);
´´´

Este trecho de código usa o RequestBuilder para criar uma instância de
MockHttpServletRequest.


    ***Criação da Resposta Simulada:

´´´
MockHttpServletResponse mockResponse;
if (asyncContext != null) {
    servletResponse = (HttpServletResponse)asyncContext.getResponse();
    mockResponse = this.unwrapResponseIfNecessary((ServletResponse)servletResponse);
} else {
    mockResponse = new MockHttpServletResponse();
    servletResponse = mockResponse;
}
´´´

Aqui, o código cria uma instância de MockHttpServletResponse que será usada para capturar
a resposta do servlet simulado.


    ***Configuração do Contexto de Requisição:

´´´
RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request,
                                                    (HttpServletResponse)servletResponse));
´´´


    ***Passagem da Requisição pelo DispatcherServlet e Filtros:

´´´
MockFilterChain filterChain = new MockFilterChain(this.servlet, this.filters);
filterChain.doFilter(request, (ServletResponse)servletResponse);
´´´

Este trecho de código é onde a requisição simulada (MockHttpServletRequest) passa pelo
DispatcherServlet simulado e pelos filtros configurados. O MockFilterChain encadeia os
filtros e o servlet, simulando o processo de filtragem e despacho de uma requisição real.


    ***Aplicação das Ações e Verificações Padrão:

´´´
this.applyDefaultResultActions(mvcResult);
´´´

Este método aplica verificações e manipuladores de resultado configurados por padrão na
resposta obtida.


    ***Retorno do Resultado da Execução:

´´´
    return new ResultActions() {
        public ResultActions andExpect(ResultMatcher matcher) throws Exception {
            matcher.match(mvcResult);
            return this;
        }

        public ResultActions andDo(ResultHandler handler) throws Exception {
            handler.handle(mvcResult);
            return this;
        }

        public MvcResult andReturn() {
            return mvcResult;
        }
    };
´´´

    O método perform retorna um objeto ResultActions, permitindo encadear verificações e
    manipulações adicionais no resultado da requisição simulada.


Resumo

O RequestBuilder constrói a requisição simulada (MockHttpServletRequest), que é então
processada pelo MockFilterChain, passando pelo DispatcherServlet simulado e pelos filtros
configurados. Isso simula o processamento de uma requisição HTTP real, permitindo que
você teste o comportamento dos controladores da sua aplicação em um ambiente controlado.


"""
*/