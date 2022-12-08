import java.sql.*;
import java.util.*;

public class User {
    Bookstore bookstore;
    ResultSet result = null;

    Statement statement;
    Statement statement2;
    Statement statement3;
    Connection connection;
    ArrayList<Book> booksSearched;
    ArrayList<Book> booksInCart;


    public User(Bookstore bookstore) {
        this.bookstore = bookstore;
        this.connection = null;
        booksSearched = new ArrayList<>();
        booksInCart = new ArrayList<>();

    }

    public void userLogin() {

        Scanner input = new Scanner(System.in);

        System.out.println("\n------------------\n" +
                "USER LOGIN \n" +
                "------------------" );
        do {
                System.out.println("Username:");
                String userName = input.nextLine();
                System.out.println("Password:");
                String password = input.nextLine();
                loginExistingUser(userName,password);
        } while (input.hasNextInt());
    }
    public void loginExistingUser(String userName, String password) {
        System.out.println("Logging in");
        {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/OnlineBookstore", "postgres", "admin");
                statement = connection.createStatement();
                statement2 = connection.createStatement();
                statement3 = connection.createStatement();
                if (connection != null) {
                    System.out.println("Connection OK");
                } else {
                    System.out.println("Failed");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        userMenu();
    }

    public void userMenu() {
        Scanner input = new Scanner(System.in);
        System.out.println("\n------------------\n" +
                "USER MENU \n" +
                "------------------" );
        System.out.println("1/ Search\n" + "2/ Go to Cart\n" + "3/ Logout\n" );
        do {
            try {
                switch ((input.nextInt())) {
                    case 0:
                    case 3:
                        bookstore.printWelcome();
                        break;
                    case 1:
                        userSearch();
                        break;
                    case 2:
                        showUserCart();
                        break;
                }
            }
            catch(InputMismatchException e) {
                System.out.println("Please enter a valid input");
                bookstore.printWelcome();
            }

        } while (!input.hasNextInt());
    }

    public void showUserCart() {
        Scanner input = new Scanner(System.in);
        System.out.println("\n------------------\n" +
                "  USER CART \n" +
                "------------------\n" );
        System.out.println("Showing cart...\n");
        showCartItems();
        System.out.println(
                "0/ Go Back\n" +
                "1/ Place Order\n" +
                "2/ Increase Quantity\n" +
                "3/ Decrease Quantity\n" +
                "4/ Remove book from Cart\n");
        do {
            try {
                switch ((input.nextInt())) {
                    case 0:
                        userMenu();
                        break;
                    case 1:
                        placeOrder();
                        break;
                    case 2:
                        increaseQuantityPrompt();
                        break;
                    case 3:
                        decreaseQuantityPrompt();
                        break;
                    case 4:
                        removeBookFromCartPrompt();
                        break;
                }
            }
            catch(InputMismatchException e) {
                System.out.println("Please enter a valid input");
                showUserCart();
            }

        } while (!input.hasNextInt());
    }

    private void removeBookFromCartPrompt() {
        Scanner input = new Scanner(System.in);
        do {
            try {
                System.out.println("Showing book(s) in cart:");
                showCartItems();
                System.out.println("Which book would you like to remove from cart? (enter the book number)");
                removeFromCart();
            }
            catch(InputMismatchException e) {
                System.out.println("Please enter a valid input");
                removeBookFromCartPrompt();
            }
        }while(!input.hasNextInt());
    }

    private void removeFromCart() throws InputMismatchException{
        Scanner input = new Scanner(System.in);
        int bookNum = input.nextInt();
        if (bookNum < booksInCart.size()) {
            booksInCart.remove(bookNum);
            System.out.println("This is not a valid option");
            showUserCart();
        }
        else {
            System.out.println("This is not a valid option");
            addToCart();
        }
    }

    private void decreaseQuantityPrompt() {
        Scanner input = new Scanner(System.in);
        do {
            try {
                System.out.println("Showing book(s) in cart:");
                showCartItems();
                System.out.println("Which book in the cart would you like to decrease the quantity of? (enter the book number)");
                decreaseQuantity();
            }
            catch(Exception e) {
                System.out.println("Please enter a valid input");
                decreaseQuantityPrompt();
            }
        }while(!input.hasNextInt());
    }

    private void decreaseQuantity() {
        Scanner input = new Scanner(System.in);
        int bookNum = input.nextInt();
        System.out.println("By how much would you like to decrease the quantity of the selected book?");
        int removeThisMany = input.nextInt();
        if (bookNum < booksInCart.size()) {
            Book selectedBook = booksInCart.get(bookNum);
            if(0 <= removeThisMany && removeThisMany <= selectedBook.getQuantitiy()) {
                selectedBook.setQuantityToBuy(selectedBook.getQuantitiy() - removeThisMany);
                showUserCart();
            }
            else {
                System.out.println("You cannot remove this much!");
                addToCart();
            }
        }
        else {
            System.out.println("This is not a valid option");
            addToCart();
        }
    }

    private void increaseQuantityPrompt() {
        Scanner input = new Scanner(System.in);
        do {
            try {
                System.out.println("Which book in the cart would you like to increase the quantity of? (enter the book number)");
                increaseQuantity(input.nextInt());
            }
            catch(Exception e) {
                System.out.println("Please enter a valid input");
                increaseQuantityPrompt();
            }
        }while(!input.hasNextInt());
    }

    private void increaseQuantity(int nextInt) {
    }

    private void placeOrder() {
    }

    private void showCartItems() {
        if (booksInCart.isEmpty()) {
            System.out.println("There is nothing in the cart");
        }
        else {
            for (Book b : booksInCart) {
                System.out.println("-" + b);
            }
        }
    }

    public void userSearch() {
        Scanner input = new Scanner(System.in);
        System.out.println("\n------------------\n" +
                "  SEARCH BY: \n" +
                "------------------");
        System.out.println(
                "1/ Book Name\n" +
                        "2/ Author Name\n" +
                        "3/ Publisher\n" +
                        "4/ ISBN\n" +
                        "5/ Genre\n");
        do {
            try {
                switch ((input.nextInt())) {
                    case 0:
                        userMenu();
                        break;
                    case 1:
                        searchByName();
                        break;
                    case 2:
                        searchByAuthor();
                        break;
                    case 3:
                        searchByPublisher();
                        break;
                    case 4:
                        searchByISBN();
                        break;
                    case 5:
                        searchByGenre();
                        break;
                }
            }catch(InputMismatchException e){
                    System.out.println("Please enter a valid input");
                    userSearch();
                }

        }while(!input.hasNextInt());
    }

    public void searchByAuthor() {
        //ResultSet for books
        ResultSet result = null;
        Scanner input = new Scanner(System.in);
        System.out.println("What is the name of the author?");
        String author = input.nextLine();
        try {
            //The first statement is to get the books
            result = statement.executeQuery(
                    "select *, book.name AS bookName, publisher.name AS publisherName, author.name AS authorName from book, writtenBy, publisher, author where book.isbn = writtenBy.isbn AND book.publisher = publisher.email AND author.email = writtenBy.authEmail AND author.name LIKE '%" + author + "%'");
            searchBook(result);
        } catch (SQLException sqle) {
            System.out.println("NOT WORKING!" + sqle);
        }
    }

    public void searchByGenre() {
        //ResultSet for books
        ResultSet result = null;
        Scanner input = new Scanner(System.in);
        System.out.println("What is the genre of the book");
        String genre = input.nextLine();
        try {
            //The first statement is to get the books
            result = statement.executeQuery(
                    "select *, book.name AS bookName, publisher.name AS publisherName from book, writtenBy, publisher, genres where genres.isbn = book.isbn AND book.isbn = writtenBy.isbn AND book.publisher = publisher.email AND genres.genre LIKE '%" + genre + "%'");
            searchBook(result);
        } catch (SQLException sqle) {
            System.out.println("NOT WORKING!" + sqle);
        }
    }

    public void searchByPublisher() {
        //ResultSet for books
        ResultSet result = null;
        Scanner input = new Scanner(System.in);
        System.out.println("What is the name of the publisher?");
        String publisher = input.nextLine();
        try {
            //The first statement is to get the books
            result = statement.executeQuery(
                    "select *, book.name AS bookName, publisher.name AS publisherName from book, writtenBy, publisher where book.isbn = writtenBy.isbn AND book.publisher = publisher.email AND publisher.name LIKE '%" + publisher + "%'");
            searchBook(result);
        } catch (SQLException sqle) {
            System.out.println("NOT WORKING!" + sqle);
        }
    }

    public void searchByISBN() {
        //ResultSet for books
        ResultSet result = null;
        Scanner input = new Scanner(System.in);
        System.out.println("What is ISBN of the book?");
        String ISBN = input.nextLine();
        try {
            //The first statement is to get the books
            result = statement.executeQuery(
                    "select *, book.name AS bookName, publisher.name AS publisherName from book, writtenBy, publisher where book.isbn = writtenBy.isbn AND book.publisher = publisher.email AND book.isbn LIKE '%" + ISBN + "%'");
            searchBook(result);
        } catch (SQLException sqle) {
            System.out.println("NOT WORKING!" + sqle);
        }
    }

    public void searchByName() {
        //ResultSet for books
        ResultSet result = null;
        Scanner input = new Scanner(System.in);
        System.out.println("What is name of the book?");
        String name = input.nextLine();
        try {
            //The first statement is to get the books
            result = statement.executeQuery(
                    "select *, book.name AS bookName, publisher.name AS publisherName from book, writtenBy, publisher where book.isbn = writtenBy.isbn AND book.publisher = publisher.email AND book.name LIKE '%" + name + "%'");
            searchBook(result);
        } catch (SQLException sqle) {
            System.out.println("NOT WORKING!" + sqle);
        }
    }

    private void searchBook(ResultSet result) throws SQLException {
        int counter = 0;
        ResultSet result2 = null;

        booksSearched.clear();
        boolean temp = result.next();
        if (!temp) {
            System.out.println("There are no results");
            userSearch();
        }
        boolean flag = true;
        while(flag) {
            String ISBN = result.getString("isbn");
            String bookName = result.getString("bookName");
            String price = result.getString("Price");
            String publisherName = result.getString("publisherName");
            int quantityStock = result.getInt("inStock");


            System.out.println(counter + "." +
                    " ISBN: " + ISBN +
                    ", Book Name: " + bookName +
                    ", Price: " + price +
                    ", Publisher: " + publisherName +
                    ", Pages: " + result.getString("pages") +
                     ", Quantity in stock: " + quantityStock);

            counter++;
            //This second statement is to get the author(s) of this book
            ArrayList<String> tempAuthor = new ArrayList<>();
            ArrayList<String> tempGenre = new ArrayList<>();
            result2 = statement2.executeQuery("select *, author.name AS authorName\n" +
                    "from writtenby, book, author\n" +
                    "where book.isbn = writtenby.isbn AND writtenby.authemail = author.email AND writtenby.isbn = '"+ result.getString("isbn")  +"'");
            System.out.println("    Written By: ");
            while(result2.next()) {
                System.out.println("        - " + result2.getString("authorName"));
                tempAuthor.add(result2.getString("authorName"));
            }
            //This second statement is now used to get the genre(s) of this book
            result2 = statement2.executeQuery("select *\n" +
                    "from genres, book\n" +
                    "where book.isbn = genres.isbn AND genres.isbn = '"+ result.getString("isbn")  +"'");
            System.out.println("    Genres: ");
            while(result2.next()) {
                System.out.println("        - " + result2.getString("genre"));
                tempGenre.add(result2.getString("genre"));
            }

            booksSearched.add(new Book(ISBN,bookName,price,publisherName,0,quantityStock, (ArrayList<String>) tempGenre.clone(),(ArrayList<String>) tempAuthor.clone() ));

            if (!result.next()) {
                flag = false;
                cartOption();

            }


        }
    }

    private void cartOption() {
        Scanner input = new Scanner(System.in);
        System.out.println("Would you like to add any of the books to the cart? 0/1");
        do {
            try {
                switch ((input.nextInt())) {
                    case 0:
                        userSearch();
                        break;
                    case 1:
                        addToCart();
                        break;
                }
            } catch(InputMismatchException e){
                System.out.println("Please enter a valid input");
                cartOption();
            }
        } while(!input.hasNextInt());

    }

    public void addToCart() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the book number you would like");
        int bookNum = input.nextInt();
        if (bookNum < booksSearched.size()) {
            booksInCart.add(booksSearched.get(bookNum));
            Scanner toBuy = new Scanner(System.in);
            System.out.println("How many would you like?");
            int amountToBuy = toBuy.nextInt();
            if( amountToBuy> booksSearched.get(bookNum).getInStock()) {
                System.out.println("We do not have enough in stock!");
                System.out.println("Decrease your quantity or order another book...");
                addToCart();
            }
            else {
                booksSearched.get(bookNum).setQuantityToBuy(amountToBuy);
                System.out.println("Added to cart! \n");
                cartOption();
            }
        }
        else {
            System.out.println("This is not a valid option");
            addToCart();
        }

    }
}




