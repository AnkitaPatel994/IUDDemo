-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Dec 12, 2018 at 01:35 PM
-- Server version: 5.6.16
-- PHP Version: 5.5.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `test`
--

-- --------------------------------------------------------

--
-- Table structure for table `tblperson`
--

CREATE TABLE IF NOT EXISTS `tblperson` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `gender` varchar(100) NOT NULL,
  `hobby` varchar(100) NOT NULL,
  `dob` varchar(100) NOT NULL,
  `img` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=21 ;

--
-- Dumping data for table `tblperson`
--

INSERT INTO `tblperson` (`id`, `name`, `gender`, `hobby`, `dob`, `img`) VALUES
(13, 'Parth', 'Male', 'Music,Dance', '15/02/1998', 'img/5c10b2427cfcd.jpeg'),
(15, 'Viral', 'Male', 'Music,Movie', '02/05/1988', 'img/5c10b8df133a7.jpeg'),
(16, 'Mayur', 'Male', 'Music,Movie', '04/06/1993', 'img/5c10cd1205028.jpeg'),
(17, 'Ankita', 'Female', 'Music,Movie', '15/03/1994', 'img/5c10cd3a81995.jpeg'),
(18, 'Julita', 'Female', 'Music,Dance', '30/06/1993', 'img/5c10cd8c9fcc6.jpeg'),
(19, 'kaniska', 'Female', 'Music,Movie,Dance', '13/05/2013', 'img/5c10cdccabec5.jpeg');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
