package it.polimi.deib.se2018.adrenalina.Model.card;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.TagbackGranade;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Cysko7927
 */
public abstract class Card
{
   protected Map<ColorId, List<ColorId>> roundDamageList = new HashMap<>(); // lista dei giocatori che ho attaccato io sono il giocatore dato dal ColorId chiave, chiedere a fra
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
     * @param attacked
     * @param attacker
     */
    private void addToRoundDamageList(ColorId attacked , ColorId attacker ){

        if(roundDamageList.containsKey(attacker))
        {
            List<ColorId> tempList = new LinkedList<>();
           tempList  = roundDamageList.get(attacker);
           tempList.add(attacked);
           roundDamageList.replace(attacker,roundDamageList.get(attacker),tempList);
        }

        else{

            List<ColorId> tempList = new LinkedList<>();
            tempList.add(attacked);
            roundDamageList.put(attacker,tempList);

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

        if (player == null)
            throw new NullPointerException("Parametro player o arena nullo");

        Square square;

        try //Obtain the square with coordinates x and y
        {
            square = player.getSquare().getGameBoard().getArena().getSquare(x, y); //obtain the square with coordinate x and y
        }
        catch (SquareNotInGameBoard e) //If coordinate are not valid
        {
            System.out.println(e);

            throw new IllegalArgumentException("Cordinate non valide");//Launch exceptions
        }

        player.getSquare().getRoom().removePlayerFromRoomList(player); //Remove player from room

        player.getSquare().removePlayer(player);//Remove the player from his square

        player.setSquare(square);//Add the new square on player

        square.addPlayer(player); //Add the player on the new square

        player.getSquare().getRoom().updatePlayerRoomList(); //Update the list of player inside
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
