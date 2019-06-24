package it.polimi.deib.se2018.adrenalina.View.GUI;

import it.polimi.deib.se2018.adrenalina.Controller.Setup;
import it.polimi.deib.se2018.adrenalina.View.AppClient;
import sun.security.krb5.internal.APOptions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    //Information inserted
    String nameW;
    String heroEffectW;
    String ipW;
    String portW;
    boolean guiW;
    String commW;

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


    public JFrame getSetupWindow() {
        return setupWindow;
    }

    public String getName()
    {
        return name.getText();
    }

    public String getHeroEffect()
    {
        return heroEffect.getText();
    }

    public String getIP()
    {
        return ip.getText();
    }

    public int getPort()
    {
        return Integer.parseInt(port.getText());
    }

    public boolean getGui()
    {
        return gui.isSelected();
    }

    public int getTechnology()
    {
        return commList.getSelectedIndex();
    }

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
     * @param e
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
