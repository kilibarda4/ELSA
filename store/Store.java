package store;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.Set;

public class Store {
    private String name;
    private TreeSet<Customer> customers = new TreeSet<>();
    private HashSet<Option> options = new HashSet<>();
    private HashSet<Computer> computers = new HashSet<>();
    private HashSet<Order> orders = new HashSet<>();

    public Store(String name) {
        this.name = name;
    }

    public Store(BufferedReader br) throws IOException {
        this.name = br.readLine();
        int size = Integer.parseInt(br.readLine());
        while(size-- > 0)
            customers.add(new Customer(br));

        size = Integer.parseInt(br.readLine());
        while(size-- > 0)
            options.add(new Option(br));

        size = Integer.parseInt(br.readLine());
        while(size-- > 0)
            computers.add(new Computer(br));

        size = Integer.parseInt(br.readLine());
        while(size-- > 0)
            orders.add(new Order(br));
    }

    public void save(BufferedWriter bw) throws IOException
    {
        bw.write(name + '\n');
        save(bw, customers);
        save(bw, options);
        save(bw, computers);
        save(bw, orders);
    }

    private <T extends Saveable> void save(BufferedWriter bw, Set<T> set) throws IOException
    {
        bw.write("" + set.size() + '\n');
        for(var x : set) x.save(bw);
    }

    public String name() {
        return this.name;
    }
    
    // ///////////////////////////////////////////////////////////
    // Customers
    
    public void add(Customer customer) {
        if(!customers.contains(customer)) customers.add(customer);
    }
    public Object[] customers() {
        return this.customers.toArray();
    }
    
    // ///////////////////////////////////////////////////////////
    // Options
    
    public void add(Option option) {
        if(!options.contains(option)) options.add(option);
    }
    public Object[] options() {
        return this.options.toArray();
    }
    
    // ///////////////////////////////////////////////////////////
    // Computers
    
    public void add(Computer computer) {
        if(!computers.contains(computer)) computers.add(computer);
    }
    public Object[] computers() {
        return this.computers.toArray();
    }
    
    // ///////////////////////////////////////////////////////////
    // Orders
    
    public void add(Order order) {
        if(!orders.contains(order)) orders.add(order);
    }
    public Object[] orders() {
        return this.orders.toArray();
    }

    // ///////////////////////////////////////////////////////////
    // Fields
    
}
