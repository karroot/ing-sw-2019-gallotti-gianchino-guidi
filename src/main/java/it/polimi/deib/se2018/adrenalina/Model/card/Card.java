package it.polimi.deib.se2018.adrenalina.Model.card;

import it.polimi.deib.se2018.adrenalina.Controller.Controller;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class represents a generic card
 *
 *  * @author Cysko7927
 */
public abstract class Card
{

    protected Player player;


    /**
     * this card belong now at a player
     * @param player player that noe he has the card
     * @exception NullPointerException if the player is null
     */
    public void setPlayer(Player player) throws NullPointerException
    {
        if (player == null)
            throw new NullPointerException("Parametro player nullo");

        this.player = player;
    }

    /**
     * Say which player has this card, if nobody has this card then it returns null
     * @return the player that has this card
     */
    public Player getPlayer()
    {
        return player;
    }

    /**
     * it adds the colorid of the attacker to the list of attacked player
     * @param attacked player attacked
     * @param attacker attacker player
     */
    public static void addToRoundDamageList(ColorId attacked, ColorId attacker){
        Map<ColorId, Set<ColorId>> roundDamageList = Controller.roundDamageList;
        if(roundDamageList.containsKey(attacker))
        {
            Set<ColorId> tempList;
           tempList  = roundDamageList.get(attacker);
           tempList.add(attacked);

        }

        else{

            Set<ColorId> tempList = new HashSet<>();
            tempList.add(attacked);
            roundDamageList.putIfAbsent(attacker,tempList);

        }



    }


    /**
     * it Does n damage point at a player
     * @param player player that suffers the damage
     * @param n quantity of damage point to do at player
     */
    protected void doDamage(Player player, int n)
    {
        for (int i = 0; i < n; i++)//Do the damage points
            player.doDamage(this.player.getColor());

        for (int i = player.checkMarker(this.player.getColor()); i != 0;i--) //If there are marks
            player.useMark(this.player.getColor());//Use them

        addToRoundDamageList(player.getColor(),this.player.getColor());
    }

    /**
     * Move a player located in a square in an other square
     * @param player player to move
     * @param x coordinate x of the square
     * @param y coordinate y of the square
     * @exception NullPointerException If player is null
     * @exception IllegalArgumentException If coordinate are not valid
     */
    protected void moveTarget(Player player,int x,int y) throws IllegalArgumentException,NullPointerException
    {

        MethodsWeapons.operationToMove(player, x, y);
    }

    /**
     * it Does n marks at a player
     * @param player player that suffers the damage
     * @param n quantity of damage point to do at player
     */
    protected void markTarget(Player player, int n)
    {
        for (int i = 0; i < n; i++) //Do the mark at the player
            player.addMark(this.player.getColor());
    }

    /**
     * Move the player that has this card located in a square in an other square
     * @param x coordinate x of the square
     * @param y coordinate y of the square
     * @exception NullPointerException If player is null
     * @exception IllegalArgumentException If coordinate are not valid
     */
    protected void moveYourself(int x, int y)
    {
        moveTarget(player,x,y); //Move the player that has the card in the square with coordinate x and y
    }
}
