-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: cm
-- ------------------------------------------------------
-- Server version	8.0.14

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `column_`
--

LOCK TABLES `column_` WRITE;
/*!40000 ALTER TABLE `column_` DISABLE KEYS */;
INSERT INTO `column_` VALUES (1,1,'name8',0,'/product-1/column/c33b40ae-9e5f-4594-8d3e-1d6db48e6770.png','link8','options8'),(2,1,'111',0,NULL,NULL,NULL),(3,1,'26',0,'/product-1/column-3/posterFile.gif',NULL,NULL),(4,1,NULL,0,'/product-1/column-4/posterFile.gif',NULL,NULL),(5,1,NULL,0,NULL,NULL,NULL);
/*!40000 ALTER TABLE `column_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `column_content`
--

LOCK TABLES `column_content` WRITE;
/*!40000 ALTER TABLE `column_content` DISABLE KEYS */;
INSERT INTO `column_content` VALUES (1,1,0,1,8),(1,1,1,2,4);
/*!40000 ALTER TABLE `column_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `content`
--

LOCK TABLES `content` WRITE;
/*!40000 ALTER TABLE `content` DISABLE KEYS */;
INSERT INTO `content` VALUES (1,1,NULL,NULL,NULL,0,NULL,NULL,'test','b'),(2,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(4,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(5,1,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ftp`
--

LOCK TABLES `ftp` WRITE;
/*!40000 ALTER TABLE `ftp` DISABLE KEYS */;
INSERT INTO `ftp` VALUES (1,'localhost',21,'cm','111111');
/*!40000 ALTER TABLE `ftp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'zjy333','xx'),(2,'zjy',NULL),(3,'zjy333',NULL),(4,'test',NULL),(5,'test',NULL);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (999999,'test'),(1000000,'test'),(1000001,'test');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-02-17 22:25:29
