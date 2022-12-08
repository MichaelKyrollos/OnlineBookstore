import java.lang.reflect.Array;
import java.util.ArrayList;

public class Book {



    private String ISBN;
    private String name;

    public Book(String ISBN, String name, String price, String publisher, int quantityToBuy, ArrayList<String> genre, ArrayList<String> author) {
        this.ISBN = ISBN;
        this.name = name;
        this.price = price;
        this.publisher = publisher;
        this.quantityToBuy = quantityToBuy;
        this.genre = genre;
        this.author = author;
    }

    private String price;



    private String publisher;


    private int quantityToBuy;
    private ArrayList<String> genre;
    private ArrayList<String> author;



    public void setQuantityToBuy(int quantityToBuy) {
        this.quantityToBuy = quantityToBuy;
    }
    public int getQuantitiy(){ return quantityToBuy ;}

    @Override
    public String toString() {
        return "Book" +
                "ISBN:" + ISBN +
                ", Book name:" + name +
                ", Price:" + price +
                ", Publisher:" + publisher +
                "\n    Quantity: " + quantityToBuy +
                "\n     genre:" + genre +
                "   \nauthor(s):" + author +
                '\n';
    }
}
