CREATE TABLE pessoa(
      codigo SERIAL NOT NULL PRIMARY KEY ,
      nome        VARCHAR(50) NOT NULL ,
      logradouro  VARCHAR(50) ,
      numero      VARCHAR(5) ,
      complemento VARCHAR(15) ,
      bairro      VARCHAR(20) ,
      cep         VARCHAR(8) ,
      cidade      VARCHAR(50) ,
      estado      VARCHAR(15) ,
      ativo       boolean NOT NULL
);

INSERT INTO pessoa (nome,logradouro,numero,complemento,bairro,cep,cidade,estado,ativo) VALUES ('Cristiano Araujo','Rua das Aves','666','Apto 02','Satana','35212-12','Belo Horizonte','Minas Gerais',true);
INSERT INTO pessoa (nome,logradouro,numero,complemento,bairro,cep,cidade,estado,ativo) VALUES ('João silva','Rua das Flores','234','casa 02','Pq Flores','34212-12','Osasco','São Paulo',true);
INSERT INTO pessoa (nome,logradouro,numero,complemento,bairro,cep,cidade,estado,ativo) VALUES ('Andrelina Cardoso','Rua das Americas','432','','Helena Maria','54212-12','Carapicuiba','São Paulo',false);
INSERT INTO pessoa (nome,logradouro,numero,complemento,bairro,cep,cidade,estado,ativo) VALUES ('Cristiano silva','Rua das Sereias','12','casa 1','Munhoz','33214-45','Itapevi','São Paulo',true);
INSERT INTO pessoa (nome,logradouro,numero,complemento,bairro,cep,cidade,estado,ativo) VALUES ('Tonico Gomes','Rua das Covas','54','Apto 03','Centro','33122-82','Carai','Minas Gerais',true);
INSERT INTO pessoa (nome,logradouro,numero,complemento,bairro,cep,cidade,estado,ativo) VALUES ('Maria Fransica','Rua das Matas','765','','Sã0 Pedro','33243-32','Recife','Pernanbuco',true);
