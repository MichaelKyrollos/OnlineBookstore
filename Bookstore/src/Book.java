import java.util.ArrayList;

public class Book {
    private String ISBN;
    private String name;
    private float price;
    private String publisher;
    private int quantityToBuy;
    private int inStock;
    private ArrayList<String> genre;
    private ArrayList<String> author;
    private float percentToPublisher;
    private int threshold;

    public Book(String ISBN, String name, float price, String publisher, int quantityToBuy, int inStock, ArrayList<String> genre, ArrayList<String> author, float percentToPublisher, int threshold) {
        this.ISBN = ISBN;
        this.name = name;
        this.price = price;
        this.publisher = publisher;
        this.quantityToBuy = quantityToBuy;
        this.inStock = inStock;
        this.genre = genre;
        this.author = author;
        this.percentToPublisher = percentToPublisher;
        this.threshold = threshold;
    }

    /*
     * Getter method for publisher field
     */
    public String getPublisher() {return publisher; }

    /*
     * Getter method for percent to publisher (to pay)
     */
    public float getPercentToPublisher() { return percentToPublisher;}

    /*
     * Setter method to set wanted quantity of book to buy
     */
    public void setQuantityToBuy(int quantityToBuy) {
        this.quantityToBuy = quantityToBuy;
    }

    /*
     * Getter method for price of a book
     */
    public float getPrice() { return price;}

    /*
     * Getter method for getting quantity of book to buy
     */
    public int getQuantity(){ return quantityToBuy ;}

    /*
     * Getter method for ISBN of book
     */
    public String getISBN() {return ISBN;}

    /*
     * Getter method for quantity in stock of book
     */
    public int getInStock() {
        return inStock;
    }

    /*
     * Getter method for threshold amount of a book
     */
    public int getThreshold() {
        return threshold;
    }

    /*
     * Logic to determine if stock is below threshold amount
     */
    public int computeUpdatedStock() {
        int updatedStock = 0;
        // decrease the stock
        if (thresholdRestock()) {
            updatedStock = getThreshold() * 2;
        } else {
            updatedStock = getInStock() - getQuantity();
        }
        return updatedStock;
    }

    /*
     * Returns true if the book needs to be restocked
     */
    public boolean thresholdRestock() {
        if (getInStock()-quantityToBuy < threshold) {
            return true;
        }
        return false;
    }

    /*
     * Method to print out a representation of a book, used in user interface
     */
    @Override
    public String toString() {
        String printGenre="";
        for (String s : genre) {
            printGenre+="\n      - " + s;

        }
        String printAuthor="";
        for (String s : author) {
            printAuthor+="\n      - " + s;
        }
        return  "ISBN:" + ISBN +
                ", Book name:" + name +
                ", Price:" + price +
                ", Publisher: " + publisher +
                ", Quantity: " + quantityToBuy +
                "\n    Genre:" + printGenre +
                "\n    Author(s):" + printAuthor +
                '\n';
    }
}
