package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestInput;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestPlasmaGun;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponsePlasmaGun;

import java.util.*;
import java.util.stream.Collectors;
/**
 * It represents the card PlasmaGun
 * it uses two method to check if the weapon can be used checkAllTarget() and checkPhaseGlide()
 *
 * @author Karroot
 */
public class PlasmaGun extends WeaponCard
{

    private boolean[] availableMethod = new boolean[4];
    /**
     * Create the card PlasmaGun
     * @param color color of weapon
     * @param weaponID Id of the card
     * @param isLoaded Indicates if the weapon is loaded or not
     * @exception NullPointerException if color is null
     */
    public PlasmaGun(Color color, int weaponID, boolean isLoaded) throws NullPointerException {
        super( color, weaponID, isLoaded);
        this.name = "Fucile al Plasma";
        redAmmoCost = 0;
        yellowAmmoCost = 1;
        blueAmmoCost = 1;

    }

    /**
     * this method is used to use the weapon
     * @param responseMessage response message specified for the weapon
     */
    @Override
    public void useWeapon(ResponseInput responseMessage) {
        ResponsePlasmaGun msg = (ResponsePlasmaGun) responseMessage;

        try {
            basicMode(msg.getTargetBasicEffect(),msg.getOrderEffect(),msg.getX(),msg.getY());
        } catch (IllegalAccessException e) {
            System.out.println(e);
        }

    }
    /**
     * method used to return the reqeuest message
     * @return the asked request input
     */
    public RequestInput getRequestMessage()
    {

             return new RequestPlasmaGun(checkAvailableMode(),checkAllTarget(),checkPhaseGlide(),player.getSquare().getX(),player.getSquare().getY());

    }
    /**
     * Check which modes of the weapon can be used by player that has this weapon
     * @return array of booleans of size 2 the first represent the basic mode the second the alternative mode
     * @exception IllegalStateException if this card doesn't belong at a player
     */
    public boolean[] checkAvailableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: " + name + " non appartiene a nessun giocatore.");

        availableMethod[2] = false;

        availableMethod[1] = false;

        availableMethod[0] = false;

        availableMethod[3] = false;




        if (isLoaded() && player.playerThatSee(player.getSquare().getGameBoard()).size()>1)
            availableMethod[0] = true;


        if  (isLoaded()&& player.getAmmoBlue()>0 && player.playerThatSee(player.getSquare().getGameBoard()).size()>1)
            availableMethod[1] = true;

        if (isLoaded())
            availableMethod[2] = true;

        if(isLoaded() && (checkPhaseGlide().size()>1) )
            availableMethod[3] = true;

        return availableMethod;

    }


    /**
     * It uses the basic mode of the PlasmaGun
     * @param colorPlayer player affected by weapon
     * @param orderEffect the vector of string of the effect chosen
     * @param x the x coordinate where player want to move
     * @param y the y coordinate where player want to move
     * @throws IllegalStateException if the basic mode can't be used
     * @throws IllegalArgumentException if the basic mode can't be used
     * @throws IllegalAccessException if if useWeapon can't be used
     */
    public void basicMode(ColorId colorPlayer,String[] orderEffect,int x,int y) throws IllegalStateException, IllegalArgumentException, IllegalAccessException {


        int i = 0;
        boolean[] booleans = checkAvailableMode();
        if (!checkAvailableMode()[2])
            throw  new IllegalStateException("L'arma è scarica");
        while (i < orderEffect.length)
        {
            if (orderEffect[i].equals("basic"))
            {
                if (!checkAvailableMode()[0])
                    throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");


                if(this.player.getSquare().getGameBoard().isTerminatorMode() && colorPlayer.equals(ColorId.PURPLE))
                {

                    doDamage(player.getSquare().getGameBoard().getTermi(),2);



                }
                else
                    doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0),2);
            }
            if (orderEffect[i].equals("with phase glide") && checkTargetAfterMove(x,y).size()>0) {
                if (!checkAvailableMode()[2])
                    throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");

                moveTarget(this.player, x, y);

            }
            if (orderEffect[i].equals("with charged shot") && booleans[2])
            {
                if (!checkAvailableMode()[1])
                    throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");


                if(this.player.getSquare().getGameBoard().isTerminatorMode() && colorPlayer.equals(ColorId.PURPLE))
                {

                        doDamage(player.getSquare().getGameBoard().getTermi(),1);



                }
                else
                    doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0),1);
                this.player.setAmmoBlue(this.player.getAmmoBlue() - 1);
            }
        i++;
        }

       isLoaded = false;
    }


    /**
     * Calculate in which square a player can be moved using the basic mode
     * @return Set of all square corrects
     * @exception IllegalStateException if the basic mode can't be used
     */
    public List<String> checkPhaseGlide() throws IllegalStateException
    {
        if (!this.isLoaded())
            throw  new IllegalStateException("Modalità avanzata dell'arma "+name+" non eseguibile.");

        Square square = player.getSquare();

        Set<Square> squares = square.getGameBoard().getArena().squareReachableNoWall(square.getX(),square.getY(),2);//Obtain all the reachable square



        return squares.stream().map(Square::toStringCoordinates).collect(Collectors.toList());
    }


    /**
     * It say if a player can reach another player from a defined position decide by the phase glide
     * @param x player choice of movement x
     * @param y player choice of movement y
     * @return List of reachable player
     */
    private List<ColorId> checkTargetAfterMove(int x,int y)
    {

        Player dummie2 = new Player(ColorId.DUMMIE,"a","a",false);
        List<ColorId> ListPlayerReach = new LinkedList();
        Set<ColorId> playerReachable = new HashSet<>();
        try {
            dummie2.setSquare(this.getPlayer().getSquare().getGameBoard().getArena().getSquare(x,y));
        } catch (SquareNotInGameBoard squareNotInGameBoard) {
            System.out.println(squareNotInGameBoard);
        }
        if  (dummie2.playerThatSee(dummie2.getSquare().getGameBoard()).size() > 0)
            for (Player p : dummie2.playerThatSee(dummie2.getSquare().getGameBoard()) )
            {
                if(!p.equals(dummie2) && !p.getColor().equals(this.player.getColor()))
                    playerReachable.add(p.getColor());
            }
        ListPlayerReach.addAll(playerReachable);//Returns all targets



        dummie2.getSquare().getRoom().removePlayerFromRoomList(dummie2); //Remove player from room

        dummie2.getSquare().removePlayer(dummie2);//Remove the player from his square

        dummie2.getSquare().getRoom().updatePlayerRoomList(); //Update the list of player inside
       return ListPlayerReach;
    }

    /**
     * this method is used to check all the square from which player can shoot
     * @return a map with square reachable and player that you see from this square
     */
    public  Map<String,List<ColorId>> checkAllTarget()
    {
        Player dummie = new Player(ColorId.DUMMIE,"a","a",false);
        if (!checkAvailableMode()[2]) //check mode
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");
List<ColorId> tempList ;
        Map<String,List<ColorId>> result = new HashMap<>();
        dummie.setSquare(player.getSquare());

        //Obtain all squares reachable at distance 2
        Set<Square> squares = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 2);

        for (Square t:squares) //For each squares
        {
            moveTarget(dummie,t.getX(),t.getY());
            if (dummie.playerThatSee(dummie.getSquare().getGameBoard()).size() > 1) //If dummie see some player
            {
                String coordinates = t.toStringCoordinates();//Save the coordinates

                if(result.get(coordinates)!=null)
                {
                    List<ColorId> temp =result.get(coordinates);
                    for(Player p : dummie.playerThatSee(dummie.getSquare().getGameBoard()) )
                    {
                        if(!p.getColor().equals(ColorId.DUMMIE) && !p.getColor().equals(this.player.getColor()))
                            temp.add(p.getColor());
                    }
                }
                else
                    {
                        List<ColorId> colorList = new LinkedList<>();
                        for(Player p : dummie.playerThatSee(dummie.getSquare().getGameBoard()) )
                        {
                            if(!p.getColor().equals(ColorId.DUMMIE)  && !p.getColor().equals(this.player.getColor()))
                                colorList.add(p.getColor());
                        }
                        result.put(coordinates, colorList);
                    }

            }

        }

       dummie.getSquare().getRoom().removePlayerFromRoomList(dummie); //Remove dummie from room

        dummie.getSquare().removePlayer(dummie);//Remove the dummie from his square

        dummie.getSquare().getRoom().updatePlayerRoomList(); //Update the list of player inside
    return result;
    }
}