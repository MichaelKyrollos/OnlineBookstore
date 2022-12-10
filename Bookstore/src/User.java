import java.sql.*;
import java.util.*;

public class User {
    private final Bookstore bookstore;
    private Statement statement;
    private Statement statement2;
    private Connection connection;
    private ArrayList<Book> booksSearched;
    private ArrayList<Book> booksInCart;
    private String username;


    public User(Bookstore bookstore) {
        this.bookstore = bookstore;
        this.connection = null;
        booksSearched = new ArrayList<>();
        booksInCart = new ArrayList<>();
    }

    /*
     * Setter method for username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /*
     * Getter method for username
     */
    public String getUsername() {
        return username;
    }

    /*
     * Initial login page of user
     */
    public void userLogin() {

        Scanner input = new Scanner(System.in);
        booksInCart.clear();

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

    /*
     * Menu to create new user that can be added to the database
     */
    public void newUser() {
        Scanner input = new Scanner(System.in);
        String username;
        String password;
        String address;
        do {
            System.out.println("Please enter a unique username");
            username = input.nextLine();
            System.out.println("Please enter a password");
            password = input.nextLine();
            System.out.println("Enter your home address");
            address = input.nextLine();
        } while (username.isEmpty() || password.isEmpty() || address.isEmpty());

        if (registerAccount(username,password,address)) {
            System.out.println("User successfully added");
            this.setUsername(username);
            userMenu();
        }
        else {
            System.out.println("That did not work, try again");
            bookstore.printWelcome();
        }

    }

    /*
     * Makes SQL call and connection the database to register a new account. Returns true if successful, false otherwise
     */
    private boolean registerAccount(String username, String password, String address)  {
        try {
            ResultSet result = null;
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/OnlineBookstore", "postgres", "admin");
            statement = connection.createStatement();
            if (connection != null) {
                try {
                    statement.executeUpdate(
                            "INSERT INTO \"user\" VALUES ('" + username+ "', '" + password +  "', '" + address + "');");
                }
                catch (SQLException sqle) {
                    return false;
                }
            }
        } catch (SQLException | ClassNotFoundException sqle) {
            return false;
        }
        return true;
    }

    /*
     * SQL call and connection to the database for login of existing user
     * It will match the username to the password in the schema to check if the login is valid.
     */
    public void loginExistingUser(String userName, String password) {
        {
            try {
                ResultSet result = null;
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/OnlineBookstore", "postgres", "admin");
                statement = connection.createStatement();
                statement2 = connection.createStatement();
                result = statement.executeQuery(
                        "select *, username from \"user\"  where (username='" + userName + "')");

                if (connection != null) {
                    try {
                        if(result != null) {
                            result.next();
                        if (password.equals(result.getString("password"))) {
                            System.out.println("Connection to DB successful");
                            System.out.println("Logged in");
                            this.setUsername(userName);
                            userMenu();
                        }}
                        else {
                            System.out.println("Failed Connection to DB");
                            bookstore.printWelcome();
                        }

                    }
                    catch (SQLException sqle) {
                        System.out.println("Wrong password or username");
                        bookstore.printWelcome();

                    }
                }

            } catch (Exception e) {
                System.out.println(e);
                bookstore.printWelcome();
            }
        }
    }

    /*
     * Main menu once a user has logged in
     */
    public void userMenu() {
        Scanner input = new Scanner(System.in);
        System.out.println("\n------------------\n" +
                "  USER MENU \n" +
                "------------------" );
        System.out.println("Hello " + getUsername());
        System.out.println("0/ Go Back\n1/ Search\n" + "2/ Go to Cart\n" + "3/ Show Orders\n"  + "4/ Logout\n");
        do {
            try {
                switch ((input.nextInt())) {
                    case 0:
                    case 4:
                        bookstore.printWelcome();
                        break;
                    case 1:
                        userSearch();
                        break;
                    case 2:
                        showCartMenu();
                        break;
                    case 3:
                        showOrders();
                        break;
                }
            }
            catch(InputMismatchException e) {
                System.out.println("Please enter a valid input");
                bookstore.printWelcome();
            }

        } while (!input.hasNextInt());
    }

    /*
     * Returns the orders of the user who is in the session
     */
    private void showOrders() {
        try {
            ResultSet result = null;
            ResultSet result2 = null;
            int counter = 0;
            result = statement.executeQuery("select *\n" +
                    "from \"order\" AS bookOrder\n" +
                    "where bookOrder.username = " +"'" + username + "'");
            System.out.println("\n---------------\nOrder(s) By "+ username +":\n---------------\n");
            while(result.next()){
                System.out.println("Order/Tracking Number: " + result.getString("orderNum") + ", Shipping Address: " + result.getString("shippingaddress") + ", Billing Address: " + result.getString("billingaddress") + ", Ordered By: " + result.getString("username"));
                result2 = statement2.executeQuery("select *\n" +
                        "from \"contains\" AS orderContains, book\n" +
                        "where book.isbn = orderContains.isbn AND orderContains.orderNum = " + "'" + result.getString("orderNum") + "'");
                while(result2.next()) {
                    String ISBN = result2.getString("isbn");
                    String bookName = result2.getString("name");
                    float price = result2.getFloat("Price");
                    String publisherName = result2.getString("publisher");

                    System.out.println("        " + counter + "." +
                            " ISBN: " + ISBN +
                            ", Book Name: " + bookName +
                            ", Price: " + price +
                            ", Publisher: " + publisherName +
                            ", Pages: " + result2.getString("pages") +
                            "  x" + result2.getString("quantity"));

                    counter++;
                }

            }
            System.out.println("\n---------------\n");
            Scanner input = new Scanner(System.in);
            System.out.println("\nEnter anything to go back...");
            if(input.hasNext()){
                userMenu();
            }

        } catch (SQLException sqle) {
            System.out.println("Error Occurred!");
            System.out.println(sqle);
            userMenu();
        }
    }

    /*
     * Shows cart of currently logged-in user
     * Cart is erased once the session is done.
     */
    public void showCartMenu() {
        Scanner input = new Scanner(System.in);
        System.out.println("\n------------------\n" +
                "  USER CART \n" +
                "------------------\n" );

        System.out.println("Showing cart...");
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
                showCartMenu();
            }

        } while (!input.hasNextInt());
    }

    /*
     * Interface for user to remove items from cart
     */
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
        } while(!input.hasNextInt());
    }

    /*
     * Logic to remove item from cart
     * Checks the quantity in cart and validates user input
     */
    private void removeFromCart() throws InputMismatchException{
        Scanner input = new Scanner(System.in);
        int bookNum = input.nextInt();
        if (bookNum < booksInCart.size()) {
            booksInCart.remove(bookNum);
            showCartMenu();
        }
        else {
            System.out.println("This is not a valid option");
            addToCart();
        }
    }

    /*
     * User interface to decrease quantity of item in cart
     */
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
        } while(!input.hasNextInt());
    }

    /*
     * Logic to adjust quantity of an item in cart
     */
    private void decreaseQuantity() {
        Scanner input = new Scanner(System.in);
        int bookNum = input.nextInt();
        System.out.println("By how much would you like to decrease the quantity of the selected book?");
        int removeThisMany = input.nextInt();
        if (bookNum < booksInCart.size()) {
            Book selectedBook = booksInCart.get(bookNum);
            if(0 <= removeThisMany && removeThisMany <= selectedBook.getQuantity()) {
                selectedBook.setQuantityToBuy(selectedBook.getQuantity() - removeThisMany);
                showCartMenu();
            }
            else {
                System.out.println("You cannot remove this much!");
                showCartMenu();
            }
        }
        else {
            System.out.println("This is not a valid option");
            showCartMenu();
        }
    }

    /*
     * User interface to increase quantity of item in cart
     */
    private void increaseQuantityPrompt() {
        Scanner input = new Scanner(System.in);
        do {
            try {
                System.out.println("Showing book(s) in cart:");
                showCartItems();
                System.out.println("Which book in the cart would you like to increase the quantity of? (enter the book number)");
                increaseQuantity();
            }
            catch(Exception e) {
                System.out.println("Please enter a valid input");
                increaseQuantityPrompt();
            }
        }while(!input.hasNextInt());
    }

    /*
     * Logic to increase quantity of item in cart
     */
    private void increaseQuantity() {
        Scanner input = new Scanner(System.in);
        int bookNum = input.nextInt();
        System.out.println("By how much would you like to increase the quantity of the selected book?");
        int addThisMany = input.nextInt();
        if (bookNum < booksInCart.size()) {
            Book selectedBook = booksInCart.get(bookNum);
            if(0 <= addThisMany && addThisMany + selectedBook.getQuantity()  <= selectedBook.getInStock()) {
                selectedBook.setQuantityToBuy(selectedBook.getQuantity() + addThisMany);
                showCartMenu();
            }
            else {
                System.out.println("You cannot add this much!");
                showCartMenu();
            }
        }
        else {
            System.out.println("This is not a valid option");
            addToCart();
        }
    }

    /*
     * Places order using items from cart
     */
    private void placeOrder() {
        ResultSet result;
        int orderNum = 0;
        String billingAddress="";
        String shippingAddress ="";
        if (!booksInCart.isEmpty()) {
            Scanner input = new Scanner(System.in);
            // Shipping address
            System.out.println("Would you like the shipping address to be the same as your home address? 0 for NO, 1 for YES");
            switch ((input.nextInt())) {
                case 0:
                    Scanner address = new Scanner(System.in);
                    System.out.println("What address would you like the order to ship to?");
                    shippingAddress = address.nextLine();
                    System.out.println("Done");

                    break;
                case 1:
                    try {
                        //The first statement is to get the books
                        result = statement.executeQuery(
                                "select homeaddress from \"user\" where (username='" + getUsername() + "');");
                        result.next();
                        shippingAddress = result.getString("homeaddress");
                        System.out.println("Shipping address is " + shippingAddress);
                    } catch (SQLException sqle) {
                        System.out.println("NOT WORKING!" + sqle);
                    }
                    break;
            }
            // Billing address
            System.out.println("Would you like the billing address to be the same as your home address? 0 for NO, 1 for YES");
            switch ((input.nextInt())) {
                case 0:
                    Scanner address = new Scanner(System.in);
                    System.out.println("What billing address would you like?");
                    billingAddress = address.nextLine();
                    break;
                case 1:
                    try {
                        //The first statement is to get the books
                        result = statement.executeQuery(
                                "select homeaddress from \"user\" where (username='" + getUsername() + "');");
                        result.next();
                        billingAddress = result.getString("homeaddress");
                        System.out.println("Billing address set as " + billingAddress);
                    } catch (SQLException sqle) {
                        System.out.println("NOT WORKING!" + sqle);
                    }
                    break;
            }
            if (processOrder(billingAddress, shippingAddress)) {
                booksInCart.clear();
                System.out.println("Order Successful!");
            } else {
                System.out.println("That didn't work");
            }
        }
        userMenu();
    }

    /*
     * Helper method for placing order, returns true if successful, false otherwise
     */
    private boolean processOrder(String billingAddress, String shippingAddress) {
        ResultSet result;
        // query used to generate next order number by incrementing the highest number in the table
        int orderNum;
        try {
            //The first statement is to get the books
            result = statement.executeQuery(
                    "select max(ordernum) from \"order\"");
            result.next();
            orderNum = result.getInt("max");
        } catch (SQLException sqle) {
            System.out.println("NOT WORKING!" + sqle);
            return false;
        }
        orderNum++;
        //Creating the order
        try {
            statement.executeUpdate("INSERT INTO \"order\" VALUES ('" + orderNum + "', '" + shippingAddress + "','" + getUsername() + "', '" + billingAddress + "');");
        } catch (SQLException sqle) {
            System.out.println("0 Error: Could not Add to Database!");
            System.out.println(sqle);
            return false;
        }
        // for every cart item, add it to the contains relation
        for (Book b :booksInCart) {
            try {
                statement.executeUpdate("INSERT INTO contains VALUES ('" + orderNum + "', '" + b.getISBN() + "','" + b.getQuantity() + "');");

            } catch (SQLException sqle) {
                System.out.println("1 Error: Could not Add to Database!");
                System.out.println(sqle);
                return false;
            }
            float earningPub = (b.getQuantity() * b.getPrice()) * (b.getPercentToPublisher() / 100);
            String bookPublisher = searchByPublisherEmail(b.getPublisher());
            // update the earnings in publisher
            try {
                statement.executeUpdate("UPDATE publisher SET earnings = earnings + '" + earningPub + "' where '" + bookPublisher  + "' = publisher.email");

            } catch (SQLException sqle) {
                System.out.println("1 Error: Could not Add to Database!");
                System.out.println(sqle);
                return false;
            }
            // update the stock of the book
            try {
                int buying = b.getQuantity();
                String ISBN = b.getISBN();
                statement.executeUpdate("UPDATE book SET instock = '" + b.computeUpdatedStock()  + "', amountsoldhistory = amountsoldhistory + '" + buying  + "' where book.isbn = '" + ISBN  + "'");
            } catch (SQLException sqle) {
                System.out.println("2 Error: Could not Add to Database!");
                System.out.println(sqle);
                return false;
            }
        }
        return true;

    }

    /*
     * Returns items in cart to user, for a given session
     */
    private void showCartItems() {
        if (booksInCart.isEmpty()) {
            System.out.println("There is nothing in the cart");
        }
        else {
            for (int i = 0 ; i < booksInCart.size() ; i++) {
                System.out.println(i + "." + booksInCart.get(i));
            }
        }
    }

    /*
     * User Search menu
     */
    public void userSearch() {
        Scanner input = new Scanner(System.in);
        System.out.println("\n------------------\n" +
                "  SEARCH BY: \n" +
                "------------------");
        System.out.println(
                "0/ Go Back\n1/ Book Name\n" +
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

    /*
     * Interface to search by author, includes SQL
     */
    public void searchByAuthor() {
        //ResultSet for books
        ResultSet result;
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

    /*
     * Interface to search by genre, includes SQL
     */
    public void searchByGenre() {
        //ResultSet for books
        ResultSet result;
        Scanner input = new Scanner(System.in);
        System.out.println("What is the genre of the book");
        String genre = input.nextLine();
        try {
            //The first statement is to get the books
            result = statement.executeQuery(
                    "select *, book.name AS bookName, publisher.name AS publisherName from book, publisher, genres where genres.isbn = book.isbn AND book.publisher = publisher.email AND genres.genre LIKE '%" + genre + "%'");
            searchBook(result);
        } catch (SQLException sqle) {
            System.out.println("NOT WORKING!" + sqle);
        }
    }

    /*
     * Interface to search by publisher, includes SQL
     */
    public void searchByPublisher() {
        //ResultSet for books
        ResultSet result;
        Scanner input = new Scanner(System.in);
        System.out.println("What is the name of the publisher?");
        String publisher = input.nextLine();
        try {
            //The first statement is to get the books
            result = statement.executeQuery(
                    "select *, book.name AS bookName, publisher.name AS publisherName from book, publisher where book.publisher = publisher.email AND publisher.name LIKE '%" + publisher + "%'");
            searchBook(result);
        } catch (SQLException sqle) {
            System.out.println("NOT WORKING!" + sqle);
        }
    }

    /*
     * Allows to search by email, helper function that will return the publisher email given the name
     */
    public String searchByPublisherEmail(String publisherName) {
        //ResultSet for books
        ResultSet result;
        String email ="";
        try {
            result = statement.executeQuery(
                    "select book.publisher from book, publisher where book.publisher = publisher.email AND publisher.name = '" + publisherName + "'");
            //The first statement is to get the books
            result.next();
            email = result.getString("publisher");
        } catch (SQLException sqle) {
            System.out.println("Error in getting publisher name" + sqle);
        }
        return email;
    }

    /*
     * Interface to search by ISBN, includes SQL
     */
    public void searchByISBN() {
        //ResultSet for books
        ResultSet result = null;
        Scanner input = new Scanner(System.in);
        System.out.println("What is ISBN of the book?");
        String ISBN = input.nextLine();
        try {
            //The first statement is to get the books
            result = statement.executeQuery(
                    "select *, book.name AS bookName, publisher.name AS publisherName from book, publisher where book.publisher = publisher.email AND book.isbn LIKE '%" + ISBN + "%'");
            searchBook(result);
        } catch (SQLException sqle) {
            System.out.println("NOT WORKING!" + sqle);
        }
    }

    /*
     * Interface to search by name, includes SQL
     */
    public void searchByName() {
        //ResultSet for books
        ResultSet result = null;
        Scanner input = new Scanner(System.in);
        System.out.println("What is name of the book?");
        String name = input.nextLine();
        try {
            //The first statement is to get the books
            result = statement.executeQuery(
                    "select *, book.name AS bookName, publisher.name AS publisherName from book, publisher where book.publisher = publisher.email AND book.name LIKE '%"+ name +"%'");
            searchBook(result);
        } catch (SQLException sqle) {
            System.out.println("NOT WORKING!" + sqle);
        }
    }

    /*
     * Returns data found by searches, used by all searching functions.
     * Parses through SQL data to create readable format by user
     */
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
            float price = result.getFloat("Price");
            String publisherName = result.getString("publisherName");
            int quantityStock = result.getInt("inStock");
            int threshold = result.getInt("thresholdquantity");
            float percentToPublisher = result.getFloat("percenttopublisher");

            System.out.println(counter + "." +
                    " ISBN: " + ISBN +
                    ", Book Name: " + bookName +
                    ", Price: " + price +
                    ", Publisher: " + publisherName +
                    ", Pages: " + result.getString("pages") +
                    ", Quantity in stock: " + quantityStock);

            counter++;
            //This second statement is to get the author(s) of this book
            ArrayList<String> tempAuthor = new ArrayList<String>();
            ArrayList<String> tempGenre = new ArrayList<String>();
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

            booksSearched.add(new Book(ISBN,bookName,price,publisherName,0,quantityStock, (ArrayList<String>) tempGenre.clone(),(ArrayList<String>) tempAuthor.clone(), percentToPublisher, threshold));

            if (!result.next()) {
                flag = false;
                cartOption();
            }


        }
    }

    /*
     * Prompt allowing for user to add item to cart, will continue to show until user dismisses
     */
    private void cartOption() {
        Scanner input = new Scanner(System.in);
        System.out.println("Would you like to add any of the books to the cart? 0 for NO, 1 for YES");
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

    /*
     * User input to add a book to the cart, given a list of books
     */
    public void addToCart() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the book number you would like");
        int bookNum = input.nextInt();
        int increaseBy =0;
        int index = 0;
        if (bookNum < booksSearched.size()) {
            //boolean isInCart = booksInCart.contains(booksSearched.get(bookNum));
            boolean isInCart = false;
            for (Book b :booksInCart) {
                if(b.getISBN().equals(booksSearched.get(bookNum).getISBN())){
                    isInCart = true;
                    break;
                }
                index++;
            }
            if (isInCart) {
                Scanner increaseQuantity = new Scanner(System.in);
                System.out.println("This book is already in the cart. By how much would you like to increase the amount?");
                increaseBy = increaseQuantity.nextInt();
                //int index = booksInCart.indexOf(booksSearched.get(bookNum));
                if (increaseBy + booksInCart.get(index).getQuantity()> booksSearched.get(bookNum).getInStock()) {
                    System.out.println("We do not have enough in stock!");
                    System.out.println("Decrease your quantity or order another book...");
                    addToCart();
                }
                else {
                    booksInCart.get(index).setQuantityToBuy(increaseBy+booksInCart.get(index).getQuantity());
                }
            }
            else {
                Scanner toBuy = new Scanner(System.in);
                System.out.println("How many would you like?");
                int amountToBuy = toBuy.nextInt();
                if( amountToBuy > booksSearched.get(bookNum).getInStock()) {
                    System.out.println("We do not have enough in stock!");
                    System.out.println("Decrease your quantity or order another book...");
                    addToCart();
                }
                booksInCart.add(booksSearched.get(bookNum));
                booksInCart.get(booksInCart.size()-1).setQuantityToBuy(amountToBuy);
            }
            System.out.println("Added to cart! \n");
            cartOption();
        }
        else {
            System.out.println("This is not a valid option");
            addToCart();
        }

    }
}




