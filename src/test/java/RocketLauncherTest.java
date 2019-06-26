import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.GrenadeLauncher;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.RocketLauncher;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;

/**
 * @author giovanni
 */
public class RocketLauncherTest
{

    GameBoard board = new GameBoard(new Stack<>(),new Stack<>(),1,8,new Stack<>());
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

    }

    @Test
    public void checkBasicMode() {
    }

    @Test
    public void basicMode() {
    }

    @Test
    public void checkWithRocketJump() {
    }

    @Test
    public void checkWithFragmentingWarhead() {
    }

    @Test
    public void checkPhaseGlide() {
    }
}