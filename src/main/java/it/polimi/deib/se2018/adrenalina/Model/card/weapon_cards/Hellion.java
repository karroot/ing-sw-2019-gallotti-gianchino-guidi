package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import it.polimi.deib.se2018.adrenalina.communication_message.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
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
            Set<Player> playerSet;
            playerSet = player.playerThatSee(player.getSquare().getGameBoard());

            for (Player playerIterate : playerSet)
            {
                if (playerIterate.getSquare() == player.getSquare())
                   playerSet.remove(playerIterate);
            }

            if (!playerSet.isEmpty())
                availableMethod[0] = true;
        }


        if (isLoaded() && player.getAmmoRed() > 0 && availableMethod[0])
        {
                availableMethod[1] = true;
        }

        return availableMethod;

    }


    /**
     * First of 3 methods for check the targets for the basic mode.
     *
     * @return and hashmap of squares and arraylist of players of possible targets
     */
    private HashMap<Square, ArrayList<Player>> checkBasicModeFull()
    {

        HashMap<Square, ArrayList<Player>> hashSquarePlayer = new HashMap<Square, ArrayList<Player>>();

        Set<Square> squareList;
        squareList = MethodsWeapons.squareThatSee(player);
        squareList.remove(player.getSquare());

        for (Square squareIterate : squareList)
        {
            hashSquarePlayer.put(squareIterate, (ArrayList) squareIterate.getPlayerList());
        }

        return hashSquarePlayer;

    }

    /**
     * Second of 3 methods for check the targets for the basic mode.
     *
     * @return a list of string of possible squares target
     */
    private List<String> checkBasicModeSquares ()
    {
        HashMap<Square, ArrayList<Player>> squarePlayersHashMap = checkBasicModeFull();

        List<String> squareListCoordinatesAsString = new ArrayList<>();

        for (Square squareIterate : squarePlayersHashMap.keySet())
            squareListCoordinatesAsString.add(squareIterate.toStringCoordinates());

        return squareListCoordinatesAsString;

    }

    /**
     * Third of 3 methods for check the targets for the basic mode.
     * @param squareTargetCoordinatesAsString is the square target
     * @return a list of ColorId of possible targets in that square
     */
    private List<ColorId> checkBasicModePlayers (String squareTargetCoordinatesAsString)
    {
        int x = MethodsWeapons.getXFromString(squareTargetCoordinatesAsString);
        int y = MethodsWeapons.getYFromString(squareTargetCoordinatesAsString);

        Square square = null;
        List<ColorId> colorIdList = new ArrayList<>();

        try {
            square = player.getSquare().getGameBoard().getArena().getSquare(x, y);
        } catch (SquareNotInGameBoard squareNotInGameBoard) {
            squareNotInGameBoard.printStackTrace();
        }

        HashMap<Square, ArrayList<Player>> squarePlayersHashMap = checkBasicModeFull();

        for (Player playerIterate : squarePlayersHashMap.get(square))
            colorIdList.add(playerIterate.getColor());

        return colorIdList;

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

        List<String> stringListKeys = checkBasicModeSquares();
        List<ColorId> colorIdListAttributes = new ArrayList<>();

        for (String stringIterate : stringListKeys)
        {
            colorIdListAttributes.clear();
            colorIdListAttributes = checkBasicModePlayers(stringIterate);
            hashMapToReturn.put(stringIterate, colorIdListAttributes);
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
     * It checks the targets for the NanoTracerMode
     *
     * @return a list of string of square coordinates that are the possible target squares
     */
    private List<String> checkNanoTracerModeSquare ()
    {
        return checkBasicModeSquares();
    }

    /**
     * It checks the targets for the NanoTracerMode
     *
     * @param squareTargetCoordinatesAsString is the target square
     * @return a list of ColorId of possible target players
     */
    private List<ColorId>  checkNanoTracerModePlayer (String squareTargetCoordinatesAsString)
    {
        return checkBasicModePlayers(squareTargetCoordinatesAsString);
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


        HashMap<String, List<ColorId>> hashMapToReturn = new HashMap<>();

        List<String> stringListKeys = checkNanoTracerModeSquare();
        List<ColorId> colorIdListAttributes = new ArrayList<>();

        for (String stringIterate : stringListKeys)
        {
            colorIdListAttributes.clear();
            colorIdListAttributes = checkNanoTracerModePlayer(stringIterate);
            hashMapToReturn.put(stringIterate, colorIdListAttributes);
        }

        return hashMapToReturn;
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
