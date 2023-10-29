package store;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Option implements Saveable {

    protected String name;
    protected long cost;

    public Option(String name, long cost)
    {
        this.name = name;
        this.cost = cost;

        if(cost < 0)
        {
            throw new IllegalArgumentException("Error! Negative item price: " + cost);
        }
    }

    public Option(BufferedReader br) throws IOException
    {
        this.name = br.readLine();
        this.cost = Long.parseLong(br.readLine());
    }
    
    @Override
    public void save(BufferedWriter bw) throws IOException
    {
        bw.write(name + '\n');
        bw.write("" + cost + '\n');
    }

    public long cost()
    {
        return cost;
    }

    @Override
    public String toString()
    {
        return "--- " + name + " ($" + cost + ")";
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if ((o == null) || !(o instanceof Option)) return false;
        Option x = (Option) o;
        return (cost == x.cost && name.equals(x.name));
    }

    @Override
    public int hashCode()
    {
        int hash = 17;
        hash = 31 * hash + name.hashCode();
        hash = 31 * hash + (int) cost;
        return hash;
    }
}

