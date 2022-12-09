DROP TABLE IF EXISTS "order" CASCADE;
DROP TABLE IF EXISTS "user" CASCADE;
DROP TABLE IF EXISTS "contains" CASCADE;
DROP TABLE IF EXISTS GENRES CASCADE;
DROP TABLE IF EXISTS BOOK CASCADE;
DROP TABLE IF EXISTS WRITTENBY CASCADE;
DROP TABLE IF EXISTS PUBLISHER CASCADE;
DROP TABLE IF EXISTS AUTHOR CASCADE;
DROP TABLE IF EXISTS PHONENUMBERS CASCADE;


CREATE TABLE "user" (
	username		VARCHAR(20),
	password		VARCHAR(20)	NOT NULL,
	homeAddress		VARCHAR(50)	NOT NULL,
	PRIMARY KEY (username)
);

CREATE TABLE Publisher (
    email		VARCHAR(40)	PRIMARY KEY,
    name		VARCHAR(20)	NOT NULL,
    address		VARCHAR(50)	NOT NULL,
    bankingInfo	CHAR(16) NOT NULL,
	earnings	FLOAT	NOT NULL
);

CREATE TABLE Author (
    email		VARCHAR(40) PRIMARY KEY,
    name		VARCHAR(20)	NOT NULL
);

CREATE TABLE "order" (
	orderNum			INT,
	shippingAddress		VARCHAR(50)	NOT NULL,
	username			VARCHAR(20),
	billingAddress		VARCHAR(50),
	PRIMARY KEY (orderNum),
	FOREIGN KEY (username) 
		REFERENCES "user" (username)
);

CREATE TABLE "contains" (
	orderNum		INT,
	isbn			CHAR(13),
	quantity		INT	NOT NULL,
	PRIMARY KEY (orderNum, isbn),
	FOREIGN KEY (orderNum) 
		REFERENCES "order" (orderNum)
);

CREATE TABLE BOOK (
    isbn				CHAR(13),
    name				VARCHAR(30) NOT NULL, 
    publisher			VARCHAR(40), 
    inStock				INT	NOT NULL, 
    thresholdQuantity	INT	NOT NULL, 
    percentToPublisher	INT NOT NULL, 
    price				FLOAT	NOT NULL, 
    pages				INT	NOT NULL, 
    amountSoldHistory	INT NOT NULL,    
    PRIMARY KEY(isbn),
    FOREIGN KEY (publisher)
        REFERENCES PUBLISHER (email)
);

CREATE TABLE GENRES (
    isbn		CHAR(13),
    genre		VARCHAR(20)	NOT NULL, 
    PRIMARY KEY(isbn, genre),
    FOREIGN KEY (isbn)
        REFERENCES BOOK (isbn)
);

CREATE TABLE WrittenBy (
    authEmail		VARCHAR(40),
    isbn			CHAR(13),
    PRIMARY KEY(authEmail, isbn),
    FOREIGN KEY (authEmail) 
		REFERENCES Author(email),
    FOREIGN KEY (isbn) 
		REFERENCES Book (isbn)
);

CREATE TABLE PhoneNumbers (
    email		VARCHAR(40),
    number		CHAR(11),
    PRIMARY KEY (email, number),
    FOREIGN KEY (email) 
		REFERENCES Publisher(email)
);