-- V1__Initial_schema.sql
-- Tabla para Persona
CREATE TABLE persona (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         gender VARCHAR(10),
                         age INTEGER,
                         identification VARCHAR(50),
                         address VARCHAR(255),
                         phone VARCHAR(20)
);

-- Tabla para Cliente (hereda de Persona)
CREATE TABLE cliente (
                         id SERIAL PRIMARY KEY,
                         client_id VARCHAR(50) UNIQUE NOT NULL,
                         password VARCHAR(255) NOT NULL,
                         state BOOLEAN,
                         persona_id INTEGER,
                         CONSTRAINT fk_persona FOREIGN KEY (persona_id) REFERENCES persona (id)
);

-- Tabla para Cuenta
CREATE TABLE cuenta (
                        id SERIAL PRIMARY KEY,
                        account_number VARCHAR(50) UNIQUE NOT NULL,
                        account_type VARCHAR(50),
                        initial_balance NUMERIC,
                        state BOOLEAN,
                        client_id INTEGER,
                        CONSTRAINT fk_cliente FOREIGN KEY (client_id) REFERENCES cliente (id)
);

-- Tabla para Movimiento
CREATE TABLE movimiento (
                            id SERIAL PRIMARY KEY,
                            date TIMESTAMP,
                            movement_type VARCHAR(50),
                            value NUMERIC,
                            balance NUMERIC,
                            account_id INTEGER,
                            CONSTRAINT fk_cuenta FOREIGN KEY (account_id) REFERENCES cuenta (id)
);