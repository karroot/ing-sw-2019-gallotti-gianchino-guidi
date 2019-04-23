package it.polimi.deib.se2018.adrenalina.Model;

import java.util.Arrays;
import java.util.List;

public abstract class Square
{

    private List<Player> playerList;

    int x; //Coordinate x of square

    int y; //Cordinate y of square

    private GameBoard gameBoard;

    protected ColorRoom color;

    protected SideType[] side;

    protected  Room room;

    private AmmoPoint ammoPoint;

    private SpawnPoint spawnPoint;

    private boolean isAmmoPoint;

    private boolean isSpawnPoint;



    /**
     * Create a square with its parameters
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
    }


    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public AmmoPoint getAmmoPoint()
    {
        return ammoPoint;
    }

    public SpawnPoint getSpawnPoint()
    {
        return spawnPoint;
    }

    public boolean isAmmoPoint() {
        return isAmmoPoint;
    }

    public boolean isSpawnPoint() {
        return isSpawnPoint;
    }

    public Room getRoom() {
        return room;
    }

    public List<Player> getPlayerList()
    {
        return playerList;
    }

    public GameBoard getGameBoard()
    {
        return gameBoard;
    }

    public ColorRoom getColor()
    {
        return color;
    }

    public SideType[] getSide()
    {
        return side;
    }


    /**
     * It add a player to the square if he is not already present
     * @param player player that gets into the square
     */
    public void addPlayer(Player player)
    {
        if (!playerList.contains(player)) //If player there isn't in the players' list
            playerList.add(player);//Add player in players' list
    }

    /**
     * It remove a player to the square if he is present
     * @param player player that exits to the square
     */
    public void removePlayer(Player player)
    {
        if (playerList.contains(player)) //If player there is in the players' list
            playerList.remove(player);//Remove player in players' list
    }


    //AF

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