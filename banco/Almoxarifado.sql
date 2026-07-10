-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: almoxarifado
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `auditoria_movimentacoes`
--

DROP TABLE IF EXISTS `auditoria_movimentacoes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auditoria_movimentacoes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `data_hora` datetime(6) DEFAULT NULL,
  `observacao` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `produto` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `quantidade` double DEFAULT NULL,
  `tipo` enum('ENTRADA','SAIDA') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `usuario` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `prefeitura_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5jwrn10rw5g25jm3o6kv2miyh` (`prefeitura_id`),
  CONSTRAINT `FK5jwrn10rw5g25jm3o6kv2miyh` FOREIGN KEY (`prefeitura_id`) REFERENCES `prefeituras` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditoria_movimentacoes`
--

LOCK TABLES `auditoria_movimentacoes` WRITE;
/*!40000 ALTER TABLE `auditoria_movimentacoes` DISABLE KEYS */;
/*!40000 ALTER TABLE `auditoria_movimentacoes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorias` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `prefeitura_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKgmu2lcl4aqkhktfkwsvk7me6e` (`nome`,`prefeitura_id`),
  KEY `FKidxbf7wbtrfdl07jw8k2tqyxf` (`prefeitura_id`),
  CONSTRAINT `FKidxbf7wbtrfdl07jw8k2tqyxf` FOREIGN KEY (`prefeitura_id`) REFERENCES `prefeituras` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fornecedores`
--

DROP TABLE IF EXISTS `fornecedores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fornecedores` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ativo` bit(1) DEFAULT NULL,
  `cnpj` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `endereco` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nome_fantasia` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `razao_social` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `telefone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `prefeitura_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_FORNECEDOR_PREF` (`cnpj`,`prefeitura_id`),
  KEY `FK7qnuf1o5t41xtag3hi441nl1w` (`prefeitura_id`),
  CONSTRAINT `FK7qnuf1o5t41xtag3hi441nl1w` FOREIGN KEY (`prefeitura_id`) REFERENCES `prefeituras` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fornecedores`
--

LOCK TABLES `fornecedores` WRITE;
/*!40000 ALTER TABLE `fornecedores` DISABLE KEYS */;
/*!40000 ALTER TABLE `fornecedores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logs_sistema`
--

DROP TABLE IF EXISTS `logs_sistema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logs_sistema` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `acao` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `data_hora` datetime(6) DEFAULT NULL,
  `detalhes` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `entidade` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `usuario` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `prefeitura_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9i6btawu5fufry8qhsbsstw2x` (`prefeitura_id`),
  CONSTRAINT `FK9i6btawu5fufry8qhsbsstw2x` FOREIGN KEY (`prefeitura_id`) REFERENCES `prefeituras` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logs_sistema`
--

LOCK TABLES `logs_sistema` WRITE;
/*!40000 ALTER TABLE `logs_sistema` DISABLE KEYS */;
INSERT INTO `logs_sistema` VALUES (1,'CADASTRO','2026-06-12 16:52:56.294302','Feijão Carioca ','PRODUTO','admin',NULL),(2,'CADASTRO','2026-06-15 10:25:31.771939','Cadeira de Escritório ','PRODUTO','admin',NULL),(3,'INATIVACAO','2026-06-15 10:41:47.334097','Arroz Tipo 1','PRODUTO','admin',NULL),(4,'INATIVACAO','2026-06-15 10:41:57.086512','Arroz Tipo 1','PRODUTO','admin',NULL),(5,'INATIVACAO','2026-06-15 10:42:09.230210','Arroz Tipo 1','PRODUTO','admin',NULL),(6,'INATIVACAO','2026-06-15 10:42:51.843679','Arroz Tipo 1','PRODUTO','admin',NULL),(7,'INATIVACAO','2026-06-15 10:43:52.710042','Cadeira de Escritório ','PRODUTO','admin',NULL),(8,'INATIVACAO','2026-06-15 10:44:02.493319','Arroz Tipo 1','PRODUTO','admin',NULL),(9,'INATIVACAO','2026-06-15 10:51:17.192203','Arroz Tipo 1','PRODUTO','admin',NULL),(10,'CADASTRO','2026-06-15 14:18:27.024963','Televisor 50\'\' ','PRODUTO','admin',NULL),(11,'CADASTRO','2026-06-16 11:09:54.093701','Sal Marinho ','PRODUTO','admin',NULL),(12,'CADASTRO','2026-06-17 14:28:34.313800','Cadeira de Escritório ','PRODUTO','admin',NULL),(13,'ATUALIZACAO','2026-06-17 15:02:18.551441','Cadeira de Escritório ','PRODUTO','admin',NULL),(14,'CADASTRO','2026-06-19 16:46:30.676826','Fornecedor LTDA','FORNECEDOR','admin.barreirinhas',2),(15,'ATUALIZACAO','2026-06-19 16:46:37.686535','Fornecedor LTDA','FORNECEDOR','admin.barreirinhas',2),(16,'CADASTRO','2026-06-19 17:10:46.915996','Teste de Produto','PRODUTO','admin.barreirinhas',2),(17,'CADASTRO','2026-06-19 17:14:18.196261','Secretaria de Educação','SETOR','admin.barreirinhas',2),(18,'CADASTRO','2026-06-22 08:34:30.055662','Secretaria de Saúde','SETOR','admin',1),(19,'CADASTRO','2026-06-29 11:10:10.974411','Excellence','FORNECEDOR','admin',1),(20,'CADASTRO','2026-06-29 11:11:13.945664','Fornecedor Tal ','FORNECEDOR','admin',1),(21,'CADASTRO','2026-06-29 11:15:43.348497','Secretaria de Obras','SETOR','admin',1),(22,'CADASTRO','2026-06-29 16:31:19.669323','Macarão','PRODUTO','admin',1),(23,'CADASTRO','2026-06-29 16:59:58.734655','Teste 2','PRODUTO','Junior131',NULL),(24,'CADASTRO','2026-06-29 17:17:15.767777','TEste2','FORNECEDOR','Junior131',2),(25,'INATIVACAO','2026-06-29 17:19:21.487127','TEste2','FORNECEDOR','Junior131',2),(26,'INATIVACAO','2026-06-29 17:19:25.700519','Fornecedor LTDA','FORNECEDOR','Junior131',2),(27,'CADASTRO','2026-06-29 17:31:19.101341','Fornecedor Novo','FORNECEDOR','Junior131',2),(28,'CADASTRO','2026-06-29 17:33:32.749928','Setor Novo','SETOR','Junior131',2),(29,'ATUALIZACAO','2026-06-30 08:33:19.460198','Fornecedor Tal2','FORNECEDOR','Junior131',1),(30,'CADASTRO','2026-06-30 15:04:29.346721','Dipirona Teste','PRODUTO','Junior131',2),(31,'ATUALIZACAO','2026-06-30 15:22:33.950487','Dipirona Teste','PRODUTO','Junior131',2),(32,'ATUALIZACAO','2026-06-30 17:16:29.182610','Dipirona Teste','PRODUTO','Junior131',2),(33,'INATIVACAO','2026-07-03 10:57:26.876743','Sal Marinho ','PRODUTO','Junior131',1);
/*!40000 ALTER TABLE `logs_sistema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lotes`
--

DROP TABLE IF EXISTS `lotes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lotes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `numero_lote` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `quantidade` double DEFAULT NULL,
  `data_validade` date DEFAULT NULL,
  `produto_id` bigint DEFAULT NULL,
  `prefeitura_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_lote_produto` (`produto_id`),
  KEY `FKn6upqgayncyjsr4cnghtnvimj` (`prefeitura_id`),
  CONSTRAINT `fk_lote_produto` FOREIGN KEY (`produto_id`) REFERENCES `produtos` (`id`),
  CONSTRAINT `FKn6upqgayncyjsr4cnghtnvimj` FOREIGN KEY (`prefeitura_id`) REFERENCES `prefeituras` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lotes`
--

LOCK TABLES `lotes` WRITE;
/*!40000 ALTER TABLE `lotes` DISABLE KEYS */;
/*!40000 ALTER TABLE `lotes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimentacoes`
--

DROP TABLE IF EXISTS `movimentacoes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movimentacoes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `data_movimentacao` datetime(6) DEFAULT NULL,
  `observacao` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `quantidade` double DEFAULT NULL,
  `tipo` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `produto_id` bigint DEFAULT NULL,
  `setor_id` bigint DEFAULT NULL,
  `fornecedor_id` bigint DEFAULT NULL,
  `prefeitura_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7nwbhaoq6lqh2wo48e2o5i25e` (`produto_id`),
  KEY `FKexjyjd9j4v3be66p3nc52fwht` (`setor_id`),
  KEY `FKo64sp28htgm3tsdi43k29m481` (`fornecedor_id`),
  KEY `idx_mov_prefeitura` (`prefeitura_id`),
  CONSTRAINT `FK5m64kvy8c1u4fcvvv4bbdbl40` FOREIGN KEY (`prefeitura_id`) REFERENCES `prefeituras` (`id`),
  CONSTRAINT `FK7nwbhaoq6lqh2wo48e2o5i25e` FOREIGN KEY (`produto_id`) REFERENCES `produtos` (`id`),
  CONSTRAINT `FKexjyjd9j4v3be66p3nc52fwht` FOREIGN KEY (`setor_id`) REFERENCES `setores` (`id`),
  CONSTRAINT `FKo64sp28htgm3tsdi43k29m481` FOREIGN KEY (`fornecedor_id`) REFERENCES `fornecedores` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimentacoes`
--

LOCK TABLES `movimentacoes` WRITE;
/*!40000 ALTER TABLE `movimentacoes` DISABLE KEYS */;
/*!40000 ALTER TABLE `movimentacoes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prefeituras`
--

DROP TABLE IF EXISTS `prefeituras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prefeituras` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ativo` bit(1) DEFAULT NULL,
  `cidade` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cnpj` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `estado` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nome` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `logo` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKlgyypq9bm5pe8th7wol44npss` (`cnpj`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prefeituras`
--

LOCK TABLES `prefeituras` WRITE;
/*!40000 ALTER TABLE `prefeituras` DISABLE KEYS */;
INSERT INTO `prefeituras` VALUES (1,_binary '','Tutóia','06307893000180','MA','Prefeitura Municipal de Tutóia','/logos/72131f2f-a7b2-482c-8404-bfbfb6afe31c.png'),(2,_binary '','Barreirinhas','98765432000199','MA','Prefeitura Municipal de Barreirinhas',NULL),(3,_binary '','Governador Nunes Freire','11.111.111/1111-11','MA','Prefeitura Municipal de Governador Nunes Freire','/logos/2cdecaca-2b95-4b8a-bc37-1b9ab541a48c.png');
/*!40000 ALTER TABLE `prefeituras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produtos`
--

DROP TABLE IF EXISTS `produtos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produtos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ativo` bit(1) DEFAULT NULL,
  `codigo` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `estoque_atual` double NOT NULL DEFAULT '0',
  `estoque_minimo` double NOT NULL DEFAULT '0',
  `nome` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `unidade` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `fornecedor_id` bigint DEFAULT NULL,
  `data_cadastro` datetime DEFAULT NULL,
  `data_atualizacao` datetime DEFAULT NULL,
  `categoria_id` bigint DEFAULT NULL,
  `data_validade` date DEFAULT NULL,
  `prefeitura_id` bigint DEFAULT NULL,
  `valor_unitario` double NOT NULL,
  `unidade_estoque` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `unidade_embalagem` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `fator_conversao` double DEFAULT NULL,
  `unidade_compra` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKpk2k37y05kgqceufn556j55w3` (`codigo`),
  UNIQUE KEY `UKip5w6kbtr7na5a8anmquqqhtm` (`codigo`,`prefeitura_id`),
  KEY `fk_produto_fornecedor` (`fornecedor_id`),
  KEY `fk_produto_categoria` (`categoria_id`),
  KEY `FKtihefofv54e97ls467uwrwrqg` (`prefeitura_id`),
  CONSTRAINT `FK8rqw0ljwdaom34jr2t46bjtrn` FOREIGN KEY (`categoria_id`) REFERENCES `categorias` (`id`),
  CONSTRAINT `fk_produto_categoria` FOREIGN KEY (`categoria_id`) REFERENCES `categorias` (`id`),
  CONSTRAINT `fk_produto_fornecedor` FOREIGN KEY (`fornecedor_id`) REFERENCES `fornecedores` (`id`),
  CONSTRAINT `FKtihefofv54e97ls467uwrwrqg` FOREIGN KEY (`prefeitura_id`) REFERENCES `prefeituras` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produtos`
--

LOCK TABLES `produtos` WRITE;
/*!40000 ALTER TABLE `produtos` DISABLE KEYS */;
/*!40000 ALTER TABLE `produtos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `setores`
--

DROP TABLE IF EXISTS `setores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `setores` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ativo` bit(1) DEFAULT NULL,
  `nome` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `responsavel` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `telefone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `prefeitura_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKaxh8bu5kkasy2x2ugd0mpbxdr` (`prefeitura_id`),
  CONSTRAINT `FKaxh8bu5kkasy2x2ugd0mpbxdr` FOREIGN KEY (`prefeitura_id`) REFERENCES `prefeituras` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `setores`
--

LOCK TABLES `setores` WRITE;
/*!40000 ALTER TABLE `setores` DISABLE KEYS */;
/*!40000 ALTER TABLE `setores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ativo` bit(1) DEFAULT NULL,
  `nome` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` enum('ROLE_SUPER_ADMIN','ROLE_ADMIN_PREFEITURA','ROLE_USUARIO') COLLATE utf8mb4_unicode_ci NOT NULL,
  `senha` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `prefeitura_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKm2dvbwfge291euvmk6vkkocao` (`username`),
  KEY `fk_usuario_prefeitura` (`prefeitura_id`),
  CONSTRAINT `fk_usuario_prefeitura` FOREIGN KEY (`prefeitura_id`) REFERENCES `prefeituras` (`id`),
  CONSTRAINT `FKs0brn3e12d68yb0ss72dkey50` FOREIGN KEY (`prefeitura_id`) REFERENCES `prefeituras` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,_binary '','Administrador2','ROLE_SUPER_ADMIN','$2a$10$rIhrxcFpxLbKuxvwbuR3meTfpo1lnwmc59Isys/dUNcZ51PePtV2C','admin',1),(9,_binary '','Joel Junior','ROLE_SUPER_ADMIN','$2a$10$tBpy4t4QEw1gx8ASCqax2e/BqrMXl7GeShnkj4OGfFDgEnpSmr2BK','Junior131',NULL);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-07-09 11:51:03
