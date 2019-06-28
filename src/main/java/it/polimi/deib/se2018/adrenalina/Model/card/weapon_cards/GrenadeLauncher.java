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

public class GrenadeLauncher extends WeaponCard
{

    private boolean[] availableMethod = new boolean[2];


    /**
     * It is the public constructor for the class.
     * @param color is the color of the card
     * @param weaponID is the unique id to identify the card
     * @param isLoaded to indicate if the weapon is loaded
     */
    public GrenadeLauncher (Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        this.name = "Lanciagranate";
        yellowAmmoCost = 0;
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
            throw new IllegalStateException("Carta: " + name + " non appartiene a nessun giocatore.");//If this card doesn't belong to any player, it launches an exception


        availableMethod[0] = false; //I suppose that the modes can't be used
        availableMethod[1] = false;

        if (isLoaded() && !player.playerThatSee(player.getSquare().getGameBoard()).isEmpty())
        {
                availableMethod[0] = true;

        }

        if (availableMethod[0] && player.getAmmoRed() > 0)
        {
                availableMethod[1] = true;
        }

        return availableMethod;

    }

    /**
     * This method checks the possible targets for the basic mode of this weapon.
     *
     * @return a list of players as possible targets
     * @throws IllegalStateException if this card doesn't belong at a player
     */
    private List<Player> checkBasicModePlayers() throws IllegalStateException
    {

        Set<Player> playersTarget = player.playerThatSee(player.getSquare().getGameBoard()); //Obtain all players that can be seen

        playersTarget.remove(player); //Remove the player that has this card

        List<Player> playerListToReturn = new ArrayList<>();
        playerListToReturn.addAll(playersTarget);
        return playerListToReturn; //Returns all targets
    }

    /**
     * This method checks the possible targets for the basic mode of this weapon.
     *
     * @return a list of ColorId of possible targets
     */
    public List<ColorId> checkBasicMode()
    {
        if (!checkAvailableMode()[0])//check mode
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");

        List<ColorId> colorIdList = new ArrayList<>();
        List<Player> playerList =  checkBasicModePlayers();

        for (Player playerIterate : playerList)
        {
            colorIdList.add(playerIterate.getColor());
        }

        return colorIdList; //Returns all targets

    }

    /**
     * Checks the square target for the basic mode.
     *
     * @return an hashmap of ColorId and square as strings
     */
    public HashMap<ColorId, List<String>> checkBasicModeSquares ()
    {
        HashMap<ColorId, List<String>> hashMapToReturn = new HashMap<>();
        List<ColorId> colorIdList = checkBasicMode();
        List<String> squareAsStringCoordinatesList = new ArrayList<>();
        Set<Square> squareSet;


        for (ColorId colorIdIterate : colorIdList)
        {
            squareSet = player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorIdIterate)).collect(Collectors.toList()).get(0).getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 1);

            for (Square squareIterate : squareSet)
            {
                squareAsStringCoordinatesList.add(squareIterate.toStringCoordinates());
            }

            hashMapToReturn.put(colorIdIterate, squareAsStringCoordinatesList);

            squareSet.clear();
            squareAsStringCoordinatesList.clear();
        }

        return hashMapToReturn;
    }


    /**
     * This method implements the basic mode of the weapon.
     *
     * @param colorPlayer is the target player
     * @param squareToMoveCoordinatesAsString is the square where the player has to be moved
     * @throws IllegalStateException if this card doesn't belong at a player
     */
    public void basicMode(ColorId colorPlayer, String squareToMoveCoordinatesAsString) throws IllegalStateException
    {
        if (!checkAvailableMode()[0])//check mode
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");

        int x = MethodsWeapons.getXFromString(squareToMoveCoordinatesAsString);
        int y = MethodsWeapons.getYFromString(squareToMoveCoordinatesAsString);

        doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0),1);
        if (squareToMoveCoordinatesAsString != null)
            moveTarget(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0), x, y);

        isLoaded = false;
    }

    /**
     * Checks the square target for the extra grenade.
     *
     * @return a list of square coordinate of possible targets
     */
    public List<String> checkExtraGrenade ()
    {
        if (!checkAvailableMode()[1])//check mode
            throw  new IllegalStateException("Granata extra dell'arma "+name+" non eseguibile.");

        HashMap<Square, ArrayList<Player>> hashSquarePlayer = new HashMap<Square, ArrayList<Player>>();
        List<String> squareListCoordinatesAsString = new ArrayList<>();

        Set<Square> squaresTarget = MethodsWeapons.squareThatSee(player); //Obtain all squares that player can see
        squaresTarget.remove(player.getSquare());

        for (Square squareIterate : squaresTarget)
            hashSquarePlayer.put(squareIterate, (ArrayList) squareIterate.getPlayerList());

        for (Square squareIterate : hashSquarePlayer.keySet())
            squareListCoordinatesAsString.add(squareIterate.toStringCoordinates());


        return squareListCoordinatesAsString;//Returns all targets
    }

    /**
     * It implements the extra Granade for this weapon.
     *
     * @param squareTargetCoordinatesAsString is the square target
     */
    public void extraGrenade (String squareTargetCoordinatesAsString)
    {
        if (!checkAvailableMode()[1])//check mode
            throw  new IllegalStateException("Granata extra dell'arma "+name+" non eseguibile.");

        int x = MethodsWeapons.getXFromString(squareTargetCoordinatesAsString);
        int y = MethodsWeapons.getYFromString(squareTargetCoordinatesAsString);

        Square square = null;

        try {
            square = player.getSquare().getGameBoard().getArena().getSquare(x, y);
        } catch (SquareNotInGameBoard squareNotInGameBoard) {
            squareNotInGameBoard.printStackTrace();
        }

        if (square != null) {
            for (Player playerIterate : square.getPlayerList())
            {
                doDamage(playerIterate, 1);//Do one damage
            }
        }

        player.setAmmoRed(player.getAmmoRed() - 1);
        isLoaded = false;

    }

    /**
     *
     * @param responseInput
     */
    @Override
    public void useWeapon(ResponseInput responseInput) {
        basicMode(((ResponseGrenadeLauncher) responseInput).getTargetBasicMode(), ((ResponseGrenadeLauncher) responseInput).getTargetSquareToMoveBasicModeAsString());
        if (((ResponseGrenadeLauncher) responseInput).isExtraEffect())
            extraGrenade(((ResponseGrenadeLauncher) responseInput).getTargetSquareExtraGrenadeAsString());

    }


    /**
     *
     * @return
     */
    @Override
    public RequestInput getRequestMessage()
    {
        return new RequestGrenadeLauncher(checkAvailableMode(),checkBasicMode(), checkExtraGrenade(), checkBasicModeSquares());
    }


}
