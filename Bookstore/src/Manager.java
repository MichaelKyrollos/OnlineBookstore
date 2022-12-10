import java.sql.*;
import java.util.*;

public class Manager {
    Bookstore bookstore;
    ResultSet result;
    Connection connection;
    Statement statement;

    public Manager(Bookstore bookstore) {
        this.bookstore = bookstore;
    }

    public void managerLogin() {

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/OnlineBookstore", "postgres", "admin");
            statement = connection.createStatement();
            if (connection != null) {
                System.out.println("Connection OK");
                //return true;
            } else {
                System.out.println("Failed");
                bookstore.printWelcome();
                //return false;
            }
        } catch (Exception e) {
            System.out.println(e);
            bookstore.printWelcome();
        }
        showManagerMenu();
    }

    private void showManagerMenu() {
        Scanner input = new Scanner(System.in);
        System.out.println("------------------\n" +
                "  MANAGER MENU \n" +
                "------------------\n" );
        System.out.println("0/ To Exit\n1/ Add author\n" +  "2/ Add book\n" + "3/ Delete book\n" +
                "4/ Add publisher\n" + "5/ Report Sales Per Genre\n" + "6/ Report Sales Per Author\n" + "7/ Report Sales Per Publisher\n" + "8/ Report Sales vs Expenditures\n" + "9/ Log out of Manager Account\n");
        do {
            try {
                switch ((input.nextInt())) {
                    case 0:
                        bookstore.printWelcome();

                    case 1:
                        addAuthor();
                        managerLogin();
                        break;

                    case 2:
                        addBook();
                        break;

                    case 3:
                        deleteBook();
                        break;

                    case 4:
                        addPublisher();
                        break;

                    case 5:
                        reportSalesPerGenre();
                        break;

                    case 6:
                        reportSalesPerAuthor();
                        break;

                    case 7:
                        reportSalesPerPublisher();
                        break;

                    case 8:
                        bookstore.printWelcome();

                }
            }
            catch(InputMismatchException e) {
                System.out.println("Please enter a valid input");
                managerLogin() ;
            }

        } while (!input.hasNextInt());
    }


    public void addPublisher() {
        Scanner input = new Scanner(System.in);
        String email = "";
        String name = "";
        String address = "";
        String bankingInfo = "";
        String phoneQuant  = "";
        String phonenum = "";

        System.out.println("\n------------------\n" +
                "ADD PUBLISHER \n" +
                "------------------" );

        while (email.isEmpty() || name.isEmpty() || address.isEmpty() || bankingInfo.isEmpty()) {
            System.out.print("Email: ");
            email = input.nextLine();
            System.out.print("Name: ");
            name = input.nextLine();
            System.out.print("Address: ");
            address = input.nextLine();
            System.out.print("Banking Info: ");
            bankingInfo = input.nextLine();
            if (email.isEmpty() || name.isEmpty() || address.isEmpty() || bankingInfo.isEmpty())
                System.out.println("Please enter a valid input.\n");
        }


        if (!addPublisherToDB(email, name, address, bankingInfo)) {
            managerLogin();
        }

        while (!isInteger(phoneQuant)) {
            System.out.println("How many phone numbers does this publisher have?");
            phoneQuant = input.nextLine();
            if(!isInteger(phoneQuant))
                System.out.println("Please enter a valid number.");
        }

        for (int i = 0; i < Integer.parseInt(phoneQuant); i++) {
            System.out.println("Phone Number #" + (i+1) + ":");
            phonenum = input.nextLine();
            if(!addPhoneNumToDB(phonenum, email)){
                i--;
                System.out.println("Please enter a valid input.");
            }
        }
        managerLogin();
    }

    public void generateReport() {
    }

    public void deleteBook() {
        Scanner input = new Scanner(System.in);
        String isbn = "";

        System.out.println("\n------------------\n" +
                "REMOVE BOOK \n" +
                "------------------" );

        try {
            result = statement.executeQuery(
                    "SELECT isbn, name FROM book;"
            );
            while(result.next())  {
                System.out.print("ISBN: " + result.getString("isbn"));
                System.out.println("\tName: " + result.getString("name"));
            }
            System.out.println();
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        while (isbn.isEmpty()) {
            System.out.println("Please enter the ISBN of the Book you would like to remove: ");
            isbn = input.nextLine();
            if (isbn.isEmpty())
                System.out.println("Please enter a valid input!");
        }

        deleteBookFromDB(isbn);
    }

    public void addAuthor() {
        Scanner input = new Scanner(System.in);
        String email = "";
        String name = "";
        System.out.println("\n------------------\n" +
                "ADD AUTHOR \n" +
                "------------------" );

        while (email.isEmpty() || name.isEmpty()) {
            System.out.print("Email: ");
            email = input.nextLine();
            System.out.print("Name: ");
            name = input.nextLine();
            if (email.isEmpty() || name.isEmpty())
                System.out.println("Please enter a valid input.\n");
        }

        addAuthorToDB(email, name);

        //return;

        //managerLogin();
    }

    public void addBook() {
        Scanner input = new Scanner(System.in);
        String isbn = "";
        String name = "";
        String publisher = "";
        String quantity = "";
        String price = "";
        String pages = "";
        String genreNum = "";
        String genre = "";
        String percent = "";
        String threshold = "";

        System.out.println("\n------------------\n" +
                "ADD BOOK \n" +
                "------------------" );

        while (isbn.isEmpty() || name.isEmpty() || publisher.isEmpty() || quantity.isEmpty() || price.isEmpty() || pages.isEmpty()
                || !isInteger(quantity) || !isFloat(price) || !isInteger(pages) || !isInteger(percent) || !isInteger(threshold)) {
            System.out.print("ISBN: ");
            isbn = input.nextLine();
            System.out.print("Name: ");
            name = input.nextLine();
            System.out.print("Publisher: ");
            publisher = input.nextLine();
            System.out.print("Stock: ");
            quantity = input.nextLine();
            System.out.print("Price: ");
            price = input.nextLine();
            System.out.print("Pages: ");
            pages = input.nextLine();
            System.out.print("Percentage to publisher: ");
            percent = input.nextLine();
            System.out.print("Threshold Quantity: ");
            threshold = input.nextLine();
            if (isbn.isEmpty() || name.isEmpty() || publisher.isEmpty() || quantity.isEmpty() || price.isEmpty() || pages.isEmpty()
                    || !isInteger(quantity) || !isFloat(price) || !isInteger(pages) || !isInteger(percent) || !isInteger(threshold))
                System.out.println("Please enter a valid input.\n");
        }


        if(!addBookToDB(isbn, name, publisher, quantity, price, pages, percent, threshold)) {
            managerLogin();
        }

        while (!isInteger(genreNum)) {
            System.out.println("How many genres does this book have?");
            genreNum = input.nextLine();
            if(!isInteger(genreNum))
                System.out.println("Please enter a valid number.");
        }

        for (int i = 0; i < Integer.parseInt(genreNum); i++) {
            System.out.print("Genre #" + (i+1) + ": ");
            genre = input.nextLine();
            if(!addGenreToDB(isbn, genre)){
                i--;
                System.out.println("Please enter a valid input.");
            }
        }

        addWrittenBy(isbn, name);
        managerLogin();
    }

    public void addWrittenBy(String isbn, String name) {
        Scanner input = new Scanner(System.in);
        String authors = "";
        String email = "";

        while (!isInteger(authors)) {
            System.out.println("How many authors does this book have?");
            authors = input.nextLine();
            if(!isInteger(authors)) {
                System.out.println("Please enter a valid number.");
            }else if(Integer.parseInt(authors) == 0) {
                System.out.println("Please enter a valid number.");
            }
        }

        int numAuths = Integer.parseInt(authors);

        System.out.println("Listing current authors in the database...");

        while (numAuths > 0) {
            int num = 1;
            try {
                result = statement.executeQuery(
                        "SELECT name, email FROM author;"
                );
                while(result.next())  {
                    System.out.println("Author #" + num++);
                    System.out.println("\tName: " + result.getString("name"));
                    System.out.println("\tEmail: " + result.getString("email"));
                }
                System.out.println();
            } catch (SQLException sqle) {
                System.out.println(sqle);
            }

            System.out.println("Please enter the email of the author(s) of \"" + name + "\":");
            System.out.println("Note: If the author's name is not on this list, please enter 0 to add the author to the database");
            email = input.nextLine();
            if(Objects.equals(email, "0")) {
                addAuthor();
                numAuths++;
            } else if(!addWrittenByToDB(email, isbn)){
                numAuths++;
                System.out.println("Please enter a valid input.");
            }
            numAuths--;
        }

/*
        for (int i = 0; i < Integer.parseInt(authors); i++) {
            try {
                result = statement.executeQuery(
                        "SELECT name, email FROM author;"
                );
                while(result.next())  {
                    System.out.println("Author #" + num++);
                    System.out.println("\tName: " + result.getString("name"));
                    System.out.println("\tEmail: " + result.getString("email"));
                }
                System.out.println();
            } catch (SQLException sqle) {
                System.out.println(sqle);
            }

            System.out.println("Please enter the email of the author(s) of " + name + ":");
            System.out.println("Note: If the author's name is not on this list, please enter 0 to add the author to the database");
            email = input.nextLine();
            if(Objects.equals(email, "0")) {
                addAuthor();
            }
            if(!addWrittenByToDB(email, isbn)){
                i--;
                System.out.println("Please enter a valid input.");
            }
        }*/
        return;
    }

    public boolean addPublisherToDB(String email, String name, String address, String bankingInfo) {
        System.out.println("Adding to database...");

        try {
            statement.executeUpdate("INSERT INTO publisher VALUES ('" + email + "', '" + name +
                    "', '" + address + "', '" + bankingInfo + "', 0);");
            //System.out.println("Successfully added to Database!");
        } catch (SQLException sqle) {
            System.out.println("Error: Could not Add to Database!");
            System.out.println(sqle);
            return false;
        }
        return true;
        //managerLogin();
    }

    public boolean addPhoneNumToDB(String phone, String publisher) {
        System.out.println("Adding to database...");

        try {
            statement.executeUpdate("INSERT INTO PhoneNumbers VALUES ('" + publisher + "', '" + phone + "');");
            //System.out.println("Successfully added to Database!");
        } catch (SQLException sqle) {
            System.out.println("Error: Could not Add to Database!");
            System.out.println(sqle);
            return false;
        }
        return true;
    }

    public boolean addBookToDB(String isbn, String name, String publisher, String quantity, String price, String pages, String percent, String threshold) {
        System.out.println("Adding to database...");

        try {
            statement.executeUpdate("INSERT INTO book VALUES ('" + isbn + "', '" + name + "', '" + publisher + "', '" + quantity + "', " + Float.parseFloat(threshold) + ", " +
                    Float.parseFloat(percent) + ", " + Float.parseFloat(price) + ", " + Integer.parseInt(pages) + ", " + 0 + ");");
            //System.out.println("Successfully added to Database!");
        } catch (SQLException sqle) {
            System.out.println("Error: Could not Add to Database!");
            System.out.println(sqle);
            return false;
        }

        return true;

    }

    public boolean addGenreToDB(String isbn, String genre) {
        System.out.println("Adding to database...");

        try {
            statement.executeUpdate("INSERT INTO genres VALUES ('" + isbn + "', '" + genre + "');");
        } catch (SQLException sqle) {
            System.out.println("Error: Could not Add to Database!");
            System.out.println(sqle);
            return false;
        }
        return true;
    }

    public void addAuthorToDB(String email, String name) {
        System.out.println("Adding to database...");

        try {
            statement.executeUpdate("INSERT INTO author VALUES ('" + email + "', '" + name + "');");
        } catch (SQLException sqle) {
            System.out.println("Error: Could not Add to Database!");
            System.out.println(sqle);
        }
    }

    public boolean addWrittenByToDB(String email, String isbn) {
        System.out.println("Adding to database...");

        try {
            statement.executeUpdate("INSERT INTO writtenBy VALUES ('" + email + "', '" + isbn + "');");
        } catch (SQLException sqle) {
            System.out.println("Error: Could not Add to Database!");
            System.out.println(sqle);
            return false;
        }
        return true;
    }

    public void deleteBookFromDB(String isbn) {
        System.out.println("Removing from database...");

        try {
            statement.executeUpdate("DELETE FROM genres WHERE (isbn='" + isbn + "');");
            statement.executeUpdate("DELETE FROM writtenBy WHERE (isbn='" + isbn + "');");
            statement.executeUpdate("DELETE FROM book WHERE (isbn='" + isbn + "');");
            //System.out.println("Successfully removed from Database!");
        } catch (SQLException sqle) {
            System.out.println("Error: Could not remove from Database!");
            System.out.println(sqle);
        }
        managerLogin();
    }


    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public static boolean isFloat(String s) {
        try {
            Float.parseFloat(s);
        } catch(NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public void reportSalesPerGenre(){
        try {
            ResultSet result = null;
            result = statement.executeQuery("select genre, sum(quantity)\n" +
                    "from book, \"order\" AS bookOrder, \"contains\" AS orderContains, genres\n" +
                    "where book.isbn = orderContains.isbn AND orderContains.orderNum = bookOrder.orderNum AND book.isbn = genres.isbn\n" +
                    "group by genre\n" +
                    "order by sum(quantity) DESC");
            System.out.println("\n---------------\nSales Per Genre:\n---------------\n");
            while(result.next()){
                System.out.println("Genre: " + result.getString("genre") + ", Sold: " + result.getString("sum") + " book(s)");
            }
            System.out.println("\n---------------\n");
            Scanner input = new Scanner(System.in);
            System.out.println("\nEnter anything to go back...");
            if(input.hasNext()){
                showManagerMenu();
            }

        } catch (SQLException sqle) {
            System.out.println("Error Occurred!");
            System.out.println(sqle);
            showManagerMenu();
        }
    }

    public void reportSalesPerAuthor(){
        try {
            ResultSet result = null;
            result = statement.executeQuery("select author.name, author.email, sum(quantity)\n" +
                    "from book, \"order\" AS bookOrder, \"contains\" AS orderContains, author, writtenBy\n" +
                    "where book.isbn = orderContains.isbn AND orderContains.orderNum = bookOrder.orderNum AND book.isbn = writtenBy.isbn AND writtenBy.authEmail = author.email\n" +
                    "group by author.name, author.email\n" +
                    "order by sum(quantity) DESC");
            System.out.println("\n---------------\nSales Per Author:\n---------------\n");
            while(result.next()){
                System.out.println("Author Name: " + result.getString("name") + ", Email: " + result.getString("email") + ", Sold: " + result.getString("sum") + " book(s)");
            }
            System.out.println("\n---------------\n");
            Scanner input = new Scanner(System.in);
            System.out.println("\nEnter anything to go back...");
            if(input.hasNext()){
                showManagerMenu();
            }

        } catch (SQLException sqle) {
            System.out.println("Error Occurred!");
            System.out.println(sqle);
            showManagerMenu();
        }
    }

    public void reportSalesPerPublisher(){
        try {
            ResultSet result = null;
            result = statement.executeQuery("select publisher.name, publisher.email, sum(quantity)\n" +
                    "from book, \"order\" AS bookOrder, \"contains\" AS orderContains, publisher\n" +
                    "where book.isbn = orderContains.isbn AND orderContains.orderNum = bookOrder.orderNum AND book.publisher = publisher.email\n" +
                    "group by publisher.name, publisher.email\n" +
                    "order by sum(quantity) DESC");
            System.out.println("\n---------------\nSales Per Publisher:\n---------------\n");
            while(result.next()){
                System.out.println("Publisher Name: " + result.getString("name") + ", Email: " + result.getString("email") + ", Sold: " + result.getString("sum") + " book(s)");
            }
            System.out.println("\n---------------\n");
            Scanner input = new Scanner(System.in);
            System.out.println("\nEnter anything to go back...");
            if(input.hasNext()){
                showManagerMenu();
            }

        } catch (SQLException sqle) {
            System.out.println("Error Occurred!");
            System.out.println(sqle);
            showManagerMenu();
        }
    }

}
