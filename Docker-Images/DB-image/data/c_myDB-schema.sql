SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS myDB;
CREATE SCHEMA myDB;
USE myDB;

--
-- Table structure for table `user`
--

CREATE TABLE user (
  user_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL UNIQUE,
  password VARCHAR(512) NOT NULL,
  PRIMARY KEY  (user_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `movie`
--

CREATE TABLE movie (
  movie_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  title VARCHAR(90) NOT NULL UNIQUE,
  release_year VARCHAR(5) DEFAULT NULL,
  category VARCHAR(45) NOT NULL,
  PRIMARY KEY  (movie_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `cinema`
--

CREATE TABLE cinema (
  cinema_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL UNIQUE,
  PRIMARY KEY  (cinema_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `screening`
--

CREATE TABLE screening (
  screening_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  time VARCHAR(45) NOT NULL,
  room VARCHAR(45) NOT NULL,
  property VARCHAR(45) NOT NULL,
  user_id INT UNSIGNED NOT NULL,
  movie_id INT UNSIGNED NOT NULL,
  cinema_id INT UNSIGNED NOT NULL,
  PRIMARY KEY  (screening_id),
  KEY idx_fk_user_id (user_id),
  KEY idx_fk_movie_id (movie_id),
  KEY idx_fk_cinema_id (cinema_id),
  CONSTRAINT fk_screening_user FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_screening_movie FOREIGN KEY (movie_id) REFERENCES movie (movie_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_screening_cinema FOREIGN KEY (cinema_id) REFERENCES cinema (cinema_id) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


