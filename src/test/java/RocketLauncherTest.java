import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.RocketLauncher;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import static org.junit.Assert.*;

/**
 * @author giovanni
 */
public class RocketLauncherTest
{

    GameBoard board = new GameBoard(new Stack<>(),new Stack<>(),4,8,new Stack<>());
    Player p1 = new Player(ColorId.YELLOW,"caso","ciao",true);;
    Player p2 = new Player(ColorId.GREY,"caso","ciao",false);;
    Player p3 = new Player(ColorId.GREEN,"caso","ciao",false);;
    Player p4 = new Player(ColorId.PURPLE,"caso","ciao",false);;
    Player p5 = new Player(ColorId.BLUE,"caso","ciao",false);
    RocketLauncher rocketLauncher = new RocketLauncher(Color.RED,0,true);
    Square start;

    @Before
    public void setUp() throws Exception
    {
        start = board.getArena().getSquare(1,1);

        board.setAllPlayer(p1);
        board.setAllPlayer(p2);
        board.setAllPlayer(p3);
        board.setAllPlayer(p4);
        board.setAllPlayer(p5);

        p1.setSquare(start);
        p2.setSquare(start);

        MethodsWeapons.moveTarget(p2,2,1);

        p1.addWeapon(rocketLauncher);
        rocketLauncher.setPlayer(p1);

    }

    @Test
    public void checkAvailableMode()
    {
        assertTrue(rocketLauncher.checkAvailableMode()[0]);
        assertTrue(rocketLauncher.checkAvailableMode()[1]);
        assertTrue(rocketLauncher.checkAvailableMode()[2]);


    }

    @Test
    public void checkSquaresToMoveTest()
    {
        p3.setSquare(start);

        MethodsWeapons.moveTarget(p1,1,2);
        MethodsWeapons.moveTarget(p2,2,1);
        MethodsWeapons.moveTarget(p3,1,3);

        HashMap<String, List<ColorId>> hashMap = rocketLauncher.checkRocketJumpColors();

        List<String> stringList = new ArrayList<>();

        stringList.addAll(hashMap.keySet());

        assertEquals(2, stringList.size());



    }

    @Test
    public void allSquaresNoMove ()
    {
        List<String> stringList = new ArrayList<>();

        MethodsWeapons.moveTarget(p1,1,2);

        stringList.addAll(rocketLauncher.allSquaresNoMove());

        assertEquals(5, stringList.size());
    }

    @Test
    public void checkWithFragmentingWarhead ()
    {
        p3.setSquare(start);

        MethodsWeapons.moveTarget(p1,1,2);
        MethodsWeapons.moveTarget(p2,1,1);
        MethodsWeapons.moveTarget(p3,2,1);

        assertFalse(rocketLauncher.checkWithFragmentingWarhead());
        assertFalse(rocketLauncher.checkWithFragmentingWarheadRocketJump());

        MethodsWeapons.moveTarget(p3,1,1);

        assertTrue(rocketLauncher.checkWithFragmentingWarhead());

        MethodsWeapons.moveTarget(p2,2,2);
        MethodsWeapons.moveTarget(p3,2,2);

        assertTrue(rocketLauncher.checkWithFragmentingWarheadRocketJump());
    }

    @Test
    public void checkPlayersBasicMode ()
    {
        p3.setSquare(start);

        MethodsWeapons.moveTarget(p1,1,2);
        MethodsWeapons.moveTarget(p2,1,1);
        MethodsWeapons.moveTarget(p3,2,1);

        List<ColorId> colorIdList = rocketLauncher.checkPlayersBasicMode();

        assertEquals(2, colorIdList.size());
    }

    @Test
    public void basicMode ()
    {
        p3.setSquare(start);

        MethodsWeapons.moveTarget(p1,1,2);
        MethodsWeapons.moveTarget(p2,2,1);
        MethodsWeapons.moveTarget(p3,2,1);

        rocketLauncher.basicMode(p3.getColor(),start.toStringCoordinates(),start.toStringCoordinates(), true);

        assertEquals(1, p2.getNumberOfDamagePoint());
        assertEquals(3, p3.getNumberOfDamagePoint());

        assertEquals(start, p3.getSquare());
        assertEquals(start, p1.getSquare());


    }

}