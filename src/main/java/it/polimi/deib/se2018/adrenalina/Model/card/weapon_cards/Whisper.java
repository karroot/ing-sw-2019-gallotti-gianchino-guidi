package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestInput;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestWhisper;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseWhisper;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons.playersReachable;


public class Whisper extends WeaponCard
{
    private boolean[] availableMethod = new boolean[1];

    /**
     * Create the card Whisper
     * @param color color of weapon
     * @param weaponID Id of the card
     * @param isLoaded Indicates if the weapon is loaded or not
     * @exception NullPointerException if color is null
     */
    public Whisper( Color color, int weaponID, boolean isLoaded) throws NullPointerException {
        super( color, weaponID, isLoaded);
        this.name = "Fucile di Precisione";
        yellowAmmoCost = 1;
        blueAmmoCost = 2;
        redAmmoCost = 0;
    }

    @Override
    public void useWeapon(ResponseInput responseMessage) {

basicMode(((ResponseWhisper) responseMessage).getTargetBasicMode());
    }
    public RequestInput getRequestMessage()
    {
        return new RequestWhisper(checkAvailableMode(),checkBasicMode());
    }
    /**
     * Check which modes of the weapon can be used by player that has this weapon
     * @return array of booleans of size 1 the first represent the basic mode
     * @exception IllegalStateException if this card doesn't belong at a player
     */
    public boolean[] checkAvailableMode() throws IllegalStateException
    {
        List<Player> playerList = new LinkedList<>();

       Set<Player> playerdistance1= new HashSet<>();

        if (player == null)
            throw new IllegalStateException("Carta: "+ name + " non appartiene a nessun giocatore");

        playerList.addAll(player.playerThatSee(player.getSquare().getGameBoard()));
        playerdistance1.addAll(MethodsWeapons.playersReachable(player.getSquare(),1));
        playerList.removeAll(playerdistance1);

        availableMethod[0] = false;




        if (isLoaded() &&  !playerList.isEmpty())
            availableMethod[0] = true;



        return availableMethod;

    }
    /**
     * Return the list of all target available for using the basic mode of this weapon
     * @return all player that can be affected with the lock rifle in basic mode
     */
    public List<ColorId> checkBasicMode() throws IllegalStateException
    {
        List<Player> playerList = new LinkedList<>();
        List<ColorId> colorList = new LinkedList<>();

        Set<Player> playerdistance1= new HashSet<>();

        if (!checkAvailableMode()[0])
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");



        playerList.addAll(player.playerThatSee(player.getSquare().getGameBoard()));
        playerdistance1.addAll(MethodsWeapons.playersReachable(player.getSquare(),1));
        playerList.removeAll(playerdistance1);

        for (Player p :playerList )
        {
            colorList.add(p.getColor());
        }




        return  colorList;//Returns all targets
    }

    /**
     * It uses the basic mode of the Whisper
     * @param colorPlayer player affected by weapon
     * @exception IllegalStateException if the basic mode can't be used
     */
    public void basicMode(ColorId colorPlayer)  throws  IllegalStateException
    {
        if (!checkAvailableMode()[0])
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0),3);
        markTarget(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0),1);

        this.isLoaded = false;
    }
}