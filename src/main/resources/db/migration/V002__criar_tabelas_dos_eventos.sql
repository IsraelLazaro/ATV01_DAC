--CREATE SCHEMA IF NOT EXISTS public;

-- Criação de tabelas e outros objetos do banco de dados


-- Tabela Usuario
CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- Tabela Evento
CREATE TABLE evento (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE,
    horario TIME NOT NULL,
    local VARCHAR(255) NOT NULL,
    capacidade_maxima INT NOT NULL
);

-- Tabela Ingresso
CREATE TABLE ingresso (
    id SERIAL PRIMARY KEY,
    evento_id INT NOT NULL,
    modalidade VARCHAR(255) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    quantidade INT NOT NULL,
    restricao VARCHAR(50),
    CONSTRAINT fk_ingresso_evento FOREIGN KEY (evento_id) REFERENCES evento (id) ON DELETE CASCADE
);

-- Tabela Compra
CREATE TABLE compra (
    id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    evento_id INT NOT NULL,
    valor_total DECIMAL(10, 2),
    CONSTRAINT fk_compra_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id) ON DELETE CASCADE,
    CONSTRAINT fk_compra_evento FOREIGN KEY (evento_id) REFERENCES evento (id) ON DELETE CASCADE
);