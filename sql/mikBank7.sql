-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Czas generowania: 16 Paź 2019, 20:09
-- Wersja serwera: 8.0.18
-- Wersja PHP: 7.2.19-0ubuntu0.18.04.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `mikbank7`
--
CREATE DATABASE IF NOT EXISTS `mikbank7` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `mikbank7`;

-- -----------------------------------------------------
-- Table `mikBank7`.`clientData`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `clientData` (
  `clientID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `accountNO` INT(11) ZEROFILL NOT NULL,
  `firstName` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `eMail` VARCHAR(45) NULL,
  `creationDate` TIMESTAMP NOT NULL,
  PRIMARY KEY (`clientID`),
  UNIQUE INDEX `clientID_UNIQUE` (`clientID` ASC),
  UNIQUE INDEX `accountNO_UNIQUE` (`accountNO` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mikBank7`.`bankTransaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bankTransaction` (
  `transactionID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `accountFrom` INT(11) NOT NULL,
  `accountTo` INT(11) NOT NULL,
  `amount` DOUBLE NOT NULL,
  `transactionDate` TIMESTAMP NOT NULL,
  PRIMARY KEY (`transactionID`),
  UNIQUE INDEX `transactionID_UNIQUE` (`transactionID` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mikBank7`.`accountData`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `accountData` (
  `accountNo` INT(11) ZEROFILL UNSIGNED NOT NULL,
  `accountBalance` DOUBLE NULL DEFAULT 0.000,
  `accountLimit` INT NULL DEFAULT 0,
  `bankTransaction_transactionID` INT UNSIGNED NULL,
  PRIMARY KEY (`accountNo`),
  UNIQUE INDEX `accountNo_UNIQUE` (`accountNo` ASC),
  INDEX `fk_accountData_bankTransaction1_idx` (`bankTransaction_transactionID` ASC),
  CONSTRAINT `fk_accountData_clientData`
    FOREIGN KEY (`accountNo`)
    REFERENCES `clientData` (`accountNO`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_accountData_bankTransaction1`
    FOREIGN KEY (`bankTransaction_transactionID`)
    REFERENCES `bankTransaction` (`transactionID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
