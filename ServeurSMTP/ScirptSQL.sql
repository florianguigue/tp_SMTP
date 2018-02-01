-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  jeu. 01 fév. 2018 à 20:14
-- Version du serveur :  5.7.19
-- Version de PHP :  5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `serverpop`
--

-- --------------------------------------------------------

--
-- Structure de la table `authentication`
--

DROP TABLE IF EXISTS `authentication`;
CREATE TABLE IF NOT EXISTS `authentication` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `authentication`
--

INSERT INTO `authentication` (`id`, `login`, `password`, `address`) VALUES
(1, 'test1', 'password', 'test1@serverpop.fr'),
(2, 'test2', 'password', 'test2@serverpop.fr'),
(3, 'test3', 'password', 'test3@serverpop.fr'),
(4, 'test4', 'password', 'test4@serverpop.fr');

-- --------------------------------------------------------

--
-- Structure de la table `mail`
--

DROP TABLE IF EXISTS `mail`;
CREATE TABLE IF NOT EXISTS `mail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `addressSender` varchar(255) NOT NULL,
  `addressReceiver` varchar(255) NOT NULL,
  `subject` varchar(255) NOT NULL,
  `body` varchar(255) NOT NULL,
  `Date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_id_receiver_id` (`addressReceiver`),
  KEY `fk_id_sender_id` (`addressSender`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `mail`
--

INSERT INTO `mail` (`id`, `addressSender`, `addressReceiver`, `subject`, `body`, `Date`) VALUES
(1, 'test1@test.com', 'test2@serverpop.fr', 'test', 'Ceci est un test', '2018-01-31 14:03:17'),
(3, 'test3@test.com', 'test1@serverpop.fr', 'test', 'Ceci est un test 2', '2018-01-31 14:03:17'),
(4, 'test1@test.com', 'test1@serverpop.fr', 'test', 'Ceci est un test 3', '2018-01-31 14:03:17'),
(5, 'test1@test.com', 'test1@serverpop.fr', 'test', 'Ceci est un test 4', '2018-01-31 14:03:17');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
