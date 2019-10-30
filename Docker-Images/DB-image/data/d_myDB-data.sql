SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

USE myDB;

--
-- Dumping data for table user
--

SET AUTOCOMMIT=0;
INSERT INTO user VALUES
(1,'Koffi','adminpw','1'),
(2,'Nath','adminpw','1');
COMMIT;

SET AUTOCOMMIT=0;
INSERT INTO movie VALUES
(1,'The Ring','2003','Horror'),
(2,'Elite','2018','Teenager');
COMMIT;

SET AUTOCOMMIT=0;
INSERT INTO screening VALUES
(1,'1970-01-01 14:00:01','R08','3D', 1, 1),
(2,'1970-01-01 17:30:02','R07','2D', 2, 2);
COMMIT;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;