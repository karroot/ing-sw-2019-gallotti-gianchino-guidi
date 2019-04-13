package it.polimi.deib.se2018.adrenalina.Model.graph.exceptions;

public class SquareNotInGameBoard extends Exception
{
    public SquareNotInGameBoard()
    {
        super();
    }

    public SquareNotInGameBoard(String message)
    {
        super(message);
    }
}
