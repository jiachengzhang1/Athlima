-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: db
-- ------------------------------------------------------
-- Server version	8.0.12-commercial

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(255) DEFAULT NULL,
  `date` char(255) DEFAULT NULL,
  `time` char(255) DEFAULT NULL,
  `location` char(255) DEFAULT NULL,
  `skill` char(1) DEFAULT NULL,
  `description` char(255) DEFAULT NULL,
  `teamSize` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES (7,'jasd','wqer','asfxx','qwwe','b','werqwr',30),(8,'jasd','wqer','asfxx','qwwe','b','werqwr',30),(9,'jasd','wqer','asfxx','qwwe','b','werqwr',30),(10,'jasd','wqer','asfxx','qwwe','b','werqwr',30),(11,'jasd','wqer','asfxx','qwwe','b','werqwr',30),(12,'jasd','wqer','asfxx','qwwe','b','werqwr',30),(13,'jasd','wqer','asfxx','qwwe','b','werqwr',30),(14,'jasd','wqer','asfxx','qwwe','b','werqwr',30),(15,'jasd','wqer','asfxx','qwwe','b','werqwr',30);
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sport`
--

DROP TABLE IF EXISTS `sport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sport` (
  `id` int(255) NOT NULL,
  `sportName` char(255) DEFAULT NULL,
  `user_id` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sport`
--

LOCK TABLES `sport` WRITE;
/*!40000 ALTER TABLE `sport` DISABLE KEYS */;
INSERT INTO `sport` VALUES (1,'soccer',1),(2,'basketball',30),(3,'football',28),(4,'somewhat',123),(5,'idk',999),(6,'udontwannatakethat',1024),(7,'iunderstand',65535),(8,'error',404),(9,'asdasd',1111),(10,'1001001001',0);
/*!40000 ALTER TABLE `sport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `password` char(12) DEFAULT NULL,
  `readName` char(255) DEFAULT NULL,
  `emailAddress` char(255) DEFAULT NULL,
  `ps` char(255) DEFAULT '',
  `prefer_sport` char(255) DEFAULT NULL,
  `schoolYear` int(1) DEFAULT NULL,
  `student_ID` int(255) DEFAULT NULL,
  `event_id` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `emailAddress` (`emailAddress`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'123','xxx','hotmail','ppp','bbb',4,NULL,NULL),(2,'test1',NULL,NULL,'',NULL,NULL,NULL,NULL),(3,'test2',NULL,NULL,'',NULL,NULL,NULL,NULL),(4,NULL,NULL,NULL,'',NULL,NULL,NULL,NULL),(5,NULL,NULL,NULL,'',NULL,NULL,NULL,NULL),(6,'last','tester','dontremain','anything','meaningful',1,1234,5678),(7,'psw','name','email','something','football',2,3333,4444),(8,'password','person','email@something.edu','good','basketball',6,12343,99888),(9,'123456','firstone','email@firstone.edu','firstuser','none',0,23345677,1),(10,'error','here','because','theuser','didntgraduate',40,1,9),(16,'1234','name','create2','asadf','b',1,NULL,NULL),(22,'ttt','aaa','test000','bbb','ccc',1,NULL,NULL),(29,'ttt','aaa','test001','bbb','ccc',1,NULL,NULL),(31,'ttt','aaa','asjsjjs','bbb','ccc',1,NULL,NULL);
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

-- Dump completed on 2018-10-29 20:16:59
