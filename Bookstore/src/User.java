import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

public class User {
    Bookstore bookstore;

    public User(Bookstore bookstore) {
        this.bookstore = bookstore;
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
                "------------------" );
    }

    public void userSearch() {
        Scanner input = new Scanner(System.in);
        System.out.println("\n------------------\n" +
                "  SEARCH BY: \n" +
                "------------------" );
        System.out.println(
                "1/ Book Name\n" +
                "2/ Author Name\n" +
                "3/ Publisher\n" +
                "4/ ISBN\n" +
                "5/ Genre\n" );
        do {
            try {
                switch ((input.nextInt())) {
                    case 0:
                        userMenu();
                        break;

                    case 1:
                        System.out.println("What is the name of the book you're looking for?");
                        searchByName(input.nextLine());
                        break;

                    case 2:
                        System.out.println("What is the name of the author that you're searching for?");
                        searchByAuthor(input.nextInt());
                        break;

                    case 3:
                        System.out.println("What is the ISBN number of the book?");
                        searchByISBN(input.nextInt());
                        break;

                    case 4:
                        System.out.println("What is the name of the publisher?");
                        searchByPublisher(input.nextLine());
                        break;

                    case 5:
                        System.out.println("What is the genre of the book");
                        if (input.nextLine().equals("0")) {
                            searchByGenre(input.nextLine());}
                        break;
                }
            }
            catch(InputMismatchException e) {
                System.out.println("Please enter a valid input");
                userSearch();
            }

        } while (!input.hasNextInt());
    }

    public void searchByAuthor(int nextInt) {
    }

    public void searchByGenre(String genre) {
    }

    public void searchByPublisher(String publisher) {
    }

    public void searchByISBN(int ISBN) {
    }

    public void searchByName(String name) {
    }

    }




