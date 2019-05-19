package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;


import java.util.*;



public class HeatSeeker extends WeaponCard
{

    private boolean[] avaiableMethod = new boolean[1];



    /**
     * This is the constructor for the card HeatSeeker
     *
     * @param color
     * @param weaponID
     * @param isLoaded
     */
    public HeatSeeker( Color color, int weaponID, boolean isLoaded) throws NullPointerException
    {
        super( color, weaponID, isLoaded);
        this.name = "HeatSeeker";
        yellowAmmoCost = 1;
        blueAmmoCost = 0;
        redAmmoCost = 2;
    }

    /**
     * This method checks which modes of the weapon can be used by the player that owns this weapon
     *
     * @return array of booleans of size 2. The first boolean represents the basic mode the second the alternative mode
     * @exception IllegalStateException if this card doesn't belong to any player
     */
    public boolean[] checkAvaliableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: "+ name + " non appartiene a nessun giocatore");//If this card doesn't belong at a player launch exception

        avaiableMethod[0] = false; //I suppose that the modes can't be used

        List<Player> playerList = player.getSquare().getGameBoard().getAllPlayer();
        playerList.removeAll(player.playerThatSee(player.getSquare().getGameBoard()));


        if (isLoaded() && !playerList.isEmpty() )//If the first mode can be used
            avaiableMethod[0] = true;

        return avaiableMethod;

    }


    //così: seleziono tutti i player ed escludo quelli che posso vedere

    /**
     *
     * @return
     * @throws IllegalStateException
     */
    public List<Player> checkBasicMode() throws IllegalStateException
    {
        if (!checkAvaliableMode()[0])
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        List<Player> playerList = player.getSquare().getGameBoard().getAllPlayer();
        playerList.removeAll(player.playerThatSee(player.getSquare().getGameBoard()));

        return playerList;//Returns all targets
    }

    /**
     *
     * @param player
     * @throws SquareNotInGameBoard
     * @throws IllegalStateException
     */
    public void basicMode(Player player) throws SquareNotInGameBoard,IllegalStateException
    {
        if (!checkAvaliableMode()[0])
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        doDamage(player,3);

        this.isLoaded = false;
    }


}