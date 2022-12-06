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
        System.out.println("------------------\n" +
                "  MANAGER LOGIN \n" +
                "------------------\n" );
        System.out.println("1/ Add book to database\n" +  "2/ Delete book\n" + "3/ Generate report\n" +
                "4/ Add publisher\n" + "5/ Logout\n");
        Scanner input = new Scanner(System.in);
        do {
            try {
                switch ((input.nextInt())) {
                    case 0:
                        bookstore.printWelcome();

                    case 5:
                        bookstore.printWelcome();

                    case 1:
                        managerAddBook();
                        break;

                    case 2:
                        deleteBook();
                        break;

                    case 3:
                        generateReport();
                        break;

                    case 4:
                        addPublisher();
                        break;

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

        while (isbn.isEmpty()) {
            System.out.println("Please enter the ISBN of the Book you would like to remove: ");
            isbn = input.nextLine();
            if (isbn.isEmpty())
                System.out.println("Please enter a valid input!");
        }

        deleteBookFromDB(isbn);
    }

    public void managerAddBook() {
        Scanner input = new Scanner(System.in);
        String isbn = "";
        String name = "";
        String publisher = "";
        String quantity = "";
        String price = "";
        String pages = "";

        System.out.println("\n------------------\n" +
                "ADD BOOK \n" +
                "------------------" );

        while (isbn.isEmpty() || name.isEmpty() || publisher.isEmpty() || quantity.isEmpty() || price.isEmpty() || pages.isEmpty()
                || !isInteger(quantity) || !isFloat(price) || !isInteger(pages)) {
            System.out.print("ISBN: ");
            isbn = input.nextLine();
            System.out.print("Name: ");
            name = input.nextLine();
            System.out.print("Publisher: ");
            publisher = input.nextLine();
            System.out.print("Quantity: ");
            quantity = input.nextLine();
            System.out.print("Price: ");
            price = input.nextLine();
            System.out.print("Pages: ");
            pages = input.nextLine();
            if (isbn.isEmpty() || name.isEmpty() || publisher.isEmpty() || quantity.isEmpty() || price.isEmpty() || pages.isEmpty()
                    || !isInteger(quantity) || !isFloat(price) || !isInteger(pages))
                System.out.println("Please enter a valid input.\n");
        }
        addBookToDB(isbn, name, publisher, quantity, price, pages);
    }

    public boolean addPublisherToDB(String email, String name, String address, String bankingInfo) {
        System.out.println("Adding to database...");
        if (!connect()) {
            System.out.println("Error: Could not Connect to Database!");
            return false;
        }

        try {
            statement.executeUpdate("INSERT INTO publisher VALUES ('" + email + "', '" + name +
                    "', '" + address + "', '" + bankingInfo + "');");
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
        if (!connect()) {
            System.out.println("Error: Could not Connect to Database!");
            return false;
        }

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

    public void addBookToDB(String isbn, String name, String publisher, String quantity, String price, String pages) {
        System.out.println("Adding to database...");
        if (!connect()) {
            System.out.println("Error: Could not Connect to Database!");
            return;
        }

        try {
            statement.executeUpdate("INSERT INTO book VALUES ('" + isbn + "', '" + name + "', '" + publisher + "', '" + quantity + "', " + 0 + ", " +
                    0 + ", " + Float.parseFloat(price) + ", " + Integer.parseInt(pages) + ", " + 0 + ", " + 0 + ", " + 0 + ");");
            //System.out.println("Successfully added to Database!");
        } catch (SQLException sqle) {
            System.out.println("Error: Could not Add to Database!");
            System.out.println(sqle);
        }
        managerLogin();
    }

    public void deleteBookFromDB(String isbn) {
        System.out.println("Removing from database...");
        if (!connect()) {
            System.out.println("Error: Could not Connect to Database!");
            return;
        }

        try {
            statement.executeUpdate("DELETE FROM book WHERE (isbn='" + isbn + "');");
            //System.out.println("Successfully removed from Database!");
        } catch (SQLException sqle) {
            System.out.println("Error: Could not remove from Database!");
            System.out.println(sqle);
        }
        managerLogin();
    }

    public boolean connect() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/OnlineBookstore", "postgres", "admin");
            statement = connection.createStatement();
            if (connection != null) {
                System.out.println("Connection OK");
                return true;
            } else {
                System.out.println("Failed");
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
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

}
