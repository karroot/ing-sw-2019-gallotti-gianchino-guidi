package it.polimi.deib.se2018.adrenalina.View.GUI;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Track;
import it.polimi.deib.se2018.adrenalina.View.GUI.square_components.SquareComponentGui;
import it.polimi.deib.se2018.adrenalina.View.Terminal;
import it.polimi.deib.se2018.adrenalina.View.TimerAFK;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This class implements the jframe that is the window with all the images and the information of the match
 * to show at the user
 * @author Cysko7927
 */
public class BoardGUI
{

    GUI terminal;
    //############
    private JFrame allBoardWindow = new JFrame("Adrenalina");//Frame per tutta la finestra
    private BorderLayout layoutAll = new BorderLayout();

    //Arena
    private JPanel arenaWindow = new JPanel();
    private SpringLayout layoutForArena = new SpringLayout();
    private JLabel arena = new JLabel();
    private SquareComponentGui[] allSquares = new SquareComponentGui[12];

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
    private JComboBox chosenBoard = new JComboBox();

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
    private final Object choiceSyn = new Object(); //Variable to sync the read of input after that the user clicked

    /**
     * Create The gui for the match and it shows at the user
     * @param terminal object terminal of the private view
     */
    public BoardGUI(Terminal terminal)
    {
        this.terminal = (GUI) terminal;

        //Create the labels for the damage Point
        for (int i = 0;i<damages.length;i++)
            damages[i] = new JLabel();

        //Set all layout
        allBoardWindow.setBounds(500,500,600,300);

        allBoardWindow.setLayout(layoutAll);
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
        ImageIcon im = new ImageIcon(getClass().getClassLoader().getResource("skulls.png"));
        panelSkulls.setIcon(im);

        panelPower.add(power1);
        panelPower.add(power2);
        panelPower.add(power3);

        //Add all square and set the component to represent the player on the arena
        arenaWindow.add(arena);
        arena.setLayout(layoutForArena);
        allSquares[0] = new SquareComponentGui(arena,329,13);
        allSquares[1] = new SquareComponentGui(arena,329,153);
        allSquares[2] = new SquareComponentGui(arena,329,317);
        allSquares[3] = new SquareComponentGui(arena,329,440);
        allSquares[4] = new SquareComponentGui(arena,180,13);
        allSquares[5] = new SquareComponentGui(arena,180,153);
        allSquares[6] = new SquareComponentGui(arena,180,317);
        allSquares[7] = new SquareComponentGui(arena,180,440);
        allSquares[8] = new SquareComponentGui(arena,50,13);
        allSquares[9] = new SquareComponentGui(arena,50,153);
        allSquares[10] = new SquareComponentGui(arena,50,317);
        allSquares[11] = new SquareComponentGui(arena,50,471);


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
        playerWindow.add(chosenBoard);
        chosenBoard.addActionListener(new ComboBoxSwitchBoards(this));

        //Add all damage Point

        createTheDamageTrack();

        //Add all damage point on skull track
        createSkullTrack();

        //Add all the skulls for player's Board
        createSkullScore();

        //

        allBoardWindow.setVisible(true);

    }


    /**
     * Method that inserted a text on input window to explain at the user
     * the choice
     * @param text text to print
     */
    public synchronized void addTextForInput(String text)
    {
        textToAsk = new JLabel(text);
        inputWindow.add(textToAsk);
        allBoardWindow.invalidate();
        allBoardWindow.validate();
        allBoardWindow.repaint();
    }

    /**
     * Method that inserted an option on input window through a bottom to click
     * @param text text to print on the bottom
     */
    public synchronized void addOptionForInput(String text)
    {
        JButton temp = new JButton(text);
        temp.addActionListener(new ClickInput(this)); //Add the action listener
        listOfAllOptions.add(temp);
        inputWindow.add(temp);
        allBoardWindow.invalidate();
        allBoardWindow.validate();
        allBoardWindow.repaint();
    }

    /**
     * Method that handle the request of input
     * The bottom become clickable and the user can choose an option
     * @return the integer that represent which bottom clicked the user
     */
    public synchronized int getInputChoice()
    {
        allBoardWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        allBoardWindow.invalidate();
        allBoardWindow.validate();
        allBoardWindow.repaint();

        onInput = true;

        TimerAFK.startTimer(Thread.currentThread());
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
                    onInput = false;
                    clearWindowInput(); //Clear the input window
                    allBoardWindow.invalidate();
                    allBoardWindow.validate();
                    allBoardWindow.repaint();
                    allBoardWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    JOptionPane.showMessageDialog(allBoardWindow, "Hai impiegato troppo tempo: turno saltato.");
                    Thread.currentThread().interrupt();
                    throw new ThreadDeath();
                }
            }


            TimerAFK.interruptTimer();

            int temp = choice; //Read the choice of the user
            choice = -1;
            clearWindowInput(); //Clear the input window
            allBoardWindow.invalidate();
            allBoardWindow.validate();
            allBoardWindow.repaint();
            allBoardWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            return temp;
        }

    }

    /**
     * Methods that shows the weapon that are in the spawn Points Yellow
     * @param text text with the name of all the weapons that are in the spawn Points Yellow
     */
    public void setWeaponPointY(String text)
    {
        weaponYPoint.setText("Armi nello spawn Point Giallo:"+text);
    }

    /**
     * Methods that shows the weapon that are in the spawn Points Red
     * @param text text with the name of all the weapons that are in the spawn Points Red
     */
    public void setWeaponPointR(String text)
    {
        weaponRPoint.setText("Armi nello spawn Point Rosso:"+text);
    }

    /**
     * Methods that shows the weapon that are in the spawn Points Blue
     * @param text text with the name of all the weapons that are in the spawn Points Blue
     */
    public void setWeaponPointB(String text)
    {
        weaponBPoint.setText("Armi nello spawn Point Blu:"+text);
    }


    /**
     * Methods that shows the ammo yellow of the player on the gui
     * @param text ammo yellow of the player
     */
    public void setAmmoY(String text)
    {
        ammoYellow.setText("Munizioni Gialle:"+text);
    }

    /**
     * Methods that shows the ammo blue of the player on the gui
     * @param text ammo blue of the player
     */
    public void setAmmoB(String text)
    {
        ammoBlue.setText("Munizioni Blu:"+text);
    }

    /**
     * Methods that shows the ammo red of the player on the gui
     * @param text ammo red of the player
     */
    public void setAmmoR(String text)
    {
        ammoRed.setText("Munizioni Rosse:"+text);
    }

    /**
     * Method that add an option on the list of all the boards that the user can see
     * @param player color of the player to add at the list
     */
    public void setBoardsPossible(String player)
    {
        chosenBoard.addItem(player);
    }

    /**
     * Remove all the options on the list of all the boards that the user can see
     */
    public void clearBoardsPossible()
    {
        chosenBoard.removeAllItems();
    }

    /**
     * Add the figure of a player on a square
     * @param indexOfSquare index that represents the square where to put the player
     * @param player colorId that represents the player to put
     */
    public void setPlayerOnSquare(int indexOfSquare,ColorId player)
    {
        allSquares[indexOfSquare].addPlayer(player);
    }

    /**
     * Methods that clears all the square on the Arena : All the player and the ammotiles icon will reset
     */
    public void clearAllSquares()
    {
        for (SquareComponentGui t:allSquares)
        {
            t.clearSquare();
        }
    }

    /**
     * Add the figure of a ammo tile on a square
     * @param indexOfSquare index that represents the square where to put the ammo tile
     * @param code code that represents the ammo tile to put
     */
    public void setAmmoTilesOnSquare(int indexOfSquare,int code)
    {
        allSquares[indexOfSquare].addAmmoTiles(code);
    }

    /**
     * Add the figure of a weapon card on the player's board
     * @param nameWeapon name of the weapon to add(If it is null the figure will not add)
     * @param index index where to put the weapon(0-2)
     * @param reloaded the weapon is loaded or not
     */
    public void setWeapon(String nameWeapon,int index,boolean reloaded)
    {
        ImageIcon imageIcon;

        //If there isn't a weapon
        if (nameWeapon == null)
            imageIcon = null;
        else if (!reloaded)//If the weapon is not reloaded
            imageIcon = new ImageIcon(getClass().getClassLoader().getResource("noreload.png"));
        else
            imageIcon = new ImageIcon(getClass().getClassLoader().getResource(nameWeapon+".png"));


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

    /**
     * Add the figure of a power up on the player's board
     * @param Pdu code that represents the power up (if Pdu is 0 the figure will not add)
     * @param index index where to put the power up(0-2)
     */
    public void setPower(int Pdu,int index)
    {
        ImageIcon imageIcon;


        if (Pdu == 0)
            imageIcon = null;
        else
        {
            imageIcon = new ImageIcon(getClass().getClassLoader().getResource("AD_powerups_IT_0" + Pdu+".png"));

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

    /**
     * Set the image of the player's board and it shows it at the user
     * @param colorId color of player's board
     */
    public void setPlayerBoard(ColorId colorId)
    {
        ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource("board"+colorId+".png"));
        playerBoard.setIcon(imageIcon);

    }

    /**
     * Method that shows the player's score on the windows
     * @param score player's score
     */
    public void setScore(int score)
    {
        panelScore.setText("Punteggio:"+score);
    }

    /**
     * Show the mark that the player has too
     * @param text string with all the marks
     */
    public void setTextMarker(String text)
    {
        textMarker.setText("Marchi che hai:"+text);
    }


    /**
     * Show the arena represented by the code
     * @param codeArena code of the arena
     */
    public void setArena(int codeArena)
    {
        arena.setIcon(new ImageIcon(getClass().getClassLoader().getResource(codeArena+".png")));
    }


    /**
     * Show the skull on the player's Board
     * @param deaths number of deaths during the match
     */
    public void setSkullForScore(int deaths)
    {
        ImageIcon im;
        im = new ImageIcon(getClass().getClassLoader().getResource("skull.png"));


        for (int i = 0;i < deaths && i < skullForScore.length;i++)
        {
            skullForScore[i].setIcon(im);
        }
    }


    /**
     * Flips the player's board for the frenzy mode
     * @param colorId color of player's board to flip
     */
    public void setFlipped(ColorId colorId)
    {
        playerBoard.setIcon(new ImageIcon(getClass().getClassLoader().getResource("boardF"+colorId+".png")));
    }


    /**
     * Show all the damage Point on player's board
     * @param damagesPoints number of damage points
     */
    public void setDamages(ColorId[] damagesPoints)
    {
        ImageIcon im;

        for (int i=0;i<damagesPoints.length;i++)
        {
            if (damagesPoints[i]==null)
                im = null;
             else
                im = new ImageIcon(getClass().getClassLoader().getResource(damagesPoints[i]+".png"));


            damages[i].setIcon(im);
        }

    }

    /**
     * Show the points on Skulls Track
     * @param SkullCounter number of Skull counter
     * @param tracks list of all the tracks on kill shoot track
     */
    public void setPanelSkulls(int SkullCounter, List<Track> tracks)
    {
        int j=0;
        for (int i = 8- SkullCounter; j<tracks.size();i++)
        {
            allSkulls[i].setIcon(new ImageIcon(getClass().getClassLoader().getResource(tracks.get(j).getPlayer()+".png")));
            j++;
        }
    }

    //GETTER and SETTER

    /**
     * Getter for all the input's button on input window
     * @return lis of all the input's button on input window
     */
    public List<JButton> getListOfAllOptions()
    {
        return new ArrayList<>(listOfAllOptions);
    }

    /**
     * Say if the input window is ready to receive an input(clicking button)
     * @return true if the input window is ready to receive an input
     */
    public boolean isOnInput()
    {
        return onInput;
    }

    /**
     * Method that it being used by class clickInput to set the integer that represents
     * which button clicked the player
     * @param choice integer that represents which button clicked the player
     */
    public void setChoice(int choice)
    {
        synchronized (choiceSyn)
        {
            this.choice = choice;
            choiceSyn.notifyAll();
        }
    }

    /**
     * Getter for the Jframe of the main window
     * @return Jframe of the main window
     */
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

    /**
     * Constructor for this event handler
     * @param boardGUI boardGui where there is the Jframe with all the information
     */
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
                    "Hai scelto:" + choice.getText()
                            + "\n Confermi?",
                    "Confermi scelta?",
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
                JOptionPane.showMessageDialog(boardGUI.getAllBoardWindow(), "Scegli un'altra opzione.'");
            }
        }

    }
}

/**
 * @author Cysko7927
 * This class implement an Event handler for the Combo box that permits
 * to switch between the various players' boards
 */
class ComboBoxSwitchBoards implements ActionListener
{

    BoardGUI gui;
    /**
     * Constructor for this event handler
     * @param gui boardGui where there is the Jframe with all the information
     */
    public ComboBoxSwitchBoards(BoardGUI gui)
    {
        this.gui = gui;
    }

    /**
     * Invoked when the user click on the combo box to chose a different player's board to see
     * @param e ActionEvent
     */
    public void actionPerformed(ActionEvent e)
    {
        JComboBox cb = (JComboBox)e.getSource();
        String choice = (String)cb.getSelectedItem();

        try
        {
            switch (choice)
            {
                case "Tua Plancia":
                    gui.terminal.changePlayerBoard(null);
                    break;
                case "Plancia Giocatore:BLUE":
                    gui.terminal.changePlayerBoard(ColorId.BLUE);
                    break;
                case "Plancia Giocatore:GREEN":
                    gui.terminal.changePlayerBoard(ColorId.GREEN);
                    break;
                case "Plancia Giocatore:GREY":
                    gui.terminal.changePlayerBoard(ColorId.GREY);
                    break;
                case "Plancia Giocatore:PURPLE":
                    gui.terminal.changePlayerBoard(ColorId.PURPLE);
                    break;
                case "Plancia Giocatore:YELLOW":
                    gui.terminal.changePlayerBoard(ColorId.YELLOW);
                    break;
            }
        }
        catch (NullPointerException ex)
        {

        }

    }

}


