-- MySQL dump 10.13  Distrib 5.7.20, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: AVOTE_TEST
-- ------------------------------------------------------
-- Server version	5.7.20-0ubuntu0.17.04.1

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
-- Table structure for table `DEPARTMENTS`
--

DROP TABLE IF EXISTS `DEPARTMENTS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DEPARTMENTS` (
  `department_id` varchar(3) NOT NULL,
  `department` varchar(100) NOT NULL,
  PRIMARY KEY (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DEPARTMENTS`
--

LOCK TABLES `DEPARTMENTS` WRITE;
/*!40000 ALTER TABLE `DEPARTMENTS` DISABLE KEYS */;
INSERT INTO `DEPARTMENTS` VALUES ('01','Ain'),('02','Aisne'),('03','Allier'),('04','Alpes-de-Haute-Provence'),('05','Hautes-Alpes'),('06','Alpes-Martimes'),('07','Ardèche'),('08','Ardennes'),('09','Ariège'),('10','Aube'),('11','Aude'),('12','Aveyron'),('13','Bouches-du-Rhône'),('14','Calvados'),('15','Cantal'),('16','Charente'),('17','Charente-Maritime'),('18','Cher'),('19','Corrèze'),('21','Côte-d\'Or'),('22','Côtes-d\'Armor'),('23','Creuse'),('24','Dordogne'),('25','Doubs'),('26','Drôme'),('27','Eure'),('28','Eure-et-Loir'),('29','Finistère'),('30','Gard'),('31','Haute-Garonne	'),('32','Gers'),('33','Gironde'),('34','Hérault'),('35','Ille-et-Vilaine'),('36','Indre'),('37','Indre-et-Loire'),('38','Isère'),('39','Jura'),('40','Landes'),('41','Loir-et-Cher'),('42','Loire'),('43','Haute-Loire'),('44','Loire-Atlantique'),('45','Loiret'),('46','Lot'),('47','Lot-et-Garonne'),('48','Lozère'),('49','Maine-et-Loire'),('50','Manche'),('51','Marne'),('52','Haute-Marne'),('53','Mayenne'),('54','Meurthe-et-Moselle'),('55','Meuse'),('56','Morbihan'),('57','Moselle'),('58','Nièvre'),('59','Nord'),('60','Oise'),('61','Orne'),('62','Pas-de-Calais	'),('63','Puy-de-Dôme'),('64','Pyrénées-Atlantique'),('65','Hautes-Pyrénées'),('66','Pyrénées-Orientales'),('67','Bas-Rhin'),('68','Haut-Rhin'),('69','Rhône'),('70','Haute-Saône'),('71','Saône-et-Loire'),('72','Sarthe'),('73','Savoie'),('74','Haute-Savoie'),('75','Paris'),('76','Seine-Maritime'),('77','Seine-et-Marne'),('78','Yvelines'),('79','Deux-Sèvres'),('80','Somme'),('81','Tarn'),('82','Tarn-et-Garonne'),('83','Var'),('84','Vaucluse'),('85','Vendée'),('86','Vienne'),('87','Haute-Vienne'),('88','Vosges'),('89','Yonne'),('91','Essonne'),('92','Hauts-de-Seine'),('93','Seine-St-Denis'),('94','Val-de-Marne'),('95','Val-d\'Oise');
/*!40000 ALTER TABLE `DEPARTMENTS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `addresses`
--

DROP TABLE IF EXISTS `addresses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `addresses` (
  `address_id` int(11) NOT NULL AUTO_INCREMENT,
  `location` varchar(255) NOT NULL,
  `zip_code` varchar(100) NOT NULL,
  `city` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  PRIMARY KEY (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `addresses`
--

LOCK TABLES `addresses` WRITE;
/*!40000 ALTER TABLE `addresses` DISABLE KEYS */;
/*!40000 ALTER TABLE `addresses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `choices`
--

DROP TABLE IF EXISTS `choices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `choices` (
  `choice_id` int(11) NOT NULL AUTO_INCREMENT,
  `poll_id` int(11) NOT NULL,
  `label` tinytext NOT NULL,
  `color` tinytext,
  PRIMARY KEY (`choice_id`),
  KEY `choice_poll_FK` (`poll_id`),
  CONSTRAINT `choice_poll_FK` FOREIGN KEY (`poll_id`) REFERENCES `polls` (`poll_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=518 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `choices`
--

LOCK TABLES `choices` WRITE;
/*!40000 ALTER TABLE `choices` DISABLE KEYS */;
INSERT INTO `choices` VALUES (513,783,'Ubuntu','red'),(514,783,'MacOS','blue'),(515,783,'Windows 10','dark-green'),(516,784,'choix 1','green'),(517,784,'choix 2','pink');
/*!40000 ALTER TABLE `choices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `polls`
--

DROP TABLE IF EXISTS `polls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `polls` (
  `poll_id` int(11) NOT NULL AUTO_INCREMENT,
  `owner_id` int(11) NOT NULL,
  `subject` text NOT NULL,
  `anonymity` tinyint(1) NOT NULL,
  `poll_type` int(11) NOT NULL,
  `number_rounds` int(11) NOT NULL,
  `is_published` tinyint(1) NOT NULL,
  `is_closed` tinyint(1) NOT NULL,
  `created_at` datetime NOT NULL,
  `published_at` datetime DEFAULT NULL,
  `closed_at` datetime DEFAULT NULL,
  `restriction_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`poll_id`),
  KEY `poll_restriction_FK` (`restriction_id`),
  KEY `poll_owner_FK` (`owner_id`),
  CONSTRAINT `poll_owner_FK` FOREIGN KEY (`owner_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `poll_restriction_FK` FOREIGN KEY (`restriction_id`) REFERENCES `restrictions` (`restriction_id`)
) ENGINE=InnoDB AUTO_INCREMENT=785 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `polls`
--

LOCK TABLES `polls` WRITE;
/*!40000 ALTER TABLE `polls` DISABLE KEYS */;
INSERT INTO `polls` VALUES (783,369,'Voter les systèmes d\'exploitation suivants de 1 à 5',0,1,1,1,0,'1970-01-01 01:00:00','2017-11-23 12:41:51',NULL,NULL),(784,446,'Sujet avec 2 choix',0,1,1,1,0,'1970-01-01 01:00:00','2017-11-23 13:08:23',NULL,NULL);
/*!40000 ALTER TABLE `polls` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restrictions`
--

DROP TABLE IF EXISTS `restrictions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `restrictions` (
  `restriction_id` int(11) NOT NULL AUTO_INCREMENT,
  `age_min` int(11) DEFAULT NULL,
  `age_max` int(11) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `department` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`restriction_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restrictions`
--

LOCK TABLES `restrictions` WRITE;
/*!40000 ALTER TABLE `restrictions` DISABLE KEYS */;
INSERT INTO `restrictions` VALUES (5,18,0,NULL,NULL),(7,0,0,NULL,NULL),(10,0,0,NULL,NULL);
/*!40000 ALTER TABLE `restrictions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_API`
--

DROP TABLE IF EXISTS `user_API`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_API` (
  `user_id` int(11) NOT NULL,
  `api_key` char(32) NOT NULL,
  PRIMARY KEY (`api_key`),
  KEY `user_api_user_FK` (`user_id`),
  CONSTRAINT `user_api_user_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_API`
--

LOCK TABLES `user_API` WRITE;
/*!40000 ALTER TABLE `user_API` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_API` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_polls`
--

DROP TABLE IF EXISTS `user_polls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_polls` (
  `user_id` int(11) NOT NULL,
  `poll_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`poll_id`),
  KEY `user_vote_poll_id_FK` (`poll_id`),
  CONSTRAINT `user_vote_poll_id_FK` FOREIGN KEY (`poll_id`) REFERENCES `polls` (`poll_id`),
  CONSTRAINT `user_vote_user_id_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_polls`
--

LOCK TABLES `user_polls` WRITE;
/*!40000 ALTER TABLE `user_polls` DISABLE KEYS */;
INSERT INTO `user_polls` VALUES (445,783),(446,783),(351,784),(369,784),(445,784),(447,784),(448,784);
/*!40000 ALTER TABLE `user_polls` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `address_id` int(11) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `phone_number` varchar(30) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` date NOT NULL,
  `is_activated` tinyint(1) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`),
  KEY `user_address_FK` (`address_id`),
  CONSTRAINT `user_address_FK` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`address_id`)
) ENGINE=InnoDB AUTO_INCREMENT=483 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (351,'a','a',NULL,'2000-11-01','a@a',NULL,'0cc175b9c0f1b6a831c399e269772661','2017-11-18',1,0),(369,'Robert','Kalna',NULL,'1994-07-05','kalnarobert@gmail.com',NULL,'0cc175b9c0f1b6a831c399e269772661','2017-11-18',1,0),(443,'First_Name','Last_Name',NULL,'1996-11-18','Emailwithage21@email',NULL,'Password','2017-11-18',1,0),(445,'Thomas','Richard',NULL,'1996-11-23','thomas@richard',NULL,'0cc175b9c0f1b6a831c399e269772661','2017-11-18',1,0),(446,'Kevin','Chaboud',NULL,'1996-11-23','kevin@chaboud',NULL,'0cc175b9c0f1b6a831c399e269772661','2017-11-18',1,0),(447,'Achraf','Abdessalem',NULL,'1996-11-23','achraf@abdessalem',NULL,'0cc175b9c0f1b6a831c399e269772661','2017-11-18',1,0),(448,'Aissatou','Diallo',NULL,'1996-11-16','aissatou@diallo',NULL,'0cc175b9c0f1b6a831c399e269772661','2017-11-18',1,0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `votes`
--

DROP TABLE IF EXISTS `votes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `votes` (
  `vote_id` int(11) NOT NULL AUTO_INCREMENT,
  `choice_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `value` int(11) DEFAULT NULL,
  PRIMARY KEY (`vote_id`),
  KEY `vote_choice_id_FK` (`choice_id`),
  KEY `vote_user_id_FK` (`user_id`),
  CONSTRAINT `vote_choice_id_FK` FOREIGN KEY (`choice_id`) REFERENCES `choices` (`choice_id`),
  CONSTRAINT `vote_user_id_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `votes`
--

LOCK TABLES `votes` WRITE;
/*!40000 ALTER TABLE `votes` DISABLE KEYS */;
INSERT INTO `votes` VALUES (37,513,445,1),(38,514,445,2),(39,515,445,2),(40,513,446,1),(41,514,446,1),(42,515,446,3),(43,516,445,2),(44,517,445,1),(45,516,369,2),(46,517,369,1),(47,516,448,2),(48,517,448,1),(49,516,447,2),(50,517,447,5),(51,516,351,2),(52,517,351,5);
/*!40000 ALTER TABLE `votes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-23 13:57:43
