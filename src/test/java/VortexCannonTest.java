import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.Furnace;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.VortexCannon;
import org.junit.Before;
import org.junit.Test;

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

    }

    @Test
    public void basicMode() {
    }

    @Test
    public void checkWithBlackHoleMode() {
    }
}