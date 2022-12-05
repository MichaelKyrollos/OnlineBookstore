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
    }

    public void generateReport() {
    }

    public void deleteBook() {
        
    }

    public void managerAddBook() {
    }




}
