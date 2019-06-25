package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import it.polimi.deib.se2018.adrenalina.communication_message.*;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author giovanni
 */


public class VortexCannon extends WeaponCard
{
    private boolean[] availableMethod = new boolean[2];


    /**
     * It is the public constructor for the class.
     * @param color is the color of the card
     * @param weaponID is the unique id to identify the card
     * @param isLoaded to indicate if the weapon is loaded
     */
    public VortexCannon( Color color, int weaponID, boolean isLoaded) {
        super(color, weaponID, isLoaded);
        this.name = "Cannone Vortex";
        yellowAmmoCost = 0;
        blueAmmoCost = 1;
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
            throw new IllegalStateException("Carta: " + name + " non appartiene a nessun giocatore.");//If this card doesn't belong to any player, it launches an exception

        availableMethod[0] = false;
        availableMethod[1] = false;

        List<Square> squareList = new ArrayList<>();

        squareList.addAll(MethodsWeapons.squareThatSee(player));
        squareList.remove(player.getSquare());

        if (isLoaded() && MethodsWeapons.areSquareISeeNotMineNotEmpty(player, squareList))
        {
                availableMethod[0] = true;
        }

        if (isLoaded && availableMethod[0] &&  player.getAmmoRed() >= 1)
        {
            availableMethod[1] = true;
        }

        return availableMethod;

    }

    /**
     *
     * @return
     * @throws IllegalStateException
     */
    private HashMap<Square, List<Player>> checkBasicModeFull() throws IllegalStateException
    {
        if (!checkAvailableMode()[0])//check mode
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");

        HashMap<Square, List<Player>> hashMapreturn = new HashMap<>();

        Player playerTemp = new Player(null,null, null, true);

        List<Square> squareTarget = new ArrayList<>();
        List<Player> playersTarget;
        squareTarget = (ArrayList) MethodsWeapons.squareThatSee(player);
        squareTarget.remove(player.getSquare()); //create squarelist of possible vortex locations

        for (Square squareIterate: squareTarget)
        {
            playersTarget = new ArrayList<>();
            playerTemp.setSquare(squareIterate);
            for (Square squareIterate2: playerTemp.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 1)) //all the square at distance 1 from the possible location of the vortex
                {
                  playersTarget.addAll(squareIterate2.getPlayerList());
                }
            if (!playersTarget.isEmpty())
                hashMapreturn.put(squareIterate, (ArrayList) playersTarget);
        }

        return hashMapreturn;

    }


    /**
     *
     * @return
     */
    private List<String> checkBasicModeSquares ()
    {
        HashMap<Square, List<Player>> squarePlayersHashMap = checkBasicModeFull();

        List<String> squareListCoordinatesAsString = new ArrayList<>();

        for (Square squareIterate : squarePlayersHashMap.keySet())
            squareListCoordinatesAsString.add(squareIterate.toStringCoordinates());

        return squareListCoordinatesAsString;

    }


    /**
     *
     * @param squareTargetCoordinatesAsString
     * @return
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

        HashMap<Square, List<Player>> squarePlayersHashMap = checkBasicModeFull();

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
        if (!checkAvailableMode()[0])//check mode
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
     * @param squareToMoveCoordinatesAsString
     */
    public void basicMode (ColorId colorPlayer, String squareToMoveCoordinatesAsString)
    {
        if (!checkAvailableMode()[0])//check mode
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");

        int x = MethodsWeapons.getXFromString(squareToMoveCoordinatesAsString);
        int y = MethodsWeapons.getYFromString(squareToMoveCoordinatesAsString);

        doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0),2);

        MethodsWeapons.moveTarget(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0), x, y);

        isLoaded = false;
    }


    /**
     *
     * @param colorPlayerAlreadySelected
     * @param squareTargetCoordinatesAsString
     * @return
     * @throws IllegalStateException
     */
    public List<ColorId> checkWithBlackHoleMode(ColorId colorPlayerAlreadySelected, String squareTargetCoordinatesAsString) throws IllegalStateException
    {
        if (!checkAvailableMode()[1])//check mode
            throw  new IllegalStateException("Modalità black hole dell'arma "+name+" non eseguibile.");

        List<ColorId> colorIdList = checkBasicModePlayers(squareTargetCoordinatesAsString);
        colorIdList.remove(colorPlayerAlreadySelected);
        if (!colorIdList.isEmpty())
            return colorIdList;
        else return null;
    }

    /**
     *
     * @param playerTarget1
     * @param playerTarget2
     * @param vortexSquareAsString
     */
    public void blackHoleMode (ColorId playerTarget1, ColorId playerTarget2, String vortexSquareAsString)
    {
        if (!checkAvailableMode()[1])//check mode
            throw  new IllegalStateException("Modalità black hole dell'arma "+name+" non eseguibile.");

        int x = MethodsWeapons.getXFromString(vortexSquareAsString);
        int y = MethodsWeapons.getYFromString(vortexSquareAsString);

        doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(playerTarget1)).collect(Collectors.toList()).get(0),1);
        MethodsWeapons.moveTarget(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(playerTarget1)).collect(Collectors.toList()).get(0), x, y);

        if (playerTarget2 != null)
        {
            doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(playerTarget2)).collect(Collectors.toList()).get(0),1);
            MethodsWeapons.moveTarget(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(playerTarget2)).collect(Collectors.toList()).get(0), x, y);
        }

        player.setAmmoRed(player.getAmmoRed() - 1);
        isLoaded = false;

    }

    /**
     *
     * @param responseMessage response message specified for the weapon
     */
    @Override
    public void useWeapon(ResponseInput responseMessage)
    {
        if (((ResponseVortexCannon) responseMessage).isMode())

            blackHoleMode(((ResponseVortexCannon) responseMessage).getTarget1BlackHoleMode(), ((ResponseVortexCannon) responseMessage).getTarget2BlackHoleMode(), ((ResponseVortexCannon) responseMessage).getTargetVortexSquareAsString());
        else
            basicMode(((ResponseVortexCannon) responseMessage).getTargetPlayerBasicMode(), ((ResponseVortexCannon) responseMessage).getTargetVortexSquareAsString());
    }

    /**
     *
     * @return
     */
    @Override
    public RequestInput getRequestMessage()
    {
        if (checkAvailableMode()[0] && checkAvailableMode()[1])

            return new RequestShockwave(checkAvailableMode(),checkBasicMode());

        else if(checkAvailableMode()[0] && !checkAvailableMode()[1])

            return new RequestShockwave(checkAvailableMode(),checkBasicMode());

        else
            return new RequestShockwave(checkAvailableMode(),new HashMap<>());
    }
}

