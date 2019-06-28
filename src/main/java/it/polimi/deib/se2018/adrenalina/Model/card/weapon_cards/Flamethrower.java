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
     * It is the public constructor for the class.
     * @param color is the color of the card
     * @param weaponID is the unique id to identify the card
     * @param isLoaded to indicate if the weapon is loaded
     */
    public Flamethrower( Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        this.name = "Lanciafiamme";
        yellowAmmoCost = 0;
        blueAmmoCost = 0;
        redAmmoCost = 1;
    }


    /**
     * It checks which modes of the weapon can be used
     * @return an array of boolean of which modes are avaiable to the players
     * @throws IllegalStateException if this card doesn't belong at a player
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



        if (isLoaded() && MethodsWeapons.isThereAPlayerAtDistance1(player) && player.getAmmoYellow()>1)
        {
                availableMethod[1] = true;
        }

        return availableMethod;

    }


    /**
     * This method finds all the possible targets in all the directions.
     * @return an hashmap with CardinalDirection as key and an array of dimension 2 of a List Players
     * @throws IllegalStateException if this weapon doesn not belong to any player
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

        playersN[0] = new ArrayList<Player>();
        playersN[1] = new ArrayList<Player>();
        playersE[0] = new ArrayList<Player>();
        playersE[1] = new ArrayList<Player>();
        playersS[0] = new ArrayList<Player>();
        playersS[1] = new ArrayList<Player>();
        playersW[0] = new ArrayList<Player>();
        playersW[1] = new ArrayList<Player>();


       boolean N=false, E=false, S=false, W=false;

       playerList = MethodsWeapons.playersAtDistance1(player);

       for (Player playerIterate : playerList) {
           Square squareTemp;
           squareTemp = playerIterate.getSquare();
           if (MethodsWeapons.checkSquareNorth(player.getSquare(), squareTemp.getX(), squareTemp.getY())) //caso nord
           {
               playersN1.add(playerIterate);
               if (!playersN1.isEmpty() && MethodsWeapons.squareBehindThis(player.getSquare(), squareTemp) != null && !N)
               {
                   Square sq = MethodsWeapons.squareBehindThis(player.getSquare(), squareTemp);
                   playersN2.addAll(MethodsWeapons.squareBehindThis(player.getSquare(), squareTemp).getPlayerList());
                   N = true;
               }

           }
           if (MethodsWeapons.checkSquareEast(player.getSquare(), squareTemp.getX(), squareTemp.getY())) {
               playersE1.add(playerIterate);
               if (!playersE1.isEmpty() && MethodsWeapons.squareBehindThis(player.getSquare(), squareTemp) != null && !E) {
                   playersE2.addAll(MethodsWeapons.squareBehindThis(player.getSquare(), squareTemp).getPlayerList());
                   E = true;
               }
           }
           if(MethodsWeapons.checkSquareSouth(player.getSquare(), squareTemp.getX(), squareTemp.getY())) {
               playersS1.add(playerIterate);
               if (!playersS1.isEmpty() && MethodsWeapons.squareBehindThis(player.getSquare(), squareTemp) != null && !S) {
                   playersS2.addAll(MethodsWeapons.squareBehindThis(player.getSquare(), squareTemp).getPlayerList());
                   S = true;
               }

           }
           if(MethodsWeapons.checkSquareWest(player.getSquare(), squareTemp.getX(), squareTemp.getY())) {
               playersW1.add(playerIterate);
               if (!playersW1.isEmpty() && MethodsWeapons.squareBehindThis(player.getSquare(), squareTemp) != null && !W) {
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

    /**
     * This method converts an hashmap of CardinalDirections and Players into CardinalDirections and ColorId
     * @return an hashmap with CardinalDirection as key and an array of dimension 2 of a list of ColorId
     */
    public HashMap<CardinalDirection, ArrayList<ColorId>[]> checkBasicModeForMessage ()
    {
        if (!checkAvailableMode()[0]) //check mode
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");

        HashMap<CardinalDirection, ArrayList<Player>[]> hashMap = checkBasicModePrivate();
        HashMap<CardinalDirection, ArrayList<ColorId>[]> hashMapToReturn = new HashMap<>();


        for (CardinalDirection cardinalDirection : hashMap.keySet()) //Iterate cardinalDirections
        {
            ArrayList<ColorId>[] colorIdVector = new ArrayList[2];
            colorIdVector[0] = new ArrayList<ColorId>();
            colorIdVector[1] = new ArrayList<ColorId>();

            int i=0;

            for (ArrayList<Player> playerArrayList : hashMap.get(cardinalDirection)) //Iterate ArrayList in the vector
            {

                ArrayList<ColorId> colorIdArrayList = new ArrayList<>();

                for (Player player : playerArrayList) //Iterate players
                {
                    colorIdArrayList.add(player.getColor());
                }

                colorIdVector[i].addAll(colorIdArrayList);
                i++;
            }

            hashMapToReturn.put(cardinalDirection, colorIdVector);
        }

        return hashMapToReturn;

    }


    /**
     * This is the basic mode of the weapon.
     * @param colorPlayerTarget1 is the target in the fist square
     * @param colorPlayerTarget2 is the target in the square behind the first one
     * @throws IllegalStateException if this weapon doesn not belong to any player
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
     * It checks the target of the Barbecue mode.
     * @return an hashmap with CardinalDirection as key and an array of dimension 2 of a List Players
     * @throws IllegalStateException if this weapon doesn not belong to any player
     */
    public  HashMap<CardinalDirection, ArrayList<ColorId>[]> checkBarbecueMode()
    {
        if (!checkAvailableMode()[1]) //check mode
            throw  new IllegalStateException("Modalità barbecue dell'arma "+name+" non eseguibile.");

        return checkBasicModeForMessage();
    }

    /**
     * It implements the barbecue mode of the weapon
     * @param cardinalDirection is the direction where the player decided to fire
     * @param colorTargetPlayerInThisDirection is a target in that direction
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
     * This method implements the damage for the barbecue mode
     * @param square1Target the first square where the weapon will fire
     * @param squareBehindTarget the square behind the fist one in the same direction to fire
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