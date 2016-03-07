--------------------------------------------------------------------
-------------------------------------------------------------------
--
--		File Name: Extractions_Schema.Sql
--    									written by Emacro on July 8th 2007
--
-------------------------------------------------------------------
--------------------------------------------------------------------

DROP TABLE IF EXISTS RUOTE;
DROP TABLE IF EXISTS EXTRACTIONS;
DROP TABLE IF EXISTS EXTRACTS;
DROP TABLE IF EXISTS NUMBERS;

DROP SEQUENCE IF EXISTS EXTRACTIONS_SEQ;
DROP SEQUENCE IF EXISTS EXTRACTS_SEQ;
-------------------------------------------------------------------------------
-- Sequences
-------------------------------------------------------------------------------
-- CREATE SEQUENCE EXTRACTIONS_SEQ START WITH 1 INCREMENT BY 1;
-- CREATE SEQUENCE EXTRACTS_SEQ START WITH 1 INCREMENT BY 1;
--------------------------------------------------------------------

--------------------------------------
--
--	table Ruote
--
--------------------------------------
CREATE TABLE RUOTE (
	ID			INTEGER NOT NULL,
	NAME		VARCHAR (20) NOT NULL,
	
	PRIMARY KEY (ID)
);

--------------------------------------
--
--	table Extractions
--
--------------------------------------
CREATE TABLE EXTRACTIONS  (
	ID			INTEGER NOT NULL AUTO_INCREMENT (1,1),
	DATE 		DATE NOT NULL,
	NUMBER	INTEGER,					-- number of extraction
	
	PRIMARY KEY (ID)
);

--------------------------------------
--
--	table Extracts
--
--------------------------------------
CREATE TABLE EXTRACTS  (
	ID					INTEGER NOT NULL AUTO_INCREMENT (1,1),
	RUOTA				INTEGER NOT NULL, -- foreign key RUOTE.ID
	EXTRACTION	INTEGER NOT NULL, -- foreign key EXTRACTIONS.ID
	
	PRIMARY KEY (ID),
	FOREIGN KEY (RUOTA) REFERENCES RUOTE (ID),
	FOREIGN KEY (EXTRACTION) REFERENCES EXTRACTIONS (ID)
);

--------------------------------------
--
--	table Numbers
--
--------------------------------------
CREATE TABLE NUMBERS (
	EXTRACT	  INTEGER NOT NULL,
	POSITION    INTEGER NOT NULL,
	NUMBER      INTEGER NOT NULL DEFAULT 0,
	
	FOREIGN KEY (EXTRACT) REFERENCES EXTRACTS (ID)
);

-- Inserts Permanenti (NON di prova)

INSERT INTO RUOTE VALUES (1,'BARI');
INSERT INTO RUOTE VALUES (2,'CAGLIARI');
INSERT INTO RUOTE VALUES (3,'FIRENZE');
INSERT INTO RUOTE VALUES (4,'GENOVA');
INSERT INTO RUOTE VALUES (5,'MILANO');
INSERT INTO RUOTE VALUES (6,'NAPOLI');
INSERT INTO RUOTE VALUES (7,'PALERMO');
INSERT INTO RUOTE VALUES (8,'ROMA');
INSERT INTO RUOTE VALUES (9,'TORINO');
INSERT INTO RUOTE VALUES (10,'VENEZIA');
INSERT INTO RUOTE VALUES (11,'NAZIONALE');

--------------------------------------------------------------------------------------
