DROP DATABASE IF EXISTS superheroes; 
CREATE DATABASE superheroes;

USE superheroes;

CREATE TABLE superhero(
	superheroId INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	description VARCHAR(100),
    imagePath VARCHAR(100),
	superpower VARCHAR(20) NOT NULL
);

CREATE TABLE location(
	locationId INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	description VARCHAR(100),
	address VARCHAR(100),
	latitude DECIMAL(10,8) NOT NULL,
	longitude DECIMAL(11,8) NOT NULL
);

CREATE TABLE organization(
	organizationId INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	description VARCHAR(100),
	address VARCHAR(100),
	websiteUrl VARCHAR(100)
);

CREATE TABLE sighting(
	sightingId INT PRIMARY KEY AUTO_INCREMENT,
    date DATETIME NOT NULL,
	superheroId INT NOT NULL,
	locationId INT NOT NULL,
	FOREIGN KEY (superheroId) REFERENCES superhero(superheroId),
	FOREIGN KEY (locationId) REFERENCES location(locationId)
);

CREATE TABLE superheroOrganization(
	superheroId INT NOT NULL,
	organizationId INT NOT NULL,
    PRIMARY KEY(superheroId, organizationId),
	FOREIGN KEY (superheroId) REFERENCES superhero(superheroId),
	FOREIGN KEY (organizationId) REFERENCES organization(organizationId)
);

INSERT INTO location VALUES 
	(1,'Times Square','The most popular square in NYC.','Manhattan, NY 10036, United States',40.75889600,-73.98513000),
    (2, 'London Eye','One of the most famous attractions in London.','Riverside Building, County Hall, London SE1 7PB, United Kingdom',51.50339900,-0.11951900),
    (3, 'Empire State Building','One of the world\'s most famous skyscrapers.','20 W 34th St, New York, NY 10001, United States',40.74881700,-73.98542800);

INSERT INTO organization VALUES 
(1,'Marvel','The most famous superhero organisation','8583 W. Marconi Drive Brooklyn, NY 11229','https://marvel.com'),
(2,'DC','The second most famous superhero organisation','47 W 13th St, New York, NY 10011, USA','https://dc.com'),
(3,'Villains Inc','The organisation of Villains.','88 Main Street London EC31 4WE','https://villains.com');

INSERT INTO superhero VALUES 
	(1,'Batman','The Dark Knight','batmanLogo.jpeg','Fly'),
    (2,'Superman','Man of Steel','supermanLogo.jpeg','Laser eyes'),
    (3,'Spiderman','The youngest avanger ',NULL,'spider webs'),
    (4,'Joker','The coolest villain.',NULL,'wicked');

INSERT INTO sighting VALUES 
	(1,'2022-02-20 12:00:00',2,1),
    (2,'2022-02-21 15:15:15',3,1),
    (3,'2022-02-20 22:00:00',4,2),
    (4,'2022-01-21 15:15:12',1,3),
    (5,'2022-01-30 21:00:00',4,3),
    (6,'2022-02-15 22:00:00',2,1),
    (7,'2021-12-21 15:15:15',4,3),
    (8,'2022-01-01 17:15:12',3,3),
    (9,'2022-02-21 19:15:12',2,2),
    (10,'2022-01-01 15:15:12',1,1);

DROP DATABASE IF EXISTS superheroesTest; -- superheroesTest should have all tabels empty
CREATE DATABASE superheroesTest;

USE superheroesTest;

CREATE TABLE superhero(
	superheroId INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	description VARCHAR(100),
    imagePath VARCHAR(100),
	superpower VARCHAR(20) NOT NULL
);

CREATE TABLE location(
	locationId INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	description VARCHAR(100),
	address VARCHAR(100),
	latitude DECIMAL(10,8) NOT NULL,
	longitude DECIMAL(11,8) NOT NULL
);

CREATE TABLE organization(
	organizationId INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	description VARCHAR(100),
	address VARCHAR(100),
	websiteUrl VARCHAR(100)
);

CREATE TABLE sighting(
	sightingId INT PRIMARY KEY AUTO_INCREMENT,
    date DATETIME NOT NULL,
	superheroId INT NOT NULL,
	locationId INT NOT NULL,
	FOREIGN KEY (superheroId) REFERENCES superhero(superheroId),
	FOREIGN KEY (locationId) REFERENCES location(locationId)
);

CREATE TABLE superheroOrganization(
	superheroId INT NOT NULL,
	organizationId INT NOT NULL,
    PRIMARY KEY(superheroId, organizationId),
	FOREIGN KEY (superheroId) REFERENCES superhero(superheroId),
	FOREIGN KEY (organizationId) REFERENCES organization(organizationId)
);





