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
	

CREATE TABLE `hibah`.`direktorat` (
  `id_direktorat` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(1000) NOT NULL,
  `desc` VARCHAR(1000) NULL,
  `update_date` DATETIME NOT NULL,
  `update_by` VARCHAR(100) NOT NULL,
  `create_date` DATE NOT NULL,
  `create_by` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC));
  
  
  CREATE TABLE `hibah`.`satker` (
  `id_satker` INT NOT NULL AUTO_INCREMENT,
  `id_direktorat` INT NOT NULL,
  `name` VARCHAR(1000) NOT NULL,
  `desc` VARCHAR(1000) NULL,
  `update_date` DATETIME NOT NULL,
  `update_by` VARCHAR(45) NOT NULL,
  `create_date` DATE NOT NULL,
  `create_by` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_satker`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  INDEX `FK_SATKER_DIREKTORAT_idx` (`id_direktorat` ASC),
  CONSTRAINT `FK_SATKER_DIREKTORAT`
    FOREIGN KEY (`id_direktorat`)
    REFERENCES `hibah`.`direktorat` (`id_direktorat`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
	
	
	
	CREATE TABLE `hibah`.`barang` (
  `id_barang` INT NOT NULL AUTO_INCREMENT,
  `kode_barang` VARCHAR(100) NOT NULL,
  `nama_barang` VARCHAR(500) NOT NULL,
  `update_date` DATETIME NOT NULL,
  `update_by` VARCHAR(45) NOT NULL,
  `create_date` DATE NOT NULL,
  `create_by` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_barang`));
  
  
  
  CREATE TABLE `hibah`.`transaksi` (
  `id_transaksi` INT NOT NULL AUTO_INCREMENT,
  `id_satker` INT NOT NULL,
  `id_barang` INT NOT NULL,
  `nup` INT NULL,
  `penerima` VARCHAR(2000) NOT NULL,
  `lokasi` VARCHAR(2000) NULL,
  `tahun_anggaran` VARCHAR(45) NULL,
  `nilai_perolehan` BIGINT NULL,
  `persetujuan_pmk` VARCHAR(500) NULL,
  `persetujuan_kemenkeu` VARCHAR(500) NULL,
  `sk_penetapan_hibah` VARCHAR(500) NULL,
  `bast` VARCHAR(500) NULL,
  `sk_penghapusan` VARCHAR(500) NULL,
  `update_date` DATETIME NOT NULL,
  `update_by` VARCHAR(45) NOT NULL,
  `create_date` DATE NOT NULL,
  `create_by` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_transaksi`),
  INDEX `FK_TRANSAKSI_BARANG_idx` (`id_barang` ASC),
  INDEX `FK_TRANSAKSI_SATKER_idx` (`id_satker` ASC),
  CONSTRAINT `FK_TRANSAKSI_SATKER`
    FOREIGN KEY (`id_satker`)
    REFERENCES `hibah`.`satker` (`id_satker`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_TRANSAKSI_BARANG`
    FOREIGN KEY (`id_barang`)
    REFERENCES `hibah`.`barang` (`id_barang`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

