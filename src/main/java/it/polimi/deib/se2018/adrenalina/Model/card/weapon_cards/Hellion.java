package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestHellion;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestInput;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseHellion;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * This class implements the weapon Hellion.
 *
 * @author gioguidi
 */


public class Hellion extends WeaponCard
{

    private boolean[] availableMethod = new boolean[2];

    /**
     * It is the public constructor for the class.
     *
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
     * It checks which modes of the weapon can be used.
     *
     * @return an array of boolean of which modes are available to the players
     */
    public boolean[] checkAvailableMode()
    {
        if (player == null)
            throw new IllegalStateException("Carta: " + name + " non appartiene a nessun giocatore.");

        int j=1;
        availableMethod[0] = false;//I suppose that the modes can't be used
        availableMethod[j] = false;

        if (isLoaded()) //the basic mode requires that the player sees a target with a minimum distance of 1 square (the square must be different than the weapon owner's one)
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

        if (isLoaded() && player.getAmmoRed() > 0 && availableMethod[0])
            availableMethod[1] = true;

        return availableMethod;

    }


    /**
     * Method to check the targets for the basic mode.
     *
     * @return an hashmap of String and lists of ColorId
     */
    public HashMap<String, List<ColorId>> checkBasicMode ()
    {
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
     * Method that implements the basic mode of this weapon.
     *
     * @param colorPlayer is the target
     */
    public void basicMode(ColorId colorPlayer)
    {
        if(this.player.getSquare().getGameBoard().isTerminatorMode() && colorPlayer.equals(ColorId.PURPLE))
            doDamage(player.getSquare().getGameBoard().getTermi(),1);
        else
            doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0),1);

        for (Player playerIterate : player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0).getSquare().getPlayerList())
        {
            if(this.player.getSquare().getGameBoard().isTerminatorMode() && playerIterate.getColor().equals(ColorId.PURPLE))
                markTarget(player.getSquare().getGameBoard().getTermi(),1);
            else
                markTarget(playerIterate,1);
        }

        isLoaded = false;
    }


    /**
     * It is the full implementation to check the targets for the nano tracer mode. It will use the same logic of the basic mode check.
     *
     * @return an hashmap of String and lists of ColorId
     */
    public HashMap<String, List<ColorId>> checkNanoTracerMode ()
    {
        return checkBasicMode();
    }

    /**
     * It implements the alternative mode for the weapon.
     *
     * @param colorPlayer is the target player (1 damage to the target and 2 marks to the target and each one in that square)
     */
    public void nanoTracerMode(ColorId colorPlayer)
    {
        if(this.player.getSquare().getGameBoard().isTerminatorMode() && colorPlayer.equals(ColorId.PURPLE))
            doDamage(player.getSquare().getGameBoard().getTermi(),1);
        else
            doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0),1);

        for (Player playerIterate : player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0).getSquare().getPlayerList())
        {
            if(this.player.getSquare().getGameBoard().isTerminatorMode() && playerIterate.getColor().equals(ColorId.PURPLE))
                markTarget(player.getSquare().getGameBoard().getTermi(),2);
            else
                markTarget(playerIterate,2);
        }

        player.setAmmoRed(player.getAmmoRed()-1);

        isLoaded = false;
    }

    /**
     * This method extracts the targets for the modes of the weapon.
     *
     * @param responseMessage is the response generated for the weapon.
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
     * This method will create a request message for this weapon.
     *
     * @return the new request
     */
    @Override
    public RequestInput getRequestMessage()
    {
        return new RequestHellion(checkAvailableMode(), checkBasicMode(), checkNanoTracerMode());
    }

}
