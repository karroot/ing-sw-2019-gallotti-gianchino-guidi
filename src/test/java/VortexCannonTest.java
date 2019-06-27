import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.Furnace;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.VortexCannon;
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
public class VortexCannonTest
{

    GameBoard board = new GameBoard(new Stack<>(),new Stack<>(),1,8,new Stack<>());
    Player p1 = new Player(ColorId.YELLOW,"caso","ciao",true);;
    Player p2 = new Player(ColorId.GREY,"caso","ciao",false);;
    Player p3 = new Player(ColorId.GREEN,"caso","ciao",false);;
    Player p4 = new Player(ColorId.PURPLE,"caso","ciao",false);;
    Player p5 = new Player(ColorId.BLUE,"caso","ciao",false);
    VortexCannon vortexCannon = new VortexCannon(Color.RED,0,true);
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




        MethodsWeapons.moveTarget(p1,1,1);
        MethodsWeapons.moveTarget(p2,2,3);

        p1.addWeapon(vortexCannon);
        vortexCannon.setPlayer(p1);
    }

    @Test
    public void checkAvailableMode()
    {
        assertFalse(vortexCannon.checkAvailableMode()[0]);
        assertFalse(vortexCannon.checkAvailableMode()[1]);

        p3.setSquare(start);

        MethodsWeapons.moveTarget(p3,1,2);

        assertTrue(vortexCannon.checkAvailableMode()[0]);

        p1.setAmmoRed(0);
        assertFalse(vortexCannon.checkAvailableMode()[1]);

        p1.setAmmoRed(3);
        assertTrue(vortexCannon.checkAvailableMode()[1]);

    }

    @Test
    public void checkBasicMode()
    {
        HashMap<String, List<ColorId>> hashMap = new HashMap<>();

        p3.setSquare(start);

        MethodsWeapons.moveTarget(p2,1,2);

        hashMap = vortexCannon.checkBasicMode();

        assertTrue(hashMap.keySet().contains(p2.getSquare().toStringCoordinates()));
//        assertTrue(hashMap.get(p2.getSquare().toStringCoordinates()).contains(p2.getColor()));

        MethodsWeapons.moveTarget(p3,2,2);

//        assertEquals(2, hashMap.get(p2.getSquare().toStringCoordinates()).size());


    }

    @Test
    public void basicMode()
    {
        p3.setSquare(start);

        MethodsWeapons.moveTarget(p2,2,1);

        vortexCannon. basicMode(p2.getColor(), "x = 3, y = 1");

        assertEquals(3,p2.getSquare().getX());
        assertEquals(1, p2.getSquare().getY());
        assertEquals(2, p2.getNumberOfDamagePoint());
    }


    @Test
    public void checkWithBlackHoleMode()
    {
        List<ColorId> colorIdList = new ArrayList<>();

        p3.setSquare(start);

        MethodsWeapons.moveTarget(p2,2,1);
        MethodsWeapons.moveTarget(p3,4,1);

        colorIdList = vortexCannon.checkWithBlackHoleMode(p2.getColor(), "x = 3, y = 1");

        assertEquals(1, colorIdList.size());
        assertTrue(colorIdList.contains(p3.getColor()));


    }

    @Test
    public void blackHoleMode()
    {
        p3.setSquare(start);
        p4.setSquare(start);
        MethodsWeapons.moveTarget(p2,2,1);
        MethodsWeapons.moveTarget(p3,4,1);
        MethodsWeapons.moveTarget(p4, 2, 1);

        vortexCannon.blackHoleMode(p2.getColor(), p3.getColor(), "x = 3, y = 1");

        vortexCannon.setLoaded(true);
        p1.setAmmoRed(1);

        vortexCannon.blackHoleMode(p4.getColor(), null, "x = 3, y = 1");

        assertEquals(3,p2.getSquare().getX());
        assertEquals(1, p2.getSquare().getY());

        assertEquals(3,p3.getSquare().getX());
        assertEquals(1, p3.getSquare().getY());

        assertEquals(1, p2.getNumberOfDamagePoint());

        assertEquals(3,p4.getSquare().getX());
        assertEquals(1, p4.getSquare().getY());
    }
}