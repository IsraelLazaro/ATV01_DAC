--CREATE SCHEMA IF NOT EXISTS public;

-- Criação de tabelas e outros objetos do banco de dados


-- Criação da tabela Usuario
CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- Criação da tabela Evento
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

-- Criação da tabela Ingresso
CREATE TABLE ingresso (
    id SERIAL PRIMARY KEY,
    evento_id INT NOT NULL,
    modalidade VARCHAR(255) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    quantidade INT NOT NULL,
    restricao VARCHAR(50),
    CONSTRAINT fk_ingresso_evento FOREIGN KEY (evento_id) REFERENCES evento (id) ON DELETE CASCADE
);

-- Criação da tabela Compra
CREATE TABLE compra (
    id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    evento_id INT NOT NULL,
    valor_total DECIMAL(10, 2),
    CONSTRAINT fk_compra_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id) ON DELETE CASCADE,
    CONSTRAINT fk_compra_evento FOREIGN KEY (evento_id) REFERENCES evento (id) ON DELETE CASCADE
);

-- o menos 1 exemplo em cada uma das entidades/tabelas identificadas nessa etapa.
INSERT INTO usuario (nome, email) VALUES ('João Silva', 'joao.silva@example.com');
INSERT INTO evento (nome, descricao, data_inicio, data_fim, horario, local, capacidade_maxima) 
VALUES ('Show de Rock', 'Um show imperdível de rock.', '2025-03-15', NULL, '20:00', 'Arena Principal', 5000);
INSERT INTO ingresso (evento_id, modalidade, preco, quantidade, restricao) 
VALUES (1, 'VIP', 250.00, 100, 'GERAL');
INSERT INTO compra (usuario_id, evento_id, valor_total) 
VALUES (1, 1, 250.00);
