import it.polimi.deib.se2018.adrenalina.Model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class is a case test for the Room.
 *
 * @author gioguidi
 */


 public class TestRoom
 {


     private Room roomTest;
     private Player player1, player2, player3;

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

         player1 = new Player(ColorId.GREY, null, null, true);
         player2 = new Player(ColorId.PURPLE, null, null, false);
         player3 = new Player(ColorId.BLUE, null, null, false);

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

         player1.setSquare(roomTest.getSquareList().get(0));
         roomTest.getSquareList().get(0).addPlayer(player1);

         roomTest.updatePlayerRoomList();

         assertEquals(1, roomTest.getPlayerRoomList().size());
         assertTrue(roomTest.getPlayerRoomList().contains(player1));

         player2.setSquare(roomTest.getSquareList().get(1));
         player3.setSquare(roomTest.getSquareList().get(2));

         roomTest.getSquareList().get(1).addPlayer(player2);
         roomTest.getSquareList().get(2).addPlayer(player3);

         roomTest.updatePlayerRoomList();

         assertEquals(3, roomTest.getPlayerRoomList().size());

     }

}
