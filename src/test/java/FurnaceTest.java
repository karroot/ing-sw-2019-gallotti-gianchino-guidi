import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.Furnace;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.ZX2;
import jdk.nashorn.internal.runtime.ECMAException;
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


public class FurnaceTest {
    GameBoard board = new GameBoard(new Stack<>(),new Stack<>(),1,8,new Stack<>());
    Player p1 = new Player(ColorId.YELLOW,"caso","ciao",true);;
    Player p2 = new Player(ColorId.GREY,"caso","ciao",false);;
    Player p3 = new Player(ColorId.GREEN,"caso","ciao",false);;
    Player p4 = new Player(ColorId.PURPLE,"caso","ciao",false);;
    Player p5 = new Player(ColorId.BLUE,"caso","ciao",false);
    Furnace furnace = new Furnace(Color.RED,0,true);
    Square start;


    @Before
    public void setUp() throws Exception
    {
      //  start = board.getArena().getSquare(1,1);
       // p1.setSquare(start);
   //     p2.setSquare(start);
      //  p3.setSquare(start);
        //p4.setSquare(start);
        //p5.setSquare(start);



        //MethodsWeapons.moveTarget(p1,3,3);
     //   MethodsWeapons.moveTarget(p2,2,2);
       // MethodsWeapons.moveTarget(p3,1,2);
        //MethodsWeapons.moveTarget(p4,2,3);
        //MethodsWeapons.moveTarget(p5,4,1);
        start = board.getArena().getSquare(1,1);
        p1.setSquare(start);
        p2.setSquare(start);

        MethodsWeapons.moveTarget(p2,2,2);

        p1.addWeapon(furnace);
        furnace.setPlayer(p1);

    }

    @Test
    public void checkAvailableMode() throws  Exception {
        assertFalse(furnace.checkAvailableMode()[0]);
        assertFalse(furnace.checkAvailableMode()[1]);



        MethodsWeapons.moveTarget(p2,1,1);
        assertTrue(!furnace.checkAvailableMode()[0] && !furnace.checkAvailableMode()[1]);

        MethodsWeapons.moveTarget(p2,1,2);
        assertTrue(furnace.checkAvailableMode()[0]);
        assertTrue(furnace.checkAvailableMode()[1]);

        MethodsWeapons.moveTarget(p2,2,1);
        assertFalse(furnace.checkAvailableMode()[0]);
        assertTrue(furnace.checkAvailableMode()[1]);

        p3.setSquare(start);
        MethodsWeapons.moveTarget(p1,2,2);
        MethodsWeapons.moveTarget(p3, 3, 2);

        assertTrue(furnace.checkAvailableMode()[0]);
        assertTrue(furnace.checkAvailableMode()[1]);

        MethodsWeapons.moveTarget(p1,1,1);
        MethodsWeapons.moveTarget(p2, 1, 3);
        MethodsWeapons.moveTarget(p3, 3, 3);
        assertFalse(furnace.checkAvailableMode()[1]);

        MethodsWeapons.moveTarget(p2, 1, 2);
        assertTrue(furnace.checkAvailableMode()[1]);



    }

    @Test
    public void checkBasicMode() throws Exception {
        p3.setSquare(start);
        p4.setSquare(start);
        p5.setSquare(start);

        HashMap<Room, ArrayList<Player>> roomArrayListHashMap = new HashMap<>();

        Room key;

        MethodsWeapons.moveTarget(p1,1,1);
        MethodsWeapons.moveTarget(p2,1,2);
        MethodsWeapons.moveTarget(p3,1,2);
        MethodsWeapons.moveTarget(p4,2,1);
        MethodsWeapons.moveTarget(p5,2,1);

        key = p4.getSquare().getRoom();

        roomArrayListHashMap = furnace.checkBasicMode();

        assertFalse(roomArrayListHashMap.containsKey(key));

        key = p3.getSquare().getRoom();
        assertEquals(2, roomArrayListHashMap.get(key).size());
        assertTrue(roomArrayListHashMap.get(key).contains(p2));
        assertTrue(roomArrayListHashMap.get(key).contains(p3));
        assertFalse(roomArrayListHashMap.get(key).contains(p4));






    }

    @Test
    public void basicMode() throws Exception {
        p3.setSquare(start);

        MethodsWeapons.moveTarget(p1,1,1);
        MethodsWeapons.moveTarget(p2,1,2);
        MethodsWeapons.moveTarget(p3,1,2);

        furnace.basicMode(p2.getSquare().getRoom());

        assertEquals(1,p2.getNumberOfDamagePoint());
        assertEquals(1,p3.getNumberOfDamagePoint());
    }

    @Test
    public void checkInCozyFireMode() throws Exception {
        HashMap<Square, ArrayList<Player>> hashSquarePlayer = new HashMap<Square, ArrayList<Player>>();
        Square key;

        List<Player> playerList = new ArrayList<>();

        p3.setSquare(start);
        p4.setSquare(start);
        p5.setSquare(start);



        MethodsWeapons.moveTarget(p1,1,1);
        MethodsWeapons.moveTarget(p2,1,2);
        MethodsWeapons.moveTarget(p3,1,2);
        MethodsWeapons.moveTarget(p4,2,1);
        MethodsWeapons.moveTarget(p5,2,1);

        key = p2.getSquare();
        playerList.add(p2);
        playerList.add(p3);

        hashSquarePlayer = furnace.checkInCozyFireMode();

        assertTrue(hashSquarePlayer.containsKey(key));
        assertEquals(hashSquarePlayer.get(key), playerList);

        key = p4.getSquare();

        assertTrue(hashSquarePlayer.containsKey(key));
        assertEquals(hashSquarePlayer.get(key).size(), 2);
    }

    @Test
    public void inCozyFireMode() throws Exception {
        p3.setSquare(start);
        p4.setSquare(start);
        p5.setSquare(start);


        MethodsWeapons.moveTarget(p1,1,1);
        MethodsWeapons.moveTarget(p2,1,2);
        MethodsWeapons.moveTarget(p3,1,2);
        MethodsWeapons.moveTarget(p4,1,2);
        MethodsWeapons.moveTarget(p5,2,2);

        furnace.inCozyFireMode(p2.getSquare());

        assertEquals(1,p2.getNumberOfDamagePoint());
        assertEquals(1,p3.getNumberOfDamagePoint());
        assertEquals(1,p4.getNumberOfDamagePoint());
        assertEquals(1,p2.checkMarker(p1.getColor()));
        assertEquals(1,p3.checkMarker(p1.getColor()));
        assertEquals(0,p5.getNumberOfDamagePoint());
        assertEquals(0,p5.checkMarker(p1.getColor()));

        assertFalse(furnace.isLoaded());

    }
}
