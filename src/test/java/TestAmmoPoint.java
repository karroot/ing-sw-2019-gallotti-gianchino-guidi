import it.polimi.deib.se2018.adrenalina.Model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import static it.polimi.deib.se2018.adrenalina.Model.Color.*;
import static org.junit.Assert.assertEquals;

/**
 * This class is a case test for the AmmoPoint.
 *
 * @author giovanni
 */




public class TestAmmoPoint {

    AmmoPoint ammoPointTest;
    GameBoard gameboard;
    Stack<AmmoTiles> ammoTilesStack;
    AmmoTiles ammoTiles1;
    AmmoTiles ammoTiles2;
    AmmoTiles ammoTiles3;

    Player player;





    @Before
    public void setup()
    {
        gameboard = new GameBoard(null,null,1,0,null);
        ammoTilesStack = new Stack<>();
        ammoTiles1 = new JustAmmo(1, RED, BLUE);
        ammoTiles1 = new JustAmmo(2, YELLOW, BLUE);
        ammoTiles1 = new JustAmmo(3, RED, YELLOW);

        player = new Player(ColorId.GREY, null, null, true);

        SideType[] sideType = new SideType[4];
        sideType[0] = SideType.DOOR;
        sideType[1] = SideType.OPEN;
        sideType[2] = SideType.LIMIT;
        sideType[3] = SideType.LIMIT;


        ammoPointTest = new AmmoPoint(1,1, gameboard, ColorRoom.WHITE,sideType);
    }


    @Test
    public void testCreation ()
    {
        List<Room> roomList = new LinkedList<>();
        roomList = gameboard.getRoomList();
        Room room;
        Square square = null;
        int x = 1;
        int y = 1;

        for (Room roomIterate:gameboard.getRoomList()) //i will search for the square of the graph with x and y fixed
        {
            for (Square squareIterate: roomIterate.getSquareList())
            {
                if (squareIterate.getX() == x && squareIterate.getY() == y)
                {
                    square=squareIterate;
                }

            }
        }

        assertEquals(square.getX(), ammoPointTest.getX());
        assertEquals(square.getY(), ammoPointTest.getY());
        assertEquals(square.getColor(), ammoPointTest.getColor());
        assertEquals(square.getSide(), ammoPointTest.getSide());
    }


    @Test
    public void testReplaceAmmoTiles ()
    {
        ammoTilesStack.add(ammoTiles1);
        ammoTilesStack.add(ammoTiles2);
        ammoTilesStack.add(ammoTiles3);

        gameboard.setAmmoTilesStack(ammoTilesStack);

        AmmoTiles ammoTiles = gameboard.getAmmoTilesStack().peek();
        ammoPointTest.replaceAmmoTiles();

        assertEquals(ammoTiles, ammoPointTest.getAmmoTiles());

    }
/*
    @Test
    public void testUseAmmoTiles ()
    {
        AmmoTiles ammoTilesTemp;

        ammoPointTest.setAmmoTiles(ammoTiles1);

        ammoTilesTemp = ammoPointTest.useAmmoTiles(player);

        assertEquals(ammoTilesTemp, ammoTiles1);
        assertEquals(null, ammoPointTest.getAmmoTiles());




    }*/


}
