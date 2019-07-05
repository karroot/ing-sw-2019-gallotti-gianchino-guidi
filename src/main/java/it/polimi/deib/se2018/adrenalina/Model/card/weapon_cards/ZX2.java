package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;


import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestInput;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestZX2;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseZX2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class represents the weapon ZX2
 *
 * @author Cysko7927
 */
public class ZX2 extends WeaponCard
{

    private boolean[] availableMethod = new boolean[2];

    /**
     * Create the card ZX2
     * @param color color of weapon
     * @param weaponID Id of the card
     * @param isLoaded Indicates if the weapon is loaded or not
     * @exception NullPointerException if color is null
     */
    public ZX2(Color color, int weaponID, boolean isLoaded) throws NullPointerException
    {
        super(color, weaponID, isLoaded);
        this.name = "ZX2";
        yellowAmmoCost = 1;
        blueAmmoCost = 0;
        redAmmoCost = 1;
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

        if (isLoaded() && player.playerThatSee(player.getSquare().getGameBoard()).size() > 1)//If the first mode can be used
            availableMethod[0] = true;

        if (isLoaded() && player.playerThatSee(player.getSquare().getGameBoard()).size() > 3 )//If the second mode can be used
            availableMethod[1] = true;



        return availableMethod;

    }

    /**
     * Return the list of all target available for using the basic mode of this weapon
     * @return all player that can be affected with the ZX2 in basic mode
     * @exception IllegalStateException if the basic mode can't be used
     */
    public List<ColorId> checkBasicMode() throws IllegalStateException
    {
        if (!checkAvailableMode()[0]) //check mode
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");

        Set<Player> target = player.playerThatSee(player.getSquare().getGameBoard()); //Obtain all players that can be seen

        target.remove(player);//Remove the player that has this card

        return new ArrayList<>(target).stream().map(Player::getColor).collect(Collectors.toList());//Returns all targets
    }

    /**
     * It uses the basic mode of the ZX2
     * @param player player affected by weapon
     * @exception IllegalStateException if the basic mode can't be used
     */
    public void basicMode(Player player) throws IllegalStateException
    {
        if (!checkAvailableMode()[0])//check mode
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");

        doDamage(player,1);//Do one damage
        markTarget(player,2);//Do two marks

        isLoaded = false;
    }

    /**
     * Return the list of all target available for using the alternative mode of this weapon
     * @return all player that can be affected with the ZX2 in alternative mode
     * @exception IllegalStateException if the alternative mode can't be used
     */
    public List<ColorId> checkInScannerMode() throws IllegalStateException
    {

        if (!checkAvailableMode()[1])
            throw  new IllegalStateException("Modalità avanzata dell'arma "+name+" non eseguibile.");

        Set<Player> playerList = player.playerThatSee(player.getSquare().getGameBoard()); //Obtain all players that can be seen

        playerList.remove(player);//Remove from targets the player that shoot

        return new ArrayList<>(playerList).stream().map(Player::getColor).collect(Collectors.toList()); //Returns all targets
    }

    /**
     * It uses the alternative mode of the ZX2
     * @param players players affected by weapon
     * @exception IllegalStateException if the alternative mode can't be used
     */
    public void inScannerMode(List<Player> players) throws IllegalStateException
    {
        if (!checkAvailableMode()[1])//Check mode
            throw  new IllegalStateException("Modalità avanzata dell'arma "+name+" non eseguibile.");

        for (Player x:players) //For each player
        {
            markTarget(x,1);//Do one mark
        }

        isLoaded = false;
    }

    /**
     * Use the weapons taking the targets from response message
     * @param responseMessage response message specified for the weapon
     */
    public void useWeapon(ResponseInput responseMessage)
    {
        ResponseZX2 msg = (ResponseZX2) responseMessage;

        if (msg.isMode())
            inScannerMode(MethodsWeapons.ColorToPlayer(msg.getTargetsAlternativeMode(),player.getSquare().getGameBoard()));
        else
            basicMode(MethodsWeapons.ColorToPlayer(msg.getTargetBasicMode(),player.getSquare().getGameBoard()));
    }

    /**
     * Generate the request message for the ZX2 to send through the network
     * @return request message for the ZX2
     */
    @Override
    public RequestInput getRequestMessage()
    {
        if (checkAvailableMode()[0] && checkAvailableMode()[1])

            return new RequestZX2(checkAvailableMode(),checkBasicMode(), checkInScannerMode());

        else if(checkAvailableMode()[0] && !checkAvailableMode()[1])

            return new RequestZX2(checkAvailableMode(),checkBasicMode(),new ArrayList<>());

        else
            return new RequestZX2(checkAvailableMode(),new ArrayList<>(),checkInScannerMode());
    }
}