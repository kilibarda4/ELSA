package store;

public class WorkingOrNot {
    public static void main(String[] args)
    {
        Customer Petar = new Customer("Petar", "pero@mavs.uta.edu");
        System.out.println(Petar);

        Option graficka = new Option("RX 3070", 299);
        System.out.println(graficka);

        Option maticna = new Option("Maticna", 199);
        

        Order porudzbina = new Order(Petar);
        System.out.println(porudzbina);

        Order porudz = new Order(Petar);

        Computer komp = new Computer("Dell","XPS 1500");
        komp.addOption(graficka);
        komp.addOption(maticna);
        System.out.println(komp.cost());

        porudz.addComputer(komp);
        System.out.println(porudz);
    }
}