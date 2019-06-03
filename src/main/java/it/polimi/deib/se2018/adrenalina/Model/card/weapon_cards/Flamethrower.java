package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;

import java.lang.reflect.Array;
import java.util.*;




/**
 * @author giovanni
 */


public class Flamethrower extends WeaponCard
{
    private boolean[] avaiableMethod = new boolean[2];


    public Flamethrower( Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        this.name = "Flamethrower";
        yellowAmmoCost = 0;
        blueAmmoCost = 0;
        redAmmoCost = 1;
    }

    @Override
    public void useWeapon(ResponseInput responseMessage) {

    }


    public boolean[] checkAvaliableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: " + name + " non appartiene a nessun giocatore");//If this card doesn't belong to any player, it launches an exception


        avaiableMethod[0] = false;//I suppose that the modes can't be used
        avaiableMethod[1] = false;

        if (isLoaded() && MethodsWeapons.isThereAPlayerAtDistance1(player)) //se non ci sono players?
        {
            avaiableMethod[0] = true;
        }



        if (avaiableMethod[0] && player.getAmmoYellow()>1)
        {
                avaiableMethod[1] = true;
        }
        return avaiableMethod;

    }




    /**
     * Return the list of all target available for using the basic mode of this weapon
     *
     * @return all player that can be affected with the weapon in basic mode
     * @exception IllegalStateException if the basic mode can't be used
     */
public HashMap<CardinalDirection, List<Player>> checkBasicMode() throws IllegalStateException
    {
        if (!checkAvaliableMode()[0]) //check mode
            throw  new IllegalStateException("Modalità xxx dell'arma: "+name+" non eseguibile");

        HashMap<CardinalDirection, List<Player>> hashMapReturn = new HashMap<>();

        //NB POSSO SCEGLIERE SOLO 1 PLAYER IN OGNI SQUARE!!!!!


        //todo controllo


        List<Player> playerList;
        List<Player> playersN = new ArrayList<>();
        List<Player> playersE = new ArrayList<>();
        List<Player> playersS = new ArrayList<>();
        List<Player> playersW = new ArrayList<>();

       boolean N=false, E=false, S=false, W=false;

       playerList = MethodsWeapons.playersAtDistance1(player);

       for (Player playerIterate : playerList) {
           Square squareTemp;
           squareTemp = playerIterate.getSquare();
           if (MethodsWeapons.checkSquareNorth(player.getSquare(), squareTemp.getX(), squareTemp.getY())) {
               playersN.add(playerIterate);
               if (MethodsWeapons.squareBehindThis(player.getSquare(), squareTemp) != null && !N) {
                   playersN.addAll(MethodsWeapons.squareBehindThis(player.getSquare(), squareTemp).getPlayerList());
                   N = true;
               }

           }
           if (MethodsWeapons.checkSquareEast(player.getSquare(), squareTemp.getX(), squareTemp.getY())) {
               playersE.add(playerIterate);
               if (MethodsWeapons.squareBehindThis(player.getSquare(), squareTemp) != null && !E) {
                   playersE.addAll(MethodsWeapons.squareBehindThis(player.getSquare(), squareTemp).getPlayerList());
                   E = true;
               }
           }
           if(MethodsWeapons.checkSquareSouth(player.getSquare(), squareTemp.getX(), squareTemp.getY())) {
               playersS.add(playerIterate);
               if (MethodsWeapons.squareBehindThis(player.getSquare(), squareTemp) != null && !S) {
                   playersS.addAll(MethodsWeapons.squareBehindThis(player.getSquare(), squareTemp).getPlayerList());
                   S = true;
               }

           }
           if(MethodsWeapons.checkSquareWest(player.getSquare(), squareTemp.getX(), squareTemp.getY())) {
               playersW.add(playerIterate);
               if (MethodsWeapons.squareBehindThis(player.getSquare(), squareTemp) != null && !W) {
                   playersW.addAll(MethodsWeapons.squareBehindThis(player.getSquare(), squareTemp).getPlayerList());
                   W = true;
               }
           }
       }

       hashMapReturn.put(CardinalDirection.NORTH, playersN);
       hashMapReturn.put(CardinalDirection.EAST, playersE);
       hashMapReturn.put(CardinalDirection.SOUTH, playersS);
       hashMapReturn.put(CardinalDirection.WEST, playersW);

       return hashMapReturn;


    }




    public void basicMode(Player playerTarget1, Player playerTarget2) throws IllegalStateException
    {
        if (!checkAvaliableMode()[0])//check mode
            throw  new IllegalStateException("Modalità xxx dell'arma: "+name+" non eseguibile");

        doDamage(playerTarget1,1);//Do one damage
        if (playerTarget2 != null)
            doDamage(playerTarget2, 1);




        isLoaded = false;
    }


    public  HashMap<CardinalDirection, ArrayList<Player>[]> checkBarbecueMode()
    {
        if (!checkAvaliableMode()[1]) //check mode
            throw  new IllegalStateException("Modalità xx dell'arma: "+name+" non eseguibile");

        HashMap<CardinalDirection, ArrayList<Player>[]> hashMapReturn = new HashMap<>();

        List<Player> playerList;
        List<Player> playersN1 = new ArrayList<>();
        List<Player> playersE1 = new ArrayList<>();
        List<Player> playersS1 = new ArrayList<>();
        List<Player> playersW1 = new ArrayList<>();

        List<Player> playersN2 = new ArrayList<>();
        List<Player> playersE2 = new ArrayList<>();
        List<Player> playersS2 = new ArrayList<>();
        List<Player> playersW2 = new ArrayList<>();

        ArrayList<Player>[] playersN = (ArrayList<Player>[]) new ArrayList[4];
        ArrayList<Player>[] playersE = (ArrayList<Player>[]) new ArrayList[4];
        ArrayList<Player>[] playersS = (ArrayList<Player>[]) new ArrayList[4];
        ArrayList<Player>[] playersW = (ArrayList<Player>[]) new ArrayList[4];


        boolean N=false, E=false, S=false, W=false;

        playerList = MethodsWeapons.playersAtDistance1(player);

        for (Player playerIterate : playerList) {
            Square squareTemp;
            squareTemp = playerIterate.getSquare();
            if (MethodsWeapons.checkSquareNorth(player.getSquare(), squareTemp.getX(), squareTemp.getY())) {
                playersN1.add(playerIterate);
                if (MethodsWeapons.squareBehindThis(player.getSquare(), squareTemp) != null && !N) {
                    playersN2.addAll(MethodsWeapons.squareBehindThis(player.getSquare(), squareTemp).getPlayerList());
                    N = true;
                }
            }
            if (MethodsWeapons.checkSquareEast(player.getSquare(), squareTemp.getX(), squareTemp.getY())) {
                playersE1.add(playerIterate);
                if (MethodsWeapons.squareBehindThis(player.getSquare(), squareTemp) != null && !E) {
                    playersE2.addAll(MethodsWeapons.squareBehindThis(player.getSquare(), squareTemp).getPlayerList());
                    E = true;
                }
            }
            if(MethodsWeapons.checkSquareSouth(player.getSquare(), squareTemp.getX(), squareTemp.getY())) {
                playersS1.add(playerIterate);
                if (MethodsWeapons.squareBehindThis(player.getSquare(), squareTemp) != null && !S) {
                    playersS2.addAll(MethodsWeapons.squareBehindThis(player.getSquare(), squareTemp).getPlayerList());
                    S = true;
                }

            }
            if(MethodsWeapons.checkSquareWest(player.getSquare(), squareTemp.getX(), squareTemp.getY())) {
                playersW1.add(playerIterate);
                if (MethodsWeapons.squareBehindThis(player.getSquare(), squareTemp) != null && !W) {
                    playersW2.addAll(MethodsWeapons.squareBehindThis(player.getSquare(), squareTemp).getPlayerList());
                    W = true;
                }
            }
        }

        playersN[0].addAll(playersN1);
        playersN[1].addAll(playersN2);

        playersE[0].addAll(playersE1);
        playersE[1].addAll(playersE2);

        playersS[0].addAll(playersS1);
        playersS[1].addAll(playersS2);

        playersW[0].addAll(playersW1);
        playersW[1].addAll(playersW2);


        hashMapReturn.put(CardinalDirection.NORTH, playersN);
        hashMapReturn.put(CardinalDirection.EAST, playersE);
        hashMapReturn.put(CardinalDirection.SOUTH, playersS);
        hashMapReturn.put(CardinalDirection.WEST, playersW);

       return hashMapReturn;
    }

    public void barbecueMode (Square square1, Square squareBehind )
    {
        if (!checkAvaliableMode()[1]) //check mode
            throw  new IllegalStateException("Modalità xx dell'arma: "+name+" non eseguibile");

        for (Player playerIterate : square1.getPlayerList())
        {
            doDamage(playerIterate,2);
        }

        for (Player playerIterate : squareBehind.getPlayerList())
        {
            doDamage(playerIterate,1);
        }

        player.setAmmoYellow(player.getAmmoYellow() - 2);

        isLoaded = false;

       //quadrato parametro -> 2 danni a tutti
         //   quadrato dietro -> 1 danno a tutti
        //tolgo munizioni
    }


}