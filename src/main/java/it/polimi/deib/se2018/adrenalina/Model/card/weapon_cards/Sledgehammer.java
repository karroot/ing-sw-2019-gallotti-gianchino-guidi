package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestInput;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestSledgehammer;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseSledgehammer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * This class represents the weapon Sledgehammer
 *
 * @author Cysko7927
 */
public class Sledgehammer extends WeaponCard
{

    private boolean[] availableMethod = new boolean[2];

    /**
     * Create the card Sledgehammer
     * @param color color of weapon
     * @param weaponID Id of the card
     * @param isLoaded Indicates if the weapon is loaded or not
     * @exception NullPointerException if color is null
     */
    public Sledgehammer(Color color, int weaponID, boolean isLoaded) {
        super(color, weaponID, isLoaded);
        this.name = "Martello Ionico";
        yellowAmmoCost = 1;
        blueAmmoCost = 0;
        redAmmoCost = 0;
    }

    /**
     * Check which modes of the weapon can be used by player that has this weapon
     * @return array of booleans of size 2 the first represent the basic mode the second the alternative mode
     * @exception IllegalStateException if this card doesn't belong at a player
     */
    public boolean[] checkAvailableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: " + name + " non appartiene a nessun giocatore.");//If this card doesn't belong at a player launch exception

        availableMethod[0] = false; //I suppose that the modes can't be used
        availableMethod[1] = false;

        if (isLoaded() && player.getSquare().getPlayerList().size() > 1)//If the first mode can be used
            availableMethod[0] = true;

        if (isLoaded() && player.getSquare().getPlayerList().size() > 1 && player.getAmmoRed() >= 1)//If the second mode can be used
            availableMethod[1] = true;


        return availableMethod;

    }

    /**
     * Return the list of all target available for using of this weapon
     * @return all player that can be affected with the Sledgehammer in basic mode
     * @exception IllegalStateException if the basic mode can't be used
     */
    public List<ColorId> checkTargetForModes() throws IllegalStateException
    {
        if (!checkAvailableMode()[0]) //check mode
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");

        List<Player> target = player.getSquare().getPlayerList(); //Obtain all players are in same square

        target.remove(player);//Remove the player that has this card

        return new ArrayList<>(target).stream().map(Player::getColor).collect(Collectors.toList());//Returns all targets
    }

    /**
     * It uses the basic mode of the Sledgehammer
     * @param player player affected by weapon
     * @exception IllegalStateException if the basic mode can't be used
     */
    public void basicMode(Player player) throws IllegalStateException
    {
        if (!checkAvailableMode()[0])//check mode
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");

        doDamage(player,2);//Do two damage

        isLoaded = false;
    }

    /**
     * Return the list of all square available for moving the player that will affect by Sledgehammer
     * in "in pulverize" mode
     * @return square available
     * @exception IllegalStateException if the alternative mode can't be used
     */
    public List<Square> checkMoveForAlternativeMode() throws IllegalStateException
    {

        if (!checkAvailableMode()[1])
            throw  new IllegalStateException("Modalità avanzata dell'arma "+name+" non eseguibile.");

        //Obtain all square to distance 2
        Set<Square> squares = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 2);

         return squares
                .stream()
                .filter(square -> MethodsWeapons.checkSquareOneDirectionTwoMoves(player.getSquare(), square.getX(), square.getY()))
                .collect(Collectors.toList());
    }

    /**
     * It uses the alternative mode of the Sledgehammer
     * Do 3 damage point at a player and move him into a direction
     * @param player players affected by weapon
     * @param x coordinate x of the square where the player affected by weapon will move
     * @param y coordinate y of the square where the player affected by weapon will move
     * @exception IllegalStateException if the alternative mode can't be used
     */
    public void inPulverizeMode(Player player ,int x,int y) throws IllegalStateException
    {
        if (!checkAvailableMode()[1])//Check mode
            throw  new IllegalStateException("Modalità avanzata dell'arma "+name+" non eseguibile.");

        doDamage(player,3);
        moveTarget(player,x,y);

        this.player.setAmmoRed(this.player.getAmmoRed() - 1);

        isLoaded = false;
    }


    /**
     * Use the weapons taking the targets from response message
     * @param responseMessage response message specified for the weapon
     */
    public void useWeapon(ResponseInput responseMessage)
    {
        ResponseSledgehammer msg = (ResponseSledgehammer) responseMessage;

        if (msg.isMode())
            inPulverizeMode(MethodsWeapons.ColorToPlayer(msg.getTarget(),player.getSquare().getGameBoard()),
                    msg.getX(),msg.getY());
        else
            basicMode(MethodsWeapons.ColorToPlayer(msg.getTarget(),player.getSquare().getGameBoard()));
    }

    /**
     * Generate the request message for the Sledgehammer to send through the network
     * @return request message for the Sledgehammer
     */
    @Override
    public RequestInput getRequestMessage()
    {
        if (checkAvailableMode()[0] && checkAvailableMode()[1])

            return new RequestSledgehammer(checkAvailableMode(),checkTargetForModes(), checkMoveForAlternativeMode());

        else if(checkAvailableMode()[0] && !checkAvailableMode()[1])

            return new RequestSledgehammer(checkAvailableMode(),checkTargetForModes(),new ArrayList<>());

        else
            return new RequestSledgehammer(checkAvailableMode(),new ArrayList<>(),checkMoveForAlternativeMode());
    }
}