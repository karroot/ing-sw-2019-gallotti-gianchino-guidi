import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.Furnace;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.HeatSeeker;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import java.util.Stack;

import static org.junit.Assert.*;

/**
 * @author giovanni
 */
public class HeatSeekerTest {

    GameBoard board = new GameBoard(new Stack<>(),new Stack<>(),1,8,new Stack<>());
    Player p1 = new Player(ColorId.YELLOW,"caso","ciao",true);;
    Player p2 = new Player(ColorId.GREY,"caso","ciao",false);;
    Player p3 = new Player(ColorId.GREEN,"caso","ciao",false);;
    Player p4 = new Player(ColorId.PURPLE,"caso","ciao",false);;
    Player p5 = new Player(ColorId.BLUE,"caso","ciao",false);
    HeatSeeker heatSeeker = new HeatSeeker(Color.RED,0,true);
    Square start;

    @Before
    public void setUp() throws Exception {
        start = board.getArena().getSquare(1,1);
        board.setAllPlayer(p1);
        board.setAllPlayer(p2);
        board.setAllPlayer(p3);
        board.setAllPlayer(p4);
        board.setAllPlayer(p5);

        p1.setSquare(start);

        p1.addWeapon(heatSeeker);
        heatSeeker.setPlayer(p1);
    }

    @Test
    public void checkAvailableMode() {
        p2.setSquare(start);
        p3.setSquare(start);
        p4.setSquare(start);
        p5.setSquare(start);

        MethodsWeapons.moveTarget(p1,1,1);
        MethodsWeapons.moveTarget(p2,1,1);
        MethodsWeapons.moveTarget(p3,1,1);
        MethodsWeapons.moveTarget(p4,1,1);
        MethodsWeapons.moveTarget(p5,1,1);

        assertFalse(heatSeeker.checkAvailableMode()[0]);

        MethodsWeapons.moveTarget(p3,2,2);
        assertTrue(heatSeeker.checkAvailableMode()[0]);
    }

    @Test
    public void checkBasicMode() {
        p2.setSquare(start);
        p3.setSquare(start);
        p4.setSquare(start);
        p5.setSquare(start);

        boolean thrown = false;

        MethodsWeapons.moveTarget(p1,1,1);
        MethodsWeapons.moveTarget(p2,1,1);
        MethodsWeapons.moveTarget(p3,1,1);
        MethodsWeapons.moveTarget(p4,1,1);
        MethodsWeapons.moveTarget(p5,1,1);

        try {
            heatSeeker.checkBasicMode();
        } catch (IllegalStateException e) {
            thrown = true;
        }

        assertTrue(thrown);

        MethodsWeapons.moveTarget(p3,2,2);

        assertEquals(1,heatSeeker.checkBasicMode().size());
        assertTrue(heatSeeker.checkBasicMode().contains(p3.getColor()));

    }

    @Test
    public void basicMode() {
        p2.setSquare(start);
        p3.setSquare(start);

        MethodsWeapons.moveTarget(p1,1,1);
        MethodsWeapons.moveTarget(p2,2,2);

        p1.getSquare().getGameBoard().setAllPlayer(p1);
        p1.getSquare().getGameBoard().setAllPlayer(p2);

        heatSeeker.basicMode(p2.getColor());

        assertEquals(3,p2.getNumberOfDamagePoint());
        assertFalse(heatSeeker.isLoaded());

    }
}