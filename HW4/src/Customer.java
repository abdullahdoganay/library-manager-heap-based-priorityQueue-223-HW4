//-----------------------------------------------------
// Title: Customer Class
// Author: Abdullah Doğanay
// ID: 10549887192
// Section: 2
// Assignment: 4
// Description: This class contains instance variables and constructor method
// -----------------------------------------------------
public class Customer implements Comparable<Customer>{
    public int registrationYear;
    public String customerID;
    public int startOfTheReservationDay;
    public int totalReservationDay;
    public Book desiredBook;
    public boolean hasBook;
    public boolean done;

    public  Customer(int registrationYear, String customerID, int startOfTheReservationDay, int totalReservationDay, Book desiredBook)
    //--------------------------------------------------------
    // Summary: Constructor method
    // Precondition: takes values to initialize.
    // Postcondition: object created.
    // --------------------------------------------------------
    {
        this.customerID = customerID;
        this.desiredBook = desiredBook;
        this.totalReservationDay = totalReservationDay;
        this.startOfTheReservationDay = startOfTheReservationDay;
        this.registrationYear = registrationYear;


    }


    @Override
    public int compareTo(Customer o) {
        if(this.startOfTheReservationDay < o.startOfTheReservationDay) return -1;
        if(this.startOfTheReservationDay > o.startOfTheReservationDay) return 1;
        if(this.registrationYear < o.registrationYear) return -1;
        if(this.registrationYear > o.registrationYear) return 1;
        return 0;

    }
}
