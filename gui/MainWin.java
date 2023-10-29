package gui;

import store.Store;
import store.Customer;
import store.Option;
import store.Computer;
import store.Order;

import javax.swing.JFrame;           // for main window
import javax.swing.JOptionPane;      // for standard dialogs
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JDialog;          // for custom dialogs (for alternate About dialog)
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;         // row of menu selections
import javax.swing.JMenu;            // menu selection that offers another menu
import javax.swing.JMenuItem;        // menu selection that does something
import javax.swing.JToolBar;         // row of buttons under the menu
import javax.swing.JButton;          // regular button
import javax.swing.JComboBox;
import javax.swing.JToggleButton;    // 2-state button
import javax.swing.BorderFactory;    // manufacturers Border objects around buttons
import javax.swing.Box;              // to create toolbar spacer
import javax.swing.GroupLayout;
import javax.swing.UIManager;        // to access default icons
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.JLabel;           // text or image holder
import javax.swing.ImageIcon;        // holds a custom icon
import javax.swing.SwingConstants;   // useful values for Swing method calls

import javax.imageio.ImageIO;        // loads an image from a file

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;                 // opens a file
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;          // reports an error reading from a file
import java.util.Objects;
import java.awt.BorderLayout;        // layout manager for main window
import java.awt.FlowLayout;          // layout manager for About dialog
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Color;               // the color of widgets, text, or borders
import java.awt.Dimension;
import java.awt.Font;                // rich text in a JLabel or similar widget
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage; // holds an image loaded from a file

public class MainWin extends JFrame {
    private JLabel display;
    private Store store;
    private File filename;

    public MainWin(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 450);
        setMinimumSize(new Dimension(900, 450));

        // Add a menu bar to the PAGE_START area of the Border Layout
        JMenuBar menubar = new JMenuBar();
        
        JMenu     file       = new JMenu("File");
        JMenuItem aNew       = new JMenuItem("New");
        JMenuItem open       = new JMenuItem("Open");
        JMenuItem save       = new JMenuItem("Save");
        JMenuItem saveAs     = new JMenuItem("Save As");
        JMenuItem quit       = new JMenuItem("Quit");

        JMenu     insert     = new JMenu("Insert");
        JMenuItem customer   = new JMenuItem("Customer");
        JMenuItem option     = new JMenuItem("Option");
        JMenuItem computer   = new JMenuItem("Computer");
        JMenuItem order      = new JMenuItem("Order");

        JMenu     view       = new JMenu("View");
        JMenuItem customers  = new JMenuItem("Customers");
        JMenuItem options    = new JMenuItem("Options");
        JMenuItem computers  = new JMenuItem("Computers");
        JMenuItem orders     = new JMenuItem("Orders");

        JMenu     help       = new JMenu("Help");
        JMenuItem about      = new JMenuItem("About");
        
        aNew.addActionListener(event -> onNewClick());
        open.addActionListener(event -> onOpenClick());
        save.addActionListener(event -> onSaveClick());
        saveAs.addActionListener(event -> onSaveAsClick());
        quit .addActionListener(event -> onQuitClick());
        customer.addActionListener(event -> onInsertCustomerClick());
        option.addActionListener(event -> onInsertOptionClick());
        computer.addActionListener(event -> onInsertComputerClick());
        order.addActionListener(event -> onInsertOrderClick());
        customers.addActionListener(event -> onViewClick(Record.CUSTOMER));
        options.addActionListener(event -> onViewClick(Record.OPTION));
        computers.addActionListener(event -> onViewClick(Record.COMPUTER));
        orders.addActionListener(event -> onViewClick(Record.ORDER));
        about.addActionListener(event -> onAboutClick());


        file.add(aNew);
        file.add(open);
        file.add(save);
        file.add(saveAs);
        file.add(quit);
        insert.add(customer);
        insert.add(option);
        insert.add(computer);
        view.add(customers);
        view.add(options);
        view.add(computers);
        view.add(orders);
        help.add(about);
        

        menubar.add(file);
        menubar.add(insert);
        menubar.add(view);
        menubar.add(help);
        setJMenuBar(menubar);
        
        //add toolbar under the menubar
        JToolBar toolbar = new JToolBar("TOOLBAR");
    
        //adding glue from the left so the buttons are centered
        toolbar.add(Box.createHorizontalGlue());

        JButton newStore = new JButton(new ImageIcon("extras/newStore.png"));
        newStore.setActionCommand("Add new Store");
        newStore.setToolTipText("Instance a new store");
        toolbar.add(newStore);
        newStore.addActionListener(event -> onNewClick());

        JButton saveStore = new JButton(new ImageIcon("extras/saveStore.png"));
        saveStore.setActionCommand("Save Store");
        saveStore.setToolTipText("Save Store");
        toolbar.add(saveStore);
        saveStore.addActionListener(event -> onSaveClick());

        JButton saveAsStore = new JButton(new ImageIcon("extras/saveAsStore.png"));
        saveAsStore.setActionCommand("Save Store as...");
        saveAsStore.setToolTipText("Save store as...");
        toolbar.add(saveAsStore);
        saveAsStore.addActionListener(event -> onSaveAsClick());

        JButton openStore = new JButton(new ImageIcon("extras/loadStore.png"));
        openStore.setActionCommand("Open existing Store");
        openStore.setToolTipText("Open a store from files");
        toolbar.add(openStore);
        openStore.addActionListener(event -> onOpenClick());

        //adding space between the file handling group of buttons and the rest
        toolbar.add(Box.createHorizontalStrut(25));

        // Create the 6 buttons using the icons provided
        JButton insertCustomer = new JButton(new ImageIcon("extras/newCustomer.png"));
          insertCustomer.setActionCommand("Add new Customer");
          insertCustomer.setToolTipText("Add a new customer");
          toolbar.add(insertCustomer);
          insertCustomer.addActionListener(event -> onInsertCustomerClick());

        JButton insertOption = new JButton(new ImageIcon("extras/newOption.png"));
          insertOption.setActionCommand("Create new Option");
          insertOption.setToolTipText("Create a new option");
          toolbar.add(insertOption);
          insertOption.addActionListener(event -> onInsertOptionClick());

        JButton insertComputer = new JButton(new ImageIcon("extras/newPC.png"));
          insertComputer.setActionCommand("Build new Computer");
          insertComputer.setToolTipText("Build a new Computer");
          toolbar.add(insertComputer);
          insertComputer.addActionListener(event -> onInsertComputerClick());

        JButton insertOrder = new JButton(new ImageIcon("extras/newOrder.png"));
          insertOrder.setActionCommand("Add a new order");
          insertOrder.setToolTipText("Make a new order");
          toolbar.add(insertOrder);
          insertOrder.addActionListener(event -> onInsertOrderClick());
        
        //add separation between the insert and view groups
        toolbar.add(Box.createHorizontalStrut(25));
        
        JButton viewCustomers = new JButton(new ImageIcon("extras/viewCustomers.png"));
          viewCustomers.setActionCommand("View Customers");
          viewCustomers.setToolTipText("View Current Customers");
          toolbar.add(viewCustomers);
          viewCustomers.addActionListener(event -> onViewClick(Record.CUSTOMER));

        JButton viewOptions = new JButton(new ImageIcon("extras/viewOptions.png"));
          viewOptions.setActionCommand("View All Options");
          viewOptions.setToolTipText("View Current Options");
          toolbar.add(viewOptions);
          viewOptions.addActionListener(event -> onViewClick(Record.OPTION));

        JButton viewComputers = new JButton(new ImageIcon("extras/viewComputers.png"));
        viewComputers.setActionCommand("View Computers");
        viewComputers.setToolTipText("View all Computers");
        toolbar.add(viewComputers);
        viewComputers.addActionListener(event -> onViewClick(Record.COMPUTER));
        
        JButton viewOrders = new JButton(new ImageIcon("extras/viewOrders.png"));
        viewOrders.setActionCommand("View Orders");
        viewOrders.setToolTipText("View current orders");
        toolbar.add(viewOrders);
        viewOrders.addActionListener(event -> onViewClick(Record.ORDER));

        //Putting glue on the right after the buttons
        toolbar.add(Box.createHorizontalGlue());

        getContentPane().add(toolbar, BorderLayout.PAGE_START);
        
        pack();
        display = new JLabel();
        display.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(display, BorderLayout.CENTER);
        
        setVisible(true);
        
        // //Instance new store
        newStore();
    }

    //method to instance a new store and prompt for name
    protected void onNewClick()
    {
        String name = JOptionPane.showInputDialog("Enter a name for the new store: ");
        if (name != null)
        {
            store = new Store(name);
        }
    }

    protected void onSaveClick()
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename)))
        {
            store.save(bw);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unable to save " + filename + '\n' + e, "Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void onSaveAsClick()
    {
        final JFileChooser fc = new JFileChooser(filename);
        FileFilter storeFiles = new FileNameExtensionFilter("Store files", "store");
        fc.addChoosableFileFilter(storeFiles);
        fc.setFileFilter(storeFiles);

        int result = fc.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            filename = fc.getSelectedFile();
            if (!filename.getAbsolutePath().endsWith(".store"))
            filename = new File(filename.getAbsolutePath() + ".store");
            onSaveClick();
        }
    }

    protected void onOpenClick()
    {
        final JFileChooser fc = new JFileChooser(filename);
        FileFilter storeFiles = new FileNameExtensionFilter("Store files", "store");
        fc.addChoosableFileFilter(storeFiles);
        fc.setFileFilter(storeFiles);

        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            filename = fc.getSelectedFile();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filename)))
        {
            store = new Store(br);
            onViewClick(Record.CUSTOMER);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unable to open: " + filename + '\n' + e,"Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void onQuitClick() {System.exit(0);}

    protected String[] unifiedDialog(String[] fields, String title, String iconFilename)
    {
        String[] result = null;
        ImageIcon icon = null;
        try{
            if(iconFilename != null)
            icon = new ImageIcon(iconFilename);
        } catch(Exception e) {
        }

        Object[] widgets = new Object[2*fields.length];

        for(int i = 0; i < fields.length; i++) {
            widgets[2*i] = new JLabel("<html><br>" + fields[i] + "</html>");
            widgets[2*i+1] = new JTextField();
        }

        int button = JOptionPane.showConfirmDialog(this, widgets, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, icon);
        
        if(button == JOptionPane.OK_OPTION) {
            result = new String[fields.length];
            for(int i = 0; i < fields.length; i++) {
                JTextField textField = (JTextField) widgets[2*i+1];
                result[i] = textField.getText();
            }
        }
        return result;
    }

    protected void onInsertCustomerClick()
    {
        try {
            String[] result = unifiedDialog(new String[]{"Name", "Email"}, "New Customer", "extras/newCustomer.png");

            if(result != null) {
                store.add(new Customer(result[0], result[1]));
                onViewClick(Record.CUSTOMER);
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, e, "Customer Not Created", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void onInsertOptionClick()
    {
        try {
            String[] result = unifiedDialog(new String[]{"Name", "Cost"}, "New Option", "extras/newOption.png");
            
            if(result != null) {
                store.add(new Option(result[0], (long) Double.parseDouble(result[1])));
                onViewClick(Record.OPTION);
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, e, "Option Not Created", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void onInsertComputerClick()
    {
        ImageIcon icon = null;
        try {
             icon = new ImageIcon("gui/resources/add_computer.png");
        } catch(Exception e) {
        }
        try {
            String[] result = unifiedDialog(new String[]{"PC Name", "PC Model"}, "New Computer", "extras/newPC.png");
            if(result == null) return;
            Computer c = new Computer(result[0], result[1]);
            JComboBox<Object> cb = new JComboBox<>(store.options());
            int optionsAdded = 0; // Don't add computers with no options
            while(true) 
            {
                int button = JOptionPane.showConfirmDialog(this, cb, "Another Option?", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, icon);
                if(button != JOptionPane.YES_OPTION) break;
                c.addOption((Option) cb.getSelectedItem());
                ++optionsAdded;
            }
            if(optionsAdded > 0) {
                store.add(c);
                onViewClick(Record.COMPUTER);
            }
        } catch(NullPointerException e) {
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, e, "Computer Not Created", JOptionPane.ERROR_MESSAGE);
        }    
    }

    protected void onInsertOrderClick() 
    {
        ImageIcon icon = null;
        try {
            icon = new ImageIcon("extras/newOrder.png");
        } catch(Exception e) {
        }

        try {
            Object[] customers = store.customers();
            if(customers.length == 0) {
                onInsertCustomerClick();
                customers = store.customers();
                if(customers.length == 0) return;
            }
            Customer customer = (Customer) customers[0];
            if(customers.length > 1) {
                JLabel label = new JLabel("Which Customer is this order for?");
                JComboBox<Object> cb = new JComboBox<>(customers);
                int button = JOptionPane.showConfirmDialog(this, new Object[]{label, cb}, "New Order", 
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, icon);
                if(button != JOptionPane.OK_OPTION) return;
                customer = (Customer) cb.getSelectedItem();
            }
            
            Order o = new Order(customer);
            JComboBox<Object> cb = new JComboBox<>(store.computers());
            int computersAdded = 0;
            while(true) {
                JLabel label = new JLabel("<html><p>" + o.toString().replaceAll("\n", "<br/>") + "</p></html>");
                int button = JOptionPane.showConfirmDialog(this, new Object[]{label, cb}, "Another Computer?", 
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, icon);
                    
                if(button != JOptionPane.YES_OPTION) break;
                
                o.addComputer((Computer) cb.getSelectedItem());
                ++computersAdded;
            }
            if(computersAdded > 0) 
            {
                store.add(o);
                onViewClick(Record.ORDER);
            }
        } catch(NullPointerException e) {
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, e, "Order Not Created", JOptionPane.ERROR_MESSAGE);
        }    
    }

    //define onViewClick bheavior
    protected void onViewClick(Record record)
    {
        Object [] objects = null;
        String header = "";

        if(record == Record.CUSTOMER)
        {
            header = "Customers";
            objects = store.customers();
        }
        else if(record == Record.OPTION)
        {
            header = "Options";
            objects = store.options();
        }
        else if(record == Record.COMPUTER)
        {
            header = "Computers";
            objects = store.computers();
        }
        else if(record == Record.ORDER)
        {
            header = "Current Orders";
            objects = store.orders();
        }

        if(objects != null)
        {
            StringBuilder list = new StringBuilder();

            list.append("<html><h4>" + header + "</h4><ol>");

            for(int i = 0; i < objects.length; i++)
            {
                list.append("<li>" + objects[i].toString() + "</li>");
            }

            list.append("</ol></html>");
            display.setText(list.toString());
            display.setVerticalAlignment(JLabel.TOP);
        } else {
            JOptionPane.showMessageDialog(
                this,
                "No objects found",
                "ERROR",
             JOptionPane.ERROR_MESSAGE);
        }
    }


    //create instance of the Store
    protected void newStore() {
        store = new Store("ELSA");
    }

    // Display About dialog    
    protected void onAboutClick() {
        
        JLabel title = new JLabel("<html>"
          + "<p><font size=+2>About</font></p>"
          + "</html>",
          SwingConstants.CENTER);

        JLabel artists = new JLabel("<html>"
          + "<br/><p><i>This software is released into the public domain</i></p>"
          + "<p><i>with no restrictions on use, modification, or redistribution</i></p><br/>"
          + "<p>All products listed below are licensed for commercial and personal</p>"
          + "<p>purposes with attribution:</p><br/>"

          + "<p>Save icons created by Freepik - Flaticon</p>"
          + "<p><font size=-2>https://www.flaticon.com/free-icons/save</p>"

          + "<p>Save icons created by Freepik - Flaticon (Save As icon)</p>"
          + "<p><font size=-2>https://www.flaticon.com/free-icons/save</p>"

          + "<p>Open folder icons created by Freepik - Flaticon</p>"
          + "<p><font size=-2>https://www.flaticon.com/free-icons/open-folder</p>"

          + "<p>Retail icons created by Freepik - Flaticon</p>"
          + "<p><font size=-2>https://www.flaticon.com/free-icons/retail</p>"

          + "<p>Member card icons created by POD Gladiator - Flaticon</p>"
          + "<p><font size=-2>https://www.flaticon.com/free-icons/member-card</font></p>"

          + "<p>Computer icons created by Firza Alamsyah - Flaticon</p>"
          + "<p><font size=-2>https://www.flaticon.com/free-icons/computer</font></p>"

          + "<p>Screen icons created by Those Icons - Flaticon</p>"
          + "<p><font size=-2>https://www.flaticon.com/free-icons/screen</font></p>"

          + "<p>Technology icons created by Freepik - Flaticon</p>"
          + "<p><font size=-2>https://www.flaticon.com/free-icons/technology</font></p>"
          
          + "<p>Online Order icons created by pisces86 - Flaticon</p>"
          + "<p><font size=-2>https://www.flaticon.com/free-icons/online-order</font></p>"
                    
          + "<p>Shopping Cart icons created by Kiranshastry - Flaticon</p>"
          + "<p><font size=-2>https://www.flaticon.com/free-icons/shopping-cart</font></p>"
          
          + "<p>Network icons created by alkhalifi design - Flaticon</p>"
          + "<p><font=-2>https://www.flaticon.com/free-icons/network</font></p>"
          + "</html>");
         JOptionPane.showMessageDialog(this, 
             new Object[]{title, artists},
             "ELSA",
             JOptionPane.PLAIN_MESSAGE
         );
     }
}

enum Record {
    CUSTOMER, OPTION,
    COMPUTER, ORDER
}