package it.polimi.deib.se2018.adrenalina.Model.power_up_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.GameBoard;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;

import java.util.*;


public class Newton extends PowerUpCard implements MoveTarget
{

    public Newton(Color color, int idPU) throws NullPointerException
    {
        super(color, idPU);
    }

    /**
     * To use the newton card
     * Move a player located in a square in an other square
     * (Requires) The coordinates must be such that the player will be moved in an unique direction(North,East,South,West)
     * @param player player to move
     * @param x coordinate x of the square
     * @param y coordinate y of the square
     * @param arena Game board where the square is located
     * @exception NullPointerException If player or arena are null
     * @exception IllegalArgumentException If coordinate are not valid
     */
    @Override
    public void moveTarget(Player player, int x, int y, GameBoard arena) throws NullPointerException , IllegalArgumentException
    {
        if (player == null || arena == null)
            throw new NullPointerException("Parametro player o arena nullo");

        Square square;

        try //Obtain the square with coordinates x and y
        {
            square = arena.getArena().getSquare(x, y);
        }
        catch (SquareNotInGameBoard e) //If coordinate are not valid
        {
            System.out.println(e);

            throw new IllegalArgumentException("Cordinate non valide");//Launch exceptions
        }


        player.getSquare().removePlayer(player); //Remove the player from square where is located now

        //Remove player from Room //Chiedere a giovanni di modificare l'interfaccia

        square.addPlayer(player);//Add the player to the square

        square.getRoom().getPlayerRoomList();//Add player to the room that contains the square

        player.setSquare(square);//The player is now in the new square
    }

    /**
     * Return a set of all square where the player indicated can be move in an unique direction
     * @param player player to move
     * @param arena Game board where are located the square
     * @return set of all square where the player can be move in an unique direction
     */
    public Set<Square> checkMoveTarget(Player player, GameBoard arena)throws NullPointerException
    {
        if (player == null || arena == null)//Check null
            throw new NullPointerException("Parametro player o arena nullo");

        Set<Square> squaresCorrect = new LinkedHashSet<>(); //Create an empty set

        Set<Square> squares = arena.getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 2);
        //Obtain all square reachable from where the player is located

        int x = player.getSquare().getX(); //Obtain coordinate of the square where the player is located
        int y = player.getSquare().getY();

        for (Square temp : squares) //For each square in all square reachable
        {
            if (checkSquare(temp,x,y)) //Check if the square is valid for to use the newton card
                squaresCorrect.add(temp);//Add to the set to return
        }

        return squaresCorrect; //Return all square correct
    }



    //Check if the square "e" is located to North or East or South or West of the square with coordinates x and y
    private boolean checkSquare(Square e, int x,int y)
    {
        if ((x == e.getX() + 1 && y == e.getY() ) || (x == e.getX() + 2 && y == e.getY()))//If the square (x,y) is at north
            return true;
        else if ((x == e.getX() - 1 && y == e.getY() ) || (x == e.getX() - 2 && y == e.getY()))//If the square (x,y) is at south
            return true;
        else  if ((x == e.getX() && y == e.getY() + 1 ) || (x == e.getX() && y == e.getY() + 2))//If the square (x,y) is at East
            return true;
        else  if ((x == e.getX() && y == e.getY() - 1 ) || (x == e.getX() && y == e.getY() - 2))//If the square (x,y) is at West
            return true;
        else
            return false;
    }
}