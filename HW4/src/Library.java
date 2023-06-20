//-----------------------------------------------------
// Title: Library Class
// Author: Abdullah Doğanay
// ID: 10549887192
// Section: 2
// Assignment: 4
// Description: This class contains the instance variables and methods of library class.
// -----------------------------------------------------

import java.util.ArrayList;


public class Library {
    ArrayList<Customer> listOfCustomers;
    ArrayList<Book> listOfBooks;
    ArrayList<Customer> waitList = new ArrayList<>();
    public Library(ArrayList<Customer> listOfCustomers, ArrayList<Book> listOfBooks){
        this.listOfBooks = listOfBooks;
        this.listOfCustomers = listOfCustomers;
    }


    public void giveBook(Customer customer)
    //--------------------------------------------------------
    // Summary: This method is changes the count of specific book. and represent to giving the book to the customer.
    // Precondition: takes the customer that we want to give his desired book to him.
    // Postcondition: since we give the book to the customer number of books decreased.
    // --------------------------------------------------------
    {
        if( customer.desiredBook.howManyBooks != 0){
            customer.desiredBook.howManyBooks--;
            customer.hasBook = true;
        }
    }



    public void libraryFunction(int day)
    //--------------------------------------------------------
    // Summary: main method for the implementation of project
    // Precondition: takes day
    // Postcondition: according to day it prints what is wanted
    // --------------------------------------------------------
    {
        //işlem kişi arrayi elinde
        ArrayList<Customer> listOfProcess = new ArrayList<>();
        for(int i = 0; i< listOfCustomers.size(); i++){
           if(listOfCustomers.get(i).startOfTheReservationDay <= day && !listOfCustomers.get(i).hasBook){
                listOfProcess.add(listOfCustomers.get(i));
           }
       }

        Customer[] custArr = new Customer[listOfProcess.size()];
        for(int i = 0; i<listOfProcess.size(); i++){
            custArr[i] = listOfProcess.get(i);
        }

        MinPQ<Customer> minPQ = new MinPQ<>(custArr);
        for (int i=0; i<=minPQ.size(); i++){
            if (minPQ.min().desiredBook.howManyBooks != 0){
                giveBook(minPQ.delMin());
            }
            if (minPQ.min().desiredBook.howManyBooks == 0){
                waitList.add(minPQ.delMin());
            }
        }
        for (int i=0; i<waitList.size(); i++){
            System.out.println("w "+waitList.get(i).customerID);
        }
        kitapIade(day);
        for (int i = 0; i<waitList.size(); i++){
            if (waitList.get(i).desiredBook.howManyBooks != 0){
                giveBook(waitList.get(i));
                waitList.remove(waitList.get(i));
            }
        }
    }




    public void bookInfo()
    //--------------------------------------------------------
    // Summary: this method prints the conditions of book
    // Precondition:
    // Postcondition: prints the conditions of books.
    // --------------------------------------------------------
    {
        for(int i = 0; i<listOfBooks.size(); i++){
            System.out.println(listOfBooks.get(i).name+","+listOfBooks.get(i).howManyBooks);
        }
    }


    public void kitapIade(int day)
    //--------------------------------------------------------
    // Summary: this method returns the book to the library
    // Precondition: takes day
    // Postcondition: book returned
    // --------------------------------------------------------
    {
        for(int i = 0; i<listOfCustomers.size(); i++) {
            if (listOfCustomers.get(i).hasBook && (listOfCustomers.get(i).totalReservationDay + listOfCustomers.get(i).startOfTheReservationDay) < day) {
                listOfCustomers.get(i).desiredBook.howManyBooks++;
                listOfCustomers.get(i).hasBook = false;
            }
        }
    }

}
