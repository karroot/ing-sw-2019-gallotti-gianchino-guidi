package it.polimi.deib.se2018.adrenalina.Model;

public class AmmoPoint extends Square
{
    /**
     * This class implements a Square where there is an AmmoTile.
     * This point has 1 AmmoTile to grab.
     */
    private AmmoTiles ammoTiles;


    /**
     * This method is a getter the the private attribute ammoTiles.
     *
     * @return the ammoTiles card
     */
    public AmmoTiles getAmmoTiles()
    {
        return ammoTiles;
    }

    /**
     * This method draw uses the AmmoTiles card that is on the point, sets the ammoTiles on null
     *
     * @param player is who will gte the ammo and the powerup if present
     * @return the AmmoTiles that is used
     */
    public AmmoTiles useAmmoTiles (Player player)
    {
        AmmoTiles tempAmmoTiles = ammoTiles;
        ammoTiles.useAmmoTilesCards(player);
        ammoTiles = null;
        return tempAmmoTiles;
    }

    /**
     * This method replaces the ammoTiles card on this square.
     * It supposes a Player called the getAmmoTile before.
     */
    public void replaceAmmoTiles ()
    {
        GameBoard tempGame = getGameBoard();
        ammoTiles = tempGame.getAmmoTilesStack().pop();
    }



    /**
     * This is the public constructor of this class.
     *
     * @param x is the x coordinate of the square
     * @param y is the y coordinate of the square
     * @param gameBoard is the refence to the gameboard
     * @param color is the color of the room
     * @param side is the array that defines the side types of this square
     */
    public AmmoPoint (int x, int y, GameBoard gameBoard, ColorRoom color, SideType[] side)
    {
        super(x,y,gameBoard,color,side);
    }

}
