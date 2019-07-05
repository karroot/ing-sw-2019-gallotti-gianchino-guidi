import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.CardinalDirection;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.Flamethrower;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import static org.junit.Assert.*;


/**
 * This class is a case test for the Flamethrower.
 *
 * @author gioguidi
 */


public class FlamethrowerTest
{

    GameBoard board = new GameBoard(new Stack<>(),new Stack<>(),1,8,new Stack<>());
    Player p1 = new Player(ColorId.YELLOW,"caso","ciao",true);;
    private Player p2 = new Player(ColorId.GREY,"caso","ciao",false);;
    private Player p3 = new Player(ColorId.GREEN,"caso","ciao",false);;
    private Player p4 = new Player(ColorId.PURPLE,"caso","ciao",false);;
    private Player p5 = new Player(ColorId.BLUE,"caso","ciao",false);
    private Flamethrower flamethrower = new Flamethrower(Color.RED,0,true);
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

        MethodsWeapons.moveTarget(p2,4,1);

        p1.addWeapon(flamethrower);
        flamethrower.setPlayer(p1);
    }

    @Test
    public void checkAvailableMode()
    {
        assertFalse(flamethrower.checkAvailableMode()[0]);
        assertFalse(flamethrower.checkAvailableMode()[1]);

        MethodsWeapons.moveTarget(p2,3,1);

        assertFalse(flamethrower.checkAvailableMode()[0]);
        assertFalse(flamethrower.checkAvailableMode()[1]);

        MethodsWeapons.moveTarget(p2,2,1);

        assertTrue(flamethrower.checkAvailableMode()[0]);
        assertFalse(flamethrower.checkAvailableMode()[1]);

        p1.setAmmoYellow(2);

        assertTrue(flamethrower.checkAvailableMode()[1]);
    }

    @Test
    public void checkBasicMode()
    {

        HashMap<CardinalDirection, ArrayList<ColorId>[]> hashMap = new HashMap<>();

        MethodsWeapons.moveTarget(p2,2,1);

        p3.setSquare(start);
        p4.setSquare(start);
        p5.setSquare(start);
        MethodsWeapons.moveTarget(p3,1,2);
        MethodsWeapons.moveTarget(p4,1,3);
        MethodsWeapons.moveTarget(p5,2,2);

        List<ColorId> colorIdList = new ArrayList<>();
        colorIdList.add(p3.getColor());


        hashMap = flamethrower.checkBasicModeForMessage();

        assertEquals(colorIdList, hashMap.get(CardinalDirection.NORTH)[0]);


        MethodsWeapons.moveTarget(p1,2,3);
        MethodsWeapons.moveTarget(p2,2,2);
        MethodsWeapons.moveTarget(p3,2,1);
        MethodsWeapons.moveTarget(p4,1,3);
        MethodsWeapons.moveTarget(p5,1,3);

        hashMap.clear();

        hashMap = flamethrower.checkBasicModeForMessage();

        assertTrue(hashMap.keySet().contains(CardinalDirection.SOUTH));
        assertTrue(hashMap.keySet().contains(CardinalDirection.WEST));


    }

    @Test
    public void basicMode()
    {
        p3.setSquare(start);
        MethodsWeapons.moveTarget(p2,2,1);
        MethodsWeapons.moveTarget(p3,2,1);


        flamethrower.basicMode(p2.getColor(), p3.getColor());

        assertEquals(1, p2.getNumberOfDamagePoint());

    }

    @Test
    public void checkBarbecueMode()
    {
        HashMap<CardinalDirection, ArrayList<ColorId>[]> hashMap = new HashMap<>();

        MethodsWeapons.moveTarget(p2,2,1);

        p3.setSquare(start);
        p4.setSquare(start);
        p5.setSquare(start);
        MethodsWeapons.moveTarget(p3,1,2);
        MethodsWeapons.moveTarget(p4,1,3);
        MethodsWeapons.moveTarget(p5,2,2);

        List<ColorId> colorIdList = new ArrayList<>();
        colorIdList.add(p4.getColor());

        p1.setAmmoYellow(3);

        hashMap = flamethrower.checkBarbecueMode();

        assertEquals(colorIdList, hashMap.get(CardinalDirection.NORTH)[1]);

    }

    @Test
    public void barbecueMode()
    {
        HashMap<CardinalDirection, ArrayList<ColorId>[]> hashMap = new HashMap<>();

        MethodsWeapons.moveTarget(p2,2,1);

        p3.setSquare(start);
        p4.setSquare(start);
        p5.setSquare(start);
        MethodsWeapons.moveTarget(p3,1,2);
        MethodsWeapons.moveTarget(p4,1,3);
        MethodsWeapons.moveTarget(p5,2,2);

        p1.setAmmoYellow(3);

        flamethrower.barbecueMode(CardinalDirection.NORTH, p3.getColor());

        assertEquals(2, p3.getNumberOfDamagePoint());
        assertEquals(1, p4.getNumberOfDamagePoint());


    }
}