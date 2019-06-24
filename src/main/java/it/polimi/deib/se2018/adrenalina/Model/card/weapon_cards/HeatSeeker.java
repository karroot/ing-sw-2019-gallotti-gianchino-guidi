package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import it.polimi.deib.se2018.adrenalina.communication_message.*;


import java.util.*;
import java.util.stream.Collectors;


/**
 * @author giovanni
 */


public class HeatSeeker extends WeaponCard
{

    private boolean[] availableMethod = new boolean[1];


    /**
     * It is the public constructor for the class.
     * @param color is the color of the card
     * @param weaponID is the unique id to identify the card
     * @param isLoaded to indicate if the weapon is loaded
     */
    public HeatSeeker( Color color, int weaponID, boolean isLoaded) throws NullPointerException
    {
        super( color, weaponID, isLoaded);
        this.name = "Razzo Termico";
        yellowAmmoCost = 1;
        blueAmmoCost = 0;
        redAmmoCost = 2;
    }

    /**
     * It checks which modes of the weapon can be used
     * @return an array of boolean of which modes are available to the players
     * @throws IllegalStateException if this card doesn't belong at a player
     */
    public boolean[] checkAvailableMode() throws IllegalStateException {
        if (player == null)
            throw new IllegalStateException("Carta: " + name + " non appartiene a nessun giocatore.");

        availableMethod[0] = false; //I suppose that the modes can't be used


        if (isLoaded() && !checkPlayers().isEmpty() )//If the first mode can be used
            availableMethod[0] = true;

        return availableMethod;

    }


    /**
     * It checks the possible targets for the basic mode
     *
     * @return a list of ColorId of possible targets
     * @throws IllegalStateException if the mode is not available
     */
    public List<ColorId> checkBasicMode() throws IllegalStateException
    {
        if (!checkAvailableMode()[0])//check mode
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");

        return checkPlayers();//Returns all targets
    }

    /**
     *  It implements the basic mode of the weapon
     *
     * @param colorPlayer is the target player as ColorId
     * @throws IllegalStateException if the mode is not available
     */
    public void basicMode(ColorId colorPlayer) throws IllegalStateException
    {
        if (!checkAvailableMode()[0])//check mode
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");

        doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer))
                        .collect(Collectors.toList()).get(0),3);

        this.isLoaded = false;
    }


    /**
     * Checks the possible target for the basic mode
     *
     * @return a list of ColorId of possible targets
     */
    private List<ColorId> checkPlayers ()
    {
        List<ColorId> colorIdList = new ArrayList<>();

        List<Player> playerList = player.getSquare().getGameBoard().getAllPlayer();
        playerList.remove(player);

        Set<Player> playerSet = player.playerThatSee(player.getSquare().getGameBoard());

        playerSet.remove(player);
        playerList.removeAll(player.playerThatSee(player.getSquare().getGameBoard()));
        if (!playerList.isEmpty())
        {
            for (Player playerIterate : playerList)
                colorIdList.add(playerIterate.getColor());
        }

        return colorIdList;

    }

    /**
     *
     * @param responseInput
     */
    @Override
    public void useWeapon(ResponseInput responseInput)
    {
            basicMode(((ResponseHeatSeeker) responseInput).getTagetBasicMode());

    }


    /**
     *
     * @return
     */
    @Override
    public RequestInput getRequestMessage()
    {
        if (checkAvailableMode()[0])

            return new RequestHeatSeeker(checkAvailableMode(),checkBasicMode());
        else throw new IllegalStateException();
    }


}