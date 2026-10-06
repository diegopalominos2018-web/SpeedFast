-- ============================================================
-- SpeedFast - Script de creación de base de datos
-- Compatible con el proyecto Java/JDBC de Semana 8
-- ============================================================

CREATE DATABASE IF NOT EXISTS speedfast_db;
USE speedfast_db;

-- ------------------------------------------------------------
-- Tabla: repartidor
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS repartidor (
    id INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- ------------------------------------------------------------
-- Tabla: pedido
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS pedido (
    id INT PRIMARY KEY,
    direccion VARCHAR(255) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    distancia DECIMAL(10,2) NOT NULL DEFAULT 0,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    CONSTRAINT chk_pedido_tipo
        CHECK (tipo IN ('Comida', 'Encomienda', 'Express', 'COMIDA', 'ENCOMIENDA', 'EXPRESS')),
    CONSTRAINT chk_pedido_estado
        CHECK (estado IN ('PENDIENTE', 'EN_REPARTO', 'ENTREGADO'))
);

-- ------------------------------------------------------------
-- Tabla: entrega
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS entrega (
    id INT PRIMARY KEY,
    id_pedido INT NOT NULL,
    id_repartidor INT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    CONSTRAINT entrega_ibfk_1
        FOREIGN KEY (id_pedido)
        REFERENCES pedido(id),
    CONSTRAINT entrega_ibfk_2
        FOREIGN KEY (id_repartidor)
        REFERENCES repartidor(id)
);

-- ------------------------------------------------------------
-- Consultas de comprobación
-- ------------------------------------------------------------
SELECT * FROM repartidor;
SELECT * FROM pedido;
SELECT * FROM entrega;

-- La siguiente consulta permite comprobar la relación completa:
SELECT
    e.id AS id_entrega,
    p.id AS id_pedido,
    p.direccion,
    r.id AS id_repartidor,
    r.nombre AS repartidor,
    e.fecha,
    e.hora
FROM entrega e
INNER JOIN pedido p ON e.id_pedido = p.id
INNER JOIN repartidor r ON e.id_repartidor = r.id
ORDER BY e.id;
