-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 05-03-2025 a las 00:06:36
-- Versión del servidor: 8.0.31
-- Versión de PHP: 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `proyectosubasta`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `administrador`
--

DROP TABLE IF EXISTS `administrador`;
CREATE TABLE IF NOT EXISTS `administrador` (
  `id_usuario` int NOT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `administrador`
--

INSERT INTO `administrador` (`id_usuario`) VALUES
(5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `auto`
--

DROP TABLE IF EXISTS `auto`;
CREATE TABLE IF NOT EXISTS `auto` (
  `id_auto` int NOT NULL AUTO_INCREMENT,
  `marca` varchar(50) NOT NULL,
  `modelo` varchar(50) NOT NULL,
  `año` int NOT NULL,
  `precio_base` decimal(10,2) NOT NULL,
  `estado` varchar(20) DEFAULT 'DISPONIBLE',
  `subasta_id` int DEFAULT NULL,
  `vendedor_id` int NOT NULL,
  `estado_logico` varchar(10) DEFAULT 'ACTIVO',
  PRIMARY KEY (`id_auto`),
  KEY `subasta_id` (`subasta_id`),
  KEY `vendedor_id` (`vendedor_id`)
) ;

--
-- Volcado de datos para la tabla `auto`
--

INSERT INTO `auto` (`id_auto`, `marca`, `modelo`, `año`, `precio_base`, `estado`, `subasta_id`, `vendedor_id`, `estado_logico`) VALUES
(1, 'Toyota', 'Corolla', 2020, '12000.00', 'DISPONIBLE', 1, 2, 'ACTIVO'),
(2, 'Honda', 'Civic', 2019, '14000.00', 'DISPONIBLE', 1, 2, 'ACTIVO'),
(3, 'Ford', 'Focus', 2018, '10000.00', 'DISPONIBLE', 1, 2, 'ACTIVO'),
(4, 'Chevrolet', 'Cruze', 2021, '16000.00', 'DISPONIBLE', 2, 4, 'ACTIVO'),
(5, 'Nissan', 'Sentra', 2017, '9000.00', 'DISPONIBLE', 2, 4, 'ACTIVO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comprador`
--

DROP TABLE IF EXISTS `comprador`;
CREATE TABLE IF NOT EXISTS `comprador` (
  `id_usuario` int NOT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `comprador`
--

INSERT INTO `comprador` (`id_usuario`) VALUES
(1),
(3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `puja`
--

DROP TABLE IF EXISTS `puja`;
CREATE TABLE IF NOT EXISTS `puja` (
  `id_puja` int NOT NULL AUTO_INCREMENT,
  `auto_id` int NOT NULL,
  `comprador_id` int NOT NULL,
  `monto` decimal(10,2) NOT NULL,
  `fecha` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `estado` varchar(20) DEFAULT 'ACTIVA',
  PRIMARY KEY (`id_puja`),
  KEY `auto_id` (`auto_id`),
  KEY `comprador_id` (`comprador_id`)
) ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `subasta`
--

DROP TABLE IF EXISTS `subasta`;
CREATE TABLE IF NOT EXISTS `subasta` (
  `id_subasta` int NOT NULL AUTO_INCREMENT,
  `fecha_inicio` datetime NOT NULL,
  `fecha_fin` datetime NOT NULL,
  `estado` varchar(20) DEFAULT 'PROGRAMADA',
  `vendedor_id` int NOT NULL,
  `estado_logico` varchar(10) DEFAULT 'ACTIVO',
  PRIMARY KEY (`id_subasta`),
  KEY `vendedor_id` (`vendedor_id`)
) ;

--
-- Volcado de datos para la tabla `subasta`
--

INSERT INTO `subasta` (`id_subasta`, `fecha_inicio`, `fecha_fin`, `estado`, `vendedor_id`, `estado_logico`) VALUES
(1, '2025-03-05 00:00:00', '2025-03-05 01:00:00', 'ACTIVA', 2, 'ACTIVO'),
(2, '2025-03-05 00:00:00', '2025-03-05 03:00:00', 'ACTIVA', 4, 'ACTIVO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE IF NOT EXISTS `usuario` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `contraseña` varchar(255) NOT NULL,
  `tipo` varchar(20) NOT NULL,
  `estado_logico` varchar(10) DEFAULT 'ACTIVO',
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `correo` (`correo`)
) ;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id_usuario`, `nombre`, `correo`, `contraseña`, `tipo`, `estado_logico`) VALUES
(1, 'Juan Pérez', 'juan.perez@email.com', 'hash1', 'COMPRADOR', 'ACTIVO'),
(2, 'María Gómez', 'maria.gomez@email.com', 'hash2', 'VENDEDOR', 'ACTIVO'),
(3, 'Carlos Ruiz', 'carlos.ruiz@email.com', 'hash3', 'COMPRADOR', 'ACTIVO'),
(4, 'Laura Fernández', 'laura.fernandez@email.com', 'hash4', 'VENDEDOR', 'ACTIVO'),
(5, 'Admin', 'admin@email.com', 'hash5', 'ADMIN', 'ACTIVO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vendedor`
--

DROP TABLE IF EXISTS `vendedor`;
CREATE TABLE IF NOT EXISTS `vendedor` (
  `id_usuario` int NOT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `vendedor`
--

INSERT INTO `vendedor` (`id_usuario`) VALUES
(2),
(4);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
