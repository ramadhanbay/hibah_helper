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
  `id_role` INT NULL,
  `password` VARCHAR(1000) NOT NULL,
  `update_date` DATETIME NOT NULL,
  `update_by` VARCHAR(45) NOT NULL,
  `create_date` DATE NOT NULL,
  `create_by` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_user`),
  INDEX `fk_user_role_idx` (`id_role` ASC),
  CONSTRAINT `fk_user_role`
    FOREIGN KEY (`id_role`)
    REFERENCES `hibah`.`roles` (`id_role`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);