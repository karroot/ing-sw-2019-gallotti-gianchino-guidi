package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestInput;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestMachineGun;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseMachineGun;

import java.util.*;
import java.util.stream.Collectors;

public class MachineGun extends WeaponCard
{
    private boolean[] availableMethod = new boolean[3];

    /**
     * Create the card ZX2
     * @param color color of weapon
     * @param weaponID Id of the card
     * @param isLoaded Indicates if the weapon is loaded or not
     * @exception NullPointerException if color is null
     */
    public MachineGun( Color color, int weaponID, boolean isLoaded)  throws NullPointerException  {

        super(color, weaponID, isLoaded);
        this.name = "MachineGun";
        blueAmmoCost = 1;

        yellowAmmoCost = 0;

        redAmmoCost = 1;
    }

    @Override
    public void useWeapon(ResponseInput responseMessage) {
        basicMode(((ResponseMachineGun) responseMessage).getTargetBasicMode(),
                ((ResponseMachineGun) responseMessage).getTargetBasicModeSecond(),
                ((ResponseMachineGun) responseMessage).getTargetAdditionalMode(),
                ((ResponseMachineGun) responseMessage).getTargetSecondAdditionalMode(),
                ((ResponseMachineGun) responseMessage).getTargetSecondAdditionalModeSecond(),
                ((ResponseMachineGun) responseMessage).isMode(),
                ((ResponseMachineGun) responseMessage).isSecondMode(),
                ((ResponseMachineGun) responseMessage).isAddDamage()
                );

    }

    public RequestInput getRequestMessage()
    {
        return new RequestMachineGun(checkAvailableMode(),checkBasicMode(),checkFocusShotcMode(),checkTurretTripodeMode());
    }
    /**
     * Check which modes of the weapon can be used by player that has this weapon
     * @return array of booleans of size 3 the first represent the basic mode the second and third the alternative mode
     * @exception IllegalStateException if this card doesn't belong at a player
     */
    public boolean[] checkAvailableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: "+ name + " non appartiene a nessun giocatore");

        availableMethod[1] = false;
        availableMethod[0] = false;
        availableMethod[2] = false;


        if (isLoaded() && player.playerThatSee(player.getSquare().getGameBoard()).size()>1)
            availableMethod[0] = true;


        if  (isLoaded()&& player.getAmmoBlue()>0 && player.playerThatSee(player.getSquare().getGameBoard()).size()>1)
            availableMethod[2] = true;

        if (isLoaded()&& player.getAmmoYellow()>0 && player.playerThatSee(player.getSquare().getGameBoard()).size()>1)
            availableMethod[1] = true;

        return availableMethod;

    }
    /**
     * Return the list of all target available for using the basic mode of this weapon
     * @return all player that can be affected with the lock rifle in basic mode
     * @exception IllegalStateException if the basic mode can't be used
     */
    public List<ColorId> checkBasicMode() throws IllegalStateException
    {
        if (!checkAvailableMode()[0])
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        List<ColorId> playerList = new LinkedList<>();
        for (Player p : player.playerThatSee(player.getSquare().getGameBoard()) )
        {
            playerList.add(p.getColor());
        }



        return playerList;//Returns all targets
    }



    /**
     * It uses the basic mode of the lock rifle
     * @param colorPlayer1 player affected by weapon, is also the player of the focushotmode
     * @param colorPlayerdamaged  player that get the additional damage of turret tripode
     * @param colorPlayerGreen alternative player chosen by turret tripode mode
     * @param colorPlayer2 player affected by weapon
     * @param  colorPlayer3 player affected by focus shoot,it can be null
     * @param  FocusShotcMode if true indicate to use focus shot
     * @param  TurretTripode if true indicate to use turret tripode
     * @param addDamage if true indicate to add one damage to playerDamaged , it can be null
     * @exception IllegalStateException if the basic mode can't be used
     * @exception IllegalArgumentException if basicMode revice wrong player's input
     */
    public void basicMode(ColorId colorPlayer1, ColorId colorPlayer2 , ColorId colorPlayer3, ColorId colorPlayerGreen, ColorId colorPlayerdamaged, boolean FocusShotcMode, boolean TurretTripode, boolean addDamage) throws IllegalArgumentException,IllegalStateException
    {

        if (FocusShotcMode)
            {
                if (!checkAvailableMode()[1])
                    throw new IllegalStateException("Modalità avanzata dell'arma: " + name + " non eseguibile");

                doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer3)).collect(Collectors.toList()).get(0),1);
                this.player.setAmmoYellow(this.player.getAmmoYellow() - 1);
            }
        if (TurretTripode)
        {
            if (!checkAvailableMode()[2])
                throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

            if (colorPlayerGreen==null && !addDamage)
                throw new IllegalArgumentException("Mode: "+ name + " select at least one between damage player1 or damage player 3");//If this card doesn't belong at a player launch exception

            if (colorPlayerGreen!=null)
            {
                if (colorPlayerGreen.equals(colorPlayer2) || colorPlayerGreen.equals(colorPlayer1))
                {
                    throw new IllegalArgumentException("player3 must be different from player2 and player1");
                }
                doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayerGreen)).collect(Collectors.toList()).get(0),1);
            }
            if (addDamage)
            {
                if ( (!(colorPlayerdamaged.equals(colorPlayer1)) || (colorPlayerdamaged.equals(colorPlayer2) ) )&& ((colorPlayerdamaged.equals(colorPlayer1)) || !(colorPlayerdamaged.equals(colorPlayer2))))
                    throw new IllegalArgumentException("Mode: "+ name + " playeradddamage must be player1 or player2");

                if (colorPlayerdamaged.equals(colorPlayer1))
                    doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer1)).collect(Collectors.toList()).get(0),1);
                if (colorPlayerdamaged.equals(colorPlayer2))
                    doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer2)).collect(Collectors.toList()).get(0),1);

            }

            this.player.setAmmoBlue(this.player.getAmmoBlue() - 1);
        }
        if (!checkAvailableMode()[0])
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        if (colorPlayer1.equals(colorPlayer2))
            throw new IllegalArgumentException("player1 must be different from player2");
        doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer1)).collect(Collectors.toList()).get(0),1);
        if (colorPlayer2!= null)
            doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer2)).collect(Collectors.toList()).get(0),1);
        this.isLoaded = false;
    }

    /**
     * Return the list of all target available for using the focus shoot mode of this weapon
     * @return all player that can be affected with the lock rifle in focus shoot mode
     * @exception IllegalStateException if the alternative mode can't be used
     */
    public List<ColorId> checkFocusShotcMode() throws IllegalStateException
    {
        if (!checkAvailableMode()[1])
            throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

        List<ColorId> playerList = new LinkedList<>();
        for (ColorId p : checkBasicMode() )
        {
            playerList.add(p);
        }



        return playerList;//Returns all targets
    }




    /**
     * Return the list of all target available for using the focus Turret Tripode mode of this weapon
     * @return all player that can be affected with the lock rifle in focus shoot mode
     * @exception IllegalStateException if the alternative mode can't be used
     */
    public List<ColorId> checkTurretTripodeMode() throws  IllegalStateException
    {
        if (!checkAvailableMode()[2])
            throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

        List<ColorId> playerList = new LinkedList<>();
        for (Player p : player.playerThatSee(player.getSquare().getGameBoard()) )
        {
            playerList.add(p.getColor());
        }





        return playerList;//Returns all targets
    }





}