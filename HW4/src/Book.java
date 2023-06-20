public class Book {
    public String author;
    public String name;
    public int howManyBooks;


    public Book(String author, String name, int howManyBooks){
        this.author = author;
        this.name = name;
        this.howManyBooks = howManyBooks;
    }

    public Book(String name){
        this.name = name;
    }

    public Book() {

    }
}
