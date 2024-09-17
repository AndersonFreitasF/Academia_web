Projeto de Gestão de Academia

Este projeto consiste em uma aplicação de gestão de academia desenvolvida com Java e Spring Boot, integrando funcionalidades do front-end com o back-end para gerenciar alunos, treinos, treinadores e pagamentos. O sistema permite que os alunos façam login, comprem treinos, visualizem suas compras e acessem seus dados de treino, tudo de forma centralizada.

Tecnologias Utilizadas

Java: Linguagem principal do back-end, utilizada para a lógica da aplicação.
Spring Boot: Framework para facilitar o desenvolvimento e integração de serviços REST.
Hibernate: Framework de ORM, utilizado para o mapeamento entre as entidades Java e o banco de dados.
Thymeleaf: Motor de templates para renderizar o front-end com base nos dados do back-end.
H2: Banco de dados utilizado para desenvolvimento.
Git: Controle de versão para gerenciar o código do projeto.

Funcionalidades
Cadastro e gerenciamento de alunos e treinadores.
Compra e visualização de treinos pelos alunos.
Gestão de pagamentos e associação entre alunos e treinos.
Integração entre front-end e back-end utilizando Thymeleaf e Spring MVC.

Vantagens

Manutenção facilitada: O uso de Spring Boot e Hibernate facilita a adição de novas funcionalidades e a integração com o banco de dados.


Escalabilidade: O projeto pode ser facilmente escalado com a adição de novos módulos e funcionalidades devido à arquitetura modular.

Desvantagens

Desempenho com Lazy Loading: A configuração de Lazy Loading pode gerar exceções de inicialização se não for gerenciada corretamente, como o erro de LazyInitializationException.
