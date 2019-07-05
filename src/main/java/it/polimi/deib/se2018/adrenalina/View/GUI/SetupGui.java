package it.polimi.deib.se2018.adrenalina.View.GUI;

import it.polimi.deib.se2018.adrenalina.View.AppClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class implements a frame to ask at the user the inputs to connect at the server
 * and start the match
 * @author Cysko7927
 */
public class SetupGui
{
    //Main Window
    JFrame setupWindow = new JFrame("Adrenalina setup Client");
    SpringLayout layout = new SpringLayout();
    JLabel title = new JLabel("Inserisci le informazioni richieste"); //Main text
    //Component to ask name
    JLabel l1 = new JLabel("Nome:");
    JTextField name = new JTextField();
    //Component to ask hero-comment
    JLabel l2 = new JLabel("Frase d'effetto:");
    JTextField heroEffect = new JTextField();
    //Component to ask the address IPV4 of the server
    JLabel l3 = new JLabel("Indirizzo Ip:");
    JTextField ip = new JTextField();
    //Component to ask the Port tcp
    JLabel l4 = new JLabel("Porta TCP:");
    JTextField port = new JTextField();
    //Component to ask GUI
    JCheckBox gui = new JCheckBox("GUI");
    //Component to ask technology of communication
    String[] commStrings = { "Socket","RMI"};
    JComboBox commList = new JComboBox(commStrings);
    //Ok button
    JButton ok = new JButton("Ok");

    boolean ready;

    /**
     * Create the Gui for the  setup initial and it shows at the user
     */
    public SetupGui()
    {
        ready = false;
        setupWindow.getContentPane().setLayout(layout);
        name.setColumns(20);
        heroEffect.setColumns(20);
        ip.setColumns(15);
        port.setColumns(6);
        commList.setSelectedIndex(0);
        setupWindow.setBounds(500,500,600,300);

        setupWindow.add(title);
        setupWindow.getContentPane().add(l1);
        setupWindow.getContentPane().add(name);
        setupWindow.getContentPane().add(l2);
        setupWindow.getContentPane().add(heroEffect);
        setupWindow.getContentPane().add(l3);
        setupWindow.getContentPane().add(ip);
        setupWindow.getContentPane().add(l4);
        setupWindow.getContentPane().add(port);
        setupWindow.getContentPane().add(gui);
        setupWindow.getContentPane().add(commList);
        setupWindow.getContentPane().add(ok);


        layout.putConstraint(SpringLayout.NORTH,name,10,SpringLayout.SOUTH,title);
        layout.putConstraint(SpringLayout.NORTH,l1,10,SpringLayout.SOUTH,title);
        layout.putConstraint(SpringLayout.WEST, name,  5,SpringLayout.EAST, l1);

        layout.putConstraint(SpringLayout.NORTH,heroEffect,5,SpringLayout.SOUTH,name);
        layout.putConstraint(SpringLayout.NORTH,l2,10,SpringLayout.SOUTH,l1);
        layout.putConstraint(SpringLayout.WEST, heroEffect,  5,SpringLayout.EAST, l2);

        layout.putConstraint(SpringLayout.NORTH,ip,5,SpringLayout.SOUTH,heroEffect);
        layout.putConstraint(SpringLayout.NORTH,l3,10,SpringLayout.SOUTH,l2);
        layout.putConstraint(SpringLayout.WEST, ip,  5,SpringLayout.EAST, l3);

        layout.putConstraint(SpringLayout.NORTH,port,5,SpringLayout.SOUTH,ip);
        layout.putConstraint(SpringLayout.NORTH,l4,10,SpringLayout.SOUTH,l3);
        layout.putConstraint(SpringLayout.WEST, port,  5,SpringLayout.EAST, l4);

        layout.putConstraint(SpringLayout.NORTH,gui,10,SpringLayout.SOUTH,l4);

        layout.putConstraint(SpringLayout.NORTH,commList,10,SpringLayout.SOUTH,gui);
        layout.putConstraint(SpringLayout.NORTH,ok,15,SpringLayout.SOUTH,commList);

        ok.addActionListener(new ClickOk(this));

        setupWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setupWindow.setVisible(true);
    }

    /**
     * Create the Gui to ask the reconnection and it shows at the user
     * @param nameS name used by user during the match
     * @param heroEffectS hero comment used by user during the match
     */
    public SetupGui(String nameS,String heroEffectS)
    {
        ready = false;
        setupWindow.getContentPane().setLayout(layout);
        title.setText("Reinserisci le informazioni del server");
        name.setColumns(20);
        name.setText(nameS);
        heroEffect.setColumns(20);
        heroEffect.setText(heroEffectS);
        ip.setColumns(15);
        port.setColumns(6);
        setupWindow.setBounds(500,500,600,300);

        setupWindow.add(title);
        setupWindow.getContentPane().add(l1);
        setupWindow.getContentPane().add(name);
        setupWindow.getContentPane().add(l2);
        setupWindow.getContentPane().add(heroEffect);
        setupWindow.getContentPane().add(l3);
        setupWindow.getContentPane().add(ip);
        setupWindow.getContentPane().add(l4);
        setupWindow.getContentPane().add(port);
        setupWindow.getContentPane().add(ok);


        layout.putConstraint(SpringLayout.NORTH,name,10,SpringLayout.SOUTH,title);
        layout.putConstraint(SpringLayout.NORTH,l1,10,SpringLayout.SOUTH,title);
        layout.putConstraint(SpringLayout.WEST, name,  5,SpringLayout.EAST, l1);

        layout.putConstraint(SpringLayout.NORTH,heroEffect,5,SpringLayout.SOUTH,name);
        layout.putConstraint(SpringLayout.NORTH,l2,10,SpringLayout.SOUTH,l1);
        layout.putConstraint(SpringLayout.WEST, heroEffect,  5,SpringLayout.EAST, l2);

        layout.putConstraint(SpringLayout.NORTH,ip,5,SpringLayout.SOUTH,heroEffect);
        layout.putConstraint(SpringLayout.NORTH,l3,10,SpringLayout.SOUTH,l2);
        layout.putConstraint(SpringLayout.WEST, ip,  5,SpringLayout.EAST, l3);

        layout.putConstraint(SpringLayout.NORTH,port,5,SpringLayout.SOUTH,ip);
        layout.putConstraint(SpringLayout.NORTH,l4,10,SpringLayout.SOUTH,l3);
        layout.putConstraint(SpringLayout.WEST, port,  5,SpringLayout.EAST, l4);

        layout.putConstraint(SpringLayout.NORTH,ok,15,SpringLayout.SOUTH,commList);

        ok.addActionListener(new ClickOk(this));

        setupWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setupWindow.setVisible(true);
    }

    //Getter


    /**
     * Getter for the jframe of the window
     * @return jframe of the window
     */
    public JFrame getSetupWindow() {
        return setupWindow;
    }

    /**
     * Getter for the name inserted by user
     * @return name inserted by user
     */
    public String getName()
    {
        return name.getText();
    }

    /**
     * Getter for the hero comment inserted by user
     * @return hero comment inserted by user
     */
    public String getHeroEffect()
    {
        return heroEffect.getText();
    }

    /**
     * Getter for the ip address inserted by user
     * @return ip address inserted by user
     */
    public String getIP()
    {
        return ip.getText();
    }

    /**
     * Getter for the port TCP inserted by user
     * @return port TCP inserted by user
     */
    public int getPort()
    {
        return Integer.parseInt(port.getText());
    }

    /**
     * Say if the user chose to use the gui or not
     * @return true if the user chose to use the gui
     */
    public boolean getGui()
    {
        return gui.isSelected();
    }

    /**
     * Getter for technology communication chosen by user
     * @return technology communication chosen by user
     */
    public int getTechnology()
    {
        return commList.getSelectedIndex();
    }

    /**
     * Say if the information was inserted and are correct
     * @return true if the information was inserted and are correct
     */
    public boolean isReady()
    {
        return ready;
    }
}

class ClickOk implements ActionListener
{

    SetupGui setupGui;

    public ClickOk(SetupGui setupGui)
    {
        this.setupGui = setupGui;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String error = "Errore nei seguenti campi:\n";
        boolean thereAreErrors = false;

        if (setupGui.getName().equals(""))
        {
            thereAreErrors = true;
            error = error + "Nome: Stringa vuota non accettata\n";
        }
        if (setupGui.getHeroEffect().equals(""))
        {
            thereAreErrors = true;
            error = error + "Frase d'effetto: Stringa vuota non accettata\n";
        }
        if (!validIP(setupGui.getIP()))
        {
            thereAreErrors = true;
            error = error + "IP: Non hai inserito un IP\n";
        }
        if (!isNumeric(setupGui.port.getText()))
        {
            thereAreErrors = true;
            error = error + "Porta: Non hai inserito un numero di porta\n";
        }

        if (thereAreErrors)
        {
            JOptionPane.showMessageDialog(setupGui.getSetupWindow(), error);
        }
        else
        {
            setupGui.ready = true;
            setupGui.getSetupWindow().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            setupGui.getSetupWindow().dispose();

            synchronized (AppClient.syncSetup)
            {
                AppClient.syncSetup.notifyAll(); //notify that the information are ready
            }

        }



    }

    /**
     * Method that check if in the string there is written
     * an address IPv4
     * @param ip string to check
     * @return true if the string is an address IPv4
     */
    public  boolean validIP (String ip) {
        try {
            if ( ip == null || ip.isEmpty() ) {
                return false;
            }

            String[] parts = ip.split( "\\." );
            if ( parts.length != 4 ) {
                return false;
            }

            for ( String s : parts ) {
                int i = Integer.parseInt( s );
                if ( (i < 0) || (i > 255) ) {
                    return false;
                }
            }
            if ( ip.endsWith(".") ) {
                return false;
            }

            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Method that check if in the string there is written
     * an number
     * @param str string to check
     * @return true if the string is number
     */
    public  boolean isNumeric(String str)
    {
        if (str.equals(""))
            return false;

        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
