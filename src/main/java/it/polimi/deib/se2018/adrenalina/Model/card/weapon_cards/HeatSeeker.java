package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseFurnace;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseHeatSeeker;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;


import java.util.*;
import java.util.stream.Collectors;


/**
 * @author giovanni
 */


public class HeatSeeker extends WeaponCard
{

    private boolean[] availableMethod = new boolean[1];



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
    public boolean[] checkAvailableMode() throws IllegalStateException {
        if (player == null)
            throw new IllegalStateException("Carta: " + name + " non appartiene a nessun giocatore");//If this card doesn't belong at a player launch exception

        availableMethod[0] = false; //I suppose that the modes can't be used


        if (isLoaded() && !checkPlayers().isEmpty() )//If the first mode can be used
            availableMethod[0] = true;

        return availableMethod;

    }


    //così: seleziono tutti i player ed escludo quelli che posso vedere

    /**
     *
     * @return
     * @throws IllegalStateException
     */
    public List<ColorId> checkBasicMode() throws IllegalStateException
    {
        return checkPlayers();//Returns all targets
    }

    /**
     *
     * @param
     * @throws SquareNotInGameBoard
     * @throws IllegalStateException
     */
    public void basicMode(ColorId colorPlayer) throws SquareNotInGameBoard,IllegalStateException
    {
        doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0),3);

        this.isLoaded = false;
    }



    private List<ColorId> checkPlayers ()
    {
        List<ColorId> colorIdList = new ArrayList<>();

        List<Player> playerList = player.getSquare().getGameBoard().getAllPlayer();
        playerList.remove(player);

        Set<Player> playerSet = player.playerThatSee(player.getSquare().getGameBoard());

        playerSet.remove(player);
        playerList.removeAll(player.playerThatSee(player.getSquare().getGameBoard()));
        if (!playerList.isEmpty())
        {
            for (Player playerIterate : playerList)
                colorIdList.add(playerIterate.getColor());
        }

        return colorIdList;

    }

    @Override
    public void useWeapon(ResponseInput responseInput)
    {

        try {
            basicMode(((ResponseHeatSeeker) responseInput).getTagetBasicMode());
        } catch (SquareNotInGameBoard squareNotInGameBoard) {
            squareNotInGameBoard.printStackTrace();
        }

    }


}