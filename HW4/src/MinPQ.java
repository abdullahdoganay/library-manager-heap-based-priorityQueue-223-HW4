//-----------------------------------------------------
// Title: MinPQ Class
// Author: Abdullah Doğanay
// ID: 10549887192
// Section: 2
// Assignment: 4
// Description: This class contains instance variables and methods of minimum priority queue.
// -----------------------------------------------------
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MinPQ<Customer> implements Iterable<Customer>
{
    private Customer[] pq;
    private int n;
    private Comparator<Customer> comparator;
    public MinPQ(int initCapacity) {
        pq = (Customer[]) new Object[initCapacity + 1];
        n = 0;
    }


    public MinPQ(int initCapacity, Comparator<Customer> comparator)
    //--------------------------------------------------------
    // Summary: This method is the constructor of MinPQ method
    // Precondition: it takes capacity of queue and comparator object
    // Postcondition: object created
    // --------------------------------------------------------
    {
        this.comparator = comparator;
        pq = (Customer[]) new Object[initCapacity + 1];
        n = 0;
    }

    public MinPQ(Customer[] customers)
    //--------------------------------------------------------
    // Summary: This method is the constructor of MinPQ method
    // Precondition: it takes array that we want to order
    // Postcondition: object created
    // --------------------------------------------------------
    {
        n = customers.length;
        pq = (Customer[]) new Object[customers.length + 1];
        for (int i = 0; i < n; i++)
            pq[i+1] = customers[i];
        for (int k = n/2; k >= 1; k--)
            sink(k);
        assert isMinHeap();
    }

    public boolean isEmpty()
    //--------------------------------------------------------
    // Summary: Helper method. its for checking if the array is empty or not
    // Precondition:
    // Postcondition: boolean value returned
    // --------------------------------------------------------
    {
        return n == 0;
    }

    public int size()
    //--------------------------------------------------------
    // Summary: getter method of size of array
    // Precondition:
    // Postcondition: size returned
    // --------------------------------------------------------
    {
        return n;
    }

    public Customer min()
    //--------------------------------------------------------
    // Summary: This method returns the customer which is root of the heap. (minimum value in the heap)
    // Precondition:
    // Postcondition: customer returned
    // --------------------------------------------------------
    {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }

    private void resize(int capacity)
    //--------------------------------------------------------
    // Summary: This method resize the array with new capacity
    // Precondition: it takes capacity of queue and comparator object
    // Postcondition: array resized.
    // --------------------------------------------------------
    {
        assert capacity > n;
        Customer[] temp = (Customer[]) new Object[capacity];
        for (int i = 1; i <= n; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }

    public void insert(Customer x)
    //--------------------------------------------------------
    // Summary: This method inserts a new customer to the heap based PQ.
    // Precondition: it takes customer that we want to insert
    // Postcondition: customer added
    // --------------------------------------------------------
    {

        if (n == pq.length - 1) resize(2 * pq.length);
        pq[++n] = x;
        swim(n);
        assert isMinHeap();
    }

    public Customer delMin()
    //--------------------------------------------------------
    // Summary: This method returns the root of the heap which is minimum and delete it from the array and heap.
    // Precondition:
    // Postcondition: customer deleted
    // --------------------------------------------------------
    {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        Customer min = pq[1];
        exch(1, n--);
        sink(1);
        pq[n+1] = null;     // to avoid loitering and help with garbage collection
        if ((n > 0) && (n == (pq.length - 1) / 4)) resize(pq.length / 2);
        assert isMinHeap();
        return min;
    }

    private void swim(int k)
    //--------------------------------------------------------
    // Summary: This method is for replace the customer towards up in the heap
    // Precondition: it takes integer value as an indices of customer that we want to swim
    // Postcondition: customer replaced upward.
    // --------------------------------------------------------
    {
        while (k > 1 && greater(k/2, k)) {
            exch(k/2, k);
            k = k/2;
        }
    }

    private void sink(int k)
    //--------------------------------------------------------
    // Summary: This method is for replace the customer towards down in the heap
    // Precondition: it takes integer value as an indices of customer that we want to sink
    // Postcondition: customer replaced downward.
    // --------------------------------------------------------
    {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && greater(j, j+1)) j++;
            if (!greater(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    private boolean greater(int i, int j)
    //--------------------------------------------------------
    // Summary: Helper method. it is for checking if the ith element of the array is greater than jth element.
    // Precondition: it takes two integer value to compare
    // Postcondition: boolean value returned
    // --------------------------------------------------------
    {
        if (comparator == null) {
            return ((Comparable<Customer>) pq[i]).compareTo(pq[j]) > 0;
        }
        else {
            return comparator.compare(pq[i], pq[j]) > 0;
        }
    }

    private void exch(int i, int j)
    //--------------------------------------------------------
    // Summary: This is the helper method. method changes the 2 elements of array.
    // Precondition: it takes two integer value as an indices of elements that we want to exchange the places.
    // Postcondition: elements exchanged.
    // --------------------------------------------------------
    {
        Customer swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    private boolean isMinHeap()
    //--------------------------------------------------------
    // Summary: This is the helper method. method returns true if the heap is min heap or not.
    // Precondition:
    // Postcondition: returned boolean value.
    // --------------------------------------------------------
    {
        for (int i = 1; i <= n; i++) {
            if (pq[i] == null) return false;
        }
        for (int i = n+1; i < pq.length; i++) {
            if (pq[i] != null) return false;
        }
        if (pq[0] != null) return false;
        return isMinHeapOrdered(1);
    }

    private boolean isMinHeapOrdered(int k)
    //--------------------------------------------------------
    // Summary: This is the helper method. method returns true if the min heap ordered or not.
    // Precondition:
    // Postcondition: returned boolean value.
    // --------------------------------------------------------
    {
        if (k > n) return true;
        int left = 2*k;
        int right = 2*k + 1;
        if (left  <= n && greater(k, left))  return false;
        if (right <= n && greater(k, right)) return false;
        return isMinHeapOrdered(left) && isMinHeapOrdered(right);
    }


    public Iterator<Customer> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Customer> {

        private MinPQ<Customer> copy;


        public HeapIterator() {
            if (comparator == null) copy = new MinPQ<Customer>(size());
            else                    copy = new MinPQ<Customer>(size(), comparator);
            for (int i = 1; i <= n; i++)
                copy.insert(pq[i]);
        }

        public boolean hasNext()  { return !copy.isEmpty();                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Customer next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMin();
        }
    }



}