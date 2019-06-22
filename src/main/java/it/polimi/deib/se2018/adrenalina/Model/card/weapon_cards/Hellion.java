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
     * This is the constructor for the card Hellion
     *
     * @param color
     * @param weaponID
     * @param isLoaded
     */
    public Hellion( Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        this.name = "Raggio Solare";
        yellowAmmoCost = 1;
        blueAmmoCost = 0;
        redAmmoCost = 1;
    }

    /**
     *
     * @return
     * @throws IllegalStateException
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
     * Return the list of all target available for using the basic mode of this weapon
     *
     * @return all player that can be affected with the weapon in basic mode
     * @exception IllegalStateException if the basic mode can't be used
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
     *
     * @return
     * @throws IllegalStateException
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
     *
     * @param squareTargetCoordinatesAsString
     * @return
     * @throws IllegalStateException
     */
    private List<ColorId> checkBasicModePlayers (String squareTargetCoordinatesAsString) throws IllegalStateException
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
     *
     * @return
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
     *
     * @param colorPlayer
     * @throws IllegalStateException
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
     *
     * @return
     * @throws IllegalStateException
     */
    private List<String> checkNanoTracerModeSquare () throws IllegalStateException
    {
        return checkBasicModeSquares();
    }

    /**
     *
     * @param squareTargetCoordinatesAsString
     * @return
     * @throws IllegalStateException
     */
    private List<ColorId>  checkNanoTracerModePlayer (String squareTargetCoordinatesAsString) throws IllegalStateException
    {
        return checkBasicModePlayers(squareTargetCoordinatesAsString);
    }

    /**
     *
     * @return
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
     *
     * @param colorPlayer
     * @throws IllegalStateException
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
