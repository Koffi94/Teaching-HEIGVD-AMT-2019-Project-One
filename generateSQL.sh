#!/bin/bash

# User parameters
USERNAME="username"
PASSWD="passwd"

# Cinema parameters
NAME="cinema"
CITY="city"
PRICE="1$"

# Movie parameters
TITLE="title"
YEAR="1000"
CATEGORY="test"

# Screening parameters
ROOM1="R"
ROOM2="Z"
ROOM3="A"
PROPERTY1="5D"
PROPERTY2="IMAX"
PROPERTY3="ATMOS"

# Iterations
typeset -i USERS_NUMBER=1000
typeset -i MOVIES_NUMBER=55
typeset -i CINEMAS_NUMBER=25

typeset -i MOVIES_PER_USER=50
typeset -i CINEMAS_PER_USER=20

FILE=Docker-Images/DB-image/data/testDB.sql

touch $FILE
# Clear file of its content
echo -n > $FILE

echo "SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;" >> $FILE
echo "SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;" >> $FILE
echo "SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';" >> $FILE

echo "USE myDB" >> $FILE

# Generating users
echo "SET AUTOCOMMIT=0;" >> $FILE
echo "INSERT INTO user VALUES" >> $FILE

for ((u = 0; u < $USERS_NUMBER; u++));
do echo "(DEFAULT, $USERNAME$u, $PASSWD$u)," >> $FILE
done
echo "(DEFAULT, $USERNAME$USERS_NUMBER, $PASSWD$USERS_NUMBER);" >> $FILE
echo "COMMIT;" >> $FILE

# Generating movies
echo "SET AUTOCOMMIT=0;" >> $FILE
echo "INSERT INTO movie VALUES" >> $FILE

for ((m = 0; m < $MOVIES_NUMBER; m++));
do echo "(DEFAULT, $TITLE$m, $CATEGORY)," >> $FILE
done
echo "(DEFAULT, $TITLE$MOVIES_NUMBER, $CATEGORY);" >> $FILE  #TODO generate several category
echo "COMMIT;" >> $FILE

# Generating cinemas
echo "SET AUTOCOMMIT=0;" >> $FILE
echo "INSERT INTO cinema VALUES" >> $FILE

for ((c = 0; c < $CINEMAS_NUMBER; c++));
do echo "(DEFAULT, $NAME$c, PRICE)," >> $FILE
done
echo "(DEFAULT, $NAME$CINEMAS_NUMBER, PRICE);" >> $FILE
echo "COMMIT;" >> $FILE

# Generating screenings
echo "SET AUTOCOMMIT=0;" >> $FILE
echo "INSERT INTO screening VALUES" >> $FILE

# Fisrt half of users
for ((usr = 1; usr < $USERS_NUMBER; usr += 2));
do 
	for ((mov = 1; mov < $MOVIES_PER_USER; mov++));
	do 
		for ((ci = 1; ci < $CINEMAS_PER_USER; ci++));
		do echo "($mov:$ci, $ROOM1$mov, $PROPERTY1, $usr, $ci, $mov)," >> $FILE
		done
	done
done

# Second half of users
for ((usr = 2; usr < $USERS_NUMBER; usr += 2));
do 
	for ((mov = 1; mov < $MOVIES_PER_USER; mov++));
	do 
		for ((cin = 1; cin < $CINEMAS_PER_USER; cin++));
		do echo "($mov:$cin, $ROOM2$mov, $PROPERTY2, $usr, $cin, $mov)," >> $FILE
		done
	done
done

echo "(25:61, ${ROOM3}1, $PROPERTY3, $USERS_NUMBERS, $MOVIES_NUMBER, $CINEMAS_NUMBER);" >> $FILE
echo "COMMIT;" >> $FILE

echo "SET SQL_MODE=@OLD_SQL_MODE;" >> $FILE
echo "SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;" >> $FILE
echo "SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;" >> $FILE

