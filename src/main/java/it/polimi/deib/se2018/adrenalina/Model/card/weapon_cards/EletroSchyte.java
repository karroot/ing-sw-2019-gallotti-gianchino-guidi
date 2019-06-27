package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestElectroSchyte;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestInput;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseElectroSchyte;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;

import java.util.*;
import java.util.stream.Collectors;


public class EletroSchyte extends WeaponCard
{

    private boolean[] availableMethod = new boolean[2];


    /**
     * Create the card ElectroSchyte
     * @param color color of weapon
     * @param weaponID Id of the card
     * @param isLoaded Indicates if the weapon is loaded or not
     * @exception NullPointerException if color is null
     */
    public EletroSchyte( Color color, int weaponID, boolean isLoaded) throws NullPointerException
    {
        super( color, weaponID, isLoaded);
        this.name = "Falce Protonica";
        yellowAmmoCost = 0;
        blueAmmoCost = 1;
        redAmmoCost = 0;
    }

    @Override
    public void useWeapon(ResponseInput responseMessage) {
        if(((ResponseElectroSchyte) responseMessage).isMode())
            basicMode(((ResponseElectroSchyte) responseMessage).getTargetsAlternativeMode());
        else
            basicMode(((ResponseElectroSchyte) responseMessage).getTargetBasicMode());
    }


    public RequestInput getRequestMessage()
    {
        if (checkAvailableMode()[0] && checkAvailableMode()[1])
            return new RequestElectroSchyte(checkAvailableMode(),checkBasicMode(),checkReaper());
        else
            return new RequestElectroSchyte(checkAvailableMode(),checkBasicMode(),new ArrayList<>());

    }
    /**
     * Check which modes of the weapon can be used by player that has this weapon
     * @return array of booleans of size 2 the first represent the basic mode the second the alternative mode
     * @exception IllegalStateException if this card doesn't belong at a player
     */
    public boolean[] checkAvailableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: "+ name + " non appartiene a nessun giocatore");//If this card doesn't belong at a player launch exception

        availableMethod[1] = false; //I suppose that the modes can't be used
        availableMethod[0] = false;

        if (this.isLoaded() && player.getSquare().getPlayerList().size() > 1)//If the first mode can be used
            availableMethod[0] = true;

        if (this.isLoaded() && player.getAmmoBlue()>=1 && player.getAmmoRed()>=1 &&  player.getSquare().getPlayerList().size() > 1)//If the second mode can be used
            availableMethod[1] = true;



        return availableMethod;

    }
    /**
     * Return the list of all target available for using the basic mode of this weapon
     * @return all player that can be affected with the electroScythe in basic mode
     * @exception IllegalStateException if the basic mode can't be used
     */
    public List<ColorId> checkBasicMode() throws IllegalStateException
    {
        if (!checkAvailableMode()[0])
            throw  new IllegalStateException(" Modalità basic dell'arma: "+name+" non eseguibile");

        List<ColorId> playerList = new LinkedList<>();
        for (Player p : player.playerThatSee(player.getSquare().getGameBoard()) )
        {
            playerList.add(p.getColor());
        }


        playerList.remove(player.getColor()); //Remove from targets the player that shoot

        return playerList;//Returns all targets
    }

    /**
     * It uses the basic mode of the ElectroSchyte
     * @param colorPlayerList  list of player affected by weapon
     * @exception IllegalStateException if the basic mode can't be used
     */
    public void basicMode(List<ColorId> colorPlayerList)
    {

            for (ColorId p : colorPlayerList) {
                doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(p)).collect(Collectors.toList()).get(0),1);
            }
        isLoaded = false;
        }



    /**
     * Return the list of all target available for using the basic mode of this weapon
     * @return all player that can be affected with the electroScythe in basic mode
     * @exception IllegalStateException if the alternative mode can't be used
     */
    public List<ColorId> checkReaper() throws IllegalStateException
    {

        if (!checkAvailableMode()[1])
            throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

        List<ColorId> playerList = new LinkedList<>();
        for (Player p : player.playerThatSee(player.getSquare().getGameBoard()) )
        {
            playerList.add(p.getColor());
        }
        playerList.remove(player.getColor()); //Remove from targets the player that shoot

        return playerList;//Returns all targets
    }

    /**
     * It uses the basic mode of the weapon
     * @param colorPlayerList  list of player affected by weapon
     * @exception IllegalStateException if the alternative mode can't be used
     */
    public void reaper(List<ColorId> colorPlayerList) throws  IllegalStateException
    {

        if (!checkAvailableMode()[1])
            throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

        for (ColorId p : colorPlayerList) {
            doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(p)).collect(Collectors.toList()).get(0),2);
        }
        isLoaded = false;
        this.player.setAmmoBlue(this.player.getAmmoBlue() - 1);
        this.player.setAmmoRed(this.player.getAmmoRed() - 1);
    }

}