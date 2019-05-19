package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;

import java.util.*;

public class PlasmaGun extends WeaponCard
{
    private boolean[] avaiableMethod = new boolean[4];
    private Player dummie = new Player(ColorId.BLUE,"a","a",false);
    public PlasmaGun(Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        redAmmoCost = 0;
        yellowAmmoCost = 1;
        blueAmmoCost = 1;

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

        avaiableMethod[2] = false;

        avaiableMethod[1] = false;

        avaiableMethod[0] = false;

        avaiableMethod[3] = false;




        if (isLoaded() && player.playerThatSee(player.getSquare().getGameBoard()).size()>1)
            avaiableMethod[0] = true;


        if  (isLoaded()&& player.getAmmoBlue()>0 && player.playerThatSee(player.getSquare().getGameBoard()).size()>1)
            avaiableMethod[1] = true;
        if (isLoaded())
            avaiableMethod[2] = true;
        if(isLoaded() && (checkPhaseGlide().size()>1) )
            avaiableMethod[3] = true;
        return avaiableMethod;

    }
    /**
     * Return the list of all target available for using the basic mode of this weapon
     * @return all player that can be affected with the lock rifle in basic mode
     * @throws IllegalStateException
     */
    public List<Player> checkBasicMode() throws IllegalStateException
    {
        if (!checkAvaliableMode()[0])
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");
        Set<Player> target = MethodsWeapons.playersReachable(player.getSquare(),3); //Obtain all players that can be targets

        target.remove(player);//Remove the player that has this card
        List<Player> playerList = new LinkedList<>();
        playerList.addAll(player.playerThatSee(player.getSquare().getGameBoard()));

        for(Player i : target)
        {
            if  (!(i.equals(playerList)))
                target.remove(i);
        }
        return new ArrayList<>(target);//Returns all targets
    }

    /**
     *
     * @param player player affected by weapon
     * @throws IllegalStateException
     * @throws IllegalArgumentException
     */
    public void basicMode(Player player,String[] orderEffect,int x,int y) throws IllegalStateException, IllegalArgumentException, IllegalAccessException {
        int i = 0;
        boolean[] booleans = checkAvaliableMode();
        while (i < orderEffect.length)
        {
            if (orderEffect[i].equals("basic"))
            {
                if (!checkAvaliableMode()[0])
                    throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

                doDamage(player,2);
            }
            if (orderEffect[i].equals("with phase glide") && booleans[1]) {
                if (!checkAvaliableMode()[2])
                    throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

                moveTarget(this.player, x, y);
            }
            if (orderEffect[i].equals("with charged shot") && booleans[2])
            {
                if (!checkAvaliableMode()[1])
                    throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

                doDamage(player,1);
                this.player.setAmmoBlue(this.player.getAmmoBlue() - 1);
            }
        i++;
        }

        this.isLoaded = false;
    }


    /**
     * Calculate in which square a player can be moved using the basic mode
     * @return Set of all square corrects
     * @exception IllegalStateException if the basic mode can't be used
     */
    public List<Square> checkChargedShot() throws IllegalStateException
    {
        if (!checkAvaliableMode()[1])
            throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

        Square square = player.getSquare();

        Set<Square> squares = square.getGameBoard().getArena().squareReachableNoWall(square.getX(),square.getY(),2);//Obtain all the reachable square



        return new ArrayList<>(squares);
    }
    /**
     * Return the list of all target available for using the "with charged shot" effect of this weapon
     * @return all player that can be affected with the Plasma gun  and the effect "with charged shot"
     * Return all possible players that can be targets also if the player will move in other square
     * @exception IllegalStateException if the effect can't be used
     */
    public Set<Player> checkPhaseGlide() throws IllegalStateException
    {
        Set<Player> playerReachable = new HashSet<>();
        if (!checkAvaliableMode()[2]) //check mode
            throw  new IllegalStateException("Modalità dell'arma: "+name+" non eseguibile");
        Set<Square> target=player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX() , player.getSquare().getY(),2); //Obtain all players that can be targets



        for(Square i : target)
        {
            dummie.setSquare(i);
            if  (dummie.playerThatSee(dummie.getSquare().getGameBoard()).size() > 0)
                playerReachable.addAll(dummie.playerThatSee(dummie.getSquare().getGameBoard()));
        }
        return playerReachable;//Returns all targets

    }
}