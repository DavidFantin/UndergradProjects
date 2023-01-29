# Problem 1
SELECT *
FROM comments
WHERE Text LIKE '%lol%'
ORDER BY Poster DESC, Recipient ASC;

# Problem 2
SELECT DISTINCT Poster, P.Gender, Recipient, R.Gender
FROM comments AS C 
	JOIN users AS R ON(R.Id = C.Recipient) 
		JOIN users AS P ON(P.Id = C.Poster)
WHERE R.Gender != P.Gender;

# Problem 3
SELECT CommentId, Poster, Recipient, Text, PostDate
FROM comments 
	JOIN users AS postUsers ON (comments.Poster = postUsers.Id) 
		JOIN users AS recipUsers ON (comments.Recipient = recipUsers.Id)
WHERE PostDate >= 2020-01-01 AND (postUsers.Name = 'X' OR recipUsers.Name = 'Y');

# Problem 4
SELECT U.Id, F.Gender, COUNT(F.Gender)
FROM users AS U 
	JOIN friends FT ON(U.Id = FT.Id1) 
		JOIN users AS F ON(F.Id = FT.Id2)
GROUP BY U.Id, F.Gender;

# Problem 5
SELECT *
FROM users
WHERE Id NOT IN (SELECT DISTINCT U1.Id
				 FROM friends 
					JOIN users AS U1 ON (friends.Id1 = U1.Id) 
						JOIN users AS U2 ON (friends.Id2 = U2.Id)
				 WHERE U1.Gender = U2.Gender);
                 
# Problem 6
SELECT U.Id, U.Name, U.Gender
FROM friends AS F 
	JOIN (SELECT Startdate
		  FROM friends
		  GROUP BY Startdate
		  HAVING COUNT(*)>2) AS DT ON (F.Startdate = DT.Startdate)
		JOIN users AS U ON (F.Id1 = U.Id)
GROUP BY F.Id1
HAVING (COUNT(*) >= 2);

# Problem 7
SELECT Id, Name, Gender, FNum.FNumber
FROM users 
	JOIN (SELECT Poster, Recipient
		  FROM comments 
		  GROUP BY Poster, Recipient) AS SubQ ON (Id = SubQ.Poster)
		JOIN (SELECT Id1, COUNT(*) AS FNumber
			  FROM friends
			  GROUP BY Id1) AS FNum ON (Id = FNum.Id1)
GROUP BY Poster
HAVING COUNT(*) >= 2;

# Problem 8
SELECT Id, Name, Gender
FROM users as U1
WHERE NOT EXISTS((SELECT com.Recipient 
				  FROM comments AS com 
					 JOIN users AS U2 ON (com. Recipient = U2.Id)
				  WHERE U2.Gender = 'V' 
					AND NOT EXISTS(SELECT * 
								   FROM comments AS C 
								   WHERE U1.Id = C.Poster 
									AND com.Recipient = C.Recipient)));

# Problem 9
SELECT U1.Id, U1.Name, U1.Gender
FROM friends
	JOIN users as U1 ON (U1.Id = Id1)
		JOIN (SELECT Id, COUNT(Id) AS FriendCount
			  FROM friends
				 JOIN users ON (Id = Id1)
			  GROUP BY Id) AS FCT ON (U1.Id = FCT.Id)
				 JOIN (SELECT MAX(FCount) AS mfc
					   FROM (SELECT Id1, COUNT(Id1) FCount
						     FROM friends
								JOIN users ON (Id = Id1)
							 GROUP BY Id1)AS MFC) AS FINAL ON (FriendCount = mfc)
GROUP BY U1.Id, FriendCount DESC;