
DROP DATABASE IF exists moviedb;
create database moviedb;
use moviedb;
CREATE TABLE IF NOT EXISTS movies(
	id INT PRIMARY KEY AUTO_INCREMENT NOT NULL, 
    title VARCHAR(100) NOT NULL,
	year INT NOT NULL,
	director VARCHAR(100) NOT NULL,
	banner_url VARCHAR(200),
	trailer VARCHAR(200)
	);

CREATE TABLE IF NOT EXISTS stars(
	id INT PRIMARY KEY AUTO_INCREMENT NOT NULL, 
    first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	dob DATE,
	photo_url VARCHAR(200)
	);

CREATE TABLE IF NOT EXISTS stars_in_movies(
	star_id INT NOT NULL,
	movies_id INT NOT NULL,
	FOREIGN KEY (star_id) REFERENCES stars(id)
        ON DELETE CASCADE,	
	FOREIGN KEY (movies_id) REFERENCES movies(id)
        ON DELETE CASCADE			
	);
	
CREATE TABLE IF NOT EXISTS genres(
	id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	name VARCHAR(32) NOT NULL
	);

CREATE TABLE IF NOT EXISTS genres_in_movies(
	genres_id INT NOT NULL,
	movies_id INT NOT NULL,
	FOREIGN KEY (genres_id) REFERENCES genres(id)
		ON DELETE CASCADE,
	FOREIGN KEY (movies_id) REFERENCES movies(id)
		ON DELETE CASCADE
	);
    
CREATE TABLE IF NOT EXISTS creditcards(
	id VARCHAR(20) PRIMARY KEY NOT NULL,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	expiration DATE NOT NULL
	);
    
CREATE TABLE IF NOT EXISTS customers(
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	cc_id VARCHAR(20) NOT NULL,
	address VARCHAR(200) NOT NULL,
	email VARCHAR(50) NOT NULL,
	password VARCHAR(20) NOT NULL,
	FOREIGN KEY (cc_id) REFERENCES creditcards(id) on DELETE CASCADE
	);

CREATE TABLE IF NOT EXISTS sales(
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	customer_id INT NOT NULL,
	movies_id INT NOT NULL,
	sale_date DATE NOT NULL,
	FOREIGN KEY (customer_id) REFERENCES customers(id) on DELETE CASCADE,
	FOREIGN KEY (movies_id) REFERENCES movies(id)
	);

	
	
	
	
	
	
	
	
	
