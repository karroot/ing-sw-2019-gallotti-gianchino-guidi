import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import it.polimi.deib.se2018.adrenalina.Model.graph.*;

import java.util.List;

/**
 * This class is a case test for the Room.
 *
 * @author giovanni
 */


 public class TestRoom
 {

     //inizualizzo stack vuoto e passo

     Room roomTest;
     GameBoard gameboard = new GameBoard(null,null,1,0,null);
     Player player;

     @Before
//come creo la board?
     public void setup ()
     {
         roomTest = gameboard.getRoomList().get(0);
         player = newroomTest.getSquareList().get(0)
     }

     @Test
     public void testCreation ()
     {
         assertEquals();
     }


     @Test
     public void testPlayeRoomlist ()
     {

     }



}
