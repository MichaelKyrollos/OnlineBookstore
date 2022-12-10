INSERT INTO "user"
VALUES	('Antwan', 'anty', '1234 Colonel By Dr'),
		('Michael', 'mikey', '4321 Colonel By Dr'),
		('Victoria', 'vicky', '1111 Colonel By Dr');

INSERT INTO Publisher
VALUES	('valve@gmail.com', 'Valve', '5555 Publisher Rd', '4520111122223333', 0),
		('mihoyo@hotmail.com', 'Mihoyo', '6666 Publisher Rd', '4520444455556666', 0);

INSERT INTO Author
VALUES	('author1@gmail.com', 'One'),
		('author2@gmail.com', 'Two');

INSERT INTO Book
VALUES	('1234567890123', 'Antwan the Great', 'valve@gmail.com', 10, 6, 3, 10.99, 420, 0),
		('1231234567890', 'Vicky the Great', 'mihoyo@hotmail.com', 7, 3, 3, 15.69, 255, 0),
		('1234512367890', 'Mikey the Great', 'valve@gmail.com', 5, 3, 3, 20.00, 27, 0);

INSERT INTO Genres
VALUES	('1234567890123', 'Thriller'),
		('1234567890123', 'Chad'),
		('1231234567890', 'Horror'),
		('1231234567890', 'Thriller'),
		('1234512367890', 'Educational'),
		('1234512367890', 'Chad');

INSERT INTO Writtenby
VALUES	('author1@gmail.com', '1234567890123'),
		('author1@gmail.com', '1231234567890'),
		('author2@gmail.com', '1234512367890');

INSERT INTO Phonenumbers
VALUES	('mihoyo@hotmail.com', '6131234567'),
		('valve@gmail.com', '6131234555'),
		('valve@gmail.com', '6131234566');
