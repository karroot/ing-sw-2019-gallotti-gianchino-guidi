package it.polimi.deib.se2018.adrenalina.View.GUI;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Track;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BoardGUI
{

    //############
    private JFrame allBoardWindow = new JFrame("Adrenalina");//Frame per tutta la finestra
    private BorderLayout layoutAll = new BorderLayout();

    //Arena Grid
    private JPanel arenaWindow = new JPanel();
    private GridLayout layoutForArena = new GridLayout(3,4);
    //All square
    JButton x1y3 = new JButton();
    JButton x2y3 = new JButton();
    JButton x3y3 = new JButton();
    JButton x4y3 = new JButton();
    JButton x1y2 = new JButton();
    JButton x2y2 = new JButton();
    JButton x3y2 = new JButton();
    JButton x4y2 = new JButton();
    JButton x1y1 = new JButton();
    JButton x2y1 = new JButton();
    JButton x3y1 = new JButton();
    JButton x4y1 = new JButton();

    //Pannel to Ask the request of input
    private JPanel inputWindow = new JPanel();
    private BoxLayout layoutForInput = new BoxLayout(inputWindow,BoxLayout.Y_AXIS);

    //Elements of the player's Board
    private JPanel playerWindow = new JPanel(); //Has the flowLayout for default
    private JLabel textMarker = new JLabel(); //This text say the marks that the player has too
    private JLabel[] damages = new JLabel[12];
    private JLabel[] skullForScore = new JLabel[6]; //Represents the skulls on the player's board
    private JLabel playerBoard ; //Show the player's board
    private JLabel weapon1 = new JLabel();//All weapons and powerUp of the users
    private JLabel weapon2 = new JLabel();
    private JLabel weapon3 = new JLabel();
    private SpringLayout layoutForPlayerBoard = new SpringLayout();

    //Elements that are in the center of the window
    private JPanel centerWindow = new JPanel();
    private BoxLayout layoutForCenterWindow = new BoxLayout(centerWindow,BoxLayout.Y_AXIS);
    private JPanel panelPower = new JPanel(); //Window that shows the powerUp of the player
    private JLabel weaponYPoint = new JLabel();
    private JLabel weaponBPoint = new JLabel();
    private JLabel weaponRPoint = new JLabel();
    private JLabel panelSkulls = new JLabel();//Window that shows the skull track
    private SpringLayout layoutForPanelSkulls = new SpringLayout();
    private JLabel[] allSkulls = new JLabel[8]; //All the skulls on the arena
    private JLabel panelScore = new JLabel();//Window that shows the Score of the player
    private JLabel ammoRed = new JLabel();//Component to show the ammo
    private JLabel ammoBlue = new JLabel();
    private JLabel ammoYellow = new JLabel();
    private JLabel power1 = new JLabel();//Container for all the powerUP
    private JLabel power2 = new JLabel();
    private JLabel power3 = new JLabel();

    //Elements of the inputs
    private JLabel textToAsk;
    private List<JButton> listOfAllOptions = new LinkedList<>();
    private boolean onInput = false;
    private int choice = -1; //represent the choice made by user(Default = -1 => The user didn't click on a button)
    Object choiceSyn = new Object(); //Variable to sync the read of input after that the user clicked

    public BoardGUI()
    {
        //Create the labels for the damage Point
        for (int i = 0;i<damages.length;i++)
            damages[i] = new JLabel();

        //Set all layout
        allBoardWindow.setBounds(500,500,600,300);

        allBoardWindow.setLayout(layoutAll);
        arenaWindow.setLayout(layoutForArena);
        inputWindow.setLayout(layoutForInput);
        centerWindow.setLayout(layoutForCenterWindow);
        panelSkulls.setLayout(layoutForPanelSkulls);

        allBoardWindow.add(arenaWindow,BorderLayout.LINE_START);
        allBoardWindow.add(centerWindow,BorderLayout.CENTER);
        allBoardWindow.add(inputWindow,BorderLayout.EAST);
        allBoardWindow.add(playerWindow,BorderLayout.PAGE_END);

        //Add all center component
        centerWindow.add(panelPower);
        centerWindow.add(weaponYPoint);
        centerWindow.add(weaponRPoint);
        centerWindow.add(weaponBPoint);
        centerWindow.add(panelSkulls);
        centerWindow.add(ammoRed);
        centerWindow.add(ammoBlue);
        centerWindow.add(ammoYellow);
        centerWindow.add(panelScore);

        //Load the image of the skulls
        ImageIcon im = new ImageIcon("C:\\Users\\Cysko7927\\IdeaProjects\\ing-sw-2019-gallotti-gianchino-guidi\\src\\main\\java\\it\\polimi\\deib\\se2018\\adrenalina\\View\\Asset\\skulls.png");
        panelSkulls.setIcon(im);

        panelPower.add(power1);
        panelPower.add(power2);
        panelPower.add(power3);

        //Add all square
        arenaWindow.add(x1y3);
        arenaWindow.add(x2y3);
        arenaWindow.add(x3y3);
        arenaWindow.add(x4y3);
        arenaWindow.add(x1y2);
        arenaWindow.add(x2y2);
        arenaWindow.add(x3y2);
        arenaWindow.add(x4y2);
        arenaWindow.add(x1y1);
        arenaWindow.add(x2y1);
        arenaWindow.add(x3y1);
        arenaWindow.add(x4y1);

        //Add player's Board
        playerBoard = new JLabel();


        playerWindow.add(playerBoard);
        playerBoard.setLayout(layoutForPlayerBoard);
        textMarker.setForeground(Color.RED);
        centerWindow.add(textMarker);

        //Add all component for the weapons and the powerUps
        playerWindow.add(weapon1);
        playerWindow.add(weapon2);
        playerWindow.add(weapon3);

        //Add all damage Point

        createTheDamageTrack();

        //Add all damage point on skull track
        createSkullTrack();

        //Add all the skulls for player's Board
        createSkullScore();


        allBoardWindow.setVisible(true);
    }

    //Methods to ask the input at the user
    public void addTextForInput(String text)
    {
        allBoardWindow.setVisible(false);
        textToAsk = new JLabel(text);
        inputWindow.add(textToAsk);
        allBoardWindow.setVisible(true);
    }

    public void addOptionForInput(String text)
    {
        allBoardWindow.setVisible(false);
        JButton temp = new JButton(text);
        temp.addActionListener(new ClickInput(this)); //Add the action listener
        listOfAllOptions.add(temp);
        inputWindow.add(temp);
        allBoardWindow.setVisible(true);
    }

    public int getInputChoice()
    {
        allBoardWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        onInput = true;
        synchronized (choiceSyn)
        {
            while (choice == -1)
            {
                try
                {
                    choiceSyn.wait();
                }
                catch (InterruptedException e)
                {

                }
            }

            int temp = choice; //Read the choice of the user
            choice = -1;
            allBoardWindow.setVisible(false);
            clearWindowInput(); //Clear the input window
            allBoardWindow.setVisible(true);
            allBoardWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            return temp;
        }

    }

    //Methods that shows the weapon that are in the spawn Points
    public void setWeaponPointY(String text)
    {
        weaponYPoint.setText("Armi nello spawn Point Giallo:"+text);
    }

    public void setWeaponPointR(String text)
    {
        weaponRPoint.setText("Armi nello spawn Point Rosso:"+text);
    }

    public void setWeaponPointB(String text)
    {
        weaponBPoint.setText("Armi nello spawn Point Blu:"+text);
    }

    //Methods that shows the ammo of the player
    public void setAmmoY(String text)
    {
        ammoYellow.setText("Munizioni Gialle:"+text);
    }

    public void setAmmoB(String text)
    {
        ammoBlue.setText("Munizioni Blu:"+text);
    }

    public void setAmmoR(String text)
    {
        ammoRed.setText("Munizioni Rosse:"+text);
    }


    //Method that handles the weapon showed
    public void setWeapon(String nameWeapon,int index,boolean reloaded)
    {
        ImageIcon imageIcon;

        //If there isn't a weapon
        if (nameWeapon == null)
            imageIcon = null;
        else if (!reloaded)//If the weapon is not reloaded
            imageIcon = new ImageIcon("C:\\Users\\Cysko7927\\IdeaProjects\\ing-sw-2019-gallotti-gianchino-guidi\\src\\main\\java\\it\\polimi\\deib\\se2018\\adrenalina\\View\\Asset\\noreload.png");
        else
            imageIcon = new ImageIcon("C:\\Users\\Cysko7927\\IdeaProjects\\ing-sw-2019-gallotti-gianchino-guidi\\src\\main\\java\\it\\polimi\\deib\\se2018\\adrenalina\\View\\Asset\\"+nameWeapon+".png");


        switch (index)
        {
            case 1:
                weapon1.setIcon(imageIcon);
                weapon1.setSize(new Dimension(10,10));
                return;
            case 2:
                weapon2.setIcon(imageIcon);
                weapon2.setSize(new Dimension(10,10));
                return;
            case 3:
                weapon3.setIcon(imageIcon);
                weapon3.setSize(new Dimension(10,10));
        }
    }

    //Method that handles the powerUp showed
    public void setPower(int Pdu,int index)
    {
        ImageIcon imageIcon;


        if (Pdu == 0)
            imageIcon = null;
        else
        {
            imageIcon = new ImageIcon("C:\\Users\\Cysko7927\\IdeaProjects\\ing-sw-2019-gallotti-gianchino-guidi\\src\\main\\java\\it\\polimi\\deib\\se2018\\adrenalina\\View\\Asset\\AD_powerups_IT_0" + Pdu+".png");

        }


        switch (index)
        {
            case 1:
                power1.setIcon(imageIcon);
                return;
            case 2:
                power2.setIcon(imageIcon);
                return;
            case 3:
                power3.setIcon(imageIcon);
                return;
            default:

        }
    }

    //Method that shows the player's board
    public void setPlayerBoard(ColorId colorId)
    {
        ImageIcon imageIcon = new ImageIcon(("C:\\Users\\Cysko7927\\IdeaProjects\\ing-sw-2019-gallotti-gianchino-guidi\\src\\main\\java\\it\\polimi\\deib\\se2018\\adrenalina\\View\\Asset\\board"+colorId+".PNG"));
        playerBoard.setIcon(imageIcon);

    }

    //Method that shows the player's score
    public void setScore(int score)
    {
        panelScore.setText("Punteggio:"+score);
    }

    //Show the mark that the player has too
    public void setTextMarker(String text)
    {
        textMarker.setText("Marchi che hai:"+text);
    }

    //Show the arena represented by the code
    public void setArena(int codeArena)//todo implementarlo
    {
        switch (codeArena)
        {
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
        }
    }

    //Show the skull on the player's Board
    public void setSkullForScore(int deaths)
    {
        ImageIcon im;
        im = new ImageIcon("C:\\Users\\Cysko7927\\IdeaProjects\\ing-sw-2019-gallotti-gianchino-guidi\\src\\main\\java\\it\\polimi\\deib\\se2018\\adrenalina\\View\\Asset\\skull.png");


        for (int i = 0;i < deaths && i < skullForScore.length;i++)
        {
            skullForScore[i].setIcon(im);
        }
    }


    //Show all the damage Point on player's board
    public void setDamages(ColorId[] damagesPoints)
    {
        ImageIcon im;

        for (int i=0;i<damagesPoints.length;i++)
        {
            if (damagesPoints[i]==null)
                im = null;
             else
                im = new ImageIcon("C:\\Users\\Cysko7927\\IdeaProjects\\ing-sw-2019-gallotti-gianchino-guidi\\src\\main\\java\\it\\polimi\\deib\\se2018\\adrenalina\\View\\Asset\\"+damagesPoints[i]+".png");


            damages[i].setIcon(im);
        }

    }
    //Show the points on Skulls Track
    public void setPanelSkulls(int SkullCounter, List<Track> tracks)
    {
        int j=0;
        for (int i = 8- SkullCounter; j<tracks.size();i++)
        {
            allSkulls[i].setIcon(new ImageIcon("C:\\Users\\Cysko7927\\IdeaProjects\\ing-sw-2019-gallotti-gianchino-guidi\\src\\main\\java\\it\\polimi\\deib\\se2018\\adrenalina\\View\\Asset\\"+tracks.get(j).getPlayer()+".png"));
            j++;
        }
    }

    //GETTER and SETTER
    public List<JButton> getListOfAllOptions()
    {
        return new ArrayList<>(listOfAllOptions);
    }

    public boolean isOnInput()
    {
        return onInput;
    }

    public void setChoice(int choice)
    {
        synchronized (choiceSyn)
        {
            this.choice = choice;
            choiceSyn.notifyAll();
        }
    }

    public JFrame getAllBoardWindow() {
        return allBoardWindow;
    }

    //Private Method

    //Clear all the input window of the buttons and the string of text
    private void clearWindowInput()
    {
        for (JButton t:listOfAllOptions)
        {
            inputWindow.remove(t);
        }

        listOfAllOptions.clear();

        inputWindow.remove(textToAsk);
        onInput = false;

    }

    private void createTheDamageTrack()
    {

        playerBoard.add(damages[0]);
        layoutForPlayerBoard.putConstraint(SpringLayout.NORTH,damages[0],65,SpringLayout.NORTH,playerBoard);
        layoutForPlayerBoard.putConstraint(SpringLayout.WEST,damages[0],60,SpringLayout.NORTH,playerBoard);

        playerBoard.add(damages[1]);
        layoutForPlayerBoard.putConstraint(SpringLayout.WEST,damages[1],17,SpringLayout.EAST,damages[0]);
        layoutForPlayerBoard.putConstraint(SpringLayout.NORTH,damages[1],65,SpringLayout.NORTH,playerBoard);

        playerBoard.add(damages[2]);
        layoutForPlayerBoard.putConstraint(SpringLayout.WEST,damages[2],17,SpringLayout.EAST,damages[1]);
        layoutForPlayerBoard.putConstraint(SpringLayout.NORTH,damages[2],65,SpringLayout.NORTH,playerBoard);


        playerBoard.add(damages[3]);
        layoutForPlayerBoard.putConstraint(SpringLayout.WEST,damages[3],13,SpringLayout.EAST,damages[2]);
        layoutForPlayerBoard.putConstraint(SpringLayout.NORTH,damages[3],65,SpringLayout.NORTH,playerBoard);

        playerBoard.add(damages[4]);
        layoutForPlayerBoard.putConstraint(SpringLayout.WEST,damages[4],14,SpringLayout.EAST,damages[3]);
        layoutForPlayerBoard.putConstraint(SpringLayout.NORTH,damages[4],65,SpringLayout.NORTH,playerBoard);

        playerBoard.add(damages[5]);
        layoutForPlayerBoard.putConstraint(SpringLayout.WEST,damages[5],18,SpringLayout.EAST,damages[4]);
        layoutForPlayerBoard.putConstraint(SpringLayout.NORTH,damages[5],65,SpringLayout.NORTH,playerBoard);

        playerBoard.add(damages[6]);
        layoutForPlayerBoard.putConstraint(SpringLayout.WEST,damages[6],15,SpringLayout.EAST,damages[5]);
        layoutForPlayerBoard.putConstraint(SpringLayout.NORTH,damages[6],65,SpringLayout.NORTH,playerBoard);

        playerBoard.add(damages[7]);
        layoutForPlayerBoard.putConstraint(SpringLayout.WEST,damages[7],13,SpringLayout.EAST,damages[6]);
        layoutForPlayerBoard.putConstraint(SpringLayout.NORTH,damages[7],65,SpringLayout.NORTH,playerBoard);

        playerBoard.add(damages[8]);
        layoutForPlayerBoard.putConstraint(SpringLayout.WEST,damages[8],17,SpringLayout.EAST,damages[7]);
        layoutForPlayerBoard.putConstraint(SpringLayout.NORTH,damages[8],65,SpringLayout.NORTH,playerBoard);

        playerBoard.add(damages[9]);
        layoutForPlayerBoard.putConstraint(SpringLayout.WEST,damages[9],13,SpringLayout.EAST,damages[8]);
        layoutForPlayerBoard.putConstraint(SpringLayout.NORTH,damages[9],65,SpringLayout.NORTH,playerBoard);

        playerBoard.add(damages[10]);
        layoutForPlayerBoard.putConstraint(SpringLayout.WEST,damages[10],24,SpringLayout.EAST,damages[9]);
        layoutForPlayerBoard.putConstraint(SpringLayout.NORTH,damages[10],65,SpringLayout.NORTH,playerBoard);

        playerBoard.add(damages[11]);
        layoutForPlayerBoard.putConstraint(SpringLayout.WEST,damages[11],17,SpringLayout.EAST,damages[10]);
        layoutForPlayerBoard.putConstraint(SpringLayout.NORTH,damages[11],65,SpringLayout.NORTH,playerBoard);


    }

    private void createSkullTrack()
    {

        for (int i = 0;i<allSkulls.length;i++)
            allSkulls[i] = new JLabel();

        panelSkulls.add(allSkulls[0]);
        layoutForPanelSkulls.putConstraint(SpringLayout.NORTH,allSkulls[0],15,SpringLayout.NORTH,panelSkulls);
        layoutForPanelSkulls.putConstraint(SpringLayout.WEST,allSkulls[0],10,SpringLayout.WEST,panelSkulls);

        panelSkulls.add(allSkulls[1]);
        layoutForPanelSkulls.putConstraint(SpringLayout.NORTH,allSkulls[1],15,SpringLayout.NORTH,panelSkulls);
        layoutForPanelSkulls.putConstraint(SpringLayout.WEST,allSkulls[1],45,SpringLayout.WEST,panelSkulls);

        panelSkulls.add(allSkulls[2]);
        layoutForPanelSkulls.putConstraint(SpringLayout.NORTH,allSkulls[2],15,SpringLayout.NORTH,panelSkulls);
        layoutForPanelSkulls.putConstraint(SpringLayout.WEST,allSkulls[2],80,SpringLayout.WEST,panelSkulls);

        panelSkulls.add(allSkulls[3]);
        layoutForPanelSkulls.putConstraint(SpringLayout.NORTH,allSkulls[3],15,SpringLayout.NORTH,panelSkulls);
        layoutForPanelSkulls.putConstraint(SpringLayout.WEST,allSkulls[3],115,SpringLayout.WEST,panelSkulls);

        panelSkulls.add(allSkulls[4]);
        layoutForPanelSkulls.putConstraint(SpringLayout.NORTH,allSkulls[4],15,SpringLayout.NORTH,panelSkulls);
        layoutForPanelSkulls.putConstraint(SpringLayout.WEST,allSkulls[4],150,SpringLayout.WEST,panelSkulls);

        panelSkulls.add(allSkulls[5]);
        layoutForPanelSkulls.putConstraint(SpringLayout.NORTH,allSkulls[5],15,SpringLayout.NORTH,panelSkulls);
        layoutForPanelSkulls.putConstraint(SpringLayout.WEST,allSkulls[5],185,SpringLayout.WEST,panelSkulls);

        panelSkulls.add(allSkulls[6]);
        layoutForPanelSkulls.putConstraint(SpringLayout.NORTH,allSkulls[6],15,SpringLayout.NORTH,panelSkulls);
        layoutForPanelSkulls.putConstraint(SpringLayout.WEST,allSkulls[6],220,SpringLayout.WEST,panelSkulls);

        panelSkulls.add(allSkulls[7]);
        layoutForPanelSkulls.putConstraint(SpringLayout.NORTH,allSkulls[7],15,SpringLayout.NORTH,panelSkulls);
        layoutForPanelSkulls.putConstraint(SpringLayout.WEST,allSkulls[7],255,SpringLayout.WEST,panelSkulls);
    }


    private void createSkullScore()
    {
        for (int i = 0;i<skullForScore.length;i++)
            skullForScore[i] = new JLabel();

        playerBoard.add(skullForScore[0]);
        layoutForPlayerBoard.putConstraint(SpringLayout.NORTH,skullForScore[0],112,SpringLayout.NORTH,playerBoard);
        layoutForPlayerBoard.putConstraint(SpringLayout.WEST,skullForScore[0],135,SpringLayout.NORTH,playerBoard);

        playerBoard.add(skullForScore[1]);
        layoutForPlayerBoard.putConstraint(SpringLayout.NORTH,skullForScore[1],112,SpringLayout.NORTH,playerBoard);
        layoutForPlayerBoard.putConstraint(SpringLayout.WEST,skullForScore[1],10,SpringLayout.EAST,skullForScore[0]);

        playerBoard.add(skullForScore[2]);
        layoutForPlayerBoard.putConstraint(SpringLayout.NORTH,skullForScore[2],112,SpringLayout.NORTH,playerBoard);
        layoutForPlayerBoard.putConstraint(SpringLayout.WEST,skullForScore[2],10,SpringLayout.EAST,skullForScore[1]);

        playerBoard.add(skullForScore[3]);
        layoutForPlayerBoard.putConstraint(SpringLayout.NORTH,skullForScore[3],112,SpringLayout.NORTH,playerBoard);
        layoutForPlayerBoard.putConstraint(SpringLayout.WEST,skullForScore[3],10,SpringLayout.EAST,skullForScore[2]);

        playerBoard.add(skullForScore[4]);
        layoutForPlayerBoard.putConstraint(SpringLayout.NORTH,skullForScore[4],112,SpringLayout.NORTH,playerBoard);
        layoutForPlayerBoard.putConstraint(SpringLayout.WEST,skullForScore[4],10,SpringLayout.EAST,skullForScore[3]);

        playerBoard.add(skullForScore[5]);
        layoutForPlayerBoard.putConstraint(SpringLayout.NORTH,skullForScore[5],112,SpringLayout.NORTH,playerBoard);
        layoutForPlayerBoard.putConstraint(SpringLayout.WEST,skullForScore[5],10,SpringLayout.EAST,skullForScore[4]);
    }
}

/**
 * @author Cysko7927
 * Handle the click action of the user on the buttons that represens the possible choices asked
 * It being used by component inputWindow of the BoardGui
 */
class ClickInput implements ActionListener
{

    private BoardGUI boardGUI;

    public ClickInput(BoardGUI boardGUI)
    {
        this.boardGUI = boardGUI;
    }

    /**
     * Invoked when the user click on a button to chose an input asked.
     * Ask at user to confirm the choice before to continue
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        JButton choice;
        String[] options = {"Yes", "No"};

        if (boardGUI.isOnInput())
        {
            choice = (JButton) e.getSource();

            //Create
            int n = JOptionPane.showOptionDialog(boardGUI.getAllBoardWindow(),
                    "Hai Scelto:" + choice.getText()
                            + "\n Confermi?",
                    "Confermi Scelta?",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);

            if (n == 0)
            {
                int intChoice = boardGUI.getListOfAllOptions().indexOf(choice);
                boardGUI.setChoice(intChoice + 1);
            }
            else
            {
                JOptionPane.showMessageDialog(boardGUI.getAllBoardWindow(), "Scegli un altra opzione");
            }
        }

    }
}
