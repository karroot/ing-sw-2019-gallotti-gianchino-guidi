package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.communication_message.*;

import java.util.*;
import java.util.stream.Collectors;


public class THOR extends WeaponCard
{
    private boolean[] availableMethod = new boolean[3];


    private int reach1=0; // number of the player that the enemy player see
    private int reach2=0;

    /**
     * Create the card ZX2
     * @param color color of weapon
     * @param weaponID Id of the card
     * @param isLoaded Indicates if the weapon is loaded or not
     * @exception NullPointerException if color is null
     */
    public THOR( Color color, int weaponID, boolean isLoaded) throws NullPointerException {
        super(color, weaponID, isLoaded);
        this.name = "Torpedine";
        yellowAmmoCost = 0;
        blueAmmoCost = 1;
        redAmmoCost = 1;
    }

    @Override
    public void useWeapon(ResponseInput responseMessage) {
        if(((ResponseTHOR) responseMessage).getTargetSecondAdditionalMode() == null && ((ResponseTHOR) responseMessage).getTargetAdditionalMode() == null )

            basicMode(((ResponseTHOR) responseMessage).getTargetBasicMode(),null,null,false,false);

        else if(((ResponseTHOR) responseMessage).getTargetSecondAdditionalMode() == null )

            basicMode(((ResponseTHOR) responseMessage).getTargetBasicMode(), ((ResponseTHOR) responseMessage).getTargetAdditionalMode(),null,true,false);
        else

            basicMode(((ResponseTHOR) responseMessage).getTargetBasicMode(), ((ResponseTHOR) responseMessage).getTargetAdditionalMode(),((ResponseTHOR) responseMessage).getTargetSecondAdditionalMode(),true,true);

    }
    public RequestInput getRequestMessage()
    {
        return new RequestTHOR(checkAvailableMode(),checkBasicMode(),checkChainReaction(),checkHighVoltage());
    }
    /**
     * Check which modes of the weapon can be used by player that has this weapon
     * @return array of booleans of size 3 the first represent the basic mode the second the alternative mode the third the final alternative mode
     * @exception IllegalStateException if this card doesn't belong at a player
     */
    public boolean[] checkAvailableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: "+ name + " non appartiene a nessun giocatore");

        availableMethod[0] = false;
        availableMethod[1] = false;
        availableMethod[2] = false;
        for(Player i : player.playerThatSee(player.getSquare().getGameBoard()))
        {
            reach1+=i.playerThatSee(i.getSquare().getGameBoard()).size();
        }
        for(Player i : player.playerThatSee(player.getSquare().getGameBoard()))
        {
           for (Player j : i.playerThatSee(i.getSquare().getGameBoard()))
           {
               reach2+=j.playerThatSee(j.getSquare().getGameBoard()).size();
           }
        }
        if (isLoaded() && player.playerThatSee(player.getSquare().getGameBoard()).size()>1)
            availableMethod[0] = true;
        if (isLoaded()&& player.getAmmoBlue()>0 && reach1 > 1 )
            availableMethod[1] = true;
        if  (isLoaded()&& player.getAmmoBlue()>1 && reach2 >1)
            availableMethod[2] = true;





        return availableMethod;

    }


    /**
     * Return the list of all target available for using the basic mode of this weapon
     * @return all player that can be affected with the lock rifle in basic mode
     * @exception IllegalStateException if the basic mode can't be used
     */
    public List<ColorId> checkBasicMode() throws  IllegalStateException
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
     * @param colorPlayer1 player affected by weapon
     * @param  colorPlayer2 second player affected by weapon
     * @param colorPlayer3 third player affected by this weapon
     * @exception IllegalStateException if the basic mode can't be used
     */
    public void basicMode(ColorId colorPlayer1, ColorId colorPlayer2 , ColorId colorPlayer3, boolean ChainReaction , boolean highVoltage ) throws  IllegalStateException
    {
        if (!checkAvailableMode()[0])
            throw  new IllegalStateException("Modalità base dell'arma: "+name+" non eseguibile");


        if(highVoltage==true && ChainReaction==false)
            throw new IllegalArgumentException("you can't use highvoltage alone");
        if(ChainReaction)
        {
            if (!checkAvailableMode()[1])
                throw  new IllegalStateException("Modalità chain dell'arma: "+name+" non eseguibile");

            if (colorPlayer1.equals(colorPlayer2))
                throw new IllegalArgumentException("player1 must be different from player2");


            if(highVoltage)
            {
                if (!checkAvailableMode()[2])
                    throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");


                if (colorPlayer2.equals(colorPlayer3))
                    throw new IllegalArgumentException("player2 must be different from player3");
                if (colorPlayer1.equals(colorPlayer3))
                    throw new IllegalArgumentException("player1 must be different from player3");



                this.player.setAmmoBlue(this.player.getAmmoBlue() - 1);
                doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer3)).collect(Collectors.toList()).get(0),2);
            }
            doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer2)).collect(Collectors.toList()).get(0),1);
            this.player.setAmmoBlue(this.player.getAmmoBlue() - 1);
        }
        doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer1)).collect(Collectors.toList()).get(0),2);
        this.isLoaded = false;
        }

    /**
     * Return the list of all target available for using the alternative mode of this weapon
     * @return all player that can be affected with the Whisper in alternative mode
     * @exception IllegalStateException if the alternative mode can't be used
     */
    public List<ColorId> checkChainReaction() throws  IllegalStateException
    {
       Set<ColorId> listChain= new HashSet<>();
        List<ColorId> list= new LinkedList<>();
        if (!checkAvailableMode()[1])
            throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

        for(Player i : player.playerThatSee(player.getSquare().getGameBoard()))
        {
          if(i.playerThatSee(i.getSquare().getGameBoard()).size() >1 && !(i.equals(player))  )
          {
            for(Player k : i.playerThatSee(i.getSquare().getGameBoard()))
            {
                if(!(k.equals(i)))
                {
                    listChain.add(k.getColor());
                }
            }


          }
        }
        list.addAll(listChain);
        return list ;
    }


    /**
     * Return the list of all target available for using the final alternative mode of this weapon
     * @return all player that can be affected with the Whisper in alternative mode
     * @exception IllegalStateException if the alternative mode can't be used
     */
    public List<ColorId> checkHighVoltage() throws  IllegalStateException
    {
        List<ColorId> list= new LinkedList<>();
        Set<ColorId> listHighVoltage= new HashSet<>();
        if (!checkAvailableMode()[2])
            throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

        for(Player i : player.playerThatSee(player.getSquare().getGameBoard()))
        {
            for (Player j : i.playerThatSee(i.getSquare().getGameBoard()))
            {
                if(j.playerThatSee(j.getSquare().getGameBoard()).size()>0 && !(i.equals(player)) && !(i.equals(j)))
                {
                    for(Player k : j.playerThatSee(j.getSquare().getGameBoard()))
                    {
                        if(!(k.equals(i)) && !(k.equals(j)))
                        {
                            listHighVoltage.add(k.getColor());
                        }
                    }
                }
            }
        }
        list.addAll(listHighVoltage);
        return list;
    }



}