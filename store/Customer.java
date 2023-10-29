package store;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Customer implements Saveable, Comparable<Customer> {
    
    private String name;
    private String email;

    public Customer(String name, String email) 
    {
        this.name = name;
        this.email = email;

        //indexOf will return -1 if no occurences of @ OR . happen
        if(email.indexOf('@') == -1 || email.indexOf('.') == -1)
        {
            throw new IllegalArgumentException("Please provide a valid email address.\n");
        }
    }

    public Customer(BufferedReader br) throws IOException
    {
        this.name = br.readLine();
        this.email = br.readLine();
    }

    @Override
    public void save(BufferedWriter bw) throws IOException
    {
        bw.write(name + '\n');
        bw.write(email + '\n');
    }

    @Override
    public String toString() {
        String result = name + " (" + email + ")";
        return result;
    }

    @Override
    public int compareTo(Customer c)
    {
        int res = name.compareTo(c.name);
        if (res == 0)
        {
            res = email.compareTo(c.email);
        }
        return res;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if((o == null) || !(o instanceof Customer)) return false;
        Customer x = (Customer) o;
        return email.equals(x.email);
    }

    @Override
    public int hashCode()
    {
        int hash = 17;
        hash = 31 * hash + name.hashCode();
        hash = 31 * hash + email.hashCode();
        return hash;
    }
}
