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
        String email;
        String name;
        String address;
        String bankingInfo;

        System.out.println("\n------------------\n" +
                "ADD PUBLISHER \n" +
                "------------------" );
        do {
            System.out.println("Email: ");
            email = input.nextLine();
            System.out.println("Name: ");
            name = input.nextLine();
            System.out.println("Address: ");
            address = input.nextLine();
            System.out.println("Banking Info: ");
            bankingInfo = input.nextLine();
            addPublisherToDB(email, name, address, bankingInfo);

        } while ( input.hasNextInt() );

    }

    public void generateReport() {
    }

    public void deleteBook() {
        
    }

    public void managerAddBook() {
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




}
