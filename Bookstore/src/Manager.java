import java.sql.Connection;
import java.sql.DriverManager;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Manager {
    Bookstore bookstore;

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

        System.out.println("\n------------------\n" +
                "ADD PUBLISHER \n" +
                "------------------" );
        /*
        do {
            System.out.println("Email: ");
            email = input.nextLine();
            System.out.println("Name: ");
            name = input.nextLine();
            System.out.println("Address: ");
            address = input.nextLine();
            System.out.println("Banking Info: ");
            bankingInfo = input.nextLine();
            //addPublisherToDB(email, name, address, bankingInfo);
            if (!email.isEmpty() && !name.isEmpty() && !address.isEmpty() && !bankingInfo.isEmpty()) {
                System.out.println("testing the break\n");
                System.out.println("email = " + email);
                System.out.println("name = " + name);
                System.out.println("address = " + address);
                System.out.println("bank = " + bankingInfo);
                break;
            }

        } while (input.hasNext());*/

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
        addPublisherToDB(email, name, address, bankingInfo);
    }

    public void generateReport() {
    }

    public void deleteBook() {
        Scanner input = new Scanner(System.in);
        String isbn = "";
        String name = "";
        String publisher = "";
        String quantity = "";
        String price = "";
        String pages = "";

        System.out.println("\n------------------\n" +
                "REMOVE BOOK \n" +
                "------------------" );

        while (isbn.isEmpty() || name.isEmpty() || publisher.isEmpty() || quantity.isEmpty() || price.isEmpty() || pages.isEmpty()) {
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
            if (isbn.isEmpty() || name.isEmpty() || publisher.isEmpty() || quantity.isEmpty() || price.isEmpty() || pages.isEmpty())
                System.out.println("Please enter a valid input.\n");
        }
        deleteBookFromDB(isbn, name, publisher, quantity, price, pages);
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

        while (isbn.isEmpty() || name.isEmpty() || publisher.isEmpty() || quantity.isEmpty() || price.isEmpty() || pages.isEmpty()) {
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
            if (isbn.isEmpty() || name.isEmpty() || publisher.isEmpty() || quantity.isEmpty() || price.isEmpty() || pages.isEmpty())
                System.out.println("Please enter a valid input.\n");
        }
        addBookToDB(isbn, name, publisher, quantity, price, pages);
    }

    public void addPublisherToDB(String email, String name, String address, String bankingInfo) {
        System.out.println("Adding to Database...");
        Connection connection;
        {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Assignment2", "postgres", "admin");

                if (connection != null) {
                    System.out.println("Connection OK");
                } else {
                    System.out.println("Failed");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        managerLogin();
    }

    public void addBookToDB(String isbn, String name, String publisher, String quantity, String price, String pages) {
        System.out.println("Adding to Database...");
        Connection connection;
        {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Assignment2", "postgres", "admin");

                if (connection != null) {
                    System.out.println("Connection OK");
                } else {
                    System.out.println("Failed");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        managerLogin();
    }

    public void deleteBookFromDB(String isbn, String name, String publisher, String quantity, String price, String pages) {
        System.out.println("Removing from Database...");
        Connection connection;
        {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Assignment2", "postgres", "admin");

                if (connection != null) {
                    System.out.println("Connection OK");
                } else {
                    System.out.println("Failed");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        managerLogin();
    }


}
