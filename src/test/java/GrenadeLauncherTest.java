import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.Furnace;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.GrenadeLauncher;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.Assert.*;

/**
 * @author giovanni
 */
public class GrenadeLauncherTest {
    /*

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
        start = board.getArena().getSquare(2,1);
        p1.setSquare(start);
        p2.setSquare(start);

        MethodsWeapons.moveTarget(p2,4,1);

        p1.addWeapon(grenadeLauncher);
        grenadeLauncher.setPlayer(p1);

    }

    @Test
    public void checkAvailableMode() {
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
    public void checkBasicMode() {
        MethodsWeapons.moveTarget(p2,3,1);
        p3.setSquare(start);
        MethodsWeapons.moveTarget(p3,3,2);
        p4.setSquare(start);
        MethodsWeapons.moveTarget(p4,2,2);

        List<Player> playerList = new ArrayList<>();
        playerList.add(p2);
        playerList.add(p3);
        playerList.add(p4);

        assertEquals(playerList, grenadeLauncher.checkBasicMode());


    }

    @Test
    public void basicMode() {
        MethodsWeapons.moveTarget(p2,3,1);
        p3.setSquare(start);
        MethodsWeapons.moveTarget(p3,4,1);

        Square square = p3.getSquare();

        grenadeLauncher.basicMode(p2, 4,1);

        assertEquals(1,p2.getNumberOfDamagePoint());
        assertEquals(square,p2.getSquare());
    }

    @Test
    public void checkExtraGrenade() {
    }

    @Test
    public void extraGrenade() {
    }

     */
}