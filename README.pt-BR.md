# API do GAM Piracicaba

> English version: [README.md](README.md)

API backend da plataforma de gestão do GAM Piracicaba, um projeto de software desenvolvido voluntariamente para apoiar a missão, a organização e o trabalho pastoral do GAM Piracicaba.

O GAM Piracicaba é um grupo missionário juvenil salesiano de Piracicaba, Brasil. O grupo realiza atividades sociais, educativas e evangelizadoras, como o oratório semanal, ações missionárias e apoio às semanas missionárias dos colégios salesianos.

## Propósito

Este projeto está sendo desenvolvido voluntariamente para ajudar o GAM Piracicaba a organizar suas operações internas com mais cuidado e confiabilidade.

A API tem como objetivo apoiar:

- gestão de membros e contas;
- controle de acesso baseado em papéis;
- cadastro e busca de eventos;
- registros de oratório e missa;
- controle de presenças;
- cadastro de locais;
- autenticação com tokens de acesso e renovação;
- persistência auditável com exclusão lógica.

O objetivo não é apenas criar software, mas reduzir atritos operacionais para que os voluntários possam dedicar mais energia à missão, à formação, ao serviço e à comunidade.

## Contexto do Projeto

Para conhecer o grupo que motiva este sistema, veja:

- [Sobre o GAM Piracicaba](docs/about-gam/gam-piracicaba.pt-BR.md)

## Tecnologias

- Java 21
- Spring Boot 3.5.7
- Spring Web
- Spring Security
- Spring Data JPA
- PostgreSQL
- Flyway
- MapStruct
- Lombok
- autenticação JWT
- Maven

## Arquitetura Atual

O projeto é uma API REST em Spring Boot com áreas de domínio orientadas por funcionalidade, como contas, membros, eventos, locais, presenças e RBAC.

Atualmente, o código usa camadas e padrões explícitos, incluindo controllers, serviços de caso de uso, repositórios, DTOs, mappers, entidades de persistência, objetos de domínio, exceções customizadas, especificações dinâmicas de busca, auditoria e exclusão lógica.

Notas de arquitetura e direcionamento de refatoração estão documentados em:

- [Revisão de Arquitetura do Projeto](docs/refactor/project-refactor-roadmap.md)
- [Roadmap de Refatoração da Arquitetura](docs/refactor/architecture-refactor-roadmap.md)

## Como Executar

Pré-requisitos:

- Java 21
- Maven, ou o Maven Wrapper incluído no projeto
- Docker Desktop

Execute a suíte de testes:

```powershell
.\mvnw.cmd test
```

Execute a aplicação com o atalho de desenvolvimento:

```powershell
.\mvnw.cmd -Pdev
```

Esse comando ativa o profile Maven chamado `dev`. Esse profile Maven é apenas um atalho: ele executa o goal `spring-boot:run` e passa o profile Spring chamado `dev` para a aplicação. O profile Spring então carrega `application-dev.properties`.

O profile Spring de desenvolvimento usa o suporte do Spring Boot a Docker Compose para iniciar o PostgreSQL definido em `compose.yml` quando necessário. O container do PostgreSQL permanece em execução depois que a aplicação encerra, deixando os próximos reinícios mais rápidos.

Os bancos de desenvolvimento e de testes de integração usam PostgreSQL 18 porque as migrations chamam a função nativa `uuidv7()` do PostgreSQL. Versões atuais do Flyway podem exibir um aviso dizendo que o suporte ao PostgreSQL 18 é mais recente que a faixa de compatibilidade testada; esse aviso é esperado até que a versão gerenciada do Flyway seja atualizada.

Pare o banco de desenvolvimento quando terminar:

```powershell
docker compose stop
```

Recrie o banco de desenvolvimento do zero e remova o volume:

```powershell
docker compose down -v
```

A aplicação não ativa nenhum profile Spring por padrão. Use `.\mvnw.cmd -Pdev` para desenvolvimento local. `.\mvnw.cmd spring-boot:run` sem profile intencionalmente não carrega as configurações de desenvolvimento nem inicia o Docker Compose. O Flyway valida e executa as migrations do schema PostgreSQL durante a inicialização.

## Documentação

A documentação do projeto está organizada em `docs/`.

- `docs/about-gam/` descreve o contexto social e religioso por trás do projeto.
- `docs/refactor/` registra análises de arquitetura e melhorias planejadas.

## Status

Este é um projeto voluntário em andamento. A base já possui fundações importantes de backend, mas o produto e a arquitetura ainda evoluem conforme as necessidades reais do GAM Piracicaba ficam mais claras.

## Motivação

O software deste projeto é um meio de serviço. Seu valor está em ajudar uma comunidade voluntária a cuidar de pessoas, lembrar compromissos, organizar eventos e sustentar uma presença missionária salesiana com mais clareza e continuidade.
