import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.Flamethrower;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.GrenadeLauncher;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;


/**
 * @author giovanni
 */


public class FlamethrowerTest
{

    GameBoard board = new GameBoard(new Stack<>(),new Stack<>(),2,8,new Stack<>());
    Player p1 = new Player(ColorId.YELLOW,"caso","ciao",true);;
    Player p2 = new Player(ColorId.GREY,"caso","ciao",false);;
    Player p3 = new Player(ColorId.GREEN,"caso","ciao",false);;
    Player p4 = new Player(ColorId.PURPLE,"caso","ciao",false);;
    Player p5 = new Player(ColorId.BLUE,"caso","ciao",false);
    Flamethrower flamethrower = new Flamethrower(Color.RED,0,true);
    Square start;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void checkAvailableMode() {
    }

    @Test
    public void checkBasicMode() {
    }

    @Test
    public void basicMode() {
    }

    @Test
    public void checkBarbecueMode() {
    }

    @Test
    public void barbecueMode() {
    }
}