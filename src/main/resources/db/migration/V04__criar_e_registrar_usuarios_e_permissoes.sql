CREATE TABLE usuario (
	codigo SERIAL NOT NULL PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(150) NOT NULL
);

CREATE TABLE permissao (
	codigo SERIAL NOT NULL PRIMARY KEY,
	descricao VARCHAR(50) NOT NULL
);

CREATE TABLE usuario_permissao (
	codigo_usuario SERIAL NOT NULL,
	codigo_permissao SERIAL NOT NULL,
	PRIMARY KEY (codigo_usuario, codigo_permissao),
	FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo),
	FOREIGN KEY (codigo_permissao) REFERENCES permissao(codigo)
);

INSERT INTO usuario (codigo, nome, email, senha) values (1, 'Administrador', 'admin@umoney.com', '$2y$12$CQHhwSzJRikf60tcZJdt2eajJbmaS6oADbuvET28IckgsZbiBZz3O');
INSERT INTO usuario (codigo, nome, email, senha) values (2, 'Maria Silva', 'maria@umoney.com', '$2y$12$CQHhwSzJRikf60tcZJdt2eajJbmaS6oADbuvET28IckgsZbiBZz3O');

INSERT INTO permissao (codigo, descricao) values (1, 'ROLE_CADASTRAR_CATEGORIA');
INSERT INTO permissao (codigo, descricao) values (2, 'ROLE_PESQUISAR_CATEGORIA');
INSERT INTO permissao (codigo, descricao) values (3, 'ROLE_REMOVER_CATEGORIA');
INSERT INTO permissao (codigo, descricao) values (4, 'ROLE_ATUALIZAR_CATEGORIA');

INSERT INTO permissao (codigo, descricao) values (5, 'ROLE_CADASTRAR_PESSOA');
INSERT INTO permissao (codigo, descricao) values (6, 'ROLE_REMOVER_PESSOA');
INSERT INTO permissao (codigo, descricao) values (7, 'ROLE_PESQUISAR_PESSOA');
INSERT INTO permissao (codigo, descricao) values (8, 'ROLE_ATUALIZAR_PESSOA');

INSERT INTO permissao (codigo, descricao) values (9,  'ROLE_CADASTRAR_LANCAMENTO');
INSERT INTO permissao (codigo, descricao) values (10, 'ROLE_REMOVER_LANCAMENTO');
INSERT INTO permissao (codigo, descricao) values (11, 'ROLE_PESQUISAR_LANCAMENTO');
INSERT INTO permissao (codigo, descricao) values (12, 'ROLE_ATUALIZAR_LANCAMENTO');

-- admin
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 1);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 2);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 3);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 4);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 5);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 6);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 7);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 8);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 9);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 10);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 11);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 12);

-- maria
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 2);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 7);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 11);