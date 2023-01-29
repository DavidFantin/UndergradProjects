###CREATE VIEWS###

CREATE VIEW view1_updatable AS
    SELECT  *
    FROM  users
	WHERE Gender = "F" OR Gender = "T";

SELECT * FROM view1_updatable;

CREATE VIEW view2_nonupdatable AS
    SELECT  DISTINCT *
    FROM  users
	WHERE Gender = "M" OR Gender = "T";

SELECT * FROM view2_nonupdatable;


###SAMPLE QUERIES [VIEW 1]###

SELECT Name
FROM view1_updatable;

SELECT Name, Gender
FROM view1_updatable;

###SAMPLE QUERIES [VIEW 2]###

SELECT Name
FROM view2_nonupdatable;

SELECT Name, Gender
FROM view2_nonupdatable;

###UPDATING THE TABLE TEST###

# Works because view1 is updatable
INSERT INTO view1_updatable (Id, Name, Gender) VALUES ('TESTA', 'Testata Proba', 'T');

UPDATE view1_updatable 
SET Name = 'Lzzy Hale'
WHERE Id = 'hstrm';

# Fails because view2 in not updatable
INSERT INTO view2_nonUpdatable (Id, Name, Gender) VALUES ('TESTO', 'Testata Proba', 'T');

UPDATE view1_updatable 
SET Name = 'Ishmael'
WHERE Id = 'MobyD';