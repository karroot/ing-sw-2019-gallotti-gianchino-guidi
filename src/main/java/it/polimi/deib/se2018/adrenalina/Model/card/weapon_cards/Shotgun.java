package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;

import java.util.*;

/**
 * @author Cysko7927
 */
public class Shotgun extends WeaponCard
{

    private boolean[] avaiableMethod = new boolean[2];


    /**
     * Create the card shotgun
     * @param color color of weapon
     * @param weaponID Id of the card
     * @param isLoaded Indicates if the weapon is loaded or not
     * @exception NullPointerException if color is null
     */
    public Shotgun(Color color, int weaponID, boolean isLoaded) throws NullPointerException
    {
        super(color, weaponID, isLoaded);
        this.name = "Shotgun";
        yellowAmmoCost = 2;
        blueAmmoCost = 0;
        redAmmoCost = 0;
    }


    /**
     * Check which modes of the weapon can be used by player that has this weapon
     * @return array of booleans of size 2 the first represent the basic mode the second the alternative mode
     * @exception IllegalStateException if this card doesn't belong at a player
     */
    public boolean[] checkAvaliableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: "+ name + " non appartiene a nessun giocatore");//If this card doesn't belong at a player launch exception

        avaiableMethod[0] = false; //I suppose that the modes can't be used
        avaiableMethod[1] = false;

        if (isLoaded() && player.getSquare().getPlayerList().size() > 1)//If the first mode can be used
            avaiableMethod[0] = true;

        if (isLoaded() && MethodsWeapons.playersReachable(player.getSquare(),1).size() > 1)//If the second mode can be used
            avaiableMethod[1] = true;



        return avaiableMethod;

    }

    /**
     * Return the list of all target available for using the basic mode of this weapon
     * @return all player that can be affected with the shotgun in basic mode
     * @exception IllegalStateException if the basic mode can't be used
     */
    public List<Player> checkBasicMode() throws IllegalStateException
    {
        if (!checkAvaliableMode()[0])
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        List<Player> playerList = player.getSquare().getPlayerList();//Obtain all the player that they are in same square

        playerList.remove(player); //Remove from targets the player that shoot

        return playerList;//Returns all targets
    }

    /**
     * Calculate in which square a player can be moved using the basic mode
     * @param player player to move
     * @return Set of all square corrects
     * @exception IllegalStateException if the basic mode can't be used
     */
    public Set<Square> checkMoveBasicMode(Player player) throws IllegalStateException
    {
        if (!checkAvaliableMode()[0])
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        Square square = player.getSquare();

        Set<Square> squares = square.getGameBoard().getArena().squareReachableNoWall(square.getX(),square.getY(),1);//Obtain all the player that they are in same square

        squares.remove(square); //Remove from targets the square where the player is located

        return squares;//Returns squares
    }

    /**
     * It uses the basic mode of the shotgun
     * @param player player affected by weapon
     * @param move if the player decided to move the target
     * @param x coordinate x of the square where to move the player
     * @param y coordinate x of the square where to move the player
     * @throws SquareNotInGameBoard if coordinate are wrong
     * @exception IllegalStateException if the basic mode can't be used
     */
    public void basicMode(Player player, boolean move, int x,int y) throws SquareNotInGameBoard ,IllegalStateException
    {
        if (!checkAvaliableMode()[0])
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        doDamage(player,3);

        if (move)//If the player with shotgun decided to move the target
        {

            moveTarget(player,x,y);//Move the target
        }

        this.isLoaded = false;
    }

    /**
     * Return the list of all target available for using the alternative mode of this weapon
     * @return all player that can be affected with the shotgun in alternative mode
     * @exception IllegalStateException if the alternative mode can't be used
     */
    public List<Player> checkInLongBarrelMode() throws IllegalStateException
    {

        if (!checkAvaliableMode()[1])
            throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

        Set<Player> playerList = MethodsWeapons.playersReachable(player.getSquare(),1);//Obtain player reachable

        playerList.remove(player);//Remove from targets the player that shoot

        return new ArrayList<>(playerList); //Returns all targets
    }

    /**
     * It uses the alternative mode of the shotgun
     * @param player player affected by weapon
     * @exception IllegalStateException if the alternative mode can't be used
     */
    public void inLongBarrelMode(Player player) throws IllegalStateException
    {
        if (!checkAvaliableMode()[1])
            throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

        doDamage(player,2); //Do two damage at a player

        isLoaded = false;
    }



}