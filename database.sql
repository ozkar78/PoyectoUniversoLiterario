-- Crear base de datos
CREATE DATABASE alura_literatura;

-- Usar la base de datos
\c alura_literatura;

-- Las tablas se crearán automáticamente con Hibernate
-- Este script es solo para referencia de la estructura

-- Tabla autores
-- CREATE TABLE autores (
--     id BIGSERIAL PRIMARY KEY,
--     nombre VARCHAR(255) UNIQUE NOT NULL,
--     fecha_nacimiento INTEGER,
--     fecha_muerte INTEGER
-- );

-- Tabla libros
-- CREATE TABLE libros (
--     id BIGSERIAL PRIMARY KEY,
--     titulo VARCHAR(255) UNIQUE NOT NULL,
--     autor_id BIGINT REFERENCES autores(id),
--     numero_descargas DOUBLE PRECISION
-- );

-- Tabla para idiomas de libros
-- CREATE TABLE libro_idiomas (
--     libro_id BIGINT REFERENCES libros(id),
--     idiomas VARCHAR(50)
-- );