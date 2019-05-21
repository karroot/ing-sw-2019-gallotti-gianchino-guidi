package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;

import java.util.*;


public class THOR extends WeaponCard
{
    private boolean[] avaiableMethod = new boolean[3];


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
        this.name = "THOR";
        yellowAmmoCost = 0;
        blueAmmoCost = 1;
        redAmmoCost = 1;
    }
    /**
     * Check which modes of the weapon can be used by player that has this weapon
     * @return array of booleans of size 3 the first represent the basic mode the second the alternative mode the third the final alternative mode
     * @exception IllegalStateException if this card doesn't belong at a player
     */
    public boolean[] checkAvaliableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: "+ name + " non appartiene a nessun giocatore");

        avaiableMethod[0] = false;
        avaiableMethod[1] = false;
        avaiableMethod[2] = false;
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
            avaiableMethod[0] = true;
        if (isLoaded()&& player.getAmmoBlue()>0 && reach1 > 1 )
            avaiableMethod[1] = true;
        if  (isLoaded()&& player.getAmmoBlue()>1 && reach2 >1)
            avaiableMethod[2] = true;





        return avaiableMethod;

    }


    /**
     * Return the list of all target available for using the basic mode of this weapon
     * @return all player that can be affected with the lock rifle in basic mode
     * @exception IllegalStateException if the basic mode can't be used
     */
    public List<Player> checkBasicMode() throws  IllegalStateException
    {
        if (!checkAvaliableMode()[0])
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        List<Player> playerList = new LinkedList<>();
        playerList.addAll(player.playerThatSee(player.getSquare().getGameBoard()));


        return playerList;//Returns all targets
    }



    /**
     * It uses the basic mode of the lock rifle
     * @param player1 player affected by weapon
     * @param  player2 second player affected by weapon
     * @exception IllegalStateException if the basic mode can't be used
     */
    public void basicMode(Player player1 , Player player2 , Player player3, boolean ChainReaction , boolean highVoltage ) throws  IllegalStateException
    {
        if (!checkAvaliableMode()[0])
            throw  new IllegalStateException("Modalità base dell'arma: "+name+" non eseguibile");


        if(highVoltage==true && ChainReaction==false)
            throw new IllegalArgumentException("you can't use highvoltage alone");
        if(ChainReaction)
        {
            if (!checkAvaliableMode()[1])
                throw  new IllegalStateException("Modalità chain dell'arma: "+name+" non eseguibile");

            if (player1.equals(player2))
                throw new IllegalArgumentException("player1 must be different from player2");


            if(highVoltage)
            {
                if (!checkAvaliableMode()[2])
                    throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");


                if (player2.equals(player3))
                    throw new IllegalArgumentException("player2 must be different from player3");
                if (player1.equals(player3))
                    throw new IllegalArgumentException("player1 must be different from player3");



                this.player.setAmmoBlue(this.player.getAmmoBlue() - 1);
                doDamage(player3,2);
            }
            doDamage(player2,1);
            this.player.setAmmoBlue(this.player.getAmmoBlue() - 1);
        }
        doDamage(player1,2);
        this.isLoaded = false;
        }

    /**
     * Return the list of all target available for using the alternative mode of this weapon
     * @return all player that can be affected with the Whisper in alternative mode
     * @exception IllegalStateException if the alternative mode can't be used
     */
    public List<Player> checkChainReaction() throws  IllegalStateException
    {
       Set<Player> listChain= new HashSet<>();
        List<Player> list= new LinkedList<>();
        if (!checkAvaliableMode()[1])
            throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

        for(Player i : player.playerThatSee(player.getSquare().getGameBoard()))
        {
          if(i.playerThatSee(i.getSquare().getGameBoard()).size() >1 && !(i.equals(player))  )
          {
            for(Player k : i.playerThatSee(i.getSquare().getGameBoard()))
            {
                if(!(k.equals(i)))
                {
                    listChain.add(k);
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
    public List<Player> checkHighVoltage() throws  IllegalStateException
    {
        List<Player> list= new LinkedList<>();
        Set<Player> listHighVoltage= new HashSet<>();
        if (!checkAvaliableMode()[2])
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
                            listHighVoltage.add(k);
                        }
                    }
                }
            }
        }
        list.addAll(listHighVoltage);
        return list;
    }



}