import java.sql.*;
import java.util.*;

public class User {
    Bookstore bookstore;
    ResultSet result = null;

    Statement statement;
    Connection connection;




    public User(Bookstore bookstore) {
        this.bookstore = bookstore;
        this.connection = null;

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
                System.out.println("Which book would you like to remove from cart? (enter the book number)");
                removeFromCart(input.nextInt());
            }
            catch(InputMismatchException e) {
                System.out.println("Please enter a valid input");
                removeBookFromCartPrompt();
            }
        }while(!input.hasNextInt());
    }

    private void removeFromCart(int nextInt) {
    }

    private void decreaseQuantityPrompt() {
        Scanner input = new Scanner(System.in);
        do {
            try {
                System.out.println("Which book in the cart would you like to decrease the quantity of? (enter the book number)");
                decreaseQuantity(input.nextInt());
            }
            catch(Exception e) {
                System.out.println("Please enter a valid input");
                decreaseQuantityPrompt();
            }
        }while(!input.hasNextInt());
    }

    private void decreaseQuantity(int nextInt) {
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
                        System.out.println("What is the name of the publisher?");
                        searchByPublisher(input.nextLine());
                        break;
                    case 4:
                        System.out.println("What is the ISBN number of the book?");
                        searchByISBN(input.nextInt());
                        break;

                    case 5:
                        System.out.println("What is the genre of the book");
                        if (input.nextLine().equals("0")) {
                            searchByGenre(input.nextLine());}
                        break;
                }

        }

    public void searchByAuthor(int nextInt) {
    }

    public void searchByGenre(String genre) {
    }

    public void searchByPublisher(String publisher) {
        ResultSet result = null;

        try {
            result = statement.executeQuery(
                    "SELECT * FROM book");
            while(result.next()) {
                System.out.println("ISBN: " + result.getString("isbn"));
                System.out.println("Book Title: " + result.getString("name"));
                System.out.println("Book Title: " + result.getString("publisher"));
                System.out.println("Book Title: " + result.getInt("instock"));
                System.out.println("Book Title: " + result.getInt("thresholdquantity"));
                System.out.println("Book Title: " + result.getInt("percenttopublisher"));
                System.out.println("Book Title: " + result.getFloat("price"));
                System.out.println("Book Title: " + result.getInt("pages"));
                System.out.println("Book Title: " + result.getInt("soldthismonth"));
                System.out.println("Book Title: " + result.getInt("soldlastmonth"));
                System.out.println("Book Title: " + result.getInt("amountsoldhistory"));
            }
        } catch (SQLException sqle) {
            System.out.println("NOT WORKING!" + sqle);
        }
    }

    public void searchByISBN(int ISBN) {
    }

    public void searchByName(String name) {
    }

    }




