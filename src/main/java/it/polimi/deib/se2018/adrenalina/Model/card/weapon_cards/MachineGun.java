package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestInput;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestMachineGun;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseMachineGun;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @author Karroot
 * It represents the card MachineGun
 * it uses three method for the three different modality checkBasicMode(),checkFocusShotMode() and checkTurretTripodeMode()
 */
public class MachineGun extends WeaponCard
{
    private boolean[] availableMethod = new boolean[4];

    /**
     * Create the card MachineGun
     * @param color color of weapon
     * @param weaponID Id of the card
     * @param isLoaded Indicates if the weapon is loaded or not
     * @exception NullPointerException if color is null
     */
    public MachineGun( Color color, int weaponID, boolean isLoaded)  throws NullPointerException  {

        super(color, weaponID, isLoaded);
        this.name = "Mitragliatrice";
        blueAmmoCost = 1;

        yellowAmmoCost = 0;

        redAmmoCost = 1;
    }

    /**
     * this method is used to use the weapon
     * @param responseMessage response message specified for the weapon
     */
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
    /**
     * method used to return the reqeuest message
     * @return the asked request input
     */
    public RequestInput getRequestMessage()
    {
        if (checkAvailableMode()[0] && checkAvailableMode()[1] && checkAvailableMode()[2])
            return new RequestMachineGun(checkAvailableMode(),checkBasicMode(), checkFocusShotMode(),checkTurretTripodeMode());
        else if (checkAvailableMode()[0] && checkAvailableMode()[1] && !checkAvailableMode()[2])
            return new RequestMachineGun(checkAvailableMode(),checkBasicMode(), checkFocusShotMode(),new LinkedList<>());
        else if (checkAvailableMode()[0] && !checkAvailableMode()[1] && checkAvailableMode()[2])
            return new RequestMachineGun(checkAvailableMode(),checkBasicMode(),new LinkedList<>(),checkTurretTripodeMode());
        else
            return new RequestMachineGun(checkAvailableMode(),checkBasicMode(),new LinkedList<>(),new LinkedList<>());

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
        availableMethod[3] = false;


        if (isLoaded() && player.playerThatSee(player.getSquare().getGameBoard()).size()>1)
            availableMethod[0] = true;


        if  (isLoaded()&& player.getAmmoBlue()>0 && player.playerThatSee(player.getSquare().getGameBoard()).size()>1)
            availableMethod[2] = true;

        if (isLoaded()&& player.getAmmoYellow()>0 && player.playerThatSee(player.getSquare().getGameBoard()).size()>1)
            availableMethod[1] = true;

        if (isLoaded()&& player.getAmmoYellow()>0 && player.getAmmoBlue()>0 && player.playerThatSee(player.getSquare().getGameBoard()).size()>2)
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

        return getColorIds();
    }

    private List<ColorId> getColorIds() {
        List<ColorId> playerList = new LinkedList<>();
        for (Player p : player.playerThatSee(player.getSquare().getGameBoard()) )
        {
            char ssa;
            if(!p.equals(this.player))
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


                checkDoDamageTerminator(colorPlayer3);
                this.player.setAmmoYellow(this.player.getAmmoYellow() - 1);
            }

        if (TurretTripode)
        {
            if (!checkAvailableMode()[2])
                throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");

            if (colorPlayerGreen==null && !addDamage)
                throw new IllegalArgumentException("Nella modalità avanzata dell'arma"+ name + "bisogna selezionare almeno un player da danneggiare tra player1, player2 o player3.");//If this card doesn't belong at a player launch exception

            if (colorPlayerGreen!=null)
            {
                if (colorPlayerGreen.equals(colorPlayer2) || colorPlayerGreen.equals(colorPlayer1))
                {
                    throw new IllegalArgumentException("Player3 deve essere diverso da player2 e player1.");
                }

                checkDoDamageTerminator(colorPlayerGreen);
            }
            if (addDamage)
            {
                if ( (!(colorPlayerdamaged.equals(colorPlayer1)) || (colorPlayerdamaged.equals(colorPlayer2) ) )&& ((colorPlayerdamaged.equals(colorPlayer1)) || !(colorPlayerdamaged.equals(colorPlayer2))))
                    throw new IllegalArgumentException("Nella mdalità avanzata dell'arma"+ name + "il 'playerAddDamanage' deve essere player1 o player2.");

                if (colorPlayerdamaged.equals(colorPlayer1))
                {
                    checkDoDamageTerminator(colorPlayer1);
                }
                if (colorPlayerdamaged.equals(colorPlayer2))
                {
                    checkDoDamageTerminator(colorPlayer2);
                }

            }

            this.player.setAmmoBlue(this.player.getAmmoBlue() - 1);
        }
        if (!checkAvailableMode()[0])
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");

        if (colorPlayer1.equals(colorPlayer2))
            throw new IllegalArgumentException("Player 1 deve essere diverso da player2.");



            checkDoDamageTerminator(colorPlayer1);


        if (colorPlayer2!= null) {
            checkDoDamageTerminator(colorPlayer2);

        }
        isLoaded = false;
    }

    private void checkDoDamageTerminator(ColorId colorPlayerCheck) {
        if(this.player.getSquare().getGameBoard().isTerminatorMode() && colorPlayerCheck.equals(ColorId.PURPLE))
        {
            char ssa;
            doDamage(player.getSquare().getGameBoard().getTermi(),1);

        }
        else
            doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayerCheck)).collect(Collectors.toList()).get(0),1);
    }


    /**
     * Return the list of all target available for using the focus shoot mode of this weapon
     * @return all player that can be affected with the lock rifle in focus shoot mode
     * @exception IllegalStateException if the alternative mode can't be used
     */
    public List<ColorId> checkFocusShotMode() throws IllegalStateException
    {
        if (!checkAvailableMode()[1])
            throw  new IllegalStateException("Modalità avanzata dell'arma "+name+" non eseguibile.");

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
            throw  new IllegalStateException("Modalità avanzata dell'arma "+name+" non eseguibile.");

        return getColorIds();
    }





}