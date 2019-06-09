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
     * This is the constructor for the card Grenade Launcher
     *
     * @param color
     * @param weaponID
     * @param isLoaded
     */
    public GrenadeLauncher (Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        this.name = "Grenade Launcher";
        yellowAmmoCost = 0;
        blueAmmoCost = 0;
        redAmmoCost = 1;
    }

    public boolean[] checkAvailableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: " + name + " non appartiene a nessun giocatore");//If this card doesn't belong to any player, it launches an exception


        availableMethod[0] = false;//I suppose that the modes can't be used
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
     * Return the list of all target available for using the basic mode of this weapon
     *
     * @return all player that can be affected with the weapon in basic mode
     * @exception IllegalStateException if the basic mode can't be used
     */
    public List<ColorId> checkBasicMode() throws IllegalStateException
    {
        if (!checkAvailableMode()[0]) //check mode
            throw  new IllegalStateException("Modalità xxx dell'arma: "+name+" non eseguibile");

        List<ColorId> colorIdList = new ArrayList<>();
        Set<Player> playersTarget = player.playerThatSee(player.getSquare().getGameBoard()); //Obtain all players that can be seen

        playersTarget.remove(player);//Remove the player that has this card

        for (Player playerIterate : playersTarget)
        {
            colorIdList.add(playerIterate.getColor());
        }

        return colorIdList;//Returns all targets
    }



    public void basicMode(ColorId colorPlayer, String squareToMoveCoordinatesAsStringint) throws IllegalStateException
    {
        if (!checkAvailableMode()[0])//check mode
            throw  new IllegalStateException("Modalità xxx dell'arma: "+name+" non eseguibile");

        int x = MethodsWeapons.getXFromString(squareToMoveCoordinatesAsStringint);
        int y = MethodsWeapons.getYFromString(squareToMoveCoordinatesAsStringint);

        doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0),1);
        if (squareToMoveCoordinatesAsStringint != null)
            moveTarget(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0), x, y);

        isLoaded = false;
    }


    public List<String> checkExtraGrenade ()
    {

        HashMap<Square, ArrayList<Player>> hashSquarePlayer = new HashMap<Square, ArrayList<Player>>();

        if (!checkAvailableMode()[1]) //check mode
            throw  new IllegalStateException("Modalità xx dell'arma: "+name+" non eseguibile");

        List<String> squareListCoordinatesAsString = new ArrayList<>();

        Set<Square> squaresTarget = MethodsWeapons.squareThatSee(player); //Obtain all squares that player  can see
        squaresTarget.remove(player.getSquare());

        for (Square squareIterate : squaresTarget)
            hashSquarePlayer.put(squareIterate, (ArrayList) squareIterate.getPlayerList());

        for (Square squareIterate : hashSquarePlayer.keySet())
            squareListCoordinatesAsString.add(squareIterate.toStringCoordinates());


        return squareListCoordinatesAsString;//Returns all targets
    }

    public void extraGrenade (String squareTargetCoordinatesAsString)
    {
        if (!checkAvailableMode()[1]) //check mode
            throw  new IllegalStateException("Modalità xx dell'arma: "+name+" non eseguibile");

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

    @Override
    public void useWeapon(ResponseInput responseInput) {
        basicMode(((ResponseGrenadeLauncher) responseInput).getTargetBasicMode(), ((ResponseGrenadeLauncher) responseInput).getTargetSquareToMoveBasicModeAsString());
        if (((ResponseGrenadeLauncher) responseInput).isExtraEffect())
            extraGrenade(((ResponseGrenadeLauncher) responseInput).getTargetSquareExtraGrenadeAsString());

    }


    @Override
    public RequestInput getRequestMessage()
    {
           // return new RequestGrenadeLauncher(checkAvailableMode(),checkBasicMode(), );
        return null;
    }




}
