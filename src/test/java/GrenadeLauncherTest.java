import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.Furnace;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.GrenadeLauncher;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
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
public class GrenadeLauncherTest {

    GameBoard board = new GameBoard(new Stack<>(),new Stack<>(),2,8,new Stack<>());
    Player p1 = new Player(ColorId.YELLOW,"caso","ciao",true);;
    Player p2 = new Player(ColorId.GREY,"caso","ciao",false);;
    Player p3 = new Player(ColorId.GREEN,"caso","ciao",false);;
    Player p4 = new Player(ColorId.PURPLE,"caso","ciao",false);;
    Player p5 = new Player(ColorId.BLUE,"caso","ciao",false);
    GrenadeLauncher grenadeLauncher = new GrenadeLauncher(Color.RED,0,true);
    Square start;

    @Before
    public void setUp() throws Exception
    {
        start = board.getArena().getSquare(2,1);

        board.setAllPlayer(p1);
        board.setAllPlayer(p2);
        board.setAllPlayer(p3);
        board.setAllPlayer(p4);
        board.setAllPlayer(p5);

        p1.setSquare(start);
        p2.setSquare(start);

        MethodsWeapons.moveTarget(p2,4,1);

        p1.addWeapon(grenadeLauncher);
        grenadeLauncher.setPlayer(p1);

    }

    @Test
    public void checkAvailableMode()
    {
        assertFalse(grenadeLauncher.checkAvailableMode()[0]);
        assertFalse(grenadeLauncher.checkAvailableMode()[1]);

        p3.setSquare(start);

        MethodsWeapons.moveTarget(p3,3,2);

        assertTrue(grenadeLauncher.checkAvailableMode()[0]);

        p1.setAmmoRed(0);
        assertFalse(grenadeLauncher.checkAvailableMode()[1]);

        p1.setAmmoRed(3);
        assertTrue(grenadeLauncher.checkAvailableMode()[1]);

    }

    @Test
    public void checkBasicMode()
    {
        MethodsWeapons.moveTarget(p2,3,1);
        p3.setSquare(start);
        MethodsWeapons.moveTarget(p3,3,2);
        p4.setSquare(start);
        MethodsWeapons.moveTarget(p4,2,2);

        List<ColorId> colorIdList = new ArrayList<>();
        colorIdList.add(p2.getColor());
        colorIdList.add(p3.getColor());
        colorIdList.add(p4.getColor());




     assertEquals(colorIdList.size(), grenadeLauncher.checkBasicMode().size());


    }

    @Test
    public void basicMode() {
        MethodsWeapons.moveTarget(p2,3,1);
        p3.setSquare(start);
        MethodsWeapons.moveTarget(p3,4,1);

        Square square = p3.getSquare();

        grenadeLauncher.basicMode(p2.getColor(), "x = 4, y = 1");

        assertEquals(1,p2.getNumberOfDamagePoint());
        assertEquals(square,p2.getSquare());
    }

    @Test
    public void checkExtraGrenade()
    {
        MethodsWeapons.moveTarget(p2,3,1);
        p3.setSquare(start);
        MethodsWeapons.moveTarget(p3,4,1);

        List<String> stringList = grenadeLauncher.checkExtraGrenade();

        assertTrue(stringList.contains(p2.getSquare().toStringCoordinates()));

    }

    @Test
    public void extraGrenade()
    {
        MethodsWeapons.moveTarget(p2,3,1);
        p3.setSquare(start);
        MethodsWeapons.moveTarget(p3,4,1);

        grenadeLauncher.extraGrenade(p2.getSquare().toStringCoordinates());

        assertEquals(1, p2.getNumberOfDamagePoint());

        assertFalse(grenadeLauncher.isLoaded());


    }

    @Test
    public void checkBasicModeSquares()
    {
        MethodsWeapons.moveTarget(p1,2,1);
        MethodsWeapons.moveTarget(p2,3,1);

       HashMap<ColorId, String> colorIdStringHashMap = grenadeLauncher.checkBasicMode();
       HashMap<ColorId, List<String>> colorIdListHashMap = grenadeLauncher.checkBasicModeSquares();

       List<String> stringList = new ArrayList<>();
       stringList.addAll(colorIdListHashMap.get(p2.getColor()));

       assertEquals(3, stringList.size());
    }

    @Test
    public void checkAllSquaresISee()
    {
        List<String> stringList = new ArrayList<>();

        MethodsWeapons.moveTarget(p1,2,1);

        stringList = grenadeLauncher.checkAllSquaresISee();

        assertEquals(5, stringList.size());

    }


}