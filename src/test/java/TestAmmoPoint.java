import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.PowerUpCard;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.Teleporter;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import static it.polimi.deib.se2018.adrenalina.Model.Color.*;
import static org.junit.Assert.*;

/**
 * This class is a case test for the AmmoPoint.
 *
 * @author gioguidi
 */

public class TestAmmoPoint
{

    private AmmoPoint ammoPointTest;
    GameBoard gameboard;
    private Stack<AmmoTiles> ammoTilesStack;

    private AmmoTiles ammoTiles1;
    private AmmoTiles ammoTiles2;
    private AmmoTiles ammoTiles3;
    private AmmoTiles ammoTiles4;

    Player player;



    @Before
    public void setup()
    {
        gameboard = new GameBoard(null,null,1,0,null);
        ammoTilesStack = new Stack<>();
        ammoTiles1 = new JustAmmo(1, RED, BLUE);
        ammoTiles2 = new JustAmmo(2, YELLOW, BLUE);
        ammoTiles3 = new JustAmmo(3, RED, YELLOW);
        ammoTiles4 = new PowerAndAmmo(4, BLUE, BLUE);


        player = new Player(ColorId.GREY, null, null, true);

        SideType[] sideType = new SideType[4];
        sideType[0] = SideType.DOOR;
        sideType[1] = SideType.OPEN;
        sideType[2] = SideType.LIMIT;
        sideType[3] = SideType.LIMIT;



        ammoPointTest = new AmmoPoint(1,1, gameboard, ColorRoom.WHITE,sideType);
        ammoPointTest.setX(1);
        ammoPointTest.setY(1);
        player.setSquare(ammoPointTest);

    }


    @Test
    public void testCreation () throws Exception
    {
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

        assert square != null;

        assertEquals(square.getX(), ammoPointTest.getX());
        assertEquals(square.getY(), ammoPointTest.getY());
        assertEquals(square.getColor(), ammoPointTest.getColor());
    }



    @Test
    public void useAmmoTiles()
    {
        player.setAmmoRed(0);
        player.setAmmoYellow(0);
        player.setAmmoBlue(0);

        ammoPointTest.setAmmoTiles(ammoTiles1);
        ammoPointTest.useAmmoTiles(player);


        assertNull(ammoPointTest.getAmmoTiles());
        assertEquals(1, player.getAmmoRed());
        assertEquals(2, player.getAmmoBlue());

        Stack<PowerUpCard> powerUpCardStack = new Stack<>();
        PowerUpCard powerup1 = new Teleporter(BLUE, 22);

        powerUpCardStack.add(powerup1);

        player.getSquare().getGameBoard().setPowerUpCardStack(powerUpCardStack);

        ammoPointTest.setAmmoTiles(ammoTiles4);
        ammoPointTest.useAmmoTiles(player);

        assertEquals(1, player.getAmmoRed());
        assertEquals(3, player.getAmmoBlue());
        assertEquals(0, player.getAmmoYellow());
        assertEquals(22, player.getPowerupCardList().get(0).getIdPU());

        player.usePowerUp(0);

        assertEquals(1, player.getSquare().getGameBoard().getPowerUpCardDiscardStack().size());

        AmmoTiles ammoTiles5 = new PowerAndAmmo(64, RED, YELLOW);

        ammoPointTest.setAmmoTiles(ammoTiles5);
        ammoPointTest.useAmmoTiles(player);

        assertTrue(player.getSquare().getGameBoard().getPowerUpCardStack().isEmpty());

        AmmoTiles ammoTiles6 = new PowerAndAmmo(55, YELLOW, RED);

        ammoPointTest.setAmmoTiles(ammoTiles5);
        ammoPointTest.useAmmoTiles(player);

        assertEquals(2, player.getAmmoYellow());

        AmmoTiles ammoTiles7 = new JustAmmo(45, RED, YELLOW);
        AmmoTiles ammoTiles8 = new JustAmmo(25, YELLOW, RED);

        ammoPointTest.setAmmoTiles(ammoTiles7);
        ammoPointTest.useAmmoTiles(player);

        ammoPointTest.setAmmoTiles(ammoTiles8);
        ammoPointTest.useAmmoTiles(player);

       assertEquals(3, player.getAmmoRed());

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

        ammoPointTest.replaceAmmoTiles();
        ammoPointTest.replaceAmmoTiles();

        ammoPointTest.useAmmoTiles(player);

        assertEquals(1, player.getSquare().getGameBoard().getAmmoTilesDiscardStack().size());
        assertTrue(player.getSquare().getGameBoard().getAmmoTilesStack().isEmpty());

        ammoPointTest.replaceAmmoTiles();

        assertEquals(0, player.getSquare().getGameBoard().getAmmoTilesDiscardStack().size());

    }

}
