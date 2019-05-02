package it.polimi.deib.se2018.adrenalina.Model.power_up_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.GameBoard;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;

import java.util.*;

public class Teleporter extends PowerUpCard implements MoveTarget
{


    /**
     * Create a powerUp card generic
     * After the creation nobody (Player) has this card
     * @param color Color of the card
     * @param idPU id that identifies the card
     * @exception NullPointerException if color is null
     */
    public Teleporter(Color color, int idPU)
    {
        super(color, idPU);
    }

    /**
     * To use the teleporter card
     * Move a player located in a square in an other square
     * @param player player to move
     * @param x coordinate x of the square
     * @param y coordinate y of the square
     * @param arena Game board where the square is located
     * @exception NullPointerException If player or arena are null
     * @exception IllegalArgumentException If coordinate are not valid
     */
    @Override
    public void moveTarget(Player player, int x, int y, GameBoard arena) throws NullPointerException ,IllegalArgumentException
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
}