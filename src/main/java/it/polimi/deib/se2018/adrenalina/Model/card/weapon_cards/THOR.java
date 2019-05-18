package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;

import java.util.*;


public class THOR extends WeaponCard
{
    private boolean[] avaiableMethod = new boolean[3];


    private int reach1=0; // number of the player that the enemy player see
    private int reach2=0;
    public THOR( Color color, int weaponID, boolean isLoaded) {
        super(color, weaponID, isLoaded);
        yellowAmmoCost = 0;
        blueAmmoCost = 1;
        redAmmoCost = 1;
    }
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
     */
    public void basicMode(Player player1 , Player player2 , Player player3, boolean ChainReaction , boolean highVoltage ) throws  IllegalStateException
    {
        if (!checkAvaliableMode()[0])
            throw  new IllegalStateException("Modalità base dell'arma: "+name+" non eseguibile");

        doDamage(player1,2);
        if(highVoltage==true && ChainReaction==false)
            throw new IllegalArgumentException("you can't use highvoltage alone");
        if(ChainReaction)
        {
            if (!checkAvaliableMode()[1])
                throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

            if (player1.equals(player2))
                throw new IllegalArgumentException("player1 must be different from player2");

            doDamage(player2,1);
            this.player.setAmmoBlue(this.player.getAmmoBlue() - 1);
            if(highVoltage)
            {
                if (!checkAvaliableMode()[2])
                    throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");


                if (player2.equals(player3))
                    throw new IllegalArgumentException("player2 must be different from player3");
                if (player1.equals(player3))
                    throw new IllegalArgumentException("player1 must be different from player3");


                doDamage(player3,2);
                this.player.setAmmoBlue(this.player.getAmmoBlue() - 1);
            }

        }
        this.isLoaded = false;
        }
    public List<Player> checkChainReaction() throws  IllegalStateException
    {
       Set<Player> listChain= new HashSet<>();
        List<Player> list= new LinkedList<>();
        if (!checkAvaliableMode()[1])
            throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

        for(Player i : player.playerThatSee(player.getSquare().getGameBoard()))
        {
          if(i.playerThatSee(i.getSquare().getGameBoard()).size() >0 && !i.equals(player))
          {

              listChain.addAll(i.playerThatSee(i.getSquare().getGameBoard()));
              list.addAll(listChain);
          }
        }
        list.addAll(listChain);
        return list ;
    }



    public List<Player> checkHighVoltage() throws  IllegalStateException
    {
        List<Player> listHighVoltage= new LinkedList<>();
        if (!checkAvaliableMode()[2])
            throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

        for(Player i : player.playerThatSee(player.getSquare().getGameBoard()))
        {
            for (Player j : i.playerThatSee(i.getSquare().getGameBoard()))
            {
                if(j.playerThatSee(j.getSquare().getGameBoard()).size()>0)
                {
                    listHighVoltage.addAll(j.playerThatSee(j.getSquare().getGameBoard()));
                }
            }
        }
        return listHighVoltage;
    }



}