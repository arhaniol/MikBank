-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mikBank2
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mikBank2
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mikBank2` DEFAULT CHARACTER SET utf8 ;
USE `mikBank2` ;

-- -----------------------------------------------------
-- Table `mikBank2`.`clientData`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mikBank2`.`clientData` (
  `clientID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `accountNO` INT(11) ZEROFILL NOT NULL,
  `firstName` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `eMail` VARCHAR(45) NULL,
  `creationDate` TIMESTAMP NOT NULL,
  PRIMARY KEY (`clientID`),
  UNIQUE INDEX `clientID_UNIQUE` (`clientID` ASC) VISIBLE,
  UNIQUE INDEX `accountNO_UNIQUE` (`accountNO` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mikBank2`.`bankTransaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mikBank2`.`bankTransaction` (
  `transactionID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `accountFrom` INT(11) NOT NULL,
  `accountTo` INT(11) NOT NULL,
  `amount` DOUBLE NOT NULL,
  `transactionDate` TIMESTAMP NOT NULL,
  PRIMARY KEY (`transactionID`),
  UNIQUE INDEX `transactionID_UNIQUE` (`transactionID` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mikBank2`.`accountData`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mikBank2`.`accountData` (
  `accountNo` INT(11) ZEROFILL UNSIGNED NOT NULL,
  `accountBalance` DOUBLE NULL DEFAULT 0.000,
  `accountLimit` INT NULL DEFAULT 0,
  `bankTransaction_transactionID` INT UNSIGNED NULL,
  PRIMARY KEY (`accountNo`),
  UNIQUE INDEX `accountNo_UNIQUE` (`accountNo` ASC) VISIBLE,
  INDEX `fk_accountData_bankTransaction1_idx` (`bankTransaction_transactionID` ASC) VISIBLE,
  CONSTRAINT `fk_accountData_clientData`
    FOREIGN KEY (`accountNo`)
    REFERENCES `mikBank2`.`clientData` (`accountNO`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_accountData_bankTransaction1`
    FOREIGN KEY (`bankTransaction_transactionID`)
    REFERENCES `mikBank2`.`bankTransaction` (`transactionID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
