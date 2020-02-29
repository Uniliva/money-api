### money-api

[![Build Status](https://travis-ci.com/Uniliva/money-api.svg?branch=master)](https://travis-ci.com/Uniliva/money-api)

Projeto criado a partir do curso de fullstack Angular e Spring da Algaworks


#### Tecnologias
- Spring boot
- Spring Data
- Postgres
- Flyway
- Docker
- Travis ci


----

### Configurando o ambiente de desenvolvimento

- Banco postgres local 

```shell
docker run --name uniDB -e POSTGRES_PASSWORD=unisenha -d -p 5432:5432 postgres
```

- Conectando via dbeaver

![Conectando.png](images/dbeaver.png)

---

### Executando projeto

#### Crie profiles

Caso queira passar o profile que o spring deve sergui via variavel de ambiente

- Crie uma variavel de ambiente ex  `export SPRING_PROFILES_ACTIVE=dev` e use conforme abaixo

spring.profiles.active={SPRING_PROFILES_ACTIVE}

![profiles.png](images/profiles.png)

Existem 3 perfils: 
 - dev : ambiente de dev, rodando o postgres criado anteriomente.
 - prod : Criado para ser usado no deploy usando heroku
 - docker: Criado para ser executado usando container do docker, com docker-compose [veja aqui](https://github.com/Uniliva/money-docker-compose)



#### Instale as dependencias

```shell
mvn install
```

#### Eclipse

O eclipse, pelo que vi, não reconhece as variaveis de ambiente no linux, para executar usando variaveis de ambiente use nas configurações de execução a aba environment para configurar a variavel



![profiles.png](images/profiles-eclipse.png)

