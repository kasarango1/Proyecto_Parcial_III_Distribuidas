-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 26-02-2025 a las 05:16:39
-- Versión del servidor: 8.3.0
-- Versión de PHP: 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `autos`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ganadores`
--

DROP TABLE IF EXISTS `ganadores`;
CREATE TABLE IF NOT EXISTS `ganadores` (
  `id` int NOT NULL AUTO_INCREMENT,
  `subasta_id` int NOT NULL,
  `vehiculo_id` int NOT NULL,
  `comprador_id` int NOT NULL,
  `monto` decimal(10,2) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `subasta_id` (`subasta_id`),
  KEY `vehiculo_id` (`vehiculo_id`),
  KEY `comprador_id` (`comprador_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pujas`
--

DROP TABLE IF EXISTS `pujas`;
CREATE TABLE IF NOT EXISTS `pujas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `comprador_id` int NOT NULL,
  `subasta_id` int NOT NULL,
  `vehiculo_id` int NOT NULL,
  `monto` decimal(10,2) NOT NULL,
  `timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `comprador_id` (`comprador_id`),
  KEY `subasta_id` (`subasta_id`),
  KEY `vehiculo_id` (`vehiculo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `subastas`
--

DROP TABLE IF EXISTS `subastas`;
CREATE TABLE IF NOT EXISTS `subastas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fecha_inicio` datetime NOT NULL,
  `fecha_fin` datetime NOT NULL,
  `estado` enum('activa','finalizada','cancelada') DEFAULT 'activa',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `subasta_vehiculos`
--

DROP TABLE IF EXISTS `subasta_vehiculos`;
CREATE TABLE IF NOT EXISTS `subasta_vehiculos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `subasta_id` int NOT NULL,
  `vehiculo_id` int NOT NULL,
  `precio_minimo` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `subasta_id` (`subasta_id`),
  KEY `vehiculo_id` (`vehiculo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `contraseña` varchar(255) NOT NULL,
  `tipo` enum('vendedor','comprador','admin') NOT NULL,
  `activo` tinyint(1) DEFAULT '1',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `correo` (`correo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vehiculos`
--

DROP TABLE IF EXISTS `vehiculos`;
CREATE TABLE IF NOT EXISTS `vehiculos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `vendedor_id` int NOT NULL,
  `marca` varchar(50) NOT NULL,
  `modelo` varchar(50) NOT NULL,
  `año` year NOT NULL,
  `precio_base` decimal(10,2) NOT NULL,
  `vendido` tinyint(1) DEFAULT '0',
  `eliminado` tinyint(1) DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `vendedor_id` (`vendedor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `ganadores`
--
ALTER TABLE `ganadores`
  ADD CONSTRAINT `ganadores_ibfk_1` FOREIGN KEY (`subasta_id`) REFERENCES `subastas` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `ganadores_ibfk_2` FOREIGN KEY (`vehiculo_id`) REFERENCES `vehiculos` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `ganadores_ibfk_3` FOREIGN KEY (`comprador_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `pujas`
--
ALTER TABLE `pujas`
  ADD CONSTRAINT `pujas_ibfk_1` FOREIGN KEY (`comprador_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `pujas_ibfk_2` FOREIGN KEY (`subasta_id`) REFERENCES `subastas` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `pujas_ibfk_3` FOREIGN KEY (`vehiculo_id`) REFERENCES `vehiculos` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `subasta_vehiculos`
--
ALTER TABLE `subasta_vehiculos`
  ADD CONSTRAINT `subasta_vehiculos_ibfk_1` FOREIGN KEY (`subasta_id`) REFERENCES `subastas` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `subasta_vehiculos_ibfk_2` FOREIGN KEY (`vehiculo_id`) REFERENCES `vehiculos` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `vehiculos`
--
ALTER TABLE `vehiculos`
  ADD CONSTRAINT `vehiculos_ibfk_1` FOREIGN KEY (`vendedor_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
