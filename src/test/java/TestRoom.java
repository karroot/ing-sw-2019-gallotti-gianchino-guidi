import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import it.polimi.deib.se2018.adrenalina.Model.graph.*;

import java.util.LinkedList;
import java.util.List;

/**
 * This class is a case test for the Room.
 *
 * @author giovanni
 */


 public class TestRoom
 {


     Room roomTest;
     Player player1, player2, player3;
     Square square1, square2, square3;

     int x1 = 1, x2 = 2, x3 = 3, y1 = 1, y2 = 1, y3 = 1;

     @Before
     public void setup ()
     {
         GameBoard gameboard = new GameBoard(null,null,1,0,null);

         for (Room roomIterate:gameboard.getRoomList())
         {
             if (roomIterate.getColor() == ColorRoom.WHITE) //I get the room with the color WHITE
             {
                 roomTest=roomIterate;
                 break;

             }
         }

     }


     @Test
     public void testCreation ()
     {
         assertEquals(3, roomTest.getSquareList().size());
         assertEquals(ColorRoom.WHITE, roomTest.getColor());
     }


     @Test
     public void testUpdatePlayerRoomList()
     {

         assertTrue(roomTest.getPlayerRoomList().isEmpty());

//todo errore
         /*for (Square squareIterate: roomTest.getSquareList())
         {
             if (squareIterate.getX() == x1 && squareIterate.getY() == y1)
             {
                 player1.setSquare(squareIterate);
             }
             if (squareIterate.getX() == x2 && squareIterate.getY() == y2)
             {
                 player2.setSquare(squareIterate);
             }
             if (squareIterate.getX() == x3 && squareIterate.getY() == y3)
             {
                 player3.setSquare(squareIterate);
             }
         }



         assertEquals(1, roomTest.getPlayerRoomList().size());
         assertTrue(roomTest.getPlayerRoomList().contains(player1));

         player2.setSquare(square2);
         player3.setSquare(square3);
         assertEquals(3, roomTest.getPlayerRoomList().size());

*/

     }



}
