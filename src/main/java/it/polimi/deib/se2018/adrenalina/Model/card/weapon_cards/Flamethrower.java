package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import it.polimi.deib.se2018.adrenalina.communication_message.*;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author giovanni
 */


public class Flamethrower extends WeaponCard
{
    private boolean[] availableMethod = new boolean[2];

    /**
     *
     * @param color
     * @param weaponID
     * @param isLoaded
     */
    public Flamethrower( Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        this.name = "Lanciafiamme";
        yellowAmmoCost = 0;
        blueAmmoCost = 0;
        redAmmoCost = 1;
    }

    /**
     *
     * @return
     * @throws IllegalStateException
     */
    public boolean[] checkAvailableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: " + name + " non appartiene a nessun giocatore."); //If this card doesn't belong to any player, it launches an exception


        availableMethod[0] = false; //I suppose that the modes can't be used
        availableMethod[1] = false;

        if (isLoaded() && MethodsWeapons.isThereAPlayerAtDistance1(player))
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
     *
     * @return
     * @throws IllegalStateException
     */
    private HashMap<CardinalDirection, ArrayList<Player>[]> checkBasicModePrivate() throws IllegalStateException
    {
        if (!checkAvailableMode()[0]) //check mode
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");

        HashMap<CardinalDirection, ArrayList<Player>[]> hashMapReturn = new HashMap<>();

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

    /**
     *
     * @return
     */
    public HashMap<CardinalDirection, ArrayList<ColorId>[]> checkBasicModeForMessage ()
    {
        if (!checkAvailableMode()[0]) //check mode
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");

        HashMap<CardinalDirection, ArrayList<Player>[]> hashMap = checkBasicModePrivate();
        HashMap<CardinalDirection, ArrayList<ColorId>[]> hashMapToReturn = new HashMap<>();

        ArrayList<ColorId> colorIdArrayList = new ArrayList<>();
        ArrayList<ColorId>[] colorIdVector = new ArrayList[2];

        for (CardinalDirection cardinalDirection : hashMap.keySet()) //Iterate cardinalDirections
        {
            int i=0;

            for (ArrayList<Player> playerArrayList : hashMap.get(cardinalDirection)) //Iterate ArrayList in the vector
            {

                colorIdArrayList.clear();

                for (Player player : playerArrayList) //Iterate players
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


    /**
     *
     * @param colorPlayerTarget1
     * @param colorPlayerTarget2
     * @throws IllegalStateException
     */
    public void basicMode(ColorId colorPlayerTarget1, ColorId colorPlayerTarget2) throws IllegalStateException
    {
        if (!checkAvailableMode()[0]) //check mode
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");

        doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayerTarget1)).collect(Collectors.toList()).get(0),1);//Do one damage
        if (colorPlayerTarget2 != null)
            doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayerTarget2)).collect(Collectors.toList()).get(0),1);

        isLoaded = false;
    }

    /**
     *
     * @return
     */
    public  HashMap<CardinalDirection, ArrayList<Player>[]> checkBarbecueMode()
    {
        if (!checkAvailableMode()[1]) //check mode
            throw  new IllegalStateException("Modalità barbecue dell'arma "+name+" non eseguibile.");

        return checkBasicModePrivate();
    }

    /**
     *
     * @param cardinalDirection
     * @param colorTargetPlayerInThisDirection
     */
    public void barbecueMode (CardinalDirection cardinalDirection, ColorId colorTargetPlayerInThisDirection)
    {
        if (!checkAvailableMode()[1]) //check mode
            throw  new IllegalStateException("Modalità barbecue dell'arma "+name+" non eseguibile.");

        Player playerTarget = player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorTargetPlayerInThisDirection)).collect(Collectors.toList()).get(0);
        Square square1Target = playerTarget.getSquare();
        Square squareBehindTarget = MethodsWeapons.squareBehindThis(player.getSquare(), playerTarget.getSquare());

        barbecueModePrivate(square1Target, squareBehindTarget);
    }

    /**
     *
     * @param square1Target
     * @param squareBehindTarget
     */
    private void barbecueModePrivate (Square square1Target, Square squareBehindTarget)
    {
        if (!checkAvailableMode()[1]) //check mode
            throw  new IllegalStateException("Modalità barbecue dell'arma "+name+" non eseguibile.");


        for (Player playerIterate : square1Target.getPlayerList())
        {
            doDamage(playerIterate,2);
        }
        if (squareBehindTarget != null)
        {
            for (Player playerIterate : squareBehindTarget.getPlayerList())
            {
                doDamage(playerIterate,1);
            }
        }

        player.setAmmoYellow(player.getAmmoYellow() - 2);

        isLoaded = false;
    }

    /**
     *
     * @param responseInput
     */
    @Override
    public void useWeapon(ResponseInput responseInput)
    {
        if (((ResponseFlamethrower) responseInput).isMode())
        {
            barbecueMode(((ResponseFlamethrower) responseInput).getTargetDirectionBarbecueMode(), ((ResponseFlamethrower) responseInput).getTargetBarbecueMode());
            return;
        }

        basicMode(((ResponseFlamethrower) responseInput).getTargetBasicMode1(), ((ResponseFlamethrower) responseInput).getTargetBasicMode2());

    }


    /**
     *
     * @return
     */
    @Override
    public RequestInput getRequestMessage()
    {
        return new RequestFlamethrower(checkAvailableMode(), checkBasicModeForMessage());
    }



}