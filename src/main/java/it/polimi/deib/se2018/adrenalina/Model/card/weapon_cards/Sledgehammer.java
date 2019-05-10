package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class Sledgehammer extends WeaponCard
{

    private boolean[] avaiableMethod = new boolean[2];

    /**
     * Create the card Sledgehammer
     * @param color color of weapon
     * @param weaponID Id of the card
     * @param isLoaded Indicates if the weapon is loaded or not
     * @exception NullPointerException if color is null
     */
    public Sledgehammer(Color color, int weaponID, boolean isLoaded) {
        super(color, weaponID, isLoaded);
        this.name = "Sledgehammer";
        yellowAmmoCost = 1;
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

        if (isLoaded() && player.getSquare().getPlayerList().size() > 1 && player.getAmmoRed() >= 1)//If the second mode can be used
            avaiableMethod[1] = true;


        return avaiableMethod;

    }

    /**
     * Return the list of all target available for using of this weapon
     * @return all player that can be affected with the Sledgehammer in basic mode
     * @exception IllegalStateException if the basic mode can't be used
     */
    public List<Player> checkTargetForModes() throws IllegalStateException
    {
        if (!checkAvaliableMode()[0]) //check mode
            throw  new IllegalStateException("Modalità  dell'arma: "+name+" non eseguibile");

        List<Player> target = player.getSquare().getPlayerList(); //Obtain all players are in same square

        target.remove(player);//Remove the player that has this card

        return new ArrayList<>(target);//Returns all targets
    }

    /**
     * It uses the basic mode of the Sledgehammer
     * @param player player affected by weapon
     * @exception IllegalStateException if the basic mode can't be used
     */
    public void basicMode(Player player) throws IllegalStateException
    {
        if (!checkAvaliableMode()[0])//check mode
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        doDamage(player,2);//Do two damage

        isLoaded = false;
    }

    /**
     * Return the list of all square available for moving the player that will affect by Sledgehammer
     * in "in pulverize mode" mode
     * @return square available
     * @exception IllegalStateException if the alternative mode can't be used
     */
    public List<Square> checkMoveForAlternativeMode() throws IllegalStateException
    {

        if (!checkAvaliableMode()[1])
            throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

        //Obtain all square to distance 2
        Set<Square> squares = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 2);

         return squares
                .stream()
                .filter(square -> MethodsWeapons.checkSquareOneDirectionTwoMoves(square, player.getSquare().getX(), player.getSquare().getY()))
                .collect(Collectors.toList());
    }

    /**
     * It uses the alternative mode of the Sledgehammer
     * Do 3 damage point at a player and move him into a direction
     * @param player players affected by weapon
     * @param x coordinate x of the square where the player affected by weapon will move
     * @param y coordinate y of the square where the player affected by weapon will move
     * @exception IllegalStateException if the alternative mode can't be used
     */
    public void inPulverizeMode(Player player ,int x,int y) throws IllegalStateException
    {
        if (!checkAvaliableMode()[1])//Check mode
            throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

        doDamage(player,3);
        moveTarget(player,x,y);

        this.player.setAmmoRed(this.player.getAmmoRed() - 1);

        isLoaded = false;
    }


}