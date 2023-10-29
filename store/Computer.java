package store;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;

public class Computer implements Saveable {
    private String name;
    private String model;
    private ArrayList<Option> options = new ArrayList<>();

    public Computer(String name, String model)
    {
        this.name = name;
        this.model = model;
    }
    
    public Computer(BufferedReader br) throws IOException
    {
        this.name = br.readLine();
        this.model = br.readLine();
        int size = Integer.parseInt(br.readLine());
        while(size-- > 0) options.add(new Option(br));
    }

    @Override
    public void save(BufferedWriter bw) throws IOException
    {
        bw.write(name + '\n');
        bw.write(model + '\n');
        bw.write("" + options.size() + '\n');
        for (Option option : options) option.save(bw);
    }

    public void addOption(Option option)
    {
        options.add(option);
    }

    public long cost()
    {
        long sum = 0;
        for (Option c : options)
        {
          sum += c.cost();
        }
        return sum;
    }

    @Override
    public String toString() 
    {
        StringBuilder result = new StringBuilder(name + " (" + model + ")\n");
        for (Option x : options) result.append(x + "\n");
        return result.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if ((o == null) || !(o instanceof Computer)) return false;
        Computer x = (Computer) o;
        return x.toString().equals(o.toString());
    }

    @Override
    public int hashCode()
    {
        int hash = 17;
        hash = 31 * hash + name.hashCode();
        hash = 34 * hash + model.hashCode();
        return hash;
    }
}