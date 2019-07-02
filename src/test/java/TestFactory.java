import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import it.polimi.deib.se2018.adrenalina.Model.graph.*;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Stack;

public class TestFactory
{
    private GameBoard board;

    @Test
    public void TestcreateArena()
    {
        board = new GameBoard(new Stack<>(),new Stack<>(),1,5,new Stack<>());

        board = new GameBoard(new Stack<>(),new Stack<>(),2,5,new Stack<>());

        board = new GameBoard(new Stack<>(),new Stack<>(),3,5,new Stack<>());

        board = new GameBoard(new Stack<>(),new Stack<>(),4,5,new Stack<>());

    }

    /*
    This method tests if the creation with a illegal code for the arena launches an exception
     */
    @Test
    public void TestcreateArenaFail()
    {
        try
        {
            board = new GameBoard(new Stack<>(),new Stack<>(),100,5,new Stack<>());
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }



    }



}
