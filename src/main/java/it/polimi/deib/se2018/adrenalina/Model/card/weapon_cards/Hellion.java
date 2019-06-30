package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import it.polimi.deib.se2018.adrenalina.communication_message.*;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author giovanni
 */


public class Hellion extends WeaponCard
{

    private boolean[] availableMethod = new boolean[2];

    /**
     * It is the public constructor for the class.
     * @param color is the color of the card
     * @param weaponID is the unique id to identify the card
     * @param isLoaded to indicate if the weapon is loaded
     */
    public Hellion( Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        this.name = "Raggio Solare";
        yellowAmmoCost = 1;
        blueAmmoCost = 0;
        redAmmoCost = 1;
    }

    /**
     * It checks which modes of the weapon can be used
     * @return an array of boolean of which modes are available to the players
     * @throws IllegalStateException if this card doesn't belong at a player
     */
    public boolean[] checkAvailableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: " + name + " non appartiene a nessun giocatore.");

        availableMethod[0] = false;//I suppose that the modes can't be used
        availableMethod[1] = false;

        if (isLoaded())
        {
            List<Player> playerList = new ArrayList<>();
            playerList.addAll(player.playerThatSee(player.getSquare().getGameBoard()));

            if (!playerList.isEmpty())
            {
                List<Player> playerTempList = new ArrayList<>();
                for (Player playerIterate : playerList)
                {
                    if (playerIterate.getSquare() == player.getSquare())
                        playerTempList.add(playerIterate);
                }

                playerList.removeAll(playerTempList);

                if (!playerList.isEmpty())
                    availableMethod[0] = true;

            }


        }


        if (isLoaded() && player.getAmmoRed() > 0)
        {
            if (availableMethod[0])
                availableMethod[1] = true;
        }

        return availableMethod;

    }


    /**
     * Method to check the targets for the basic mode.
     *
     * @return an hashmap of String and lists of ColorId
     */
    public HashMap<String, List<ColorId>> checkBasicMode ()
    {
        if (!checkAvailableMode()[0])
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");


        HashMap<String, List<ColorId>> hashMapToReturn = new HashMap<>();

        //check basic mode for square and players
        HashMap<Square, List<Player>> hashSquarePlayer = new HashMap<>();

        Set<Square> squareList;
        squareList = MethodsWeapons.squareThatSee(player);
        squareList.remove(player.getSquare());

        for (Square squareIterate : squareList)
        {
            if (!squareIterate.getPlayerList().isEmpty())
                hashSquarePlayer.put(squareIterate,  squareIterate.getPlayerList());
        }


        //convert to colorid and strings


        for (Square squareIterate : hashSquarePlayer.keySet())
        {
            List<ColorId> colorIdList = new ArrayList<>();

            for (Player playerIterate : hashSquarePlayer.get(squareIterate))
                colorIdList.add(playerIterate.getColor());

            hashMapToReturn.put(squareIterate.toStringCoordinates(), colorIdList);

        }


        return hashMapToReturn;

    }

    /**
     *Method for the basic mode of this weapon
     *
     * @param colorPlayer is the target
     * @throws IllegalStateException if the mode is not available
     */
    public void basicMode(ColorId colorPlayer) throws IllegalStateException
    {
        if (!checkAvailableMode()[0])
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");

        doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0),1);

        for (Player playerIterate : player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0).getSquare().getPlayerList())
        {
            markTarget(playerIterate,1);
        }

        isLoaded = false;
    }


    /**
     * It is the full implementation to check the targets for the nano tracer mode
     *
     * @return an hashmap of String and lists of ColorId
     */
    public HashMap<String, List<ColorId>> checkNanoTracerMode ()
    {
        if (!checkAvailableMode()[1])
            throw  new IllegalStateException("Modalità nano-traccianti dell'arma "+name+" non eseguibile.");

        return checkBasicMode();
    }

    /**
     * It implements the alternative mode for the weapon
     *
     * @param colorPlayer is the target player
     * @throws IllegalStateException if the mode is not available
     */
    public void nanoTracerMode(ColorId colorPlayer) throws IllegalStateException
    {
        if (!checkAvailableMode()[1])
            throw  new IllegalStateException("Modalità nano-traccianti dell'arma "+name+" non eseguibile.");

        doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0),1);

        for (Player playerIterate : player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0).getSquare().getPlayerList())
        {
            markTarget(playerIterate,2);
        }

        player.setAmmoRed(player.getAmmoRed()-1);

        isLoaded = false;
    }

    /**
     *
     * @param responseMessage response message specified for the weapon
     */
    @Override
    public void useWeapon(ResponseInput responseMessage)
    {
        if (((ResponseHellion) responseMessage).isMode())
        {
            nanoTracerMode(((ResponseHellion) responseMessage).getTargetAlternativeMode());
            return;
        }

        basicMode(((ResponseHellion) responseMessage).getTargetBasicMode());
    }


    /**
     *
     * @return
     */
    @Override
    public RequestInput getRequestMessage()
    {
        return new RequestHellion(checkAvailableMode(), checkBasicMode(), checkNanoTracerMode());
    }

}
