--CREATE SCHEMA IF NOT EXISTS public;
-- Criação de tabelas e outros objetos do banco de dados

-- TABELA USUÁRIO
CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- TABELA EVENTO
CREATE TABLE evento (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    dataInicio DATE NOT NULL,
    dataFim DATE,
    horario TIME NOT NULL,
    local VARCHAR(255) NOT NULL,
    capacidade INT NOT NULL
);

-- TABELA INGRESSO
CREATE TABLE ingresso (
    id SERIAL PRIMARY KEY,
    evento_id INT NOT NULL,
    modalidade VARCHAR(255) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    quantidade INT NOT NULL,
    restricao VARCHAR(50),
    CONSTRAINT fk_ingresso_evento FOREIGN KEY (evento_id) REFERENCES evento (id) ON DELETE CASCADE
);

-- TABELA COMPRA
CREATE TABLE compra (
    id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    evento_id INT NOT NULL,
    quantidade INT NOT NULL,
    valor_total DECIMAL(10, 2) NOT NULL,
    dataCompra TIMESTAMP NOT NULL,
    CONSTRAINT fk_compra_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id) ON DELETE CASCADE,
    CONSTRAINT fk_compra_evento FOREIGN KEY (evento_id) REFERENCES evento (id) ON DELETE CASCADE
);

-- EXEMPLOS
INSERT INTO usuario (nome, email, senha) VALUES ('Pedro Lucas', 'pedro@example.com', 'senha123');

INSERT INTO evento (nome, descricao, dataInicio, dataFim, horario, local, capacidade) 
VALUES ('Forró-bó-dó', 'A volta da lenda.', '2025-08-22', NULL, '20:00:00', 'IFPB HALL', 200);

INSERT INTO ingresso (evento_id, modalidade, preco, quantidade, restricao) 
VALUES (1, 'VIP', 120.00, 50, NULL); 

INSERT INTO compra (usuario_id, evento_id, quantidade, dataCompra, modalidade) 
VALUES (1, 1, 2, 'GERAL');