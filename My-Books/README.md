<h2>Bookstore API Manager</h2>

O objetivo do projeto Bookstore API Manager é disponibilizar uma API para cadastro dos livros de uma livraria através de uma API REST.

Durante o projeto, são abordados os seguintes tópicos:

* Setup inicial de projeto com o Spring Boot Initialzr.
* Criação de modelo de dados para o mapeamento de entidades em bancos de dados.
* Desenvolvimento de operações de gerenciamento de livros (Cadastro, leitura por ID e remoção de livros).
* Relação de cada uma das operações acima com o padrão arquitetural REST, e a explicação de cada um dos conceitos REST envolvidos durante o desenvolvimento do projeto.
* Desenvolvimento de testes unitários para validação das funcionalidades.
* Documentação do projeto com Swagger.

Para visualizar a documentação com projeto rodando: (http://localhost:8080/swagger-ui/index.html).

Visialização no SwaggerHub:

```dtd
https://app.swaggerhub.com/apis/DANIEL0333V/my-Books/1.0.0
```


Para executar o projeto no terminal, digite o seguinte comando:

```shell script
mvn spring-boot:run 
```

Após executar o comando acima, basta apenas abrir o seguinte endereço e visualizar a execução do projeto:

```
http://localhost:8080/api/v1/books
```


Após executar o comando acima, basta apenas abrir o seguinte endereço e visualizar a execução do projeto:

São necessários os seguintes pré-requisitos para a execução do projeto desenvolvido durante a aula:

* Java 17 ou versões superiores.
* Maven 3.9.6 ou versões superiores.
* SDKMan! para o gerenciamento de múltiplcas versões de Java, Maven e Spring Boot.
* Intellj IDEA Community Edition ou sua IDE favorita.
* Controle de versão GIT instalado na sua máquina.
* Conta no GitHub para o armazenamento do seu projeto na nuvem.
* Conta no Heroku para o deploy do projeto na nuvem
* Postman para execução de testes de integração para a validação de ponta a ponta da API.



