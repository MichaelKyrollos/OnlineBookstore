import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Bookstore {
   private User user ;

    public Bookstore() {
        this.user = new User(this);
    }

    public void printWelcome()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("==================\n" +
                "ONLINE BOOKSTORE\n" +
                "==================" );
        System.out.println("0/ To Exit \n1/ Login \n" + "2/ Register \n" + "3/ Login as Manager");
        do {
            try {
                switch ((input.nextInt())) {
                    case 0:
                        System.exit(0);
                    case 1:
                        user.userLogin();
                        break;
                    case 2:
                        user.newUser();
                        break;

                    case 3:
                        Manager manager = new Manager(this);
                        manager.managerLogin();
                        break;
                }
            }
            catch(InputMismatchException e) {
                System.out.println("Please enter a valid input");
                printWelcome();
            }
        } while (!input.hasNextInt());
    }

    public static void main(String[] args) {
        Bookstore bookstore = new Bookstore();
        bookstore.printWelcome();
    }

}
