-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Máquina: localhost
-- Data de Criação: 01-Jul-2016 às 13:47
-- Versão do servidor: 5.5.49-0ubuntu0.14.04.1
-- versão do PHP: 5.5.9-1ubuntu4.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de Dados: `GERI_BD`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `AVAL`
--

CREATE TABLE IF NOT EXISTS `AVAL` (
  `ID_AVAL` int(11) NOT NULL AUTO_INCREMENT,
  `NUME_UTEN` int(11) NOT NULL,
  `CC_MEDI` int(11) NOT NULL,
  `DESC_AVAL` blob NOT NULL,
  `AVAL_RESU` int(11) NOT NULL,
  `DATA_AVAL` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `TIPO_AVAL` int(11) NOT NULL,
  PRIMARY KEY (`ID_AVAL`),
  KEY `AVAL_ibfk_1` (`TIPO_AVAL`),
  KEY `AVAL_ibfk_2` (`NUME_UTEN`),
  KEY `AVAL_ibfk_3` (`CC_MEDI`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Extraindo dados da tabela `AVAL`
--

INSERT INTO `AVAL` (`ID_AVAL`, `NUME_UTEN`, `CC_MEDI`, `DESC_AVAL`, `AVAL_RESU`, `DATA_AVAL`, `TIPO_AVAL`) VALUES
(1, 12345, 12345, 0x7b6d796176616c3a22617364617364227d0a, 3, '1991-11-02 00:00:00', 1),
(2, 12345, 12345, 0x7b6d796176616c3a22617364617364227d0a, 3, '1991-11-03 00:00:00', 1),
(3, 12345, 12345, 0x7b6d796176616c3a22617364617364227d0a, 3, '1991-11-04 00:00:00', 1),
(4, 123456, 12345, 0x7b6d796176616c3a22617364617364227d0a, 2, '1991-11-05 00:00:00', 2),
(5, 12345, 12345, 0x6173646173, 2, '2016-07-01 11:16:10', 1),
(6, 12345, 12345, 0x6473616461, 2, '2016-07-01 11:20:03', 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `MEDI`
--

CREATE TABLE IF NOT EXISTS `MEDI` (
  `CC` int(11) NOT NULL,
  `NOME` varchar(250) NOT NULL,
  `PASS` varchar(40) NOT NULL,
  `TIPO_ESPE` int(11) NOT NULL,
  `DATA_NASC` date NOT NULL,
  PRIMARY KEY (`CC`),
  KEY `MEDI_ibfk_1` (`TIPO_ESPE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `MEDI`
--

INSERT INTO `MEDI` (`CC`, `NOME`, `PASS`, `TIPO_ESPE`, `DATA_NASC`) VALUES
(12345, 'luis', 'dasda5', 1, '1991-11-02'),
(14006393, 'luis', 'luis12', 1, '1995-08-01');

-- --------------------------------------------------------

--
-- Estrutura da tabela `TIPO_AVAL`
--

CREATE TABLE IF NOT EXISTS `TIPO_AVAL` (
  `ID_AVAL` int(11) NOT NULL AUTO_INCREMENT,
  `DESC_AVAL` varchar(250) NOT NULL,
  PRIMARY KEY (`ID_AVAL`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Extraindo dados da tabela `TIPO_AVAL`
--

INSERT INTO `TIPO_AVAL` (`ID_AVAL`, `DESC_AVAL`) VALUES
(1, 'AVAL_AVDB'),
(2, 'AVAL_AIVD'),
(3, 'AVAL_MARC'),
(4, 'AVAL_AFEC'),
(5, 'AVAL_COGN'),
(6, 'AVAL_NUTR');

-- --------------------------------------------------------

--
-- Estrutura da tabela `TIPO_ESPE`
--

CREATE TABLE IF NOT EXISTS `TIPO_ESPE` (
  `ID_ESPE` int(11) NOT NULL AUTO_INCREMENT,
  `DESC_ESPE` varchar(250) NOT NULL,
  PRIMARY KEY (`ID_ESPE`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Extraindo dados da tabela `TIPO_ESPE`
--

INSERT INTO `TIPO_ESPE` (`ID_ESPE`, `DESC_ESPE`) VALUES
(1, 'espe1');

-- --------------------------------------------------------

--
-- Estrutura da tabela `UTEN`
--

CREATE TABLE IF NOT EXISTS `UTEN` (
  `NUME_UTEN` int(11) NOT NULL,
  `NOME_UTEN` varchar(250) NOT NULL,
  `DATA_NASC` date NOT NULL,
  `CC_MEDI` int(11) NOT NULL,
  `AVAL_AVDB` int(11) DEFAULT NULL,
  `AVAL_AIVD` int(11) DEFAULT NULL,
  `AVAL_MARC` int(11) DEFAULT NULL,
  `AVAL_AFEC` int(11) DEFAULT NULL,
  `AVAL_COGN` int(11) DEFAULT NULL,
  `AVAL_NUTR` int(11) DEFAULT NULL,
  PRIMARY KEY (`NUME_UTEN`),
  KEY `UTEN_ibfk_1` (`CC_MEDI`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `UTEN`
--

INSERT INTO `UTEN` (`NUME_UTEN`, `NOME_UTEN`, `DATA_NASC`, `CC_MEDI`, `AVAL_AVDB`, `AVAL_AIVD`, `AVAL_MARC`, `AVAL_AFEC`, `AVAL_COGN`, `AVAL_NUTR`) VALUES
(12345, 'nome1', '1991-11-02', 12345, 1, 2, 3, 4, 5, 6),
(12346, 'nome', '1990-12-01', 12345, 0, 0, 0, 0, 0, 0),
(123456, 'nome1', '1991-11-02', 12345, 0, 0, 0, 0, 0, 0);

--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `AVAL`
--
ALTER TABLE `AVAL`
  ADD CONSTRAINT `AVAL_ibfk_1` FOREIGN KEY (`TIPO_AVAL`) REFERENCES `TIPO_AVAL` (`ID_AVAL`),
  ADD CONSTRAINT `AVAL_ibfk_2` FOREIGN KEY (`NUME_UTEN`) REFERENCES `UTEN` (`NUME_UTEN`),
  ADD CONSTRAINT `AVAL_ibfk_3` FOREIGN KEY (`CC_MEDI`) REFERENCES `MEDI` (`CC`);

--
-- Limitadores para a tabela `MEDI`
--
ALTER TABLE `MEDI`
  ADD CONSTRAINT `MEDI_ibfk_1` FOREIGN KEY (`TIPO_ESPE`) REFERENCES `TIPO_ESPE` (`ID_ESPE`);

--
-- Limitadores para a tabela `UTEN`
--
ALTER TABLE `UTEN`
  ADD CONSTRAINT `UTEN_ibfk_1` FOREIGN KEY (`CC_MEDI`) REFERENCES `MEDI` (`CC`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
