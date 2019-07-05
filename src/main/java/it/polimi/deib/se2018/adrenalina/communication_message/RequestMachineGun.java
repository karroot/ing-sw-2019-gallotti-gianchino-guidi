package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.util.LinkedList;
import java.util.List;
/**
 *  This class represents the request that the conroller sends at the client if the player decided
 *  to use the weapon MachineGun
 * @author Karroot
 */
public class RequestMachineGun extends RequestInput {
    //Attribute for the request
    private List<ColorId> playersBasicMode;//target for the basic mode
    private List<ColorId> playersAdditionalMode;//target for the basic mode
    private List<ColorId> playerSecondAdditionalMode;//target for the second additional mode


    //Attribute for the response
    private ColorId targetBasicModeSecond;//second target chosen for the basic mode
    private ColorId targetAdditionalMode;//target chosen for the first additional mode
    private ColorId targetSecondAdditionalMode;//target chosen for the second additional mode
    private ColorId targetSecondAdditionalModeSecond;//second target chosen for the second additional mode
    
    boolean[] availableMethod = new boolean[3];
    protected String nameAdditionalmode; //Name of the alternative mode
    protected String nameSecondAdditionalmode; //Name of the alternative mode
    //Attribute for the response
    protected boolean mode; //Represent if the user choices the basic mode(false) or the alternative mode(true)
    protected boolean secondMode; //Represent if the user choices the basic mode(false) or the alternative mode(true)
    private boolean addDamage;

    //Attribute for the response
    protected ColorId targetBasicMode;//Target chosen for the basic mode


    /**
     * Create a message of request for the weapon MachineGun
     * @param availableMethod mode available
     * @param playerBasicMode target for the basic mode
     * @param playerAdditionalMode target for the alternative mode
     * @param playerSecondAdditionalMode target for additional mode
     */
    public RequestMachineGun(boolean[] availableMethod, List<ColorId> playerBasicMode, List<ColorId> playerAdditionalMode,List<ColorId> playerSecondAdditionalMode)
    {
        this.nameAdditionalmode = "modalità colpo focalizzato";
        this.nameSecondAdditionalmode = "modalità tripode di supporto";
        this.availableMethod = availableMethod;
        this.playersBasicMode = playerBasicMode;
        this.playersAdditionalMode = playerAdditionalMode;
        this.playerSecondAdditionalMode = playerSecondAdditionalMode;
        responseIsReady = false;
    }

    /**
     * this method will print all the available actions and ask the player to respond to requests
     * @param terminal terminal that will print the text and the option input at the user
     */
    @Override
    public void printActionsAndReceiveInput(Terminal terminal) { this.terminal=terminal;
        int choice = 0; //Da completare
        List<Integer> acceptedInt;
        acceptedInt = new LinkedList<>();

        terminal.addTextInput("Scegli modalità Arma:");

        if (availableMethod[0])//Print the possible effects
        {

            terminal.addOptionInput("1:modalità base da sola");
            int w=0;
            acceptedInt.add(1);
        }
        if (availableMethod[1])//Print the possible effects
        {
            terminal.addOptionInput("2: modalità base con  "+ nameAdditionalmode);
            acceptedInt.add(1);
        }
        if (availableMethod[2])
        {
            terminal.addOptionInput("3: modalità base con  " + nameSecondAdditionalmode);
            acceptedInt.add(1);
        }
        if (availableMethod[3])//Print the possible effects
        {
            terminal.addOptionInput("4: modalità base con  "+ nameAdditionalmode + " e " + nameSecondAdditionalmode);
            acceptedInt.add(1);
        }

        int j = 0;
        List<Integer> oneAndThree = new LinkedList<>();
        oneAndThree.add(1);
        oneAndThree.add(3);
        //Handle the possible choice of the users asking the correct inputs
        if (availableMethod[0] && availableMethod[1] && availableMethod[2])
            choice = terminal.inputInt(1, 4);
        if (availableMethod[0] && availableMethod[1] && !availableMethod[2])
            choice = terminal.inputInt(1, 2);
        if (availableMethod[0] && !availableMethod[1] && availableMethod[2])
            choice = terminal.inputInt(1, 2);
        if (availableMethod[0] && !availableMethod[1] && !availableMethod[2])
            choice = terminal.inputInt(oneAndThree);

        mode = false; //Set the attribute mode
        addDamage=false;
        secondMode=false;
        inputBasicMode();//Ask all the information necessary to use the basic mode

        if (choice == 4)
        {

            inputSecondAdditionalMode();//Ask all the information necessary to use the alternative mode
            inputAdditionalMode();
            mode= true;//Set the attribute mode
            secondMode = true;//Set the attribute second mode
        }
        else if (choice == 3)
        {
            inputSecondAdditionalMode();//Ask all the information necessary to use the alternative mode
            secondMode = true;//Set the attribute mode
        }
        else if (choice == 2)
        {
            inputAdditionalMode();//Ask all the information necessary to use the alternative mode
            mode = true;//Set the attribute mode
        }






        responseIsReady = true;

    }

    /**
     *Generate the response message for the MachineGun with all player's choice
     * @return response message
     * @throws IllegalStateException if the method printAction wasn't called yet
     */
    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException
    {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        if (mode)
        {
            if (secondMode)
                {

                    return new ResponseMachineGun(targetBasicMode, targetBasicModeSecond, targetAdditionalMode, targetSecondAdditionalMode,targetSecondAdditionalModeSecond,addDamage);
                }
                return new ResponseMachineGun(targetBasicMode,targetBasicModeSecond,targetAdditionalMode);
        }
        if (secondMode)
            return new ResponseMachineGun(targetBasicMode,targetBasicModeSecond,targetSecondAdditionalMode,targetSecondAdditionalModeSecond,addDamage);
        return new ResponseMachineGun(targetBasicMode,targetBasicModeSecond);
    }




    //Ask at the user to choice three target for the alternative mode if there are



    protected void inputAdditionalMode()
    {
        List<ColorId> players;
        players = playersAdditionalMode; // siccome l'arma aggiunge danno  ad uno dei primi nemici scelti
        List<ColorId> showedPlayers= new LinkedList<>();
        int i = 1;

        terminal.addTextInput("Scegli a chi vuoi aggiungere un danno:");
        for (ColorId t:players)//Ask to user the target
        {
            if(t.equals(targetBasicMode) || t.equals(targetBasicModeSecond)) //targetBasicModeSecond può essere null
            {
                terminal.addOptionInput(i+" :"+t);
                showedPlayers.add(t);
                i++;
            }
        }
        int choice = terminal.inputInt(1, i - 1);
        targetAdditionalMode  = showedPlayers.get(choice-1);


    }

    protected void inputSecondAdditionalMode() {
        List<ColorId> players;
        int choice;
        players = playerSecondAdditionalMode;
        int i = 1;
        if (players.size()>2)
        {

            List<ColorId> showedPlayers= new LinkedList<>();
                terminal.addTextInput(" altro player a cui far danno");
                for (ColorId t : players)//Ask to user the target
                {
                    if(!t.equals(targetBasicMode)&& !t.equals(targetBasicModeSecond))
                    {
                    terminal.addOptionInput(i + ": " + t);
                    showedPlayers.add(t);
                    i++;
                    }
                }
                choice = terminal.inputInt(1, i - 1);
                targetSecondAdditionalMode = showedPlayers.get(choice - 1);

        }
        terminal.addTextInput(" player già attaccato a cui far danno");
            players = playersBasicMode;
        List<ColorId> showedPlayersA= new LinkedList<>();
            i = 1;
            for (ColorId t : players)//Ask to user the target
            {
                if(targetAdditionalMode!=null)
                {
                    if (!(t.equals(targetAdditionalMode)) && !t.equals(targetBasicMode)) { // if we are in all mode activated we ask to attack only the player that wasn't in additional mode
                        terminal.addOptionInput(i + ": " + t);
                        showedPlayersA.add(t);
                        i++;
                    }
                }
                else
                    {
                    terminal.addOptionInput(i + ": " + t);
                    showedPlayersA.add(t);
                    i++;
                    }
            }
            choice = terminal.inputInt(1, i - 1);
            targetSecondAdditionalModeSecond = showedPlayersA.get(choice - 1);
            addDamage = true;

    }


    protected void inputBasicMode()
    {
        List<ColorId> players;

        players = playersBasicMode;
        int i = 1;
        terminal.addTextInput("seleziona il primo player  :");
        for (ColorId t:players)//Ask to user the target
        {
            terminal.addOptionInput(i+" :"+t);
            i++;
        }
        int choice = terminal.inputInt(1, i - 1);
        targetBasicMode = players.get(choice-1);

       if(players.size()>1) { //check se >1 o >2
           terminal.addTextInput("seleziona il secondo player :");
           i = 1;
           for (ColorId t : players)//Ask to user the target
           {
               if (!t.equals(targetBasicMode)) {
                   terminal.addOptionInput(i + ":" + t);
                   i++;
               }
           }
           choice = terminal.inputInt(1, i - 1);
           targetBasicModeSecond = players.get(choice - 1);
       }
    }
}
