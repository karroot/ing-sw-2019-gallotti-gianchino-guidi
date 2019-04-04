package it.polimi.deib.se2018.adrenalina.Model;

import java.util.List;

public abstract class Square {

    public Square() {
    }

    protected ColorId color;

    protected SideType[] side;

    protected int idSquare;

    private List<Player> playerList;

    private GameBoard gameBoard;


}