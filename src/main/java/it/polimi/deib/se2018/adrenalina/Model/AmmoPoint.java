package it.polimi.deib.se2018.adrenalina.Model;

public class AmmoPoint extends Square
{


    private AmmoTiles ammoTiles;


    public AmmoPoint (int x, int y, GameBoard gameBoard, ColorRoom color, SideType[] side)
    {
        super(x,y,gameBoard,color,side);
    }

}
