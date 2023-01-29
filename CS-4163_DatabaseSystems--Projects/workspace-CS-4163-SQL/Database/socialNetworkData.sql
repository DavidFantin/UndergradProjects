CREATE TABLE Users (
Id CHAR(5) NOT NULL,
Name VARCHAR(255) NOT NULL,
Gender CHAR,
PRIMARY KEY(Id),
CHECK(Gender IN ('M','F','V'))
);

INSERT INTO Users (Id, Name, Gender) VALUES ('et101', 'Sophie Germain', 'F');
INSERT INTO Users (Id, Name, Gender) VALUES ('iRobt', 'Isaac Asimov', 'M');
INSERT INTO Users (Id, Name, Gender) VALUES ('P0TU5', 'Ronald Reagan', 'M');
INSERT INTO Users (Id, Name, Gender) VALUES ('DPP16', 'Tsai Ing-wen', 'F');
INSERT INTO Users (Id, Name, Gender) VALUES ('hardi', 'Srinivasa Ramanujan', 'M');
INSERT INTO Users (Id, Name, Gender) VALUES ('oilEr', 'Leonhard Euler', 'M');
INSERT INTO Users (Id, Name, Gender) VALUES ('physd', 'Carl Gauss', 'M');
INSERT INTO Users (Id, Name, Gender) VALUES ('hstrm', 'Elizabeth Hale', 'F');
INSERT INTO Users (Id, Name, Gender) VALUES ('rush3', 'Neil Peart', 'M');
INSERT INTO Users (Id, Name, Gender) VALUES ('ATLAS', 'Ayn Rand', 'F');
INSERT INTO Users (Id, Name, Gender) VALUES ('compE', 'Ada Lovelace', 'F');
INSERT INTO Users (Id, Name, Gender) VALUES ('MobyD', 'Julian Cayo-Evans', 'M');
INSERT INTO Users (Id, Name, Gender) VALUES ('CPMGB', 'Margaret Thatcher', 'F');
INSERT INTO users (Id, Name, Gender) VALUES ('X111X', 'X', 'V'); # V = Variable
INSERT INTO users (Id, Name, Gender) VALUES ('Y111Y', 'Y', 'V');
INSERT INTO users (Id, Name, Gender) VALUES ('Y111Y', 'Y', 'V');
INSERT INTO users (Id, Name, Gender) VALUES ('Y111Y', 'Y', 'V');
INSERT INTO users (Id, Name, Gender) VALUES ('Y111Y', 'Y', 'V');
INSERT INTO users (Id, Name, Gender) VALUES ('Y111Y', 'Y', 'V');

CREATE TABLE Friends (
Id1 CHAR(5) NOT NULL,
Id2 CHAR(5) NOT NULL,
Startdate DATE,
PRIMARY KEY(Id1,Id2),
FOREIGN KEY(Id1) REFERENCES Users(Id),
FOREIGN KEY(Id2) REFERENCES Users(Id)
);

INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('CPMGB', 'P0TU5', '1981-01-20');
INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('P0TU5', 'CPMGB', '1981-01-20');
INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('hstrm', 'rush3', '2014-07-18');
INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('rush3', 'hstrm', '2014-07-18');
INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('rush3', 'CPMGB', '1979-05-04');
INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('CPMGB', 'rush3', '1979-05-04');
INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('rush3', 'ATLAS', '1975-02-15');
INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('ATLAS', 'rush3', '1975-02-15');
INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('ATLAS', 'CPMGB', '1957-10-10');
INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('CPMGB', 'ATLAS', '1957-10-10');
INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('DPP16', 'CPMGB', '2004-04-19');
INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('CPMGB', 'DPP16', '2004-04-19');
INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('DPP16', 'P0TU5', '2004-04-19');
INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('P0TU5', 'DPP16', '2004-04-19');
INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('physd', 'et101', '1804-11-21');
INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('et101', 'physd', '1804-11-21');
INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('compE', 'et101', '1830-07-15');
INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('et101', 'compE', '1830-07-15');
INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('physd', 'oilEr', '1772-11-07');
INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('oilEr', 'physd', '1772-11-07');
INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('iRobt', 'physd', '1951-01-01');
INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('physd', 'iRobt', '1951-01-01');
INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('hardi', 'oilEr', '1914-04-14');
INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('oilEr', 'hardi', '1914-04-14');
INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('hardi', 'physd', '1914-04-14');
INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('physd', 'hardi', '1914-04-14');
INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('iRobt', 'compE', '1950-12-02');
INSERT INTO Friends (Id1, Id2, StartDate) VALUES ('compE', 'iRobt', '1950-12-02');

CREATE TABLE Comments(
CommentId INT NOT NULL,
Poster CHAR(5),
Recipient CHAR(5),
Text VARCHAR(255) NOT NULL,
PostDate DATE,
PRIMARY KEY(CommentId),
FOREIGN KEY(Poster) REFERENCES Users(Id),
FOREIGN KEY(Recipient) REFERENCES Users(Id)
);

INSERT INTO Comments (CommentId, Poster, Recipient, Text, PostDate) VALUES (1, 'MobyD', 'CPMGB', 'Down with Welsh oppression!!!', '1982-04-27');
INSERT INTO Comments (CommentId, Poster, Recipient, Text, PostDate) VALUES (2, 'CPMGB', 'P0TU5', 'Yo did you hear about this Free Wales Army nonsense? What a riot lol', '1982-05-02');
INSERT INTO Comments (CommentId, Poster, Recipient, Text, PostDate) VALUES (3, 'P0TU5', 'CPMGB', 'lol How could I miss it? Anyways listen to this Soviet joke my Secretary of Defense just told me...', '1982-05-03');
INSERT INTO Comments (CommentId, Poster, Recipient, Text, PostDate) VALUES (4, 'et101', 'physd', 'Check out my work on Fermats Last Theorm!!', '1804-11-21');
INSERT INTO Comments (CommentId, Poster, Recipient, Text, PostDate) VALUES (5, 'physd', 'et101', 'You seem like a bright young man Le Blanc', '1805-01-17');
INSERT INTO Comments (CommentId, Poster, Recipient, Text, PostDate) VALUES (6, 'physd', 'et101', 'Thanks for helping me escape death from those dastarly French! You truly are a good man!', '1807-08-27');
INSERT INTO Comments (CommentId, Poster, Recipient, Text, PostDate) VALUES (7, 'et101', 'physd', 'Yeah... about the "man" part of that...', '1807-09-18');
INSERT INTO Comments (CommentId, Poster, Recipient, Text, PostDate) VALUES (8, 'et101', 'compE', 'We must stick together! There are so few women in our field...', '1830-12-15');
INSERT INTO Comments (CommentId, Poster, Recipient, Text, PostDate) VALUES (9, 'rush3', 'ATLAS', 'OMG I am your biggest fan!!! Can you sign my 2112 album??', '1976-09-07');
INSERT INTO Comments (CommentId, Poster, Recipient, Text, PostDate) VALUES (10, 'ATLAS', 'rush3', '*Shrug*', '1976-10-20');
INSERT INTO Comments (CommentId, Poster, Recipient, Text, PostDate) VALUES (11, 'physd', 'oilEr', 'I know you are dead, but I gotta tell ya, your Identity is AMAZING', '1848-03-14');
INSERT INTO Comments (CommentId, Poster, Recipient, Text, PostDate) VALUES (12, 'oilEr', 'physd', 'WHAT!? Did you steal my IDENTITY?? You thief!!', '1848-03-14');
INSERT INTO Comments (CommentId, Poster, Recipient, Text, PostDate) VALUES (13, 'CPMGB', 'DPP16', 'Keep up the good work, the future of Taiwan is in good hands', '2010-02-09');
INSERT INTO Comments (CommentId, Poster, Recipient, Text, PostDate) VALUES (14, 'iRobt', 'compE', 'You were an inspiration to us all, especially I, robot', '1950-12-02');
INSERT INTO Comments (CommentId, Poster, Recipient, Text, PostDate) VALUES (15, 'oilEr', 'hardi', 'If I was alive I would be honoured to meet you', '1916-08-18');
INSERT INTO Comments (CommentId, Poster, Recipient, Text, PostDate) VALUES (16, 'X111X', 'Y111Y', 'Y are you like this?', '2020-10-07');
INSERT INTO Comments (CommentId, Poster, Recipient, Text, PostDate) VALUES (17, 'Y111Y', 'X111X', 'Why so cross?', '2020-10-07');
INSERT INTO Comments (CommentId, Poster, Recipient, Text, PostDate) VALUES (18, 'oilEr', 'Y111Y', 'I prefer f(x)', '2020-10-01');
INSERT INTO Comments (CommentId, Poster, Recipient, Text, PostDate) VALUES (19, 'oilEr', 'X111X', 'Where are you?', '2020-10-06');
INSERT INTO Comments (CommentId, Poster, Recipient, Text, PostDate) VALUES (20, 'X111X', 'physd', 'I dont know what to say...', '2020-09-08');