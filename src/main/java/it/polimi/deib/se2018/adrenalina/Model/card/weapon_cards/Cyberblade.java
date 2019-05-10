package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;

import java.util.*;


public class Cyberblade extends WeaponCard
{

    private boolean[] avaiableMethod = new boolean[3];

    /**
     * Create the card shotgun
     * @param color color of weapon
     * @param weaponID Id of the card
     * @param isLoaded Indicates if the weapon is loaded or not
     * @exception NullPointerException if color is null
     */
    public Cyberblade(Color color, int weaponID, boolean isLoaded)
    {
        super(color, weaponID, isLoaded);
        this.name = "Cyberblade";
        yellowAmmoCost = 1;
        blueAmmoCost = 0;
        redAmmoCost = 1;
    }


    /**
     * Check which modes of the weapon can be used by player that has this weapon
     * @return array of booleans of size 2 the first represent the basic mode , the second the effect "with shadowstep"
     *  and the third the effect "with slice and dice"
     * @exception IllegalStateException if this card doesn't belong at a player
     */
    public boolean[] checkAvaliableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: "+ name + " non appartiene a nessun giocatore");//If this card doesn't belong at a player launch exception

        avaiableMethod[0] = false; //I suppose that the modes can't be used
        avaiableMethod[1] = false;
        avaiableMethod[2] = false;


        if (isLoaded() && MethodsWeapons.playersReachable(player.getSquare(),1).size() > 1)//if the first mode can be used
             avaiableMethod[0] = true;
        else
            return avaiableMethod;

        avaiableMethod[1] = true;

        if (isLoaded() && MethodsWeapons.playersReachable(player.getSquare(),1).size() > 2 && player.getAmmoYellow() >= 1)//If the second mode can be used
            avaiableMethod[2] = true;

        return avaiableMethod;
    }

    /**
     * Return a hash map with key the square and value a list of all player in that square
     * There aren't squares without player
     * It can become useful about the mode basic of cyberblade in some specially case of utilization
     * In general this hash map contains all possible target for cyberblade
     * @return all player that can be affected with the Cyberblade in basic mode
     * @exception IllegalStateException if the basic mode can't be used
     */
    public Map<Integer[],List<Player>> checkBasicModeAllTargetPossible() throws IllegalStateException
    {
        if (!checkAvaliableMode()[0]) //check mode
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        Map<Integer[],List<Player>> result = new HashMap<>();

        //Obtain all squares reachable at distance 1
        Set<Square> squares = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 1);


        for (Square t:squares) //For each squares
        {
            if (!t.getPlayerList().isEmpty()) //If the square has some player
            {
                Integer[] coordinates = new Integer[2];//Save the coordinates
                coordinates[0] = t.getX();
                coordinates[1] = t.getY();

                result.putIfAbsent(coordinates,t.getPlayerList()); //Add the square with the player at hash map
            }

        }

        return result;

    }

    /**
     * Return the list of all squares available for using the effect "with shadowstep" of this weapon
     * Only if this mode being used after the basic effect
     * @return all the square where the player can move
     * @exception IllegalStateException if the effect can't be used
     */
    public List<Square> checkWithShadowStep() throws IllegalStateException
    {

        if (!checkAvaliableMode()[1])
            throw  new IllegalStateException("Modalità passo d'ombra dell'arma: "+name+" non eseguibile");

        //Obtain all square reachable to distance 1
        Set<Square> squares = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 1);

        squares.remove(player.getSquare());//Remove from the squares the square where the player that shoot is located

        return new ArrayList<>(squares); //Returns all targets
    }



    /**
     * It uses the basic mode of the Cyberblade with additivity effects or not
     * @param player player affected by weapon in the basic effect
     * @param orderEffect array that represent the order of the effect and what of those apply
     * @param player2 player affected by weapon in the "with slice and dice" effect (can be null if mode don't used)
     * @param x coordinate x of the square used for "with shadowstep" effect (if effect is off it doesn't use)
     * @param y coordinate y of the square used for "with shadowstep" effect (if effect is off it doesn't use)
     * @exception IllegalStateException if the basic mode can't be used
     */
    public void basicMode(Player player,String[] orderEffect,Player player2,int x,int y) throws IllegalStateException
    {
        if (!checkAvaliableMode()[0])//check mode
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        int i = 0;

        boolean[] booleans = checkAvaliableMode();

        while (i < orderEffect.length)//Do the effects in order indicated by player in view
        {
            if (orderEffect[i].equals("basic") && booleans[0])
                doDamage(player,2);
            if (orderEffect[i].equals("with shadowstep") && booleans[1])
                moveTarget(this.player,x,y);
            if (orderEffect[i].equals("with slice and dice") && booleans[2])
            {
                doDamage(player2,2);
                this.player.setAmmoYellow(this.player.getAmmoYellow() - 1);
            }

            i++;

        }

        isLoaded = false; //Weapon now is out ammo
    }
}