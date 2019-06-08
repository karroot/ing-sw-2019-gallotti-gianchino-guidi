package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author giovanni
 */


public class Flamethrower extends WeaponCard
{
    private boolean[] availableMethod = new boolean[2];


    public Flamethrower( Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        this.name = "Flamethrower";
        yellowAmmoCost = 0;
        blueAmmoCost = 0;
        redAmmoCost = 1;
    }


    public boolean[] checkAvailableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: " + name + " non appartiene a nessun giocatore");//If this card doesn't belong to any player, it launches an exception


        availableMethod[0] = false;//I suppose that the modes can't be used
        availableMethod[1] = false;

        if (isLoaded() && MethodsWeapons.isThereAPlayerAtDistance1(player)) //se non ci sono players?
        {
            availableMethod[0] = true;
        }



        if (availableMethod[0] && player.getAmmoYellow()>1)
        {
                availableMethod[1] = true;
        }
        return availableMethod;

    }




    /**
     * Return the list of all target available for using the basic mode of this weapon
     *
     * @return all player that can be affected with the weapon in basic mode
     * @exception IllegalStateException if the basic mode can't be used
     */
private HashMap<CardinalDirection, ArrayList<Player>[]> checkBasicModePrivate() throws IllegalStateException
    {
        if (!checkAvailableMode()[0]) //check mode
            throw  new IllegalStateException("Modalità xxx dell'arma: "+name+" non eseguibile");

        HashMap<CardinalDirection, ArrayList<Player>[]> hashMapReturn = new HashMap<>();

        //NB POSSO SCEGLIERE SOLO 1 PLAYER IN OGNI SQUARE!!!!!


        //todo controllo


        List<Player> playerList;
        List<Player> playersN1 = new ArrayList<>();
        List<Player> playersN2 = new ArrayList<>();
        List<Player> playersE1 = new ArrayList<>();
        List<Player> playersE2 = new ArrayList<>();
        List<Player> playersS1 = new ArrayList<>();
        List<Player> playersS2 = new ArrayList<>();
        List<Player> playersW1 = new ArrayList<>();
        List<Player> playersW2 = new ArrayList<>();

        ArrayList<Player>[] playersN = (ArrayList<Player>[]) new ArrayList[2];
        ArrayList<Player>[] playersE = (ArrayList<Player>[]) new ArrayList[2];
        ArrayList<Player>[] playersS = (ArrayList<Player>[]) new ArrayList[2];
        ArrayList<Player>[] playersW = (ArrayList<Player>[]) new ArrayList[2];

       boolean N=false, E=false, S=false, W=false;

       playerList = MethodsWeapons.playersAtDistance1(player);

       for (Player playerIterate : playerList) {
           Square squareTemp;
           squareTemp = playerIterate.getSquare();
           if (MethodsWeapons.checkSquareNorth(player.getSquare(), squareTemp.getX(), squareTemp.getY())) //caso nord
           {
               playersN1.add(playerIterate);
               if (MethodsWeapons.squareBehindThis(player.getSquare(), squareTemp) != null && !N)
               {
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
        playersN[0].addAll(playersN1);
        playersN[1].addAll(playersN2);
        playersN[0].addAll(playersN1);
        playersN[1].addAll(playersN2);
        playersN[0].addAll(playersN1);
        playersN[1].addAll(playersN2);

       hashMapReturn.put(CardinalDirection.NORTH, playersN);
       hashMapReturn.put(CardinalDirection.EAST, playersE);
       hashMapReturn.put(CardinalDirection.SOUTH, playersS);
       hashMapReturn.put(CardinalDirection.WEST, playersW);

       return hashMapReturn;


    }

    public HashMap<CardinalDirection, ArrayList<ColorId>[]> checkBasicModeForMessage ()
    {
        HashMap<CardinalDirection, ArrayList<Player>[]> hashMap = checkBasicModePrivate();
        HashMap<CardinalDirection, ArrayList<ColorId>[]> hashMapToReturn = new HashMap<>();

        ArrayList<ColorId> colorIdArrayList = new ArrayList<>();
        ArrayList<ColorId>[] colorIdVector = new ArrayList[2];

        for (CardinalDirection cardinalDirection : hashMap.keySet()) //cicla direzioni cardinali
        {
            int i=0;

            for (ArrayList<Player> playerArrayList : hashMap.get(cardinalDirection)) //cicla arraylost nel vettore
            {

                colorIdArrayList.clear();

                for (Player player : playerArrayList) //cicla player
                {
                    colorIdArrayList.add(player.getColor());
                }

                colorIdVector[i].addAll(colorIdArrayList);
                hashMapToReturn.put(cardinalDirection, colorIdVector);
                i++;
            }
        }

        return hashMapToReturn;

    }




    public void basicMode(Player colorPlayerTarget1, Player colorPlayerTarget2) throws IllegalStateException //cambio in colorID
    {
        if (!checkAvailableMode()[0])//check mode
            throw  new IllegalStateException("Modalità xxx dell'arma: "+name+" non eseguibile");



        doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayerTarget1)).collect(Collectors.toList()).get(0),1);//Do one damage
        if (colorPlayerTarget2 != null)
            doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayerTarget2)).collect(Collectors.toList()).get(0),1);

        isLoaded = false;
    }


    public  HashMap<CardinalDirection, ArrayList<Player>[]> checkBarbecueMode()
    {
        if (!checkAvailableMode()[1]) //check mode
            throw  new IllegalStateException("Modalità xx dell'arma: "+name+" non eseguibile");

        return checkBasicModePrivate();
    }

    public void barbecueMode (String square1AsString, String squareBehindAsString )
    {
        if (!checkAvailableMode()[1]) //check mode
            throw  new IllegalStateException("Modalità xx dell'arma: "+name+" non eseguibile");

        int x1 = MethodsWeapons.getXFromString(square1AsString);
        int y1 = MethodsWeapons.getYFromString(square1AsString);
        int x2 = MethodsWeapons.getXFromString(squareBehindAsString);
        int y2 = MethodsWeapons.getYFromString(squareBehindAsString);

        Square square1 = null;
        Square squareBehind = null;

        try {
            square1 = player.getSquare().getGameBoard().getArena().getSquare(x1, y1);
            squareBehind = player.getSquare().getGameBoard().getArena().getSquare(x2, y2);
        } catch (SquareNotInGameBoard squareNotInGameBoard) {
            squareNotInGameBoard.printStackTrace();
        }

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

    @Override
    public void useWeapon(ResponseInput responseMessage) {

    }



}