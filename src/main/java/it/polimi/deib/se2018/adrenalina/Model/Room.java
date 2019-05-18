package it.polimi.deib.se2018.adrenalina.Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class implements a room.
 *
 * @author giovanni
 */
public class Room
{
    /*
     * We have 3 private attributes:
     * @attribute gameBoard to have a reference to the gameBoard
     * @attribute squareList is a list of the square in the room
     * @attribute playerRoomList is a list of the player in the room. Useful for certain weapons.
     * @attribute color is the color the of room
     *
     * We need to observe that squareList is set by the gameBoard, instead playerRoomList can change during the game
     */

    private ColorRoom color;
    private GameBoard gameBoard;
    private List<Square> squareList;
    private List<Player> playerRoomList;


    /**
     * The initialization with null is needed to reset it everytime, and return an empty list if there are no player when it is called
     *
     * @return a list of all the player in a room. Useful for certain weapons.
     */
    public void updatePlayerRoomList ()
    {
        playerRoomList = new LinkedList<>(); //initialize the list
        for (Square square: squareList) //iterates in the square of the room to find the players
        {
            playerRoomList.addAll(square.getPlayerList());
        }
    }


    /**
     * //todo
     * @return
     */
    public List<Player> getPlayerRoomList ()
    {
        return new ArrayList<>(playerRoomList);
    }

    /**
     * //todo
     * @return
     */
    public List<ColorId> getPlayerRoomListColor ()
    {
        return new ArrayList<>(playerRoomList.stream().map(Player::getColor).collect(Collectors.toList()));
    }


    /**
     * //todo
     * @param player
     */
    public void removePlayerFromRoomList (Player player)
    {
        playerRoomList.remove(player);
    }

    /**
     * This method is the public getter of the board
     * @return
     */
    public GameBoard getGameBoard()
    {
        return gameBoard;
    }

    /**
     * This method is the public getter if the squareList
     * @return
     */
    public List<Square> getSquareList()
    {
        return squareList;
    }

    /**
     * Create a room with its parameters
     * All the square that are in squareList belong at this room now
     * @param gameBoard reference to the gameBoard
     * @param squareList is a list with all the square in the room. Useful to keep track because some weapons are room based
     * @param color is the color of the room
     */
    public Room(GameBoard gameBoard, List<Square> squareList, ColorRoom color)
    {
        this.gameBoard = gameBoard;
        this.squareList = new ArrayList<>();
        this.squareList.addAll(squareList);
        this.squareList.stream().forEach(square -> square.setRoom(this));
        this.color = color;
        this.playerRoomList = new ArrayList<>();
    }


    /**
     * This is the public getter for the color of the room.
     *
     * @return the color of the room
     */
    public ColorRoom getColor() {
        return color;
    }
}