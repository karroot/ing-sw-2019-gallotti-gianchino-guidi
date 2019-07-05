package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestInput;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestVortexCannon;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseVortexCannon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


/**
 * This class implements the weapon Vortex Cannon.
 *
 * @author gioguidi
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
     * It checks which modes of the weapon can be used.
     *
     * @return an array of boolean of which modes are available to the players
     */
    public boolean[] checkAvailableMode()
    {
        int h = 0;

        if (player == null)
            throw new IllegalStateException("Carta: " + name + " non appartiene a nessun giocatore.");//If this card doesn't belong to any player, it launches an exception

        availableMethod[0] = false;
        availableMethod[1] = false;

        HashMap<Square, List<Player>> hashMapCheck = checkBasicModeFull();

        if (isLoaded() && !hashMapCheck.keySet().isEmpty())
        {
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
     * This method check all the target for the basic mode.
     *
     * @return an hashmap of target squares as keys and a list of target players in each square as values.
     */
    private HashMap<Square, List<Player>> checkBasicModeFull()
    {

        HashMap<Square, List<Player>> hashMapReturn = new HashMap<>();

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
                  playersTarget.remove(player);
                  playersTarget.remove(playerTemp);
                }

            playerListHashMap.addAll(playersTarget);

            if (!playerListHashMap.isEmpty())
                hashMapReturn.put(squareIterate, playerListHashMap);

        }

        return hashMapReturn;

    }


    /**
     * This method covert the targets into string and colorId.
     *
     * @return an hashMap of targets as String as keys and list of ColorID as targets
     */
    public HashMap<String, List<ColorId>> checkBasicMode ()
    {
        if (!checkAvailableMode()[0])//check mode
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");

        HashMap<Square, List<Player>> hashMapFull = checkBasicModeFull();

        HashMap<String, List<ColorId>> hashMapToReturn = new HashMap<>();

        List<Player> playerList = new ArrayList<>();


        List<ColorId> colorIdListAttributes;

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
     * This method implements the basic mode of the weapon.
     *
     * @param colorPlayer is the target player colorId
     * @param squareToMoveCoordinatesAsString is the square location of the vortex
     */
    public void basicMode (ColorId colorPlayer, String squareToMoveCoordinatesAsString)
    {
        if (!checkAvailableMode()[0])//check mode
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");

        int x = MethodsWeapons.getXFromString(squareToMoveCoordinatesAsString);
        int y = MethodsWeapons.getYFromString(squareToMoveCoordinatesAsString);

        if(this.player.getSquare().getGameBoard().isTerminatorMode() && colorPlayer.equals(ColorId.PURPLE))
            doDamage(player.getSquare().getGameBoard().getTermi(),2);
        else
            doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0),2);


        if(this.player.getSquare().getGameBoard().isTerminatorMode() && colorPlayer.equals(ColorId.PURPLE))
            MethodsWeapons.moveTarget(player.getSquare().getGameBoard().getTermi(), x, y);
        else
            MethodsWeapons.moveTarget(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0), x, y);

        isLoaded = false;
    }



    /**
     * This method implements the black hole mode of the weapon.
     *
     * @param playerTarget1 is the additional target1 for the black hole mode.
     * @param playerTarget2 is the additional target2 for the black hole mode.
     * @param vortexSquareAsString id the location of the opened vortex.
     */
    public void blackHoleMode (ColorId playerTarget1, ColorId playerTarget2, String vortexSquareAsString)
    {

        int x = MethodsWeapons.getXFromString(vortexSquareAsString);
        int y = MethodsWeapons.getYFromString(vortexSquareAsString);

        doDamageAndMoveTarget(playerTarget1, x, y);

        if (playerTarget2 != null)
        {
            doDamageAndMoveTarget(playerTarget2, x, y);
        }

        player.setAmmoRed(player.getAmmoRed() - 1);
        isLoaded = false;

    }

    /**
     * This method will deal 1 damage to the target and move it to the square with coordinates x and y.
     *
     * @param playerTarget is the target to be damaged
     * @param x is the x coordinate of the square to move him
     * @param y is the y coordinate of the square to move him
     */
    private void doDamageAndMoveTarget(ColorId playerTarget, int x, int y) {
        if(this.player.getSquare().getGameBoard().isTerminatorMode() && playerTarget.equals(ColorId.PURPLE))
            doDamage(player.getSquare().getGameBoard().getTermi(),1);
        else
            doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(playerTarget)).collect(Collectors.toList()).get(0),1);

        if(this.player.getSquare().getGameBoard().isTerminatorMode() && playerTarget.equals(ColorId.PURPLE))
            MethodsWeapons.moveTarget(player.getSquare().getGameBoard().getTermi(), x, y);
        else
            MethodsWeapons.moveTarget(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(playerTarget)).collect(Collectors.toList()).get(0), x, y);
    }

    /**
     * This method extracts the targets for the modes of the weapon.
     *
     * @param responseMessage is the response generated for the weapon.
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
     * This method will create a request message for this weapon.
     *
     * @return the new request
     */
    @Override
    public RequestInput getRequestMessage()
    {
        return new RequestVortexCannon(checkAvailableMode(),checkBasicMode());
    }
}

