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
         GameBoard gameboard = new GameBoard(null,null,1,0,null, null);

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

//todo errore in pratica bisognerebbe aggiornare la lista dei player a mano! Si può fare in automatico?
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

         //getter di lista player?
         //assertTrue(roomTest.getPlayerRoomList().containsAll(roomTest.getSquareList().));



     }



}
