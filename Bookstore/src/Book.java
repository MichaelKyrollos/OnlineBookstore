import java.util.ArrayList;

public class Book {
    private String ISBN;
    private String name;
    private String price;
    private String publisher;
    private int quantityToBuy;
    private int inStock;
    private ArrayList<String> genre;
    private ArrayList<String> author;

    public Book(String ISBN, String name, String price, String publisher, int quantityToBuy, int inStock, ArrayList<String> genre, ArrayList<String> author) {
        this.ISBN = ISBN;
        this.name = name;
        this.price = price;
        this.publisher = publisher;
        this.quantityToBuy = quantityToBuy;
        this.inStock = inStock;
        this.genre = genre;
        this.author = author;
    }

    public void setQuantityToBuy(int quantityToBuy) {
        this.quantityToBuy = quantityToBuy;
    }
    public int getQuantity(){ return quantityToBuy ;}
    public String getISBN() {return ISBN;}
    public int getInStock() {
        return inStock;
    }

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
