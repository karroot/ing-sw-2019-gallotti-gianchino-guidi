import it.polimi.deib.se2018.adrenalina.Model.GameBoard;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.fail;

public class TestFactory
{
    private GameBoard board;

    /*
        Test if the arena with a code correct is created
     */
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
