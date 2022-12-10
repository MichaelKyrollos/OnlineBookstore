/* NOTE: 
    -This file only contains DQL, so queries that retrieve data. UPDATE/INSERT Queries are not included
    -These queries will contain parts of Java code since we used variables as inputs at times
    -Refer to the code to see the queries being used, specifically queries are being sent to the database using the executeQuery() function
*/

/*Returns all books*/
SELECT * from book;

/*Retrieves all publishers*/
SELECT * from publisher;

/*Retrieves the isbn and name of all books*/
SELECT isbn, name FROM book;

/*Retrieves name and email of every author*/
SELECT name, email FROM author;

/*Retrieves genre and the sum of the quantities of every book with that genre (descending)*/
select genre, sum(quantity)
from book, "order" AS bookOrder, "contains" AS orderContains, genres
where book.isbn = orderContains.isbn AND orderContains.orderNum = bookOrder.orderNum AND book.isbn = genres.isbn
group by genre
order by sum(quantity) DESC

/*Retrieves genre and the sum of the quantities of every book with that genre (descending)*/
select author.name, author.email, sum(quantity)
from book, "order" AS bookOrder, "contains" AS orderContains, author, writtenBy
where book.isbn = orderContains.isbn AND orderContains.orderNum = bookOrder.orderNum AND book.isbn = writtenBy.isbn AND writtenBy.authEmail = author.email
group by author.name, author.email
order by sum(quantity) DESC

/*Retireves publisher name and email and sum of the quantities of every book that was published by that author*/
select publisher.name, publisher.email, sum(quantity)
from book, "order" AS bookOrder, "contains" AS orderContains, publisher
where book.isbn = orderContains.isbn AND orderContains.orderNum = bookOrder.orderNum AND book.publisher = publisher.email
group by publisher.name, publisher.email
order by sum(quantity) DESC

/*Retrieve the username that matches the string value of "userName*/
select *, username from "user" where (username = userName )

/*Retrieves all orders that match the inputted username*/
select * from "order" AS bookOrder where bookOrder.username = username

/*Retrieve all the books that were ordered in any inputted orderNum*/
select * from "contains" AS orderContains, book where book.isbn = orderContains.isbn AND orderContains.orderNum = result.getString("orderNum")

/*Retrieve homeaddress of the user using the username inputted*/
select homeaddress from "user" where (username='" + getUsername() + "')

/*Gets the maximum orderNumber from the orders relation, so that we know what number to give to the next order*/
select max(ordernum) from "order"

/*Get the books that are written by the inputted author name or an author name that is LIKE the author name inputted*/
select *, book.name AS bookName, publisher.name AS publisherName, author.name AS authorName 
from book, writtenBy, publisher, author 
where book.isbn = writtenBy.isbn AND book.publisher = publisher.email AND author.email = writtenBy.authEmail AND author.name LIKE author

/*Retrieves the books that fit the inputted genre or an genre that is LIKE the genre inputted*/
select *, book.name AS bookName, publisher.name AS publisherName 
from book, publisher, genres 
where genres.isbn = book.isbn AND book.publisher = publisher.email AND genres.genre LIKE genre

/*Retrieves the books that have a similar to publisher name to the inputted publisher name*/
select *, book.name AS bookName, publisher.name AS publisherName 
from book, publisher 
where book.publisher = publisher.email AND publisher.name LIKE publisher

/*Retrieves books that have a similar ISBN to the inputted ISBN*/
select *, book.name AS bookName, publisher.name AS publisherName
from book, publisher 
where book.publisher = publisher.email AND book.isbn LIKE ISBN

/*Retrieves book that have a similar name to the inputted book name*/
select *, book.name AS bookName, publisher.name AS publisherName
from book, publisher where book.publisher = publisher.email AND book.name LIKE name

/*Retieve the names of the author (and other data) that wrote the book that has the inputted ISBN*/
select *, author.name AS authorName 
from writtenby, book, author
where book.isbn = writtenby.isbn AND writtenby.authemail = author.email AND writtenby.isbn = result.getString("isbn")

/*Get the genres (and other data) of the book that has the inputted isbn*/
select *
from genres, book
where book.isbn = genres.isbn AND genres.isbn = result.getString("isbn")