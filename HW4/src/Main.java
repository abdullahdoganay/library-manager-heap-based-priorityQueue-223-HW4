//-----------------------------------------------------
// Title: Main Class
// Author: Abdullah Doğanay
// ID: 10549887192
// Section: 2
// Assignment: 4
// Description: This class contains main method
// -----------------------------------------------------


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {


        String oto = "log1.txt";
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter log file name;");
        //String fileName = scan.next();
        Path personPath = Paths.get(oto);
        List<String> personStringLines = Files.readAllLines(personPath, StandardCharsets.UTF_8);
        ArrayList<Book> listOfBooks = new ArrayList<>();
        ArrayList<Customer> listOfCustomers = new ArrayList<>();
        ArrayList<Integer> days = new ArrayList();
        Library lib = new Library(listOfCustomers, listOfBooks);

        

        boolean bool1 = false;
        for (int i=0; i<personStringLines.size(); i++){

            if (personStringLines.get(i).equals("***CUSTOMER INFO***")){
                bool1 = false;
            }

            if(bool1){
                String str;
                str = personStringLines.get(i);
                int day = Integer.parseInt(str);
                days.add(day);
            }
            if (personStringLines.get(i).equals("**DAY INFO**")){
                bool1 = true;
            }

        }
        int counterForBooks = 0;
        for(int i = 0; i<personStringLines.size(); i++){
            if(personStringLines.get(i).equals("**DAY INFO**")){
               break;
            }
            counterForBooks++;
        }
        for (int i = 1; i<counterForBooks; i++){
            String str;
            str = personStringLines.get(i);
            String author;
            String name;
            String quantity;
            author = str.split(",")[0];
            name = str.split(",")[1];
            quantity = str.split(",")[2];
            Book book = new Book(author, name, Integer.parseInt(quantity));
            listOfBooks.add(book);
        }


        boolean bool = false;
        for (int i=0; i<personStringLines.size(); i++){

            if(bool){
                String str;
                str = personStringLines.get(i);
                String registerDate;
                String customerID;
                String startOfTheReservationDate;
                String totalReservationDay;
                String desiredBookName;
                registerDate = str.split(",")[0];
                customerID = str.split(",")[1];
                startOfTheReservationDate = str.split(",")[2];
                totalReservationDay = str.split(",")[3];
                desiredBookName = str.split(",")[4];
                for (int j = 0; j< listOfBooks.size(); j++){
                    if (Objects.equals(desiredBookName, listOfBooks.get(j).name)){
                        Customer cst = new Customer(Integer.parseInt(registerDate), customerID, Integer.parseInt(startOfTheReservationDate),Integer.parseInt(totalReservationDay), listOfBooks.get(j));
                        listOfCustomers.add(cst);
                    }
                }
            }
            if (personStringLines.get(i).equals("***CUSTOMER INFO***")){
                bool = true;
            }
        }

        Customer[] arr = new Customer[listOfCustomers.size()];
        for(int i = 0; i<listOfCustomers.size(); i++){
            arr[i] = listOfCustomers.get(i);
        }

        for (int i=0; i<days.size(); i++){
            System.out.println("Day: " + days.get(i));
            System.out.println("Customer info: ");
            lib.libraryFunction(days.get(i));
            System.out.println("Book info: ");
            lib.bookInfo();

        }

    }
}