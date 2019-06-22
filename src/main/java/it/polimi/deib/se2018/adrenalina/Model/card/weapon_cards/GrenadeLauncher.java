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
     *
     * @param color
     * @param weaponID
     * @param isLoaded
     */
    public GrenadeLauncher (Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        this.name = "Lanciagranate";
        yellowAmmoCost = 0;
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
     *
     * @return
     * @throws IllegalStateException
     */
    private List<Player> checkBasicModePlayers() throws IllegalStateException
    {

        Set<Player> playersTarget = player.playerThatSee(player.getSquare().getGameBoard()); //Obtain all players that can be seen

        playersTarget.remove(player); //Remove the player that has this card

        return (List) playersTarget; //Returns all targets
    }

    public List<ColorId> checkBasicMode()
    {
        if (!checkAvailableMode()[0])//check mode
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");

        List<ColorId> colorIdList = new ArrayList<>();
        List<Player> playerList = checkBasicModePlayers();

        for (Player playerIterate : playerList)
        {
            colorIdList.add(playerIterate.getColor());
        }

        return colorIdList; //Returns all targets

    }

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
     *
     * @param colorPlayer
     * @param squareToMoveCoordinatesAsString
     * @throws IllegalStateException
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
     *
     * @return
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
     *
     * @param squareTargetCoordinatesAsString
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

        for (Player playerIterate : square.getPlayerList())
        {
            doDamage(playerIterate, 1);//Do one damage
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
