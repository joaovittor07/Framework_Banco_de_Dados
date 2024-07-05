CREATE DATABASE IF NOT EXISTS `teste_banco`;
USE `teste_banco`;
CREATE TABLE `categoria` (
	`id` INT AUTO_INCREMENT,
	`nome` VARCHAR(100) NOT NULL UNIQUE,
	PRIMARY KEY (`id`)
);
CREATE TABLE `item` (
	`id` INT AUTO_INCREMENT,
	`nome` VARCHAR(100) NOT NULL UNIQUE,
	`preco` DECIMAL(10,2) NOT NULL,
	`descricao` TEXT,
	`categoria_id` INT NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`categoria_id`) REFERENCES `categoria`(`id`)
);
CREATE TABLE `usuario` (
	`id` INT AUTO_INCREMENT,
	`nome` VARCHAR(100) NOT NULL,
	`email` VARCHAR(100),
	PRIMARY KEY (`id`)
);
CREATE TABLE `endereco` (
	`id` INT AUTO_INCREMENT,
	`cep` VARCHAR(100) NOT NULL,
	`rua` VARCHAR(100) NOT NULL,
	`numero` INT NOT NULL,
	`usuario_id` INT NOT NULL UNIQUE,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`usuario_id`) REFERENCES `usuario`(`id`)
);
CREATE TABLE `carrinho` (
	`id` INT AUTO_INCREMENT,
	`usuario_id` INT NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`usuario_id`) REFERENCES `usuario`(`id`)
);
CREATE TABLE `carrinho_has_item` (
	`carrinho_id` INT,
	`item_id` INT,
	`create_timestamp` TIMESTAMP,
	PRIMARY KEY (`carrinho_id`,`item_id`),
	FOREIGN KEY (`carrinho_id`) REFERENCES `carrinho`(`id`),
	FOREIGN KEY (`item_id`) REFERENCES `item`(`id`)
);
