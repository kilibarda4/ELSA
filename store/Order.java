package store;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;

public class Order implements Saveable {

    private static long nextOrderNumber = 0;
    private long orderNumber;
    private Customer customer;
    private ArrayList<Computer> computers = new ArrayList<>();

    public Order(Customer customer)
    {
        this.customer = customer;
        this.orderNumber = nextOrderNumber++;
    }
    public Order(BufferedReader br) throws IOException
    {
        this.orderNumber = Long.parseLong(br.readLine());
        this.customer = new Customer(br);
        int size = Integer.parseInt(br.readLine());
        computers = new ArrayList<>();
        while(size > 0)
        {
            computers.add(new Computer(br));
            size--;
        }
    }

    @Override
    public void save(BufferedWriter bw) throws IOException
    {
        bw.write("" + orderNumber + '\n');
        customer.save(bw);
        for(Computer computer : computers) computer.save(bw);
    }
    
    public void addComputer(Computer computer)
    {
        computers.add(computer);
    }

    public long cost()
    {
        long sum = 0;
        for(Computer c : computers)
        {
            sum += c.cost();
        }
        return sum;
    }

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder("Order " + orderNumber + " for " + customer + "\n($" + cost() + ")" + "\n");
        for(Computer x : computers) result.append(x);
        return result.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if ((o == null) || !(o instanceof Order)) return false;
        Order x = (Order) o;
        return customer.equals(x.customer) && computers.equals(x.computers);
    }

    @Override
    public int hashCode()
    {
        int hash = 17;
        hash = 31 * hash + (customer == null ? 0 : customer.hashCode());
        return hash;
    }

}

