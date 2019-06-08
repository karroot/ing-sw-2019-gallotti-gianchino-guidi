package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestInput;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestShotgun;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseShotgun;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Cysko7927
 */
public class Shotgun extends WeaponCard
{

    private boolean[] availableMethod = new boolean[2];


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
    public boolean[] checkAvailableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: "+ name + " non appartiene a nessun giocatore");//If this card doesn't belong at a player launch exception

        availableMethod[0] = false; //I suppose that the modes can't be used
        availableMethod[1] = false;

        if (isLoaded() && player.getSquare().getPlayerList().size() > 1)//If the first mode can be used
            availableMethod[0] = true;

        //Calculate the player that are exactly to distance 1
        Set<Player> players = MethodsWeapons.playersReachable(player.getSquare(),1);
        players.removeAll(player.getSquare().getPlayerList());

        if (isLoaded() && !players.isEmpty())//If the second mode can be used
            availableMethod[1] = true;



        return availableMethod;

    }

    /**
     * Return the list of all target available for using the basic mode of this weapon
     * @return all player that can be affected with the shotgun in basic mode
     * @exception IllegalStateException if the basic mode can't be used
     */
    public List<ColorId> checkBasicMode() throws IllegalStateException
    {
        if (!checkAvailableMode()[0])
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        List<Player> playerList = player.getSquare().getPlayerList();//Obtain all the player that they are in same square

        playerList.remove(player); //Remove from targets the player that shoot

        return playerList.stream().map(Player::getColor).collect(Collectors.toList());//Returns all targets
    }

    /**
     * Calculate in which square a player can be moved using the basic mode
     * @return Set of all square corrects
     * @exception IllegalStateException if the basic mode can't be used
     */
    public List<Square> checkMoveBasicMode() throws IllegalStateException
    {
        if (!checkAvailableMode()[0])
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        Square square = player.getSquare();

        Set<Square> squares = square.getGameBoard().getArena().squareReachableNoWall(square.getX(),square.getY(),1);//Obtain all the player that they are in same square

        squares.remove(square); //Remove from targets the square where the player is located

        return squares.stream().collect(Collectors.toList());//Returns squares
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
        if (!checkAvailableMode()[0])
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
    public List<ColorId> checkInLongBarrelMode() throws IllegalStateException
    {

        if (!checkAvailableMode()[1])
            throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

        Set<Player> playerList = MethodsWeapons.playersReachable(player.getSquare(),1);//Obtain player reachable

        playerList.remove(player);//Remove from targets the player that shoot

        return new ArrayList<>(playerList).stream().map(Player::getColor).collect(Collectors.toList()); //Returns all targets
    }

    /**
     * It uses the alternative mode of the shotgun
     * @param player player affected by weapon
     * @exception IllegalStateException if the alternative mode can't be used
     */
    public void inLongBarrelMode(Player player) throws IllegalStateException
    {
        if (!checkAvailableMode()[1])
            throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

        doDamage(player,2); //Do two damage at a player

        isLoaded = false;
    }


    public void useWeapon(ResponseInput responseMessage)
    {
        ResponseShotgun msg = (ResponseShotgun) responseMessage;

        if (msg.isMode())
            inLongBarrelMode(MethodsWeapons.ColorToPlayer(msg.getTarget(),player.getSquare().getGameBoard()));
        else
        {
            try
            {
                basicMode(MethodsWeapons.ColorToPlayer(msg.getTarget(),player.getSquare().getGameBoard()),msg.isMove(),msg.getX(),msg.getY());
            }
            catch (Exception e)
            {
                System.out.println("Impossibile usare l'arma:" + name);
            }
        }
    }

    @Override
    public RequestInput getRequestMessage() {
        if (checkAvailableMode()[0] && checkAvailableMode()[1])

            return new RequestShotgun(checkAvailableMode(),checkBasicMode(),checkInLongBarrelMode(),checkMoveBasicMode());

        else if(checkAvailableMode()[0] && !checkAvailableMode()[1])

            return new RequestShotgun(checkAvailableMode(),checkBasicMode(),new ArrayList<>(),checkMoveBasicMode());

        else
            return new RequestShotgun(checkAvailableMode(),new ArrayList<>(),checkInLongBarrelMode(),new ArrayList<>());
    }
}