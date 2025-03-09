-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS subastas_autos;
USE subastas_autos;

-- Tabla base para usuarios (implementando herencia)
CREATE TABLE usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    tipo_usuario ENUM('VENDEDOR', 'COMPRADOR', 'ADMINISTRADOR') NOT NULL
);

-- Tabla vendedores (hereda de usuarios)
CREATE TABLE vendedores (
    id_usuario INT PRIMARY KEY,
    ruc VARCHAR(13),
    direccion_negocio VARCHAR(255),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

-- Tabla compradores (hereda de usuarios)
CREATE TABLE compradores (
    id_usuario INT PRIMARY KEY,
    dni VARCHAR(10),
    direccion_envio VARCHAR(255),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

-- Tabla administradores (hereda de usuarios)
CREATE TABLE administradores (
    id_usuario INT PRIMARY KEY,
    nivel_acceso ENUM('TOTAL', 'PARCIAL') NOT NULL,
    departamento VARCHAR(100),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

-- Tabla de autos
CREATE TABLE autos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_vendedor INT NOT NULL,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    anio INT NOT NULL,
    kilometraje DECIMAL(10,2),
    color VARCHAR(30),
    precio_base DECIMAL(12,2) NOT NULL,
    descripcion TEXT,
    estado ENUM('DISPONIBLE', 'EN_SUBASTA', 'VENDIDO') DEFAULT 'DISPONIBLE',
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_vendedor) REFERENCES vendedores(id_usuario)
);

-- Tabla de subastas
CREATE TABLE subastas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fecha_inicio DATETIME NOT NULL,
    fecha_fin DATETIME NOT NULL,
    estado ENUM('PENDIENTE', 'ACTIVA', 'FINALIZADA', 'CANCELADA') DEFAULT 'PENDIENTE',
    descripcion TEXT,
    creado_por INT NOT NULL,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (creado_por) REFERENCES administradores(id_usuario)
);

-- Tabla de relación entre subastas y autos
CREATE TABLE subasta_autos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_subasta INT NOT NULL,
    id_auto INT NOT NULL,
    precio_final DECIMAL(12,2),
    estado ENUM('PENDIENTE', 'VENDIDO', 'NO_VENDIDO') DEFAULT 'PENDIENTE',
    FOREIGN KEY (id_subasta) REFERENCES subastas(id),
    FOREIGN KEY (id_auto) REFERENCES autos(id)
);

-- Tabla de pujas
CREATE TABLE pujas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_subasta_auto INT NOT NULL,
    id_comprador INT NOT NULL,
    monto DECIMAL(12,2) NOT NULL,
    fecha_puja DATETIME DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('ACTIVA', 'GANADORA', 'PERDEDORA') DEFAULT 'ACTIVA',
    FOREIGN KEY (id_subasta_auto) REFERENCES subasta_autos(id),
    FOREIGN KEY (id_comprador) REFERENCES compradores(id_usuario)
);

-- Índices para optimizar las consultas
CREATE INDEX idx_usuarios_email ON usuarios(email);
CREATE INDEX idx_autos_estado ON autos(estado);
CREATE INDEX idx_subastas_estado ON subastas(estado);
CREATE INDEX idx_pujas_subasta_auto ON pujas(id_subasta_auto);

-- Triggers para validaciones
DELIMITER //

-- Trigger para verificar que un comprador no puje por sus propios autos
CREATE TRIGGER before_puja_insert
BEFORE INSERT ON pujas
FOR EACH ROW
BEGIN
    DECLARE vendedor_id INT;
    
    SELECT a.id_vendedor INTO vendedor_id
    FROM subasta_autos sa
    JOIN autos a ON sa.id_auto = a.id
    WHERE sa.id = NEW.id_subasta_auto;
    
    IF vendedor_id = (SELECT id_usuario FROM compradores WHERE id_usuario = NEW.id_comprador) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Un comprador no puede pujar por sus propios autos';
    END IF;
END//

-- Trigger para actualizar el estado del auto cuando se vende
CREATE TRIGGER after_subasta_auto_update
AFTER UPDATE ON subasta_autos
FOR EACH ROW
BEGIN
    IF NEW.estado = 'VENDIDO' THEN
        UPDATE autos SET estado = 'VENDIDO' WHERE id = NEW.id_auto;
    ELSEIF NEW.estado = 'NO_VENDIDO' THEN
        UPDATE autos SET estado = 'DISPONIBLE' WHERE id = NEW.id_auto;
    END IF;
END//

DELIMITER ; 