package it.polimi.deib.se2018.adrenalina.View.GUI;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.View.Terminal;
import it.polimi.deib.se2018.adrenalina.communication_message.update_model.*;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Cysko7927
 * Implements a GUI with all the method needed to handle
 */
public class GUI implements Terminal
{
    private ColorId playerOfThisCli;
    private UpdateModel data;
    private BoardGUI boardGUI;

    public GUI()
    {
        boardGUI = new BoardGUI(this);
    }

    /**
     * Updated all data of the game board with the weapons in spawn points and the players' boards
     */
    @Override
    public void showBoard()
    {
        //Obtain all the weapons in spawnpoints
        List<String> blueWeapons = data.getDataOfAllSquare()
                .stream()
                .filter(squareImmutable -> squareImmutable.getX() ==3 && squareImmutable.getY() == 3 )
                .collect(Collectors.toList()).get(0).getWeaponCardList();

        List<String> redWeapons = data.getDataOfAllSquare()
                .stream()
                .filter(squareImmutable -> squareImmutable.getX() ==1 && squareImmutable.getY() == 2 )
                .collect(Collectors.toList()).get(0).getWeaponCardList();

        List<String> yellowWeapons = data.getDataOfAllSquare()
                .stream()
                .filter(squareImmutable -> squareImmutable.getX() ==4 && squareImmutable.getY() == 1 )
                .collect(Collectors.toList()).get(0).getWeaponCardList();

        boardGUI.setPanelSkulls(data.getDataOfBoard().getOriginalSkullCouner(),data.getDataOfBoard().getKillShotTrack());

        //Show the arena updated
        boardGUI.clearAllSquares();//Delete all icons on the square
        boardGUI.setArena(data.getDataOfBoard().getCode());

        for (SquareImmutable t:data.getDataOfAllSquare())
        {
            for (ColorId c:t.getPlayerList())
                boardGUI.setPlayerOnSquare(squareToIndexGui(t.getX(),t.getY()),c);

            if (t.isAmmoPoint())
                boardGUI.setAmmoTilesOnSquare(squareToIndexGui(t.getX(),t.getY()),t.getAmmoTiles().getAmmoCardID());
        }

        //Print the weapons in spawnPoints

        boardGUI.setWeaponPointB(blueWeapons.toString());

        boardGUI.setWeaponPointR(redWeapons.toString());

        boardGUI.setWeaponPointY(yellowWeapons.toString());

        //Print the player's board and set the possible boards

        boardGUI.clearBoardsPossible();
        for (PlayerImmutable t:data.getDataOfAllPlayer())
        {

            if (t.getColor().equals(playerOfThisCli))
            {
                showPlayerBoard(t);
                boardGUI.setBoardsPossible("Tua Plancia");
            }
            else
                boardGUI.setBoardsPossible("Plancia Giocatore:"+t.getColor());

        }

        boardGUI.getAllBoardWindow().setVisible(false);
        boardGUI.getAllBoardWindow().setVisible(true);

    }

    @Override
    public void showMenu() {

    }

    /**
     * Show at the user a window with all the scores of the players
     * and says also who won
     * @param messageWithFinalScore message(String) from server tha contain all the scores of the player
     */
    @Override
    public void showFinalScore(String messageWithFinalScore)
    {
        JOptionPane.showMessageDialog(boardGUI.getAllBoardWindow(), messageWithFinalScore);
    }

    /**
     * Show at user a generic message through a window
     * @param message string to show at the user
     */
    @Override
    public void showMessage(String message)
    {
        JOptionPane.showMessageDialog(boardGUI.getAllBoardWindow(), message);
    }

    /**
     * Show at user a message of error
     * @param message string that represents an error
     */
    @Override
    public void showError(String message)
    {
        JOptionPane.showMessageDialog(boardGUI.getAllBoardWindow(), message);
    }

    /**
     * This method being called by boardGui to handle the switch of the boards when the
     * player selected another board from menu
     * @param player color of the player to show
     */
    public void changePlayerBoard(ColorId player)
    {
        try
        {
            List<PlayerImmutable> dataOfAllPlayer = data.getDataOfAllPlayer();

            for (PlayerImmutable t:dataOfAllPlayer)
            {
                if (t.getColor().equals(player))
                    showPlayerBoard(t);
            }
        }
        catch (NullPointerException e)
        {

        }

    }

    /**
     * Updates the gui with all the statistic of the player
     * @param player player to show
     */
    @Override
    public void showPlayerBoard(PlayerImmutable player)
    {
        boardGUI.setPlayerBoard(player.getColor());
        boardGUI.setScore(player.getScore());
        boardGUI.setSkullForScore(player.getDeathsCounter());
        boardGUI.setTextMarker(player.getMarkCounter().toString());
        boardGUI.setDamages(player.getDamageCounter());
        boardGUI.setAmmoB(String.valueOf(player.getAmmoBlue()));
        boardGUI.setAmmoR(String.valueOf(player.getAmmoRed()));
        boardGUI.setAmmoY(String.valueOf(player.getAmmoYellow()));
        if (player.isFlipped())
            boardGUI.setFlipped(player.getColor());

        int i = 1;

        for (WeaponCardImmutable t:player.getWeaponCardList())
        {
            boardGUI.setWeapon(t.getName(),i,t.isLoaded());
            i++;
        }


        for (;i<=3;i++)
        {
            boardGUI.setWeapon(null,i,false);
        }


        i = 1;

        if (player.getColor().equals(playerOfThisCli))
        {
            for (PowerUpCardImmutable t:player.getPowerupCardList())
            {
                boardGUI.setPower(t.getIdPU(),i);
                i++;
            }

            for (;i<=3;i++)
            {
                boardGUI.setPower(0,i);
            }
        }


    }

    /**
     * Obtain the copy of the model
     * @return copy of the model
     */
    @Override
    public UpdateModel getData()
    {
        return data;
    }

    /**
     * Change the copy of the model updated
     * @param data new copy of the model
     */
    @Override
    public void setData(UpdateModel data)
    {
        this.data = data;
    }

    /**
     * Set the color of the player of this CLI
     * @param color color of the player
     */
    @Override
    public void setColorOfPlayer(ColorId color)
    {
        this.playerOfThisCli = color;
    }

    /**
     * Show the three action of a Round in the windowInput
     */
    @Override
    public void showAction()
    {
        boardGUI.addTextForInput("Scegli un azione da fare:");
        boardGUI.addOptionForInput("1:Muovere");
        boardGUI.addOptionForInput("2:Raccogli");
        boardGUI.addOptionForInput("3:Spara");
    }

    /**
     * Ask an action at the user(move,grab,Shoot)
     * @return integer that represent the choice made by user
     */
    @Override
    public int selectAction()
    {
        return boardGUI.getInputChoice();
    }

    /**
     * These implementation being used to ask at the user to click on a button of input
     * to choose an input
     * Hint: Insert the button before to call this method
     * @param min Not Used
     * @param max Not Used
     * @return integer that represents the choice of the user
     */
    @Override
    public int inputInt(int min, int max)
    {
        return boardGUI.getInputChoice();
    }

    /**
     * Ask at the player if he wants to reload using the windowInput
     * This method doesn't check if there are weapon not loaded
     * @return true if says yes
     */
    @Override
    public boolean askReloading()
    {
        boardGUI.addTextForInput("Vuoi Ricaricare?");
        boardGUI.addOptionForInput("1:Si");
        boardGUI.addOptionForInput("2:No");

        int choice = boardGUI.getInputChoice();

        if (choice == 1)
            return true;


        return false;
    }

    /**
     * Check if the player has some Teleporter or Newton and ask if he wants
     * to use them
     * @return true if the player says yes
     */
    @Override
    public boolean askPowerUPTeleOrNew()
    {
        List<String> allPowerUps = data.getDataOfAllPlayer()
                .stream()
                .filter(playerImmutable -> playerImmutable.getColor().equals(playerOfThisCli))
                .map(PlayerImmutable::getPowerupCardList)
                .collect(Collectors.toList())
                .get(0)
                .stream()
                .map(PowerUpCardImmutable::powerToString)
                .collect(Collectors.toList()); //Obtain all powerUp of the player

        boolean thereAreTeleOrNew = false;

        for (String t:allPowerUps) //Check if there are teleporter or newton
        {
            if (t.contains("Teletrasporto") || t.contains("Raggio Cinetico"))
            {
                thereAreTeleOrNew = true;
            }
        }

        if (thereAreTeleOrNew)
        {
            boardGUI.addTextForInput("Vuoi usare i powerUp?");
            boardGUI.addOptionForInput("1:Si");
            boardGUI.addOptionForInput("2:No");

            int choice = boardGUI.getInputChoice();

            if (choice == 1)
                return true;
        }

        return false;
    }


    /**
     * Show all the powerUp of the player on the CLI
     * This method being used only to show the powerUps for the respawn
     */
    @Override
    public void showPowerUp()
    {
        List<String> allPowerUps = data.getDataOfAllPlayer()
                .stream()
                .filter(playerImmutable -> playerImmutable.getColor().equals(playerOfThisCli))
                .map(PlayerImmutable::getPowerupCardList)
                .collect(Collectors.toList())
                .get(0)
                .stream()
                .map(PowerUpCardImmutable::powerToString)
                .collect(Collectors.toList()); //Obtain the list of all PowerUP

        int i = 1;

        if (allPowerUps.isEmpty()) //if the list is empty
        {
            JOptionPane.showMessageDialog(boardGUI.getAllBoardWindow(), "Non hai powerUp");//Print that there aren't powerUps
            return;
        }


        boardGUI.addTextForInput("Scegli un powerU per il Respawn"); //Print the name of all PowerUp
        for (String t:allPowerUps)
        {
            boardGUI.addOptionForInput(i+":"+t);
            i++;
        }
    }

    /**
     * Ask at the player to select a powerUp for the respawn
     * @return integer that represent the choice of the user,if there aren't powerUps returns 0
     */
    @Override
    public int selectPowerUp()
    {
        List<String> allPowerUps = data.getDataOfAllPlayer()
                .stream()
                .filter(playerImmutable -> playerImmutable.getColor().equals(playerOfThisCli))
                .map(PlayerImmutable::getPowerupCardList)
                .collect(Collectors.toList())
                .get(0)
                .stream()
                .map(PowerUpCardImmutable::powerToString)
                .collect(Collectors.toList()); //Obtain the list of all PowerUP

        if (allPowerUps.isEmpty())
            return 0;

        return boardGUI.getInputChoice();

    }

    /**
     * Ask at the user if he wants to use the Targeting Scope to do more damage
     * @return true if the user says yes
     */
    @Override
    public boolean askPowerUPTarget()
    {
        boardGUI.addTextForInput("Vuoi usare i mirini?");

        boardGUI.addOptionForInput("1:Si");
        boardGUI.addOptionForInput("2:No");

        int choice = boardGUI.getInputChoice();

        if (choice == 1)
            return true;

        return false;
    }

    /**
     * Ask at the user if he wants to obtain more ammo using the power ups
     * printing the request on GUI
     * @return true if the user said yes
     */
    @Override
    public boolean askPowerUPForReload()
    {
        boardGUI.addTextForInput("Vuoi usare dei powerUp per incrementare le munizioni?");

        boardGUI.addOptionForInput("1:Si");
        boardGUI.addOptionForInput("2:No");

        int choice = boardGUI.getInputChoice();

        if (choice == 1)
            return true;

        return false;
    }

    /**
     * Add a button of input on the gui
     * @param text text to show on the button
     */
    @Override
    public void addOptionInput(String text)
    {
        boardGUI.addOptionForInput(text);
    }

    /**
     * Add the description on the window input to show at the user
     * @param text description to show
     */
    @Override
    public void addTextInput(String text)
    {
        boardGUI.addTextForInput(text);
    }

    /**
     * These implementation being used to ask at the user to click on a button of input
     * to choose an input
     * Hint: Insert the button before to call this method
     * @param acceptedInt Not Used
     * @return integer that represents the choice of the user
     */
    @Override
    public int inputInt(List<Integer> acceptedInt)
    {
        return boardGUI.getInputChoice();
    }

    //Methods Private
    private int squareToIndexGui(int x,int y)
    {
        if (x == 1 && y == 1)
            return 0;
        if (x == 2 && y == 1)
            return 1;
        if (x == 3 && y == 1)
            return 2;
        if (x == 4 && y == 1)
            return 3;
        if (x == 1 && y == 2)
            return 4;
        if (x == 2 && y == 2)
            return 5;
        if (x == 3 && y == 2)
            return 6;
        if (x == 4 && y == 2)
            return 7;
        if (x == 1 && y == 3)
            return 8;
        if (x == 2 && y == 3)
            return 9;
        if (x == 3 && y == 3)
            return 10;
        if (x == 4 && y == 3)
            return 11;

        throw new IllegalArgumentException("Cordinate square inesistenti");

    }
}
