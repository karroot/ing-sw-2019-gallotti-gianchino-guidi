package it.polimi.deib.se2018.adrenalina.Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class implements a point where the player can spawn from
 *
 * @author gioguidi and Cysko7927
 */
public abstract class Square
{

    private List<Player> playerList; //List of all the players that are located in this room

    int x; //Coordinate x of square

    int y; //Coordinate y of square

    private GameBoard gameBoard;

    protected ColorRoom color;

    private SideType[] side;

    protected  Room room;

    boolean isAmmoPoint;

    boolean isSpawnPoint;



    /**
     * Create a square with its parameters
     *
     * @param x coordinate x of the square
     * @param y coordinate y of the square
     * @param gameBoard Game board that contains the square
     * @param color //Color of the room that contains the square
     * @param side //Defines what are the different side of the square
     */
    public Square(int x, int y, GameBoard gameBoard, ColorRoom color, SideType[] side)
    {
        this.x = x;
        this.y = y;
        this.gameBoard = gameBoard;
        this.color = color;
        this.side = side;
        this.playerList= new LinkedList<>();
    }


    /**
     * This is the public getter for the x coordinate attribute
     *
     * @return the x coordinate attribute of the class
     */
    public int getX()
    {
        return x;
    }

    /**
     * This is the public getter for the y coordinate attribute
     *
     * @return the y coordinate attribute of the class
     */
    public int getY()
    {
        return y;
    }

    /**
     * This method will convert the coordinates of the square into a string
     *
     * @return a string with the coordinates of the square
     */
    public String toStringCoordinates()
    {
        return "x = "+ x +", y = " + y;
    }

    /**
     * This method checks if the room is a ammopoint
     *
     * @return a true boolean if the square is an ammopoint
     */
    public boolean isAmmoPoint() {
        return isAmmoPoint;
    }

    /**
     * This method checks if the room is a spawnpoint
     *
     * @return a true boolean if the square is a spawnpoint
     */
    public boolean isSpawnPoint() {
        return isSpawnPoint;
    }

    /**
     * This is the public getter for the room attribute
     *
     * @return the room attribute of the class
     */
    public Room getRoom() {
        return room;
    }


    /**
     * This is the public getter for the playerList attribute
     *
     * @return the playerList attribute of the class
     */
    public List<Player> getPlayerList()
    {
        return new ArrayList<>(playerList);
    }

    /**
     * This method will covert the playerList attribute into a list of the colorId of the player
     *
     * @return a list of colorId of the players in the square
     */
    public List<ColorId> getPlayerListColor ()
    {
        return new ArrayList<>(playerList.stream().map(Player::getColor).collect(Collectors.toList()));
    }

    /**
     * This is the public getter for the gameboard attribute
     *
     * @return the gameboard attribute of the class
     */
    public GameBoard getGameBoard()
    {
        return gameBoard;
    }

    /**
     * This is the public getter for the ColorRoom attribute
     *
     * @return the ColorRoom attribute of the class
     */
    public ColorRoom getColor()
    {
        return color;
    }

    /**
     * This is the public getter for the side attribute
     *
     * @return the side attribute of the class
     */
    public SideType[] getSide()
    {
        return side.clone();
    }


    /**
     * It add a player to the square if he is not already present
     *
     * @param player player that gets into the square
     */
    public void addPlayer(Player player)
    {
        if (!playerList.contains(player)) //If player there isn't in the players' list
            playerList.add(player);//Add player in players' list
    }

    /**
     * It remove a player to the square if he is present
     *
     * @param player player that exits to the square
     */
    public void removePlayer(Player player)
    {
            playerList.remove(player);//Remove player from the players list
    }

    /**
     * This is the public setter for the room attribute
     *
     * @param room is the attribute that will be set
     */
    public void setRoom(Room room)
    {
        this.room = room;
    }


    @Override
    public String toString()
    {
        return "Square{" +
                ", x=" + x +
                ", y=" + y +
                ", color=" + color +
                '}';
    }
}