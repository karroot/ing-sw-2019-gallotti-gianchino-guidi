package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestInput;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestShockwave;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseShockwave;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * This class represents the weapon ShockWave
 *
 * @author Cysko7927
 */
public class Shockwave extends WeaponCard
{

    private boolean[] availableMethod = new boolean[2];

    /**
     * Create the card Shock wave
     * @param color color of weapon
     * @param weaponID Id of the card
     * @param isLoaded Indicates if the weapon is loaded or not
     * @exception NullPointerException if color is null
     */
    public Shockwave( Color color, int weaponID, boolean isLoaded)
    {
        super( color, weaponID, isLoaded);
        this.name = "Onda d'Urto";
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

        //If there are at least 3 players and one in each square
        if (isLoaded() && MethodsWeapons.playersReachable(player.getSquare(),1).size() > 3 && MethodsWeapons.thereIsAPlayerInEachSquare(player))// the first mode can be used
            availableMethod[0] = true;

        if (isLoaded() && MethodsWeapons.playersReachable(player.getSquare(),1).size() > 1 && player.getAmmoYellow() >=1)//If the second mode can be used
            availableMethod[1] = true;



        return availableMethod;

    }

    /**
     * Return the list of all target available for using the basic mode of this weapon
     * The object returned is a hash map with key a string that represent a square to distance 1 from player
     * that uses the Shockwave and value a list of all player that are in that square
     * @return all player that can be affected with the Shockwave in basic mode
     * @exception IllegalStateException if the basic mode can't be used
     */
    public Map<String,List<ColorId>> checkBasicMode() throws IllegalStateException
    {
        if (!checkAvailableMode()[0]) //check mode
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        Map<String,List<ColorId>> result = new HashMap<>();

        Set<Player> target = MethodsWeapons.playersReachable(player.getSquare(),1); //Obtain all players that can be seen

        target.remove(player);//Remove the player that has this card

        Set<Square> squares = target.stream().map(Player::getSquare).collect(Collectors.toSet());

        for (Square t:squares)
        {
            result.putIfAbsent("x = "+t.getX()+": y = "+t.getY(),t.getPlayerListColor());
        }

        return result;//Returns all targets
    }

    /**
     * It uses the basic mode of the Shockwave
     * @param players players affected by weapon
     * @exception IllegalStateException if the basic mode can't be used
     */
    public void basicMode(List<Player> players) throws IllegalStateException
    {
        if (!checkAvailableMode()[0])//check mode
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile."
            );

        for (Player x:players)//For each player
        {
            doDamage(x,1);//Do one damage
        }

        isLoaded = false;

    }

    /**
     * It uses the alternative mode of the Shockwave
     * It does one damage at all players at distance 1
     * @exception IllegalStateException if the alternative mode can't be used
     */
    public void inTsunamirMode() throws IllegalStateException
    {
        if (!checkAvailableMode()[1])//Check mode
            throw  new IllegalStateException("Modalità avanzata dell'arma "+name+" non eseguibile.");

        Set<Player> players = MethodsWeapons.playersReachable(player.getSquare(), 1);

        players.remove(player);

        for (Player x:players) //For each player
        {
            doDamage(x,1);//Do one damage
        }

        this.player.setAmmoYellow(this.player.getAmmoYellow() - 1);

        isLoaded = false;
    }

    /**
     * Use the weapons taking the targets from response message
     * @param responseMessage response message specified for the weapon
     */
    public void useWeapon(ResponseInput responseMessage)
    {
        ResponseShockwave msg = (ResponseShockwave) responseMessage;

        if (msg.isMode())
            inTsunamirMode();
        else
            basicMode(MethodsWeapons.ColorToPlayer(msg.getTargetsBasicMode(),player.getSquare().getGameBoard()));
    }

    /**
     * Generate the request message for the Shockwave to send through the network
     * @return request message for the Shockwave
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