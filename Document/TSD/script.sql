CREATE TABLE `hibah`.`roles` (
  `id_role` INT NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(1000) NULL,
  `update_date` DATETIME NOT NULL,
  `update_by` VARCHAR(45) NOT NULL,
  `create_date` DATE NOT NULL,
  `create_by` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_role`),
  UNIQUE INDEX `role_name_UNIQUE` (`role_name` ASC));
  
  
  CREATE TABLE `hibah`.`users` (
  `id_user` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(100) NOT NULL,
  `password` VARCHAR(400) NOT NULL,
  `last_login` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  `update_by` VARCHAR(100) NOT NULL,
  `create_date` DATE NOT NULL,
  `create_by` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC));
  
  
  CREATE TABLE `hibah`.`role_user` (
  `id_role_user` INT NOT NULL AUTO_INCREMENT,
  `id_user` INT NULL,
  `id_role` INT NULL,
  `update_date` DATETIME NOT NULL,
  `update_by` VARCHAR(45) NOT NULL,
  `create_date` DATE NOT NULL,
  `create_by` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_role_user`),
  INDEX `fk_user_idx` (`id_user` ASC),
  INDEX `fk_role_idx` (`id_role` ASC),
  CONSTRAINT `fk_user`
    FOREIGN KEY (`id_user`)
    REFERENCES `hibah`.`users` (`id_user`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_role`
    FOREIGN KEY (`id_role`)
    REFERENCES `hibah`.`roles` (`id_role`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT);