package it.polimi.deib.se2018.adrenalina.Model;

import java.util.List;

public class Room
{
    /*
    This class implements a room. The idea is that we have 3 private attributes:
    gameBoard to have a reference to the gameBoard
    squareList is a list of the square in the room
    playerRoomList is a list of the player in the room. Useful for certain weapons.

    squareList is set by the gameBoard, instead playerRoomList can change during the game
     */


    private GameBoard gameBoard;
    private List<Square> squareList;
    private List<Player> playerRoomList;


    /*
        This metod @returns a list of all the player in a room. Useful for certain weapons.
        The initialization with null is needed to reset it everytime, and return an empty list if there are no player when it is called

     */
    public List<Player> getPlayerRoomList ()
    {
        playerRoomList = null;
        for (Square square: squareList)
        {
            playerRoomList.addAll(square.getPlayerList());
        }
        return playerRoomList;
    }

    public GameBoard getGameBoard()
    {
        return gameBoard;
    }

    public List<Square> getSquareList()
    {
        return squareList;
    }

    /**
     * Create a room with its parameters
     * @param gameBoard reference to the gameBoard
     * @param squareList is a list with all the square in the room. Useful to keep track because some weapons are room based
     */
    public Room(GameBoard gameBoard, List<Square> squareList)
    {
        this.gameBoard = gameBoard;
        this.squareList = squareList;
    }




}