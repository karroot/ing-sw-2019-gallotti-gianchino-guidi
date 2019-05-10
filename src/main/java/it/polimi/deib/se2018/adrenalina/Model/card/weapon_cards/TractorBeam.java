package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;

import java.util.List;
import java.util.Set;

import static it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons.playersReachable;


public class TractorBeam extends WeaponCard
{
    private boolean[] avaiableMethod = new boolean[2];

    public TractorBeam( Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        yellowAmmoCost = 0;
        blueAmmoCost = 1;
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
            throw new IllegalStateException("Carta: "+ name + " non appartiene a nessun giocatore");

        avaiableMethod[1] = false;
        avaiableMethod[0] = false;


        if (isLoaded() && player.playerThatSee(player.getSquare().getGameBoard()).size()>1)
            avaiableMethod[0] = true;

        if (isLoaded()&& player.getAmmoRed()>0 && player.getAmmoYellow()>0&& player.playerThatSee(player.getSquare().getGameBoard()).size()>1)
            avaiableMethod[1] = true;



        return avaiableMethod;

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

        Set<Square> squares = square.getGameBoard().getArena().squareReachableNoWall(square.getX(),square.getY(),2);//Obtain all the player that they are in same square

        squares.remove(square); //Remove from targets the square where the player is located

        return squares;//Returns squares
    }
    /**
     * Calculate in which square a player can be moved using the advanced mode
     * @param player player to move
     * @return Set of all square corrects
     * @exception IllegalStateException if the basic mode can't be used
     */
    public Set<Square> checkMoveAdvancedMode(Player player) throws IllegalStateException
    {
        if (!checkAvaliableMode()[1])
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        return checkMoveBasicMode(player);//Returns squares
    }
    /**
     * Return the list of all target available for using the basic mode of this weapon
     * @return all player that can be affected with the lock rifle in basic mode
     */
    public List<Player> checkBasicMode() throws  IllegalStateException
    {
        if (!checkAvaliableMode()[0])
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        List<Player> playerList = (List<Player>) playersReachable( player.getSquare(),3);

        return playerList;//Returns all targets
    }
    /**
     * It uses the basic mode of the lock rifle
     * @param player player affected by weapon
     */
    public void basicMode(Player player,int x,int y) throws IllegalStateException
    {
        if (!checkAvaliableMode()[0])
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        doDamage(player,2);
        moveTarget(player,x,y);//Move the target
        this.isLoaded = false;
    }
    /**
     * Return the list of all target available for using the punisher mode of this weapon
     * @return all player that can be affected with the lock rifle in basic mode
     */
    public List<Player> checkPunisherMode() throws IllegalStateException
    {

        if (!checkAvaliableMode()[1])
            throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

        return checkBasicMode();
    }
    /**
     * It uses the punisher mode of the lock rifle
     * @param player player affected by weapon
     */
    public void punisherMode(Player player, int x ,int y) throws  IllegalStateException
    {

        if (!checkAvaliableMode()[1])
            throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

        doDamage(player,3);
        moveTarget(player,x,y);//Move the target
        this.isLoaded = false;
        this.player.setAmmoRed(this.player.getAmmoRed() - 1);
        this.player.setAmmoYellow(this.player.getAmmoYellow() - 1);
    }
}