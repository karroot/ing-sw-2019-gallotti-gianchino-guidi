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

        HashMap<Square, List<Player>> hashMapCheck = checkBasicModeFull();

        if (isLoaded())
        {
            if (!hashMapCheck.keySet().isEmpty())
                availableMethod[0] = true;
        }

        if (isLoaded() && availableMethod[0] &&  player.getAmmoRed() >= 1)
        {
            for (Square squareIterate : hashMapCheck.keySet())
            {
                if (hashMapCheck.get(squareIterate).size()>1)
                {
                    availableMethod[1] = true;
                    break;
                }
            }

        }

        return availableMethod;

    }

    /**
     *
     * @return
     */
    private HashMap<Square, List<Player>> checkBasicModeFull()
    {

        HashMap<Square, List<Player>> hashMapreturn = new HashMap<>();

        Player playerTemp = new Player(null,null, null, true);

        List<Square> squareTarget = new ArrayList<>();
        List<Player> playersTarget = new ArrayList<>();
        List<Square> squareDistance1 = new ArrayList<>();


        squareTarget.addAll(MethodsWeapons.squareThatSee(player));
        squareTarget.remove(player.getSquare()); //create squarelist of possible vortex locations

        for (Square squareIterate: squareTarget)
        {
            List<Player> playerListHashMap = new ArrayList<>();
            playersTarget.clear();
            squareDistance1.clear();
            playerTemp.setSquare(squareIterate);
            squareDistance1.addAll(player.getSquare().getGameBoard().getArena().squareReachableNoWall(playerTemp.getSquare().getX(), playerTemp.getSquare().getY(), 1));
            for (Square squareIterate2: squareDistance1) //all the square at distance 1 from the possible location of the vortex
                {
                  playersTarget.addAll(squareIterate2.getPlayerList());
                  if (playersTarget.contains(player))
                      playersTarget.remove(player);
                  if (playersTarget.contains(playerTemp))
                      playersTarget.remove(playerTemp);
                }

            playerListHashMap.addAll(playersTarget);

            if (!playerListHashMap.isEmpty())
                hashMapreturn.put(squareIterate, playerListHashMap);

        }

        return hashMapreturn;

    }


    /**
     *
     * @return
     */
    public HashMap<String, List<ColorId>> checkBasicMode ()
    {
        if (!checkAvailableMode()[0])//check mode
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");

        HashMap<Square, List<Player>> hashMapFull = checkBasicModeFull();

        HashMap<String, List<ColorId>> hashMapToReturn = new HashMap<>();

        List<Player> playerList = new ArrayList<>();


        List<ColorId> colorIdListAttributes = new ArrayList<>();

        for (Square squareIterate : hashMapFull.keySet())
        {
            colorIdListAttributes = new ArrayList<>();
            playerList.clear();

            playerList.addAll(hashMapFull.get(squareIterate));

            for (Player playerIterate : playerList)
            {
                colorIdListAttributes.add(playerIterate.getColor());
            }

            hashMapToReturn.put(squareIterate.toStringCoordinates(), colorIdListAttributes);
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
     * @param playerTarget1
     * @param playerTarget2
     * @param vortexSquareAsString
     */
    public void blackHoleMode (ColorId playerTarget1, ColorId playerTarget2, String vortexSquareAsString)
    {

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
        {
            basicMode(((ResponseVortexCannon) responseMessage).getTargetPlayerBasicMode(), ((ResponseVortexCannon) responseMessage).getTargetVortexSquareAsString());
            blackHoleMode(((ResponseVortexCannon) responseMessage).getTarget1BlackHoleMode(), ((ResponseVortexCannon) responseMessage).getTarget2BlackHoleMode(), ((ResponseVortexCannon) responseMessage).getTargetVortexSquareAsString());
        } else
            basicMode(((ResponseVortexCannon) responseMessage).getTargetPlayerBasicMode(), ((ResponseVortexCannon) responseMessage).getTargetVortexSquareAsString());
    }

    /**
     *
     * @return
     */
    @Override
    public RequestInput getRequestMessage()
    {
        return new RequestVortexCannon(checkAvailableMode(),checkBasicMode());
    }
}

