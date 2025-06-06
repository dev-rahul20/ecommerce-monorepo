-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 192.168.29.32    Database: ecom_user_db
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `mst_gender`
--

DROP TABLE IF EXISTS `mst_gender`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mst_gender` (
  `gender_id` int NOT NULL AUTO_INCREMENT,
  `gender_type` varchar(255) NOT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `last_updated_on` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`gender_id`),
  UNIQUE KEY `UKfmil8cm5yfe7u8goy1ir29dq1` (`gender_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_gender`
--

LOCK TABLES `mst_gender` WRITE;
/*!40000 ALTER TABLE `mst_gender` DISABLE KEYS */;
/*!40000 ALTER TABLE `mst_gender` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_isd`
--

DROP TABLE IF EXISTS `mst_isd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mst_isd` (
  `isd_id` int NOT NULL AUTO_INCREMENT,
  `isd_code` int DEFAULT NULL,
  `country_id` int DEFAULT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `last_updated_on` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`isd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_isd`
--

LOCK TABLES `mst_isd` WRITE;
/*!40000 ALTER TABLE `mst_isd` DISABLE KEYS */;
/*!40000 ALTER TABLE `mst_isd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_title`
--

DROP TABLE IF EXISTS `mst_title`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mst_title` (
  `title_id` int NOT NULL AUTO_INCREMENT,
  `title_type` varchar(255) NOT NULL,
  `createdOn` datetime(6) DEFAULT NULL,
  `lastUpdatedOn` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`title_id`),
  UNIQUE KEY `UKo1an6bg5c8j77autepopuic9a` (`title_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_title`
--

LOCK TABLES `mst_title` WRITE;
/*!40000 ALTER TABLE `mst_title` DISABLE KEYS */;
/*!40000 ALTER TABLE `mst_title` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `txn_mail`
--

DROP TABLE IF EXISTS `txn_mail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `txn_mail` (
  `mail_id` int NOT NULL AUTO_INCREMENT,
  `mail_email` varchar(255) DEFAULT NULL,
  `mail_domain` varchar(50) DEFAULT NULL,
  `mail_type` varchar(50) DEFAULT NULL,
  `user_id` int NOT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `last_updated_on` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`mail_id`),
  KEY `FKgevygsly2a0w078gl0dpdvw69` (`user_id`),
  CONSTRAINT `FKgevygsly2a0w078gl0dpdvw69` FOREIGN KEY (`user_id`) REFERENCES `txn_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `txn_mail`
--

LOCK TABLES `txn_mail` WRITE;
/*!40000 ALTER TABLE `txn_mail` DISABLE KEYS */;
/*!40000 ALTER TABLE `txn_mail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `txn_mobile`
--

DROP TABLE IF EXISTS `txn_mobile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `txn_mobile` (
  `mobile_id` int NOT NULL AUTO_INCREMENT,
  `mobile_type` varchar(50) DEFAULT NULL,
  `mobile_num` varchar(255) DEFAULT NULL,
  `isd_id` int DEFAULT NULL,
  `user_id` int NOT NULL,
  `createdOn` datetime(6) DEFAULT NULL,
  `lastUpdatedOn` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`mobile_id`),
  KEY `fr_mst_isd_id_idx` (`isd_id`),
  KEY `FKrp2th1225m2g4sra6hjhronad` (`user_id`),
  CONSTRAINT `FKomnhgxre8nr6bhsgyhesyywup` FOREIGN KEY (`isd_id`) REFERENCES `mst_isd` (`isd_id`),
  CONSTRAINT `FKrp2th1225m2g4sra6hjhronad` FOREIGN KEY (`user_id`) REFERENCES `txn_user` (`user_id`),
  CONSTRAINT `fr_mst_isd_id` FOREIGN KEY (`isd_id`) REFERENCES `mst_isd` (`isd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `txn_mobile`
--

LOCK TABLES `txn_mobile` WRITE;
/*!40000 ALTER TABLE `txn_mobile` DISABLE KEYS */;
/*!40000 ALTER TABLE `txn_mobile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `txn_user`
--

DROP TABLE IF EXISTS `txn_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `txn_user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `gender_id` int DEFAULT NULL,
  `title_id` int DEFAULT NULL,
  `user_age` varchar(255) DEFAULT NULL,
  `user_first_name` varchar(255) DEFAULT NULL,
  `user_last_name` varchar(255) DEFAULT NULL,
  `user_middle_name` varchar(255) DEFAULT NULL,
  `createdOn` datetime(6) DEFAULT NULL,
  `lastUpdatedOn` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `FKfq1i2pxp4bx1bkuq51ori3w89` (`gender_id`),
  KEY `FKhlf6c4edarhw0b9pson4mijhx` (`title_id`),
  CONSTRAINT `FKfq1i2pxp4bx1bkuq51ori3w89` FOREIGN KEY (`gender_id`) REFERENCES `mst_gender` (`gender_id`),
  CONSTRAINT `FKhlf6c4edarhw0b9pson4mijhx` FOREIGN KEY (`title_id`) REFERENCES `mst_title` (`title_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `txn_user`
--

LOCK TABLES `txn_user` WRITE;
/*!40000 ALTER TABLE `txn_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `txn_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'ecom_user_db'
--

--
-- Dumping routines for database 'ecom_user_db'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-06 18:42:16
