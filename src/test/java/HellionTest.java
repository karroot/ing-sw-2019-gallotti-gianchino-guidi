import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.GrenadeLauncher;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.Hellion;
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
public class HellionTest {


    GameBoard board = new GameBoard(new Stack<>(),new Stack<>(),1,8,new Stack<>());
    Player p1 = new Player(ColorId.YELLOW,"caso","ciao",true);;
    Player p2 = new Player(ColorId.GREY,"caso","ciao",false);;
    Player p3 = new Player(ColorId.GREEN,"caso","ciao",false);;
    Player p4 = new Player(ColorId.PURPLE,"caso","ciao",false);;
    Player p5 = new Player(ColorId.BLUE,"caso","ciao",false);
    Hellion hellion = new Hellion(Color.RED,0,true);
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

        p1.addWeapon(hellion);
        hellion.setPlayer(p1);

    }
    @Test
    public void checkAvailableMode() {
        p2.setSquare(start);

        assertFalse(hellion.checkAvailableMode()[0]);
        assertFalse(hellion.checkAvailableMode()[1]);

        MethodsWeapons.moveTarget(p2,3,1);

        assertTrue(hellion.checkAvailableMode()[0]);
        assertTrue(hellion.checkAvailableMode()[1]);

    }

    @Test
    public void checkBasicMode() {
        String square1, square2;

        List<ColorId> playerList1 = new ArrayList<>();
        List<ColorId> playerList2 = new ArrayList<>();

        p2.setSquare(start);
        p3.setSquare(start);
        p4.setSquare(start);
        p5.setSquare(start);

        MethodsWeapons.moveTarget(p2,3,1);
        MethodsWeapons.moveTarget(p3,3,1);
        MethodsWeapons.moveTarget(p4,1,3);
        MethodsWeapons.moveTarget(p5,1,3);

        square1 = p2.getSquare().toStringCoordinates();
        square2 = p4.getSquare().toStringCoordinates();

        playerList1.add(p2.getColor());
        playerList1.add(p3.getColor());
        playerList2.add(p4.getColor());
        playerList2.add(p5.getColor());

        HashMap<String, List<ColorId>> hash = hellion.checkBasicMode();

        assertEquals(playerList1, hellion.checkBasicMode().get(square1));
        assertEquals(playerList2, hellion.checkBasicMode().get(square2));
        assertEquals(playerList1, hellion.checkNanoTracerMode().get(square1));

    }

    @Test
    public void basicMode() {
        Square square1, square2;

        List<Player> playerList1 = new ArrayList<>();
        List<Player> playerList2 = new ArrayList<>();

        p2.setSquare(start);
        p3.setSquare(start);
        p4.setSquare(start);
        p5.setSquare(start);

        MethodsWeapons.moveTarget(p2,3,1);
        MethodsWeapons.moveTarget(p3,3,1);
        MethodsWeapons.moveTarget(p4,1,3);
        MethodsWeapons.moveTarget(p5,1,3);

        square1 = p2.getSquare();
        square2 = p4.getSquare();

        playerList1.add(p2);
        playerList1.add(p3);
        playerList2.add(p4);
        playerList2.add(p5);

        hellion.basicMode(p2.getColor());
        assertEquals(1, p2.getNumberOfDamagePoint());
        assertEquals(0, p3.getNumberOfDamagePoint());
        assertEquals(1, p2.checkMarker(p1.getColor()));
        assertEquals(1, p3.checkMarker(p1.getColor()));




        hellion.setLoaded(true);
        hellion.basicMode(p2.getColor()); //si resetta mark??!
        assertEquals(1, p2.checkMarker(p1.getColor()));
        assertEquals(3, p2.getNumberOfDamagePoint());


    }

    @Test
    public void checkNanoTracerMode() {
        checkBasicMode();
    }

    @Test
    public void nanoTracerMode() {
        Square square1, square2;

        List<Player> playerList1 = new ArrayList<>();
        List<Player> playerList2 = new ArrayList<>();

        p2.setSquare(start);
        p3.setSquare(start);
        p4.setSquare(start);
        p5.setSquare(start);

        MethodsWeapons.moveTarget(p2,3,1);
        MethodsWeapons.moveTarget(p3,3,1);
        MethodsWeapons.moveTarget(p4,1,3);
        MethodsWeapons.moveTarget(p5,1,3);

        square1 = p2.getSquare();
        square2 = p4.getSquare();

        playerList1.add(p2);
        playerList1.add(p3);
        playerList2.add(p4);
        playerList2.add(p5);

        hellion.nanoTracerMode(p2.getColor());
        assertEquals(1, p2.getNumberOfDamagePoint());
        assertEquals(0, p3.getNumberOfDamagePoint());
        assertEquals(2, p2.checkMarker(p1.getColor()));
        assertEquals(2, p3.checkMarker(p1.getColor()));

    }

}